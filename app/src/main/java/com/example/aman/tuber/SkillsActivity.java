package com.example.aman.tuber;

import android.annotation.SuppressLint;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SkillsActivity extends AppCompatActivity {

    ListView skillListView;
    EditText addSkillNameEditText;
    EditText addSkillDescriptionEditText;
    ArrayList<ColorSpace.Model> ItemModelList;
    ArrayList<Skill> skills;
    SkillListAdapter mSkillListAdapter;
    QueryService mQueryService;


    @Override
    protected void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_info);
        skills = new ArrayList<>();

        mQueryService = new QueryService();

        mSkillListAdapter = new SkillListAdapter(skills, SkillsActivity.this);

        addSkillNameEditText = (EditText) findViewById(R.id.SkillNameEditText);
        addSkillDescriptionEditText = (EditText) findViewById(R.id.skillDescriptionEditText);
        skillListView = (ListView)findViewById(R.id.listview);
        skillListView.setAdapter(mSkillListAdapter);
    }

    public void onAddSkill(View v) {

        String skillName = addSkillNameEditText.getText().toString();
        String skillDescription = addSkillDescriptionEditText.getText().toString();
        //String skillId = mQueryService.GetCurrentUserUID() + skillName;
        String skillId = "Hello" + skillName;
        Skill newSkill = new Skill(skillName, skillDescription, skillId);

        skills.add(newSkill);
        mSkillListAdapter.notifyDataSetChanged();
    }


}
