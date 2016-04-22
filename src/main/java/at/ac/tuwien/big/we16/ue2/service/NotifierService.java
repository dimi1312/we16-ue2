package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.beans.Auction;
import at.ac.tuwien.big.we16.ue2.beans.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class NotifierService {
    private static Map<Session, HttpSession> clients = new ConcurrentHashMap<>();
    private static Map<HttpSession,User> userList = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor;

    public NotifierService() {
        // Use the scheduled executor to regularly check for recently expired auctions
        // and send a notification to all relevant users.
        this.executor = Executors.newSingleThreadScheduledExecutor();
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
        clients.put(socketSession, httpSession);
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            userList.put(httpSession,user);

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
            session.getAsyncRemote().sendText(message);
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
    }
}
