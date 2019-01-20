package com.example.aman.tuber;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PopTutorActivity extends Activity {

    Button close_button;
    Button send_request_button;


    ValueEventListener mSkillListener;
    DatabaseReference mDatabaseReference;
    String mUID;
    QueryService mQueryService;
    DataRepositorySingleton mDRS;

    List<Skill> mSkills;

    UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_tutor);

        mSkills = new ArrayList<>();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        close_button = (Button) findViewById(R.id.popup_tutor_cancel);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send_request_button = (Button) findViewById(R.id.popup_tutor_start);
        send_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_request();
            }
        });

        getWindow().setLayout((int) (width * .7), (int) (height * .6));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        QueryService mQueryService = new QueryService();
        user = mQueryService.GetUserProfile();

        // TODO: Check if its empty
//        mSkills = user.getmSkillIds();

        Spinner findSkillSpinner = findViewById(R.id.skill_spinner);
        ArrayList<String> a = new ArrayList<String>();
        a.add("asd");
        a.add("22");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("skills");
        mSkillListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Skill skill = messageSnapshot.getValue(Skill.class);
                    if (user.getmSkillIds().contains(skill.GetSkillId())) {
                        mSkills.add(skill);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mDatabaseReference.addListenerForSingleValueEvent(mSkillListener);
        ArrayAdapter skillDropdownAdopter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, a);
        findSkillSpinner.setAdapter(skillDropdownAdopter);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mSkillListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Skill skill = messageSnapshot.getValue(Skill.class);
                    if (user.getmSkillIds().contains(skill.GetSkillId())) {
                        mSkills.add(skill);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mDatabaseReference.addListenerForSingleValueEvent(mSkillListener);
    }

    public void send_request(){
        Toast.makeText(getApplicationContext(), "SendRequest", Toast.LENGTH_SHORT);
    }

    private void SubmitTutorOffer(String tutorUID, String skillName, String skillDescription, double lat, double lon)
    {
        TutorOffer offer = new TutorOffer(tutorUID, skillName, skillDescription, lat, lon);
        DatabaseReference offerReference = FirebaseDatabase.getInstance().getReference().child("tutoroffers");

        offerReference.child(tutorUID).setValue(offer);
    }

//    public void getSkills(List<String> listOfSkills){
//        this.mSkills = listOfSkills;
//    }
}
