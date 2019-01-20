package com.example.aman.tuber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        emailEditText = (EditText) findViewById(R.id.registerEmailEditText);
        passwordEditText  = (EditText) findViewById(R.id.PasswordEditText);
        confirmPasswordEditText  = (EditText) findViewById(R.id.ConfirmPasswordEditText);

        mDatabase = FirebaseDatabase.getInstance().getReference(); // this is the root
    }

    public void OnClickRegister(View view)
    {
        final String passwordText = passwordEditText.getText().toString();
        final String confirmPasswordText = confirmPasswordEditText.getText().toString();
        final String emailText = emailEditText.getText().toString();

        if (isEditTextEmpty(emailEditText) || isEditTextEmpty(passwordEditText) || isEditTextEmpty(confirmPasswordEditText))
        {
            String emptyEditTextError = "Please enter all fields";
            Snackbar.make(findViewById(android.R.id.content), emptyEditTextError, Snackbar.LENGTH_SHORT).show();
        }

        else
        {
            if (passwordText.equals(confirmPasswordText))
            {
                AttemptRegistration(emailText, passwordText);
            }
            else
            {
                String message = "Password needs to match confirm password";
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isEditTextEmpty(EditText editText)
    {
        String text = editText.getText().toString();
        return (text.isEmpty() || text.length() == 0 || text.equals("") || text == null);
    }

    private void AttemptRegistration(final String email, final String password)
    {
        firebaseAuth = FirebaseAuth.getInstance();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            DataRepositorySingleton DRS = DataRepositorySingleton.GetInstance() ;

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            DRS.SetUser(firebaseUser);

                            UserProfile userProfile = new UserProfile(firebaseUser.getUid(), email, "", "", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 0.0, 0.0);
                            DRS.setUserProfile(userProfile);

                            // add to the database
                            addUserToFireBase(userProfile);

                            // launch the next activity
                            Intent myIntent = new Intent(RegistrationActivity.this, GatherNameActivity.class);
                            RegistrationActivity.this.startActivity(myIntent);

                        }else
                            {
                                String message = "Failed to create user: " + task.getException().getMessage();
                                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUserToFireBase(UserProfile userProfile)
    {
        // Saving the UserProfile
        String logMessage = "Entered addUserToFireBase with userProfile:Email: " + userProfile.GetUserProfileEmail();
        Log.i("Registration", logMessage);
        mDatabase.child("userProfiles").child(userProfile.GetUID()).setValue(userProfile);
    }

}
