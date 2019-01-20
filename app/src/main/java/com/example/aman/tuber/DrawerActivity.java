package com.example.aman.tuber;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DrawerActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private String testVar = "1";
    private GoogleMap mMap;
    private int userNameNumber = 0;
    Button popup_button;
    Button start_tutoring_button;
    MenuItem studentModeItem;
    SwitchCompat tutor_mode_switch;

    ArrayList<UserProfile> userLists = new ArrayList<>();

    ValueEventListener mUserProfileListener;
    ValueEventListener mOfferListener;

    DatabaseReference mDatabaseReference;
    DatabaseReference mOfferReference;
    String mUID;
    QueryService mQueryService;
    DataRepositorySingleton mDRS;

    LocationManager locationManager;
    LocationListener locationListener;
    HashMap<LatLng, String> loc2UID;

    ArrayList<TutorOffer> offers;

    UserProfile user;

    LatLng edmontonLatLng = new LatLng(53.631611, -113.323975);

    Location lastKnownLocation;

    private HashMap<Integer, Marker> visibleMarkers = new HashMap<>();

    boolean tutorMode = false;

    @Override
    protected void onStart()
    {
        super.onStart();
        loc2UID = new HashMap<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("userProfiles");
        mOfferReference = FirebaseDatabase.getInstance().getReference().child("tutoroffers");

        mUID = mQueryService.GetCurrentUserUID();
        // add the listener here
        mUserProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren())
                {
                    UserProfile profile = messageSnapshot.getValue(UserProfile.class);
                    if (profile.GetUID().equals(mUID))
                    {
                        mDRS.setUserProfile(profile);
                        Log.i("SigninActivity", "Found the right user Profile");
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };

        mOfferListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren())
                {
                    TutorOffer offer = messageSnapshot.getValue(TutorOffer.class);
                    offers.add(offer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(mUserProfileListener);
        mOfferReference.addValueEventListener(mOfferListener);
        UserProfile userProfile = mQueryService.GetUserProfile();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueryService = new QueryService();
        mDRS = DataRepositorySingleton.GetInstance();
        setContentView(R.layout.activity_drawer_layout);

        offers = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new MapsActivity()).commit();
//            navigationView.setCheckedItem(R.id.nav_account);
//        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        start_tutoring_button = (Button) findViewById(R.id.start_tutor);
        start_tutoring_button.setVisibility(View.INVISIBLE);
        start_tutoring_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PopTutorActivity.class);
                startActivity(i);
            }
        });

        studentModeItem = findViewById(R.id.nav_studentMode);



//        popup_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PopActivity.class);
//                startActivity(i);
//            }
//        });

        user = mQueryService.GetUserProfile();

    }

    private void setOnCheckedChangeListener(){
        new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                getCurrentLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        try{
            getCurrentLocation();
        } catch (Exception e){

        }


        moveCameraToUser(edmontonLatLng);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
//                allPoints.add(point);
//                mMap.clear();
/*
                mMap.addMarker(new MarkerOptions().position(point));
                userLists.add(new UserProfile(point));
                userNameNumber++;*/
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker){
                Intent i = new Intent(getApplicationContext(), PopActivity.class);
                i.putExtra("UID", loc2UID.get(marker.getPosition()));
                startActivity(i);

                return true;
            }
        });

    }

    public boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    public void getCurrentLocation(){
        if(checkPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            // TODO: Submit this information to the database
//            ArrayList<String> skillIds = new ArrayList<>();


            UserProfile profile = mQueryService.GetUserProfile();
            profile.setmLat(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
            profile.setmLon(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
//            profile.setmSkillIds(skillIds);

            mDatabaseReference.child("userProfiles").child(profile.GetUID()).setValue(profile);

//            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else{
            requestPermission();
        }
    }

    public void clearMarkers(View view){
        mMap.clear();
    }

//    public void displayMarkers(View view){
//        displayUserCoordinates(userLists);
//    }

    public void addMarker(LatLng latlng){
        mMap.addMarker(new MarkerOptions()
                .position(latlng)

        );
    }

    public void displayUserCoordinates(ArrayList<TutorOffer> listOfUsers){
        Iterator<TutorOffer> it = offers.iterator();
        while(it.hasNext()){
            TutorOffer user = it.next();
            LatLng loc = new LatLng(user.GetLat(), user.GetLon());
            addMarker(loc);
            loc2UID.put(loc, user.GetTutorUID());
        }
    }
/*
    public void updateLocation(){
        clearMap(mMap);
        if(user.updateMyLocation()){
            LatLng myLocation = new LatLng(user.getMyLat(), user.getMyLon());
            mMap.addMarker(new MarkerOptions().position(myLocation).title("MyLocation"));
            moveCameraToUser(myLocation);
        }
    }*/
    public void moveCameraToUser(LatLng latlng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5));

    }

    public void clearMap(){
        mMap.clear();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("a", "ASDASD@@@");
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_account:
                break;
            case R.id.nav_tutorMode:
                tutorMode();
                break;
            case R.id.nav_studentMode:
                studentMode();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void studentMode(){
        tutorMode = false;
        start_tutoring_button.setVisibility(View.INVISIBLE);
        displayUserCoordinates(offers);
    }
    public void tutorMode(){
        tutorMode = true;
        clearMap();
        start_tutoring_button.setVisibility(View.VISIBLE);
    }
}