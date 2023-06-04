package com.example.super_duper_chat_v2;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Objects;

public class RegisteredUser extends User  {


    public String profilePic="";



    public RegisteredUser(){
        super();
       this.firebaseAuth=FirebaseAuth.getInstance();
    }



    public RegisteredUser(String username, String email, String password,String profilePic) {

        super(username,email,password);
        this.profilePic=profilePic;
        firebaseAuth=FirebaseAuth.getInstance();

    }



    public void logIn(){


        firebaseAuth.signInWithEmailAndPassword(this.email,this.password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Activity currentActivity=getCurrentActivity();
                Activity nextActivity=getNextActivity();

                if (task.isSuccessful()){


                    Toast.makeText(currentActivity,"Successfully loggedIn",Toast.LENGTH_LONG).show();


                    AuthenticatedUser authenticatedUser=new AuthenticatedUser(RegisteredUser.this.username, RegisteredUser.this.email,RegisteredUser.this.password," ");

                    Intent i= new Intent(currentActivity, nextActivity.getClass());

                    i.putExtra("authUser",authenticatedUser);
                    currentActivity.startActivity(i);
                    currentActivity.finish();



                }else if(!task.isSuccessful()){


                    Log.d("SignUp error", task.getException().getLocalizedMessage());
                    Toast.makeText(currentActivity,"Login Failed",Toast.LENGTH_LONG).show();


                }
            }
        });

    }


}
