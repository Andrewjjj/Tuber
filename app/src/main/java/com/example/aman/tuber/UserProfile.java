package com.example.aman.tuber;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;

public class UserProfile extends FragmentActivity {
    /* This should store all the relevant information
        for a user account. This object will be stored in both
        FireBase and the app

        When we sign in we populate the UserProfile with the
        appropriate data
     */


    private String mName;
    private String mEmail;
    private String mPhone;
    private String mProfilePictureId;
    private ArrayList<Subject> mCourseList; // courses the user is capable of teaching
    private ArrayList<Subject> mSkillList; // skills the user is capable of teaching
    private Location mCurrentLocation; // TODO: we have to figure out when to update this

    private ArrayList<Transaction> mActiveTransactions;
    private ArrayList<Transaction> mPendingTransaction;
    private ArrayList<Transaction> mPastTransactions;

    public LocationManager locationManager;
    public LocationListener locationListener;

    public UserProfile(LocationManager locationManager, LocationListener locationListener) {
        locationManager = this.locationManager;
        locationListener = this.locationListener;
    }

    public UserProfile(String email){
        mEmail = email;
    }

    public UserProfile(){
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestLocationPermission();
            return false;
        }
    }

    public boolean updateMyLocation() {
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Don't have permission!", Toast.LENGTH_LONG);
            return false;
        }
    }

    public Location getLocationInfo(){
        return mCurrentLocation;
    }

    public double getMyLat(){
        return mCurrentLocation.getLatitude();
    }

    public double getMyLon(){
        return mCurrentLocation.getLongitude();
    }

    public void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

}
