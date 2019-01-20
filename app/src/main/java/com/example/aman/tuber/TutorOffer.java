package com.example.aman.tuber;

public class TutorOffer
{
    String mTutorUID;
    String mSkillName;
    String mSkillDescription;

    public TutorOffer() {}
    public TutorOffer(String tutorUID, String skillName, String skillDescription)
    {
        mTutorUID = tutorUID;
        mSkillName = skillName;
        mSkillDescription = skillDescription;
    }

    public String GetTutorUID() { return mTutorUID; }
    public String GetSkillName() { return mSkillName; }
    public String GetSkillDescription() { return mSkillDescription; }

    public void SetTutorUID(String tutorUID) { mTutorUID = tutorUID; }
    public void SetSkillName(String skillName) { mSkillName = skillName; }
    public void SetSkillDescription(String skillDescription) { mSkillDescription = skillDescription; }
}
