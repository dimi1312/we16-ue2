package at.ac.tuwien.big.we16.ue2.beans;

import at.ac.tuwien.big.we16.ue2.productdata.JSONDataLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanessa on 15.04.16.
 */
public class Sortiment {
    public static List<Auction> auctions = null;

    public Sortiment() {
        if(auctions == null) {
            auctions = new ArrayList<>();
        for(JSONDataLoader.Music music : JSONDataLoader.getMusic()) {
            addAuction(new Auction(music.getProduct_id(), music.getAlbum_name(), music.getImg(), music.getPreis(), music.getAuction_end()));
        }
        for(JSONDataLoader.Book book : JSONDataLoader.getBooks()) {
            addAuction(new Auction(book.getProduct_id(), book.getTitle(), book.getImg(), book.getPreis(), book.getAuction_end()));
        }
        for(JSONDataLoader.Movie movie : JSONDataLoader.getFilms()) {
            addAuction(new Auction(movie.getProduct_id(), movie.getTitle(), movie.getImg(), movie.getPreis(), movie.getAuction_end()));
        }
        }
    }

    public void addAuction(Auction auction) {
        this.auctions.add(auction);
    }
    public List<Auction> getAuction() {
        return this.auctions;
    }
    public Auction getProductByName(String name) {
        for(Auction p : this.auctions) {
            if(p.getBezeichnung().equals(name)) {
                return p;
            }
        }
        return null;
    }
    public Auction getProductById(String id) {
        for(Auction p : this.auctions) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

}
