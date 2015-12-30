package rocks.crisjr.partiusketch.controller;

import android.content.Context;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 30/12/2015.
 */
public class BasicController {
    private Context context;
    protected String[] categories;

    public BasicController() {
        categories = new String[2];

        categories[0] = "Festa";
        categories[1] = "Esporte";
    }

    public BasicController(Context context) {
        this.context = context;
    }

    /* Gets and Sets */
    public String[] getCategories() {
        return this.categories;
    }

    /* DP/Pixel tools */
    private static float getDensity(Context context){
        float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }

    public int convertDiptoPix(int dip){
        float scale = getDensity(this.context);
        return (int) (dip * scale + 0.5f);
    }

    public int convertPixtoDip(int pixel){
        float scale = getDensity(this.context);
        return (int)((pixel - 0.5f)/scale);
    }

}
