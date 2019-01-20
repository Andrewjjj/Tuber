package com.example.aman.tuber;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GatherNameActivity extends AppCompatActivity {

    QueryService mQueryService;
    EditText emailEditText;
    EditText nameEditText;
    EditText phoneEditText;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather_name);
        mQueryService = new QueryService();
        mDatabase = FirebaseDatabase.getInstance().getReference(); // this is the root

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        nameEditText  = (EditText) findViewById(R.id.NameEditText);
        phoneEditText  = (EditText) findViewById(R.id.PhoneEditText);
        emailEditText.setEnabled(false);
        emailEditText.setText(mQueryService.GetUserEmail());


    }

    public void ClickContinue(View view)
    {
        String nameText = nameEditText.getText().toString();
        if(nameText.isEmpty() || nameText.length() == 0 || nameText.equals("") || nameText == null)
        {
            String emptyNameError = "Name is blank. Please enter your full name to proceed";
            Snackbar.make(findViewById(android.R.id.content), emptyNameError, Snackbar.LENGTH_SHORT).show();
        }

        else
        {
            // Saving the UserProfile
            UserProfile userProfile = mQueryService.GetUserProfile();

            userProfile.setmName(nameText);
            userProfile.setmPhone(phoneEditText.getText().toString());
            mDatabase.child("userProfiles").child(userProfile.GetUID()).setValue(userProfile);
            // TODO: Go to new activity
            Snackbar.make(findViewById(android.R.id.content), "Going to new activity", Snackbar.LENGTH_SHORT).show();

            startActivity(new Intent(this, AssignProfilePictureActivity.class));
        }
    }
}
