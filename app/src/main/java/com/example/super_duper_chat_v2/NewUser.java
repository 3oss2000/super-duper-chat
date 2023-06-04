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
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Objects;

public class NewUser extends User {



    public NewUser(){
        super();


        this.firebaseAuth=FirebaseAuth.getInstance();

    }



    public NewUser(String username, String email, String password) {
        super(username,email,password);


        firebaseAuth=FirebaseAuth.getInstance();

    }


    // public boolean signUp(){


    //     firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


    //         @Override
    //         public void onComplete(@NonNull Task<AuthResult> task) {


    //             Log.d("success register",String.valueOf(task.isSuccessful()));

    //             Activity currentActivity =getCurrentActivity();
    //             Activity nextActivity=getNextActivity();

    //             if(task.isSuccessful()){
    //                 Toast.makeText(currentActivity,"SignedUp Successfully",Toast.LENGTH_LONG).show();

    //                 AuthenticatedUser authenticatedUser=new AuthenticatedUser(NewUser.this.username, NewUser.this.email,NewUser.this.password,"");
    //                 FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(authenticatedUser);
                    

    //             }else if(!task.isSuccessful()){


    //                 Log.d("firebase:", Objects.requireNonNull(task.getException()).getLocalizedMessage());
    //                 Toast.makeText(currentActivity,"signUp failed",Toast.LENGTH_LONG).show();

                    

    //             }

    //         }

    //     });
       
    // }
}
