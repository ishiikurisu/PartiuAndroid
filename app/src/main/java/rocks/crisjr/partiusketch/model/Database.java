package rocks.crisjr.partiusketch.model;

import java.util.ArrayList;

import rocks.crisjr.partiusketch.model.entity.Event;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 30/12/2015.
 */
public class Database {
    private ArrayList<Event> events;

    public Database() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
