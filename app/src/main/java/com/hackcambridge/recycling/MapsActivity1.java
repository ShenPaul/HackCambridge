package com.example.jazzyw.whph2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button mFirebaseBtn;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFirebaseBtn = (Button)findViewById(R.id.firebase_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                System.out.println ("Clicked!");
            }
        });

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

        LatLng point_one = new LatLng(39.435857, -81.224072);
        mMap.addMarker(new MarkerOptions()
                .position(point_one)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.forest)));

        LatLng point_two = new LatLng(39.437109, -81.225626);
        mMap.addMarker(new MarkerOptions()
                .position(point_two)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.forest)));

        mMap.addPolyline(new PolylineOptions().add(
                point_one,
                new LatLng(39.4365, -81.2244),
                new LatLng(39.4367, -81.2248),
                point_two
                )
                        .width(10)
                        .color(Color.GREEN)
        );

        LatLng next_point = new LatLng(46.304734, -75.255869);
        mMap.addMarker(new MarkerOptions()
                .position(next_point)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.road)));

        LatLng end_point = new LatLng(46.307623, -75.250209);
        mMap.addMarker(new MarkerOptions()
                .position(end_point)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.road)));

        mMap.addPolyline(new PolylineOptions().add(
                next_point,
                new LatLng(46.3056, -75.25456),
                new LatLng(46.3068, -75.25342),
                end_point
                )
                        .width(10)
                        .color(Color.BLACK)

        );

        // forest = forest --green
        // desert = desert123 --yellow
        // mountains = mountains123 --red
        // road = road123 --black
        // snow = snow123 --blue

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point_one, 0));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }
}