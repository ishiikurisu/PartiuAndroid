package rocks.crisjr.partiusketch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.views.MapView;

import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import rocks.crisjr.partiusketch.controller.BasicController;
import rocks.crisjr.partiusketch.controller.TimeController;

public class CreateActivity extends FragmentActivity {

    private MapView mapView = null;
    private boolean menuCollapsed = false;
    private BasicController controller = null;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* create map */
        setContentView(R.layout.activity_create);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(new com.mapbox.mapboxsdk.geometry.LatLng(41.885, -87.679));
        mapView.setZoomLevel(11);
        mapView.onCreate(savedInstanceState);



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

        // add collapsible abilities to menu
        Button button = (Button) findViewById(R.id.buttonCollapse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseMenu();
            }
        });
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
     * Adds a collapsible ability to the sidebar menu
     */
    void collapseMenu() {
        controller = getController();
        controller.setContext(getApplicationContext());
        LinearLayout viewMenu = (LinearLayout) findViewById(R.id.viewMenu);
        Button buttonCollapse = (Button) findViewById(R.id.buttonCollapse);
        Button buttonCreate = (Button) findViewById(R.id.buttonCreate);
        TextView textName = (TextView) findViewById(R.id.textName);
        TextView textCategory = (TextView) findViewById(R.id.textCategory);
        TextView textLocal = (TextView) findViewById(R.id.textLocal);
        TextView textDescription = (TextView) findViewById(R.id.textDescription);
        TextView textDate = (TextView) findViewById(R.id.textTime);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editLocal = (EditText) findViewById(R.id.editLocal);
        EditText editDescription = (EditText) findViewById(R.id.editDescription);
        Button buttonDate = (Button) findViewById(R.id.buttonDate);
        Button buttonTime = (Button) findViewById(R.id.buttonTime);

        String collapseText = "<<<";
        int visibility = View.VISIBLE;
        int width = controller.convertDiptoPix(150);

        if (!menuCollapsed) {
            visibility = View.INVISIBLE;
            collapseText = ">>>";
            width = controller.convertDiptoPix(50);
        }

        textCategory.setVisibility(visibility);
        textDescription.setVisibility(visibility);
        textLocal.setVisibility(visibility);
        textName.setVisibility(visibility);
        textDate.setVisibility(visibility);
        buttonCreate.setVisibility(visibility);
        spinner.setVisibility(visibility);
        editName.setVisibility(visibility);
        editDescription.setVisibility(visibility);
        editLocal.setVisibility(visibility);
        buttonCollapse.setText(collapseText);
        buttonDate.setVisibility(visibility);
        buttonTime.setVisibility(visibility);
        viewMenu.getLayoutParams().width = width;
        menuCollapsed = !menuCollapsed;
    }

    /**
     * Gets the controller sent from the main activity
     * @return main controller
     */
    private BasicController getController() {
        if (controller == null)
            controller = getIntent().getExtras().getParcelable("controller");
        return controller;
    }

    /**
     * Callback to "Create event" button on Creation menu
     * @param view
     */
    public void createEvent(View view) {
        String name, local, description;
        int category;

        TextView text = (TextView) findViewById(R.id.editName);
        name = text.getText().toString();
        text = (TextView) findViewById(R.id.editLocal);
        local = text.getText().toString();
        text = (TextView) findViewById(R.id.editDescription);
        description = text.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        category = spinner.getSelectedItemPosition();

        controller = getController();
        controller.createEvent(name, local, description, category);
        Toast.makeText(getApplicationContext(),
                       getResources().getString(R.string.event_created),
                       Toast.LENGTH_LONG).show();
        endActivity();
    }

    /**
     * Callback to "Pick date" button on creation menu
     * @param view
     */
    public void onClickButtonDate(View view) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                TimeController tc = new TimeController();
                String date = tc.getDate(year, monthOfYear, dayOfMonth);
                Toast.makeText(getApplicationContext(),
                               getResources().getString(R.string.event_set_to) + " " + date,
                               Toast.LENGTH_SHORT).show();
                Button b = (Button) findViewById(R.id.buttonDate);
                b.setText(date);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(CreateActivity.this,
                                                       listener,
                                                       calendar.get(Calendar.YEAR),
                                                       calendar.get(Calendar.MONTH),
                                                       calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /**
     * Callback to "Pick time" button
     * @param view
     */
    public void onClickButtonTime(View view) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TimeController tc = new TimeController();
                String hour = tc.getTime(hourOfDay, minute);
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.event_set_to) + hour,
                        Toast.LENGTH_SHORT).show();
                Button b = (Button) findViewById(R.id.buttonTime);
                b.setText(hour);
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(CreateActivity.this,
                                                       listener,
                                                       calendar.get(Calendar.HOUR_OF_DAY),
                                                       calendar.get(Calendar.MINUTE),
                                                       true);
        dialog.show();
    }

    /**
     * Actions before ending this activity
     */
    private void endActivity() {
        Intent intent = new Intent();
        controller = getController();

        intent.putExtra("controller", (Parcelable) controller);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
