package com.example.aman.tuber;

public class Skill
{
    String mSkillName;
    String mSkillDescription;
    String mSkillId; // this is UID+SkillName;

    public Skill(){}

    public Skill(String skillName, String skillDescription, String skillId)
    {
        mSkillName = skillName;
        mSkillDescription = skillDescription;
        mSkillId = skillId;
    }

    public String GetSkillName() { return mSkillName; }
    public String GetSkillDescription() { return mSkillDescription; }
    public String GetSkillId() { return mSkillId; }

    public void SetSkillName(String skillName) { mSkillName = skillName; }
    public void SetSkillDescription(String skillDescription) { mSkillDescription = skillDescription; }
    public void SetSkillId(String skillId) { mSkillId = skillId; }
}
