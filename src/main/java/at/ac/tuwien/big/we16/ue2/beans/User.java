package at.ac.tuwien.big.we16.ue2.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanessa on 14.04.16.
 */
public class User {
    private String username;
    private String password;
    private double money;
    private int auctions = 0;
    private int won = 0;
    private int lost = 0;

    public User() {
        this.username ="NaN";
        this.password = "";
        this.money = 0.0;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.money = 1500.00;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getMoney() {
        return Math.round(money*100)/100;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getAuctions() {
        return auctions;
    }

    public void setAuctions(int auctions) {
        this.auctions = auctions;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
