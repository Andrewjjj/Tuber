package com.example.aman.tuber;

import android.os.Bundle;
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

    }

    public void SignUp(View view){

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

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                            DataRepositorySingleton singleton = DataRepositorySingleton.GetInstance() ;

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            singleton.SetUser(firebaseUser);

                            UserProfile user = new UserProfile(email);
                            singleton.setUserProfile(user);

                            // add to the database
                            addUser(user);

                        }else{
                            Toast.makeText(LoginActivity.this, "Could Not Register. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUser(UserProfile userProfile){

        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our User
        String id = databaseUserProfile.push().getKey();

        // Saving the UserProfile
        databaseUserProfile.child(id).setValue(userProfile);

        //displaying a success toast
        Toast.makeText(this, "added to database", Toast.LENGTH_LONG).show();

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

                            singleton.setUserProfile( new UserProfile(email) );

                            // close this activity
//                            finish();TODO
                            // start the log in activity
//                            TODO:
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Could Not Sign In. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}