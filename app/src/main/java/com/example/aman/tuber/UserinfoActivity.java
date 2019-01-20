package com.example.aman.tuber;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class UserinfoActivity extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    private LinearLayout parentLinearLayout2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        parentLinearLayout = findViewById(R.id.parent_linear_layout);
        parentLinearLayout2 = findViewById(R.id.parent_linear_layout2);
    }

    public void onAdd(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onAdd2(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field2, null);
        // Add the new row before the add field button.
        parentLinearLayout2.addView(rowView, parentLinearLayout2.getChildCount() - 1);
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void onDelete2(View v) { parentLinearLayout2.removeView((View) v.getParent()); }
}