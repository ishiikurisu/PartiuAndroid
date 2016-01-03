package rocks.crisjr.partiusketch.controller;

import android.content.Context;

import java.io.Serializable;

import rocks.crisjr.partiusketch.model.Database;
import rocks.crisjr.partiusketch.model.entity.Event;

/**
 * This class attempts to provide the basic functionality needed across every
 * controller in the application.
 * @author Cris Joe Jr. (cristianoalvesjr@gmail.com)
 */
public class BasicController
implements Serializable {
    private Context context;
    protected String[] categories;
    protected Database db;

    public BasicController() {
        db = new Database();
        categories = db.getCategories();
    }

    public BasicController(Context context) {
        this.context = context;
    }

    /* Gets and Sets */
    public String[] getCategories() {
        return this.categories;
    }

    public Context setContext(Context context) {
        this.context = context;
        return this.context;
    }

    /* DP/Pixel tools */
    private static float getDensity(Context context){
        float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }

    /**
     * Converts density to pixel numbers, depending on the application context.
     * @param dip, the screen density.
     * @return the related number of pixels to the specified density.
     */
    public int convertDiptoPix(int dip){
        float scale = getDensity(this.context);
        return (int) (dip * scale + 0.5f);
    }

    /**
     * Converts pixel number to screen density, depending on the application context.
     * @param pixel, the number of pixels needed.
     * @return the related screen density to the specified number of pixels.
     */
    public int convertPixtoDip(int pixel){
        float scale = getDensity(this.context);
        return (int)((pixel - 0.5f)/scale);
    }

    /* Event capabilities */
    /**
     * Creates an event to add to the database
     * @param name
     * @param local
     * @param description
     * @param category
     */
    public void createEvent(String name, String local, String description, int category) {
        Event event = new Event();
        event.setName(name);
        event.setLocal(local);
        event.setDescription(description);
        event.setCategory(category);
        db.addEvent(event);
    }
}
