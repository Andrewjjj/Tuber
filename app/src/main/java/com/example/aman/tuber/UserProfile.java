package com.example.aman.tuber;

import android.location.Location;

import java.util.ArrayList;

public class UserProfile
{
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

    public UserProfile(String email){
        mEmail = email;
    }
}
