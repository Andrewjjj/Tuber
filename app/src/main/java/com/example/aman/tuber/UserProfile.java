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



    String mUID;
    String mName;
    String mEmail;
    String mPhone;
    String mProfilePictureId;
    List<String> mCourseIds; // courses the user is capable of teaching
    List<String> mSkillIds; // skills the user is capable of teaching
    //private Location mCurrentLocation; // TODO: we have to figure out when to update this

    List<String> mActiveTransactionIds;
    List<String> mPendingTransactionIds;
    List<String> mPastTransactionsIds;
    double mLat;
    double mLon;

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmProfilePictureId() {
        return mProfilePictureId;
    }

    public void setmProfilePictureId(String mProfilePictureId) {
        this.mProfilePictureId = mProfilePictureId;
    }


    public List<String> getmCourseIds() {
        return mCourseIds;
    }

    public void setmCourseIds(List<String> mCourseIds) {
        this.mCourseIds = mCourseIds;
    }

    public List<String> getmSkillIds() {
        return mSkillIds;
    }

    public void setmSkillIds(List<String> mSkillIds) {
        this.mSkillIds = mSkillIds;
    }

    public List<String> getmActiveTransactionIds() {
        return mActiveTransactionIds;
    }

    public void setmActiveTransactionIds(List<String> mActiveTransactionIds) {
        this.mActiveTransactionIds = mActiveTransactionIds;
    }

    public List<String> getmPendingTransactionIds() {
        return mPendingTransactionIds;
    }

    public void setmPendingTransactionIds(List<String> mPendingTransactionIds) {
        this.mPendingTransactionIds = mPendingTransactionIds;
    }

    public List<String> getmPastTransactionsIds() {
        return mPastTransactionsIds;
    }

    public void setmPastTransactionsIds(List<String> mPastTransactionsIds) {
        this.mPastTransactionsIds = mPastTransactionsIds;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLon() {
        return mLon;
    }

    public void setmLon(double mLon) {
        this.mLon = mLon;
    }

    public LatLng getMyLatLng(){
        return new LatLng(0, 0);
    }



    /*public void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    } */

    public UserProfile() {}

    public UserProfile(String UID, String email, String name, String phone, String profilePictureId, List<String> courseIds, List<String> skillIds, List<String> activeTransactions, List<String> pendingTransactions, List<String> pastTransactions, double lat, double lon)
    {
        mUID = UID;
        mEmail = email;
        mName = name;
        mPhone = phone;
        mProfilePictureId = profilePictureId;
        mCourseIds = courseIds;
        mSkillIds = skillIds;
        mPendingTransactionIds = pendingTransactions;
        mActiveTransactionIds = activeTransactions;
        mPastTransactionsIds = pastTransactions;
        mLat = lat;
        mLon = lon;
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
