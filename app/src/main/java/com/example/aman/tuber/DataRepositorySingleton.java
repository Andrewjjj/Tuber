package com.example.aman.tuber;

import com.google.firebase.auth.FirebaseUser;

public class DataRepositorySingleton
{
    /*
        This is the Singleton that stores all the business data
        for the app. Everything that we need to store during execution
        will be stored here and everything we can afford to keep in FireBase
        should stay there

     */

    private static DataRepositorySingleton instance = null;

    protected DataRepositorySingleton() {}

    private FirebaseUser mUser;


    // lazy construction of the instance
    public static DataRepositorySingleton GetInstance()
    {
        if (instance == null) instance = new DataRepositorySingleton();
        return instance;
    }

    public void SetUser(FirebaseUser user)
    {
        mUser = user;
    }

    public FirebaseUser GetUser()
    {
        return mUser;
    }
}
