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

    ListView listView;
    EditText editText;
    ArrayList<ColorSpace.Model> ItemModelList;
    Skill skill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_info);

    }

    @SuppressLint("NewApi")
    public void addSkill(View v)    {
        String skillName = editTextView.getText().toString();

    }


}
