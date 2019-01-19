package com.example.aman.tuber;


public class QueryService
{
    /* This is where every activity will go to
    obtain/modify any non-trivial business data
    This class is responsible for talking to the Singleton
    and FireBase so add any relevant methods here
     */
    private DataRepositorySingleton mDRS;

    public QueryService()
    {
        mDRS = DataRepositorySingleton.GetInstance();
    }

    public String GetUserEmail()
    {
        // at this point we assume the user is signed into FireBase
        return mDRS.GetUser().getEmail();
    }


    public void SignUp(String Email, String password){

    }

    public void SignIn(String Email, String password){

    }
}
