package com.hackcambridge.recycling;

import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.widget.SearchView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MyActivity";
    private GestureDetectorCompat mDetector;
    private GoogleMap mMap;
    LatLng location = new LatLng(52.202,0.119);
    private Banks banks = new Banks("cam_data.csv");
    private Location userLocation = new Location("Corn Exchange Cambridge");

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
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Bank closestBank = banks.queryMaterial(query, userLocation);
            if (closestBank != null) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(closestBank.getLocation().getLatitude(), closestBank.getLocation().getLongitude()))
                        .title(closestBank.getName()));
            } else {
                //TODO: display popup error
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyCEjKLoDgwL8ebv-6xnhZGjxJAyBbjs498");

// Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        handleIntent(getIntent());


//        // Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autoSearch);
//
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                String csvFile = "cam_data.csv";
//                Banks banks = new Banks(csvFile);
//                Location userLocation = new Location("Pembroke College Cambridge");
//                Bank closestBank = banks.queryMaterial(place.getId(), userLocation);
//                Location bLocation = closestBank.getLocation();
////                (bLocation.getLatitude(), bLocation.getLongitude();)
//
//                String name = place.getName();
//                CharSequence address = place.getAddress();
//
//                if (place.getLatLng() != null) {
//                    location = place.getLatLng();
//                }
//
//                mMap.addMarker(new MarkerOptions().position(location).title(name));
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        BottomNavigationView bottomNavigation = findViewById(R.id.botNav);
        Menu menu = bottomNavigation.getMenu();
//        menu.add(Menu.NONE, menu1, 1, "Map").setIcon(R.drawable.ic_action_one);
//        menu.add(Menu.NONE, menu2, 2, "Map").setIcon(R.drawable.ic_action_one);
//        menu.add(Menu.NONE, menu3, 3, "Map").setIcon(R.drawable.ic_action_one);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            float sensitivity = 50;
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            if((event1.getX() - event2.getX()) > sensitivity) {
                Intent intent = new Intent(MapsActivity.this, com.hackcambridge.recycling.MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "message");
                startActivity(intent);
                return true;
            }else if((event2.getX() - event1.getX()) > sensitivity){
                return true;
            }
            return true;
        }
    }
}
