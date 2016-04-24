package at.ac.tuwien.big.we16.ue2.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains information for an auction.
 * Created by vanessa on 15.04.16.
 */
public class Auction {
    private String id;                              //Invariante: id != null
    private String bezeichnung;                     //Invariante: bezeichnung != null
    private String img;                             //Invariante: img != null
    private String ablaufdatum;                     //Invariante: ablaufdatum != null format "yyyy,MM,dd,HH,mm,ss,SSS"
    private User hoechstbietender;                  //Invariante: hoechstbietender != null
    private double hoechstgebot;                    //Invariante: hoechstgebot >= 0
    private List<User> user = new ArrayList<>();
    private boolean expired = false;

    /**
     * Postcondition: The constructor creates a new Auctionobject.
     */
    public Auction() {}

    /**
     * Precondition: id != null, bezeichnung != null, img != null, ablaufdatum != null format "yyyy,MM,dd,HH,mm,ss,SSS"
     * Postcondition: The constructor creates a new Auctionobject from the given parameters
     * @param id id of the auction
     * @param bezeichnung name of the object the be sold
     * @param img path to an image of the auction
     * @param ablaufdatum end of the auction
     */
    public Auction(String id, String bezeichnung, String img, String ablaufdatum) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.setImg(img);
        this.ablaufdatum = ablaufdatum;
        this.hoechstbietender = new User();
        this.hoechstgebot = 0.0;
    }

    /**
     * Postcondition: The methode returns the name of the product of the auction.
     * @return name of the product to be sold
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Postcondition: The methode sets the expired status to true.
     */
    public void setExpired() {
        this.expired = true;
    }

    /**
     * Postcondition: The methode returns the expired status of the auction.
     * @return expired status of the auction
     */
    public boolean isExpired() {
        return this.expired;
    }

    /**
     * Precondition: bezeichnung != null
     * Postcondition: The methode sets the name of the product to the given parameter.
     * @param bezeichnung name of the product
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * Postcondition: The methode returns the path to the image of the product.
     * @return path to the image of the product
     */
    public String getImg() {
        return img;
    }

    /**
     * Precondition: img != null
     * Postcondition: The methode sets the path to the image of the product to ../images/ +  the given parameter.
     * @param img name of the image of the product
     */
    public void setImg(String img) {
        this.img = "../images/" + img;
    }

    /**
     * Postcondition: The methode returns the user who made the highest bid on this auction.
     * @return highest bidder on the auction
     */
    public User getHoechstbietender() {
        return hoechstbietender;
    }

    /**
     * Precondition: hoechstbietender != null
     * Postcondition: The methode sets the highest bidder of the auction to the given parameter.
     * @param hoechstbietender highest bidder to be set
     */
    public void setHoechstbietender(User hoechstbietender) {
        this.hoechstbietender = hoechstbietender;
    }

    /**
     * Postcondition: The methode returns the current highest bid on the product of this auction.
     * @return highest bid
     */
    public double getHoechstgebot() {
        return hoechstgebot;
    }

    /**
     * Precondition: hoechstgebot >= 0
     * Postcondition: The methode sets the highest bid on the auction to the given parameter.
     * @param hoechstgebot
     */
    public void setHoechstgebot(double hoechstgebot) {
        this.hoechstgebot = hoechstgebot;
    }

    /**
     * Postcondition: The methode returns the id of the auction.
     * @return id of the auction
     */
    public String getId() {
        return id;
    }

    /**
     * Precondition: id != null
     * Postcondition: The methode sets the id of the auction to the given parameter.
     * @param id id of the auction to be set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Postcondition: The methode returns the end of the auction.
     * @return end of auction
     */
    public String getAblaufdatum() {
        return ablaufdatum;
    }

    /**
     * Precondition: ablaufdatum != null, format ablaufdatum yyyy,MM,dd,HH,mm,ss,SSS
     * Postcondition: The methode sets the ablaufdatum to the given parameter.
     * @param ablaufdatum end of the auction
     */
    public void setAblaufdatum(String ablaufdatum) {
        this.ablaufdatum = ablaufdatum;
    }

    /**
     * Precondition: user != null
     * Postcondition: The methode adds the given user to the auction.
     * @param user user to be add to the auction
     */
    public void addUser(User user) {
        this.user.add(user);
    }

    /**
     * Precondition: user != null
     * Postcondition: The methode removes the given user from the auction.
     * @param user user to be removed from the auction
     */
    public void removeUser(User user) {
        this.user.remove(user);
    }

    /**
     * Precondition: user != null
     * Postcondition: The methode returns if the given user participates in this auction or not.
     * @param user user to be searched
     * @return true user participates in this auction
     *         false otherwise
     */
    public boolean containsUser(User user) {
        return this.user.contains(user);
    }

    /**
     * Postcondition: The methode returns a list of all users participating in this auction.
     * @return list of all participants
     */
    public List<User> getTeilnehmer() {
        return this.user;
    }

    /**
     * Postcondition: The methode compares two Auctions on the equality of the id.
     * @param o object to be compared with
     * @return true if both ids are equal
     *         false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        return id != null ? id.equals(auction.id) : auction.id == null;

    }

    /**
     * Postcondition: The methode returns the hasCode of the id;
     * @return hashCode of the auction
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
