package com.example.aman.tuber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import java.util.Iterator;

public class DrawerActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private DrawerLayout drawer;
    private String testVar = "1";
    private GoogleMap mMap;
    private int userNameNumber = 0;
    Button popup_button;

    ArrayList<UserProfile> userLists = new ArrayList<>();

    ValueEventListener mUserProfileListener;
    DatabaseReference mDatabaseReference;
    String mUID;
    QueryService mQueryService;
    DataRepositorySingleton mDRS;

    @Override
    protected void onStart()
    {
        super.onStart();
        mQueryService = new QueryService();
        mDRS = DataRepositorySingleton.GetInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("userProfiles");

        mUID = mQueryService.GetCurrentUserUID();
        // add the listener here
        mUserProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren())
                {
                    if (messageSnapshot.getKey().equals(mUID))
                    {
                        UserProfile profile = messageSnapshot.getValue(UserProfile.class);
                        mDRS.setUserProfile(profile);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(mUserProfileListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        popup_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PopActivity.class);
//                startActivity(i);
//            }
//        });

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

//        updateLocation();
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
                Log.i("asd", "222222");
                Intent i = new Intent(getApplicationContext(), PopActivity.class);
                startActivity(i);
                return true;
            }
        });
    }

    public void clearMarkers(View view){
        mMap.clear();
    }

    public void displayMarkers(View view){
        displayUserCoordinates(userLists);
    }

    public void addMarker(LatLng latlng){
        mMap.addMarker(new MarkerOptions().position(latlng));
    }

    public void displayUserCoordinates(ArrayList<UserProfile> listOfUsers){
        Iterator<UserProfile> it = listOfUsers.iterator();
        while(it.hasNext()){
            UserProfile user = it.next();
            addMarker(user.getMyLatLng());
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

    public void clearMap(GoogleMap map){
        map.clear();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("a", "ASDASD@@@");
        return false;
    }
}