package com.example.aman.tuber;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    UserProfile user = new UserProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        updateLocation();

        Log.i("asd","AS@@@@@@@@@@@@@@@@@@D23123123");


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

    public void testData(){

    }

}
