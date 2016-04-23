package at.ac.tuwien.big.we16.ue2.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanessa on 15.04.16.
 */
public class Auction {
    private String id;
    private String bezeichnung;
    private String img;
    private double preis;
    private String ablaufdatum;
    private User hoechstbietender;
    private double hoechstgebot;
    private List<User> user = new ArrayList<>();

    public Auction() {}

    public Auction(String id, String bezeichnung, String img, double preis, String ablaufdatum) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.setImg(img);
        this.preis = preis;
        this.ablaufdatum = ablaufdatum;
        this.hoechstbietender = new User();
        this.hoechstgebot = 0.0;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = "../images/" + img;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public User getHoechstbietender() {
        return hoechstbietender;
    }

    public void setHoechstbietender(User hoechstbietender) {
        this.hoechstbietender = hoechstbietender;
    }

    public double getHoechstgebot() {
        return hoechstgebot;
    }

    public void setHoechstgebot(double hoechstgebot) {
        this.hoechstgebot = hoechstgebot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAblaufdatum() {
        return ablaufdatum;
    }

    public void setAblaufdatum(String ablaufdatum) {
        this.ablaufdatum = ablaufdatum;
    }

    public void addUser(User user) {
        this.user.add(user);
    }

    public void removeUser(User user) {
        this.user.remove(user);
    }

    public boolean containsUser(User user) {
        return this.user.contains(user);
    }
    public List<User> getTeilnehmer() {
        return this.user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        return id != null ? id.equals(auction.id) : auction.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
