package ca.uwindsor.uwintechclub.chamberofcommerce;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Bundle b = getIntent().getExtras();

        String ucDirectory_UcListing_lblOwner = b.getString("ucDirectory_UcListing_lblOwner");
        String ucDirectory_UcListing_lblTitle = b.getString("ucDirectory_UcListing_lblTitle");
        String ucDirectory_UcListing_lblAddress1 = b.getString("ucDirectory_UcListing_lblAddress1");
        String ucDirectory_UcListing_lblCity = b.getString("ucDirectory_UcListing_lblCity");
        String ucDirectory_UcListing_lblStateProvince = b.getString("ucDirectory_UcListing_lblStateProvince");
        String ucDirectory_UcListing_lblZipPostal = b.getString("ucDirectory_UcListing_lblZipPostal");
        String ucDirectory_UcListing_lblFax = b.getString("ucDirectory_UcListing_lblFax");
        String ucDirectory_UcListing_lblPhone1 = b.getString("ucDirectory_UcListing_lblPhone1");
        String ucDirectory_UcListing_hlEmail = b.getString("ucDirectory_UcListing_hlEmail");
        String ucDirectory_UcListing_hlWebsit = b.getString("ucDirectory_UcListing_hlWebsit");


        setTitle(ucDirectory_UcListing_lblOwner);

        addHelpText(R.id.cDirectory_UcListing_lblOwner, ucDirectory_UcListing_lblOwner);
        addHelpText(R.id.cDirectory_UcListing_lblTitle, ucDirectory_UcListing_lblTitle);
        addHelpText(R.id.cDirectory_UcListing_lblAddress1, ucDirectory_UcListing_lblAddress1);
        addHelpText(R.id.cDirectory_UcListing_lblCity, ucDirectory_UcListing_lblCity);
        addHelpText(R.id.cDirectory_UcListing_lblStateProvince, ucDirectory_UcListing_lblStateProvince);
        addHelpText(R.id.cDirectory_UcListing_lblZipPostal, ucDirectory_UcListing_lblZipPostal);
        addHelpText(R.id.cDirectory_UcListing_lblFax, ucDirectory_UcListing_lblFax);
        addHelpText(R.id.cDirectory_UcListing_lblPhone1, ucDirectory_UcListing_lblPhone1);
        addHelpText(R.id.cDirectory_UcListing_hlEmail, ucDirectory_UcListing_hlEmail);
        addHelpText(R.id.cDirectory_UcListing_hlWebsit, ucDirectory_UcListing_hlWebsit);

        getLocationFromAddress(ucDirectory_UcListing_lblAddress1 + " " + ucDirectory_UcListing_lblCity + " " + ucDirectory_UcListing_lblStateProvince);
        currentTitle = ucDirectory_UcListing_lblOwner;

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                TextView textViewToChange = (TextView) findViewById(R.id.ucDirectory_UcListing_lblPhone1);
//                callIntent.setData(Uri.parse("tel:" + textViewToChange.getText()));
////                startActivity(callIntent);
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });
    }


    public void addHelpText(int finder, String text) {

//        System.out.println(text);

        TextView textViewToChange = (TextView) findViewById(finder);

        textViewToChange.setText(text);
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(currentPlace).title(currentTitle));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPlace, 13.0f));

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    LatLng currentPlace = new LatLng(-34, 151);
    String currentTitle = "new title";

    public void getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
//        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            currentPlace = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
        }
    }
}
