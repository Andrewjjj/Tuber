package com.example.aman.tuber;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class UserProfile
{
    /* This should store all the relevant information
        for a user account. This object will be stored in both
        FireBase and the app

        When we sign in we populate the UserProfile with the
        appropriate data
     */

    private String mUID;
    private String mName;
    private String mEmail;
    private String mPhone;
    private String mProfilePictureId;
    private ArrayList<String> mCourseIds; // courses the user is capable of teaching
    private ArrayList<String> mSkillIds; // skills the user is capable of teaching
    private Location mCurrentLocation; // TODO: we have to figure out when to update this

    private ArrayList<String> mActiveTransactionIds;
    private ArrayList<String> mPendingTransactionIds;
    private ArrayList<String> mPastTransactionsIds;

    private double mLat;
    private double mLon;

    public void setLatLon(double lat, double lon){
        this.mLat = lat;
        this.mLon = lon;
    }

    public Location getLocationInfo(){
        return mCurrentLocation;
    }

    public LatLng getMyLatLng(){
        return new LatLng(mLat, mLon);
    }

    public double getMyLat(){
        return mLat;
    }

    public double getMyLon(){
        return mLon;
    }



    /*public void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    } */

    public UserProfile(String UID, String email)
    {
        mUID = UID;
        mEmail = email;
    }


    public String GetUID()
    {
        return mUID;
    }

    public String GetUserProfileEmail()
    {
        return mEmail;
    }
}
