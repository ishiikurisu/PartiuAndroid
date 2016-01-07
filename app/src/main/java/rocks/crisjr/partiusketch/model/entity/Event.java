package rocks.crisjr.partiusketch.model.entity;

import java.io.Serializable;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 28/12/2015.
 */
public class Event implements Serializable {
    private String name;
    private String description;
    private int category;
    private String local;

    public Event() {

    }

    /* Gets and Sets */
    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public String setDescription(String description) {
        this.description = description;
        return this.description;
    }

    public String setLocal(String local) {
        this.local = local;
        return this.local;
    }

    public int setCategory(int category) {
        this.category = category;
        return this.category;
    }
}
