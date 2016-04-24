package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.beans.Auction;
import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.beans.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class NotifierService {
    private static Map<Session, HttpSession> clients = new ConcurrentHashMap<>();
    private static Map<HttpSession,User> userList = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor;
    private final ScheduledExecutorService computerUser;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy,MM,dd,HH,mm,ss,SSS");
    private Sortiment sortiment;
    private Random random = new Random();
    private User user;

    public NotifierService() {
        // Use the scheduled executor to regularly check for recently expired auctions
        // and send a notification to all relevant users.
        this.executor = Executors.newSingleThreadScheduledExecutor();
        //computeruser which bids all 10 seconds with an probability of 30% higher than the current bid on each auction
        this.computerUser = Executors.newSingleThreadScheduledExecutor();
        this.user = new User();
        this.user.setUsername("Computer");
    }

    /**
     * Precondition: sortiment != null
     * Postcondition: The methode sets the sortiment to the given parameter
     * @param sortiment Sortiment to be processed
     */
    public void setSortiment(Sortiment sortiment) {
        this.sortiment = sortiment;
    }

    /**
     * Postcondition: The methode creates a new Runnable to check all auctions if they are already expired. Every
     * second the Runnable is performed. If an Auction is expired winner and loser are informed and the Auction
     * is marked as expired so they wont be checked again
     */
    public void startAuctionEndWatcher() {
        final Runnable checkAuctions = new Runnable() {
            int i = 0;
            public void run() {
                for(Auction a : sortiment.getAuction()) {
                    //checks if auction is expired
                    if (LocalDateTime.now().isAfter(LocalDateTime.parse(a.getAblaufdatum(), format)) && !a.isExpired()) {
                        synchronized (a) {
                            User winner = a.getHoechstbietender();
                            synchronized (winner) {
                                a.removeUser(winner);
                                winner.removeAuction();
                                winner.addWon();
                            }
                            for (User loser : a.getTeilnehmer()) {
                                synchronized (loser) {
                                    loser.removeAuction();
                                    loser.addLost();
                                }
                            }
                            a.setExpired();
                        }
                        //message goes to all users
                        auctionEnd(a);
                    }
                }
            }
        };
        final ScheduledFuture<?> beeper = this.executor.scheduleAtFixedRate(checkAuctions, 1,1, TimeUnit.SECONDS);
    }

    /**
     * Postcondition: The methode creates a Runnable which runs all 10 seconds and checks each not expired auction. With a
     * probability of 30 percent, the current highest bid is overbid by 10 euros.
     */
    public void startComputerUser() {
        final Runnable computer = new Runnable() {
            public void run() {
                for(Auction auction : sortiment.getAuction()) {
                    int wahrscheinlichkeit = random.nextInt(101);
                    //probability of 30 percent
                    if(wahrscheinlichkeit <= 30 && !auction.isExpired()) {
                        synchronized (auction) {
                            User loser = auction.getHoechstbietender();
                            synchronized (loser) {
                                loser.changeMoney(auction.getHoechstgebot());
                                ueberboten(auction, loser, loser.getMoney());
                                auction.setHoechstbietender(user);
                                double neu = auction.getHoechstgebot() + 10;
                                auction.setHoechstgebot(neu);
                                gebotAbgegeben(auction, user, neu);
                            }
                        }
                    }
                }
            }
        };
        final ScheduledFuture<?> computerResult = this.computerUser.scheduleAtFixedRate(computer, 10,10, TimeUnit.SECONDS);
    }


    /**
     * This method is used by the WebSocket endpoint to save a reference to all
     * connected users. A list of connections is needed so that the users can be
     * notified about events like new bids and expired auctions (see
     * assignment). We need the socket session so that we can push data to the
     * client. We need the HTTP session to find out which user is currently
     * logged in in the browser that opened the socket connection.
     */
    public void register(Session socketSession, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            clients.put(socketSession, httpSession);
            userList.put(httpSession,user);
        }
    }

    /**
     * Precondition: a != null and a is expired
     * Postcondition: The methode sends a message about the end of the given acount and the current state of each user
     * to each user
     * @param a expired auction
     */
    public void auctionEnd(Auction a) {
        for(Session session : clients.keySet()) {
            User user = userList.get(clients.get(session));
            if(user != null) {
                String message;
                synchronized (a) {
                    synchronized (user) {
                        message = "{\"typeMsg\": \"endAuction\", \"product_id\": \"" + a.getId() + "\",\"balance\": \"" + user.getMoney() + "\", \"anzahl\": \"" + user.getAuctions() + "\", \"won\": \"" + user.getWon() + "\", \"lost\": \"" + user.getLost() + "\"}";
                    }
                }
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Precondition: auction != null, user != null, gutschrift >= 0
     * Postcondition:
     * @param auction Auction
     * @param user User which has been overbidden
     * @param gutschrift amount of money of the user
     */
    public void ueberboten(Auction auction, User user, double gutschrift) {
        String message = "{\"typeMsg\": \"ueberboten\", \"balance\": \"" + gutschrift + "\", \"hlink\": \"link"+auction.getId()+"\"}";
        for(Session session : clients.keySet()) {
           if(user.equals(userList.get(clients.get(session)))) {
               try {
                   session.getBasicRemote().sendText(message);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    /**
     * Precondition: auction != null, highesBidder != null, price >= 0
     * Postcondtition: The methode sends a message about a new bid to all users
     * @param auction Auction
     * @param highestBidder new highest bidder for auction
     * @param price bid on auction
     */
    public void gebotAbgegeben(Auction auction, User highestBidder, double price) {
        String message = "{\"typeMsg\": \"newGebot\", \"product_id\": \"" + auction.getId() +"\",\"user\": \"" + highestBidder.getUsername() +"\",\"price\": \""+price+ "\", \"hlink\": \"link"+auction.getId()+"\"}";
        this.sendMessageToAllUsers(message);
    }

    /**
     * Precondition: message != null
     * Postcondition: The methode sends the given message to all users.
     * @param message message to be send
     */
    public void sendMessageToAllUsers(String message) {
        for(Session session : clients.keySet()) {
            User user = userList.get(clients.get(session));
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void unregister(Session userSession) {
        clients.remove(userSession);
    }

    /**
     * Call this method from your servlet's shutdown listener to cleanly
     * shutdown the thread when the application stops.
     * 
     * See http://www.deadcoderising.com/execute-code-on-webapp-startup-and-shutdown-using-servletcontextlistener/
     */
    public void stop() {
        this.executor.shutdown();
        this.computerUser.shutdown();
    }
}
