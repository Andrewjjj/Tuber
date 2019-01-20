package com.example.aman.tuber;

public class SmallClass
{
    String UID;
    String email;

    public SmallClass(String UID, String email)
    {
        this.UID = UID;
        this.email = email;
    }

    public SmallClass() {}

    public void SetUID(String UID)
    {
        this.UID = UID;
    }

    public void SetEmail(String Email)
    {
        this.email = Email;
    }

    public String GetEmail()
    {
        return this.email;
    }

    public String GetUID()
    {
        return this.UID;
    }
}
