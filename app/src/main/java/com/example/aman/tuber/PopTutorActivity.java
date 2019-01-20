package com.example.aman.tuber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;


    ListView skillListView;
    EditText addSkillNameEditText;
    EditText addSkillDescriptionEditText;
    ArrayList<ColorSpace.Model> ItemModelList;
    ArrayList<Skill> skills;
    SkillListAdapter mSkillListAdapter;
    Button submitTutor;

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



        //3333
        ArrayList<String> a = new ArrayList<String>();
        a.add("asd");
        a.add("22");
        //3333

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

//        spinner = findViewById(R.id.skill_spinner);
//        adapter = ArrayAdapter.createFromResource(this, mSkills, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        ArrayAdapter adapter =
//                new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mSkills);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                //get id of the selected item using position 'i'
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        ArrayAdapter skillDropdownAdopter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mSkills);
//        findSkillSpinner.setAdapter(skillDropdownAdopter);
//        skillDropdownAdopter.notifyDataSetChanged();



//        mSkillListAdapter = new SkillListAdapter(new ArrayList<>(mSkills), PopTutorActivity.this);
//        skillListView = (ListView)findViewById(R.id.popup_listview);
//        skillListView.setAdapter(mSkillListAdapter);
//
        submitTutor = (Button) findViewById(R.id.popup_tutor_start);
        submitTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.getmSkillIds().get(0);
                submitTutorOffer(user.GetUID(), mSkills.get(0).GetSkillName(), mSkills.get(0).GetSkillDescription(), user.getmLat(), user.getmLon());
                Intent myIntent = new Intent(PopTutorActivity.this, DrawerActivity.class);
                startActivity(myIntent);
            }
        });

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

    private void submitTutorOffer(String tutorUID, String skillName, String skillDescription, double lat, double lon)
    {
        TutorOffer offer = new TutorOffer(tutorUID, skillName, skillDescription, lat, lon);
        DatabaseReference offerReference = FirebaseDatabase.getInstance().getReference().child("tutoroffers");

        offerReference.child(tutorUID).setValue(offer);
    }

//    public void getSkills(List<String> listOfSkills){
//        this.mSkills = listOfSkills;
//    }
}
