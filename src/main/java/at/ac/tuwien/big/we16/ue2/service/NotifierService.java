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
        //Computerbenutzer der auf alle Auktionen im Abstand von 10 Sekunden das Hoechstgebot mit einer Wahrscheinlichkeit von 30% ueberbietet
        this.computerUser = Executors.newSingleThreadScheduledExecutor();
    }

    public void setSortiment(Sortiment sortiment) {
        this.sortiment = sortiment;
        this.user = new User();
        this.user.setUsername("Computer");
    }
    public void startAuctionEndWatcher() {
      /*  final Runnable checkAuctions = new Runnable() {
            public void run() {
                for(Auction a : sortiment.getAuction()) {
                    if(a.getBezeichnung().equals("Reload")) {
                        System.out.println("Check");
                        if (LocalDateTime.now().isAfter(LocalDateTime.parse(a.getAblaufdatum(),format))) {
                            User winner = a.getHoechstbietender();
                            a.removeUser(winner);
                            winner.setWon(winner.getWon()+1);
                            winner.setAuctions(winner.getAuctions()-1);
                            for(User loser : a.getTeilnehmer()) {
                                loser.setAuctions(loser.getAuctions()-1);
                                loser.setLost(loser.getLost()+1);
                            }
                            auctionEnd();
                        }
                    }

                }
            }
        };
        final ScheduledFuture<?> beeper = this.executor.scheduleAtFixedRate(checkAuctions, 1,1, TimeUnit.SECONDS);*/
    }
    public void startComputerUser() {
        final Runnable computer = new Runnable() {
            public void run() {
                for(Auction auction : sortiment.getAuction()) {
                    int wahrscheinlichkeit = random.nextInt(101);
                    if(wahrscheinlichkeit <= 30) {
                        synchronized (auction) {
                            User loser = auction.getHoechstbietender();
                            synchronized (loser) {
                                loser.changeMoney(auction.getHoechstgebot());
                                ueberboten(loser, loser.getMoney());
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
    //bei allen Usern der Auction muss bei Ablauf laufend umgesetzt werden
    //sowie gewonnen bzw verloren veraendert werden
    public void auctionEnd() {
        for(Session session : clients.keySet()) {
            User user = userList.get(clients.get(session));
            if(user != null) {
                String message = "{\"typeMsg\": \"endAuction\", \"balance\": \"" + user.getMoney() + "\", \"anzahl\": \"" + user.getAuctions() + "\", \"won\": \"" + user.getWon() + "\", \"lost\": \"" + user.getLost() + "\"}";
                session.getAsyncRemote().sendText(message);
            }
        }

    }

    public void ueberboten(User user, double gutschrift) {
        String message = "{\"typeMsg\": \"ueberboten\", \"balance\": \"" + gutschrift + "\"}";
        for(Session session : clients.keySet()) {
           if(user.equals(userList.get(clients.get(session)))) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }
    public void gebotAbgegeben(Auction auction, User highestBidder, double price) {
        String message = "{\"typeMsg\": \"newGebot\", \"product_id\": \"" + auction.getId() +"\",\"user\": \"" + highestBidder.getUsername() +"\",\"price\": \""+price+"\"}";
        this.sendMessageToAllUsers(message);
    }

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
