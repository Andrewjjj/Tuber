package com.example.aman.tuber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;

    //our database reference object
    DatabaseReference databaseUserProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.userIdInput);
        editTextPassword = (EditText) findViewById(R.id.userPasswordInput);

        databaseUserProfile = FirebaseDatabase.getInstance().getReference("UserProfiles");

        OneSignal.startInit(this).init();

    }

    public void SignUp(View view)
    {
        // launch the registration flow
        Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    public void SignIn(View view){
        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successfull
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

                            DataRepositorySingleton singleton = DataRepositorySingleton.GetInstance() ;

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            singleton.SetUser(firebaseUser);

                            OneSignal.sendTag("User_ID",email);


                            // launch drawer activity now
                            // drawer activity will take care of getting the right user object
                            // launch the registration flow

                            Intent myIntent = new Intent(LoginActivity.this, DrawerActivity.class);
                            LoginActivity.this.startActivity(myIntent);

                        }
                        else{
                            String message = "Sign in failed: " + task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}