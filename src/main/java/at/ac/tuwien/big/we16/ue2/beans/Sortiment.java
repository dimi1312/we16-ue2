package at.ac.tuwien.big.we16.ue2.beans;

import at.ac.tuwien.big.we16.ue2.productdata.JSONDataLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains a list of auctions
 * Created by vanessa on 15.04.16.
 */
public class Sortiment {
    public static List<Auction> auctions = new ArrayList<>();

    /**
     * Postcondition: The constructor creates a new sortiment by loading the informations of the products
     * into the list of auctions.
     */
    public Sortiment() {
        if(auctions.size() == 0) {
            //loading the music
            for (JSONDataLoader.Music music : JSONDataLoader.getMusic()) {
                addAuction(new Auction(music.getProduct_id(), music.getAlbum_name(), music.getImg(), music.getAuction_end()));
            }
            //loading the books
            for (JSONDataLoader.Book book : JSONDataLoader.getBooks()) {
                addAuction(new Auction(book.getProduct_id(), book.getTitle(), book.getImg(), book.getAuction_end()));
            }
            //loading the movies
            for (JSONDataLoader.Movie movie : JSONDataLoader.getFilms()) {
                addAuction(new Auction(movie.getProduct_id(), movie.getTitle(), movie.getImg(), movie.getAuction_end()));
            }
        }
    }

    /**
     * Precondition: auction != null
     * Postcondition: The methode adds a new auction to the list of auctions.
     * @param auction new auction to be added
     */
    public void addAuction(Auction auction) {
        this.auctions.add(auction);
    }

    /**
     * Postcondition: The methode returns the list of auctions.
     * @return list of auctions
     */
    public List<Auction> getAuction() {
        return this.auctions;
    }

    /**
     * Precondition: id != null
     * Postcondition: The methode searches for the auction with the given id and returns it.
     * @param id id to be searched for
     * @return auction with the given id
     *            null if no auction with this id is in the sortiment
     */
    public Auction getProductById(String id) {
        for(Auction p : this.auctions) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

}
