package at.ac.tuwien.big.we16.ue2.beans;

/**
 * Created by vanessa on 15.04.16.
 */
public class Auction {
    private String id;
    private String bezeichnung;
    private String img;
    private double preis;
    private String ablaufdatum;
    private String hoechstbietender;
    private double hoechstgebot;

    public Auction() {}

    public Auction(String id, String bezeichnung, String img, double preis, String ablaufdatum) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.setImg(img);
        this.preis = preis;
        this.ablaufdatum = ablaufdatum;
        this.hoechstbietender = "";
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

    public String getHoechstbietender() {
        return hoechstbietender;
    }

    public void setHoechstbietender(String hoechstbietender) {
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
}
