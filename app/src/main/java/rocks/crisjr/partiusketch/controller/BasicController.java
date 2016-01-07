package rocks.crisjr.partiusketch.controller;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import rocks.crisjr.partiusketch.model.Database;
import rocks.crisjr.partiusketch.model.entity.Event;

/**
 * This class attempts to provide the basic functionality needed across every
 * controller in the application; and the access to the database.
 * @author Cris Joe Jr. (cristianoalvesjr@gmail.com)
 */
public class BasicController
implements Parcelable {
    private Context context = null;
    protected String[] categories = null;
    protected Database db = null;
    private String name = "Joe";

    public BasicController() {
        db = new Database();
        categories = db.getCategories();
    }

    /* Gets and Sets */
    public String[] getCategories() {
        return this.categories;
    }

    public Context setContext(Context context) {
        this.context = context;
        return this.context;
    }

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
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

    /* parcelable implementations */
    public BasicController(Parcel parcel) {
        db = (Database) parcel.readSerializable();
        name = parcel.readString();
        categories = db.getCategories();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.db);
        dest.writeString(this.name);
    }

    public static final Parcelable.Creator<BasicController> CREATOR = new Parcelable.Creator<BasicController>() {
        public BasicController createFromParcel(Parcel in) {
            return new BasicController(in);
        }

        public BasicController[] newArray(int size) {
            return new BasicController[size];
        }
    };
}