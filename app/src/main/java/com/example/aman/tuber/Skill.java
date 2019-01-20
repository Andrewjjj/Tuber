package com.example.aman.tuber;

public class Skill
{
    String mSkillName;
    String mSkillDescription;

    public Skill(){}

    public Skill(String skillName, String skillDescription)
    {
        mSkillName = skillName;
        mSkillDescription = skillDescription;
    }

    public String GetSkillName() { return mSkillName; }
    public String GetSkillDescription() { return mSkillDescription; }

    public void SetSkillName(String skillName) { mSkillName = skillName; }
    public void SetSkillDescription(String skillDescription) { mSkillDescription = skillDescription; }
}
