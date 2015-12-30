package rocks.crisjr.partiusketch.controller;

import rocks.crisjr.partiusketch.model.Database;
import rocks.crisjr.partiusketch.model.entity.Event;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 30/12/2015.
 */
public class CreateController
extends BasicController {
    private Database db;

    public CreateController() {
        super();
        db = new Database();
    }

    /**
     * Adds a new event to the database
     * @param name
     * @param local
     * @param description
     */
    public void createEvent(String name, String local, String description) {
        Event event = new Event();
        event.setName(name);
        event.setLocal(local);
        event.setDescription(description);
        db.addEvent(event);
    }
}
