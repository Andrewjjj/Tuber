package com.example.aman.tuber;

public class TutorOffer
{
    String mTutorUID;
    String mSkillName;
    String mSkillDescription;

    double mLat;
    double mLon;

    public TutorOffer() {}
    public TutorOffer(String tutorUID, String skillName, String skillDescription, double lat, double lon)
    {
        mTutorUID = tutorUID;
        mSkillName = skillName;
        mSkillDescription = skillDescription;
        mLat = lat;
        mLon = lon;
    }

    public String GetTutorUID() { return mTutorUID; }
    public String GetSkillName() { return mSkillName; }
    public String GetSkillDescription() { return mSkillDescription; }
    public double GetLat() { return mLat; }
    public double GetLon() { return mLon; }

    public void SetTutorUID(String tutorUID) { mTutorUID = tutorUID; }
    public void SetSkillName(String skillName) { mSkillName = skillName; }
    public void SetSkillDescription(String skillDescription) { mSkillDescription = skillDescription; }
    public void SetLat(double lat) { mLat = lat; }
    public void SetLon(double) { mLon = lon; }
}
