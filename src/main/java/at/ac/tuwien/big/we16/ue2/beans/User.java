package at.ac.tuwien.big.we16.ue2.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains information for an logged in user.
 * Created by vanessa on 14.04.16.
 */
public class User {
    private String username;
    private String password;
    private double money;                    //Invariante: money >= 0
    private int auctions = 0;                //Invariante: auctions >= 0
    private int won = 0;                     //Invariante: won >= 0
    private int lost = 0;                    //Invariante: lost >= 0
    boolean loggedIn=false;

    /**
     * Postcondition: The constructor creates a new userobject.
     */
    public User() {
        this.username ="kein Gebot";
        this.password = "";
        this.money = 0.0;
    }

    /**
     * Precondition: username != null and != "", passwort != null
     * Postcondition: The constructor creates a new userobject from the given parameters.
     * The amount of money the new user posseses is set to 1500 euros.
     * @param username email address of the user
     * @param password password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.money = 1500.00;
    }

    /**
     * Postcondition: The methode returns the username.
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Precondition: username != null and username not empty
     * Postcondition: The methode sets the username to the given parameter.
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Postcondition: The methode returns the current amount of money the user posseses.
     * @return amount of money of the user
     */
    public double getMoney() {
        return Math.round(money*100)/100.0;
    }

    /**
     * Precondition: if change <= 0 money - change has to be >= 0
     * Postcondition: The methode changes the current amount of money about the amount
     * given by the parameter.
     * @param change amount to change the current money
     */
    public void changeMoney(double change) {
        change = Math.round(change*100)/100.0;
        this.money += change;
    }

    /**
     * Postcondition: The methode increases the amount of current running acounts by one.
     */
    public void addAuction() {
        this.auctions++;
    }

    /**
     * Postcondition: The methode decreases the amount of current running aucounts by one.
     */
    public void removeAuction() {
        this.auctions--;
    }

    /**
     * Postcondition: The methode increases the amount of won auctions by one.
     */
    public void addWon() {
        this.won++;
    }

    /**
     * Postcondition: The methode increases the amount of lost auctions by one.
     */
    public void addLost() {
        this.lost++;
    }

    /**
     * Precondition: money >= 0
     * Postcondition: The methode sets the amount of money to the given parameter.
     * @param money amount of money to be set
     */
    public void setMoney(double money) {
        money = Math.round(money*100)/100.0;
        this.money = money;
    }

    /**
     * Postcondition: The methode returns the current amount of running auctions.
     * @return amount of running auctions
     */
    public int getAuctions() {
        return auctions;
    }

    /**
     * Precondition: auctions >= 0
     * Postcondition: The methode sets the amount of running auctions to the given parameter.
     * @param auctions amount of running auctions
     */
    public void setAuctions(int auctions) {
        this.auctions = auctions;
    }

    /**
     * Postcondition: The methode returns the amount of won auctions.
     * @return won auctions
     */
    public int getWon() {
        return won;
    }

    /**
     * Precondition: won >= 0
     * Postcondition: The methode setzt the amount of won auctions to the given parameter.
     * @param won won auctions to be set
     */
    public void setWon(int won) {
        this.won = won;
    }

    /**
     * Postcondition: The methode returns the lost auctions.
     * @return amount of lost auctions
     */
    public int getLost() {
        return lost;
    }

    /**
     * Precondition: lost >= 0
     * Postcondition: The methode sets the amount of lost auctions to the given parameter.
     * @param lost amount of lost auctions to be set
     */
    public void setLost(int lost) {
        this.lost = lost;
    }

    /**
     * Postcondition: The methode returns the password of the user.
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Precondition: password != null
     * Postcondition: The methode sets the password of the user to the given parameter.
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Postcondition: The methode returns if the user is logged in.
     * @return true if the user is logged in
     *         false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Postcondition: The methode sets the logged in status of the user to the given parameter.
     * @param loggedIn logged in status to be set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Postcondition: The methode compares two users and checks if their
     * usernames are equal.
     * @param o object to compare with
     * @return true if both users have the same username
     *         false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    /**
     * Postcondition: The methode returns the hashCode of the username.
     * @return
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
