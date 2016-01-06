package rocks.crisjr.partiusketch.model;

import java.io.Serializable;
import java.util.ArrayList;

import rocks.crisjr.partiusketch.model.entity.Event;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 30/12/2015.
 */
public class Database
implements Serializable {
    private ArrayList<Event> events;
    private String[] categories;

    public Database() {
        // create events storage
        events = new ArrayList<>();

        // create categories
        categories = new String[2];
        categories[0] = "Festa";
        categories[1] = "Esporte";
    }

    /* gets and sets */
    public String[] getCategories() {
        return this.categories;
    }

    /* rules */
    public void addEvent(Event event) {
        events.add(event);
    }
}
