package com.example.aman.tuber;

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


    // lazy construction of the instance
    public static DataRepositorySingleton GetInstance()
    {
        if (instance == null) instance = new DataRepositorySingleton();
        return instance;
    }
}
