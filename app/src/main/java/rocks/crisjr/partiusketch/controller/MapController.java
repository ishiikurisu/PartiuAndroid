package rocks.crisjr.partiusketch.controller;

import android.content.Context;
import android.widget.LinearLayout;
import rocks.crisjr.partiusketch.R;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 28/12/2015.
 */
public class MapController {

    private Context context;

    public MapController(Context context) {
        this.context = context;
    }

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
