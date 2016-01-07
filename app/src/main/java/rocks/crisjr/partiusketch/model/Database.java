package rocks.crisjr.partiusketch.model;

import java.io.Serializable;
import java.util.ArrayList;

import rocks.crisjr.partiusketch.model.entity.Event;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 30/12/2015.
 */
public class Database
implements Serializable {
    private Event[] events;
    private String[] categories;
    private int numberEvents = 0;

    public Database() {
        // create events storage
        events = new Event[256];
        numberEvents = 0;

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
        if (numberEvents == events.length) {
            Event[] temp = new Event[2*numberEvents];
            for (int i = 0; i < numberEvents; ++i)
                temp[i] = events[i];
            events = temp;
        }

        events[numberEvents] = event;
        numberEvents++;
    }
}
