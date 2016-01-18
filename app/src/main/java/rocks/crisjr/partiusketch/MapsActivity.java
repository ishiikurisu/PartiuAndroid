package rocks.crisjr.partiusketch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.app.Activity;
import android.os.Bundle;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import rocks.crisjr.partiusketch.controller.BasicController;

public class MapsActivity
extends FragmentActivity {

    private MapView mapView = null;
    private boolean menuCollapsed = false;
    private BasicController controller = new BasicController();
    final private int CREATE_EVENT_REQUEST = 0;
    final private int SEARCH_EVENT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* create map */
        setContentView(R.layout.activity_maps);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(new LatLng(41.885, -87.679));
        mapView.setZoomLevel(11);
        mapView.onCreate(savedInstanceState);

        /* add collapsible abilities to menu */
        Button button = (Button) findViewById(R.id.buttonCollapse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseMenu();
            }
        });
    }

    /**
     * Adds collapsible abilities to sidebar menu.
     */
    void collapseMenu() {
        LinearLayout layoutMenu = (LinearLayout) findViewById(R.id.layoutMenu);
        Button buttonCreate = (Button) findViewById(R.id.buttonCreate);
        Button buttonSearch = (Button) findViewById(R.id.buttonSearch);
        Button buttonCollapse = (Button) findViewById(R.id.buttonCollapse);
        int visibility = View.INVISIBLE;
        int width = 50;
        String text = ">>>";

        controller.setContext(getApplicationContext());
        mapView = (MapView) findViewById(R.id.mapview);
        if (menuCollapsed) {
            buttonCreate.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            buttonCollapse.setText("<<<");
            visibility = View.VISIBLE;
            width = 150;
            text = "<<<";
        }

        width = controller.convertDiptoPix(width);
        mapView.getLayoutParams().width = getScreenWidth() - width;
        buttonCreate.setVisibility(visibility);
        buttonSearch.setVisibility(visibility);
        buttonCollapse.setText(text);
        layoutMenu.getLayoutParams().width = width;
        menuCollapsed = !menuCollapsed;
    }

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * Method to receive the results of the child activities
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode)
        {
            case SEARCH_EVENT_REQUEST:
            case CREATE_EVENT_REQUEST:
                if (resultCode == RESULT_OK)
                    controller = intent.getExtras().getParcelable("controller");
                break;
        }
    }

    /**
     * Callback to "Search Events" button. Sends the application's main controller
     * to the Search Activity.
     * @param view
     */
    public void searchEvents(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("controller", (Parcelable) controller);
        startActivityForResult(intent, SEARCH_EVENT_REQUEST);
    }

    /**
     * Callback to "Create Events" button
     * @param view
     */
    public void createEvents(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtra("controller", (Parcelable) controller);
        startActivityForResult(intent, CREATE_EVENT_REQUEST);
    }
}
