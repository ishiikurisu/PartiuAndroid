package rocks.crisjr.partiusketch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.Calendar;

import rocks.crisjr.partiusketch.controller.BasicController;
import rocks.crisjr.partiusketch.controller.TimeController;

public class SearchActivity extends FragmentActivity {

    private MapView mapView = null;
    private boolean menuCollapsed = false;
    private BasicController controller = null;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(new LatLng(41.885, -87.679));
        mapView.setZoomLevel(11);
        mapView.onCreate(savedInstanceState);

        Button button = (Button) findViewById(R.id.buttonCollapse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseMenu();
            }
        });

        // add categories to spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        controller = getController();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                controller.getCategories()
        );
        stringArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
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
     * Adding collapsible abilities to sidebar menu
     */
    void collapseMenu() {
        controller = getController();
        controller.setContext(getApplicationContext());
        LinearLayout layoutMenu = (LinearLayout) findViewById(R.id.layoutMenu);
        Button buttonCollapse = (Button) findViewById(R.id.buttonCollapse);
        Button buttonFilter = (Button) findViewById(R.id.buttonFilter);
        TextView textCategory = (TextView) findViewById(R.id.textCategory);
        TextView textName = (TextView) findViewById(R.id.textName);
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        EditText editName = (EditText) findViewById(R.id.editName);
        LinearLayout layoutFrom = (LinearLayout) findViewById(R.id.layoutFrom);
        Button buttonDateFrom = (Button) findViewById(R.id.buttonDateFrom);
        LinearLayout layoutTo = (LinearLayout) findViewById(R.id.layoutTo);
        Button buttonDateTo = (Button) findViewById(R.id.buttonDateTo);

        int visibility = View.INVISIBLE;
        String collapsedText = ">>>";
        int width = controller.convertDiptoPix(50);

        if (menuCollapsed) {
            visibility = View.VISIBLE;
            collapsedText = "<<<";
            width = controller.convertDiptoPix(150);
        }

        buttonFilter.setVisibility(visibility);
        textCategory.setVisibility(visibility);
        textName.setVisibility(visibility);
        spinnerCategory.setVisibility(visibility);
        editName.setVisibility(visibility);
        layoutFrom.setVisibility(visibility);
        layoutTo.setVisibility(visibility);
        buttonDateFrom.setVisibility(visibility);
        buttonDateTo.setVisibility(visibility);
        buttonCollapse.setText(collapsedText);
        layoutMenu.getLayoutParams().width = width;
        menuCollapsed = !menuCollapsed;
    }

    /**
     * Gets the controller sent from the parent activity
     * @return
     */
    private BasicController getController() {
        if (controller == null)
            controller = getIntent().getExtras().getParcelable("controller");
        return controller;
    }

    /* I won't implement this method now because the Search page doesn't alter
     * the main controller.
     */
//    /**
//     * When the user presses the back button, this activity will send the
//     * the controller back.
//     */
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        controller = getController();
//
//        controller.setName(controller.getName() + " frank");
//        intent.putExtra("controller", (Parcelable) controller);
//        setResult(Activity.RESULT_OK, intent);
//
//        super.onBackPressed();
//    }

    /**
     * Callback to "Filter" button
     * @param view
     */
    public void onClickButtonFilter(View view) {
//        Toast.makeText(getApplicationContext(),
//                       getController().getName(),
//                       Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback to "From" checkbox
     * @param view
     */
    public void onClickButtonFrom(View view) {
        Button buttonDateFrom = (Button) findViewById(R.id.buttonDateFrom);
        boolean enabled = buttonDateFrom.isEnabled();
        buttonDateFrom.setEnabled(!enabled);
    }

    /**
     * Callback to "To" checkbox
     * @param view
     */
    public void onClickButtonTo(View view) {
        Button buttonDateTo = (Button) findViewById(R.id.buttonDateTo);
        boolean enabled = buttonDateTo.isEnabled();
        buttonDateTo.setEnabled(!enabled);
    }

    /**
     * Callback to date selection buttons
     * @param id
     */
    private void callbackSetDate(final int id) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                TimeController tc = new TimeController();
                String date = tc.getDate(year, monthOfYear, dayOfMonth);
                Toast.makeText(getApplicationContext(),
                               getResources().getString(R.string.event_set_to) + " " + date,
                               Toast.LENGTH_SHORT).show();
                Button b = (Button) findViewById(id);
                b.setText(date);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(SearchActivity.this,
                                                       listener,
                                                       calendar.get(Calendar.YEAR),
                                                       calendar.get(Calendar.MONTH),
                                                       calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /**
     * Callback to "Choose from date" button
     * @param view
     */
    public void onClickButtonDateFrom(View view) {
        callbackSetDate(R.id.buttonDateFrom);
    }

    /**
     * Callback to "Choose from to" button
     * @param view
     */
    public void onClickButtonDateTo(View view) {
        callbackSetDate(R.id.buttonDateTo);
    }
}
