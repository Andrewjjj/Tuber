package com.example.aman.tuber;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout drawer;
    private String testVar = "1";
    private GoogleMap mMap;

    UserProfile user = new UserProfile();
    ArrayList<UserProfile> userLists = new ArrayList<>();


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
                mMap.addMarker(new MarkerOptions().position(point));
                userLists.add(new UserProfile(point));
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

    public void updateLocation(){
        clearMap(mMap);
        if(user.updateMyLocation()){
            LatLng myLocation = new LatLng(user.getMyLat(), user.getMyLon());
            mMap.addMarker(new MarkerOptions().position(myLocation).title("MyLocation"));
            moveCameraToUser(myLocation);
        }
    }
    public void moveCameraToUser(LatLng latlng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5));
    }

    public void clearMap(GoogleMap map){
        map.clear();
    }
}