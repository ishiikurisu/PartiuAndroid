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


}
