package com.example.super_duper_chat_v2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.super_duper_chat_v2.profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class UserSettings implements Serializable {


    private Uri imagePath;
    private Activity activity;


    public UserSettings(){}

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public Activity getActivity(){return this.activity;}

    public void getPhotoFromUserDevice(){

      Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);


    }



   public Bitmap createImageBitmap(){
        Bitmap bitmap = null;

        try {
            bitmap= MediaStore.Images.Media.getBitmap(activity.getContentResolver(),imagePath);

        }catch (IOException ioException){
            ioException.printStackTrace();
        }


        return bitmap;
    }




    public void uploadImage(){



        ProgressDialog progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Uploading....");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){

                    //after image is uploadded succecfully we want to get the download url of our image in firebase and assign it to user profile picture field

                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            updateProfilePicture(task.getResult().toString());

                        }
                    });

                    Toast.makeText(UserSettings.this.activity,"Image Uploaded",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(UserSettings.this.activity,"Uploading failed",Toast.LENGTH_LONG).show();

                }

                progressDialog.dismiss();


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double  progressDone=100* (snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded"+progressDone+"%");

            }
        });








    }


    private void updateProfilePicture(String url){

        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profilePic").setValue(url);



    }






    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }


    public Uri getImagePath() {
        return imagePath;
    }



}
