package com.example.aman.tuber;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class AssignProfilePictureActivity extends AppCompatActivity {


    ImageView ProfileImage;
    Uri uri = null;
    private StorageReference mStorageRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_profile_picture);

        ProfileImage = (ImageView) findViewById(R.id.profileImageView);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void UploadPhoto(View view){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to upload from gallery or take a camera picture?");
        alertDialogBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UploadImage.CheckPermissionsCamera(AssignProfilePictureActivity.this);
            }
        });
        alertDialogBuilder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UploadImage.CheckPermissionsGallery(AssignProfilePictureActivity.this);
            }
        });
        alertDialogBuilder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                ProfileImage.setImageBitmap(image);


                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), image, "Title", null);
                uri = Uri.parse(path);

//                uri = data.getData();


            } catch (NullPointerException ex) {
                Toast.makeText(this, "Unable to upload photo. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {

            Uri imageSelected = data.getData();

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(imageSelected));
                ProfileImage.setImageBitmap(bitmap);

                uri = imageSelected;

            } catch (NullPointerException ex) {
                Toast.makeText(this, "Unable to upload photo. Please try again.",
                        Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException ex) {
                Toast.makeText(this, "Unable to upload photo. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void storeInFirebase(Uri uri){
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(uri);
//        StorageReference storageRef = storage.getReference();
//        storageRef.putFile(uri);

//        StorageReference ref = mStorageRef.child("images/"+ uri.toString());

        if(uri==null){
            Toast.makeText(this, "URI is null", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference ref = mStorageRef.child("images/" + firebaseAuth.getCurrentUser().getUid());

        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(AssignProfilePictureActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(AssignProfilePictureActivity.this, "Could not save file", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    UploadImage.UploadFromCamera(this);
                }
                else {
                    // User denied the message do Nothing
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    UploadImage.UploadFromGallery(this);
                } else {
                    // User denied the message do Nothing
                }
                break;
        }
    }

    public void Save(View view){
        storeInFirebase(uri);
    }
}
