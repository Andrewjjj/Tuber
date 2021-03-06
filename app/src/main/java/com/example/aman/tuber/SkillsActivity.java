package com.example.aman.tuber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SkillsActivity extends AppCompatActivity {

    ListView skillListView;
    EditText addSkillNameEditText;
    EditText addSkillDescriptionEditText;
    ArrayList<ColorSpace.Model> ItemModelList;
    ArrayList<Skill> skills;
    SkillListAdapter mSkillListAdapter;
    QueryService mQueryService;
    Button finishRegister;
    DatabaseReference mDatabaseReference;


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

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(); // this is the root

        mQueryService = new QueryService();

        mSkillListAdapter = new SkillListAdapter(skills, SkillsActivity.this);

        addSkillNameEditText = (EditText) findViewById(R.id.SkillNameEditText);
        addSkillDescriptionEditText = (EditText) findViewById(R.id.skillDescriptionEditText);
        skillListView = (ListView)findViewById(R.id.listview);
        skillListView.setAdapter(mSkillListAdapter);

        finishRegister = (Button) findViewById(R.id.finish_registration);
        finishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Submit this information to the database
                ArrayList<String> skillIds = new ArrayList<>();
                for (Skill skill : skills)
                {
                    mDatabaseReference.child("skills").child(skill.GetSkillId()).setValue(skill);
                    skillIds.add(skill.GetSkillId());
                }
                UserProfile profile = mQueryService.GetUserProfile();
                profile.setmSkillIds(skillIds);

                mDatabaseReference.child("userProfiles").child(profile.GetUID()).setValue(profile);

                Intent myIntent = new Intent(SkillsActivity.this, DrawerActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void onAddSkill(View v) {

        String skillName = addSkillNameEditText.getText().toString();
        String skillDescription = addSkillDescriptionEditText.getText().toString();
        String skillId = mQueryService.GetCurrentUserUID() + skillName;
        Skill newSkill = new Skill(skillName, skillDescription, skillId);

        skills.add(newSkill);
        mSkillListAdapter.notifyDataSetChanged();
    }


}
