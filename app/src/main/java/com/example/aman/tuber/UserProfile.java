package com.example.aman.tuber;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class UserProfile
{
    /* This should store all the relevant information
        for a user account. This object will be stored in both
        FireBase and the app

        When we sign in we populate the UserProfile with the
        appropriate data
     */

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    String mUID;
    String mName;
    String mEmail;

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    String mPhone;

    public String getmProfilePictureId() {
        return mProfilePictureId;
    }

    public void setmProfilePictureId(String mProfilePictureId) {
        this.mProfilePictureId = mProfilePictureId;
    }

    String mProfilePictureId;

    public List<String> getmCourseIds() {
        return mCourseIds;
    }

    public void setmCourseIds(List<String> mCourseIds) {
        this.mCourseIds = mCourseIds;
    }

    List<String> mCourseIds; // courses the user is capable of teaching
    /*List<String> mSkillIds; // skills the user is capable of teaching
    //private Location mCurrentLocation; // TODO: we have to figure out when to update this

    List<String> mActiveTransactionIds;
    List<String> mPendingTransactionIds;
    List<String> mPastTransactionsIds;

    double mLat;
    double mLon;*/

    public LatLng getMyLatLng(){
        return new LatLng(0, 0);
    }



    /*public void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    } */

    public UserProfile() {}

    public UserProfile(String UID, String email, String name, String phone, String profilePictureId, List<String> courseIds)
    {
        mUID = UID;
        mEmail = email;
        mName = name;
        mPhone = phone;
        mProfilePictureId = profilePictureId;
        mCourseIds = courseIds;
        mCourseIds.add("Something");
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
