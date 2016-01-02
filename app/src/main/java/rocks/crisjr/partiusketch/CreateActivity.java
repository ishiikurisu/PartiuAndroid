package rocks.crisjr.partiusketch;

import android.app.Activity;
import android.content.Intent;
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

import java.util.Date;

import rocks.crisjr.partiusketch.controller.BasicController;

public class CreateActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private boolean menuCollapsed = false;
    private BasicController controller = new BasicController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* create map */
        setContentView(R.layout.activity_create);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // add categories to spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
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

    /**
     * Adds a collapsible ability to the sidebar menu
     */
    void collapseMenu() {
        controller.setContext(getApplicationContext());
        ScrollView viewMenu = (ScrollView) findViewById(R.id.viewMenu);
        Button buttonCollapse = (Button) findViewById(R.id.buttonCollapse);
        Button buttonCreate = (Button) findViewById(R.id.buttonCreate);
        TextView textName = (TextView) findViewById(R.id.textName);
        TextView textCategory = (TextView) findViewById(R.id.textCategory);
        TextView textLocal = (TextView) findViewById(R.id.textLocal);
        TextView textDescription = (TextView) findViewById(R.id.textDescription);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editLocal = (EditText) findViewById(R.id.editLocal);
        EditText editDescription = (EditText) findViewById(R.id.editDescription);
        DatePicker pickerDate = (DatePicker) findViewById(R.id.pickerDate);
        TimePicker pickerTime = (TimePicker) findViewById(R.id.pickerTime);

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
        buttonCreate.setVisibility(visibility);
        spinner.setVisibility(visibility);
        editName.setVisibility(visibility);
        editDescription.setVisibility(visibility);
        editLocal.setVisibility(visibility);
        buttonCollapse.setText(collapseText);
        pickerDate.setVisibility(visibility);
        pickerTime.setVisibility(visibility);
        viewMenu.getLayoutParams().width = width;
        menuCollapsed = !menuCollapsed;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        myMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

        controller.createEvent(name, local, description, category);
        Toast.makeText(getApplicationContext(),
                       getResources().getString(R.string.event_created),
                       Toast.LENGTH_LONG).show();
        controller.shit.concat(" frank");
        finish();
    }
}
