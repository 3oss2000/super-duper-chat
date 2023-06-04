package com.example.super_duper_chat_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class signup extends AppCompatActivity {
    @Exclude
    public FirebaseAuth firebaseAuth;

    TextInputLayout usernameView;
    TextInputLayout emailView;
    TextInputLayout passwordView;
    TextInputLayout password2View;
    Button signButton;
    //TextView textAccountCheckerView;
    //boolean isSigningUp=true;

    //boolean haveAccount=false;
    NewUser newUser;

    // @Override
    // public void onBackPressed() {
    //     moveTaskToBack(false);
    //     //Parameter boolean nonRoot - If false then this only works if the activity is the root of a task; if true it will work for any activity in a task.
    // }

    private Boolean validateUsername() {
        String val = usernameView.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            usernameView.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            usernameView.setError("White Spaces are not allowed");
            return false;
        } else {
            usernameView.setError(null);
            usernameView.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = emailView.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            emailView.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailView.setError("Invalid email address");
            return false;
        } else {
            emailView.setError(null);
            emailView.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val1 = passwordView.getEditText().getText().toString();
        String val2 = password2View.getEditText().getText().toString();

        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val1.isEmpty()) {
            passwordView.setError("Field cannot be empty");
            return false;
        } else if (val2.isEmpty()) {
            password2View.setError("Field cannot be empty");
            return false;
        } else if (!val1.matches(passwordVal)) {
            passwordView.setError("Password is too weak\n- Your password must contain at least one digit.\n" +
                    "- Your password must contain at least one lower case letter.\n" +
                    "- Your password must contain at least one upper case letter.\n" +
                    "- Your password must contain at least one special character such as @, #, $, %, ^, &amp;, +, or =.\n" +
                    "- Your password must not contain any white spaces.\n" +
                    "- Your password must be at least 4 characters long.");
            return false;
        } else if (!val1.matches(val2)) {
            password2View.setError("Password doesn't match");
            return false;
        } else {
            passwordView.setError(null);
            password2View.setErrorEnabled(false);
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);


        usernameView = findViewById(R.id.email);
        emailView = findViewById(R.id.user_email);
        passwordView = findViewById(R.id.user_password);
        signButton = findViewById(R.id.signup_button);
        password2View = findViewById(R.id.password2);

        signButton.setOnClickListener(view -> {
            if (!validateUsername() || !validateEmail() || !validatePassword()) {
                return;
            } else {
                handleSignup();
            }
        });
    }


    private void handleSignup() {
        String username = usernameView.getEditText().getText().toString();
        String email = emailView.getEditText().getText().toString();
        String password = passwordView.getEditText().getText().toString();
        if (password.isEmpty() || email.isEmpty() || (username.isEmpty())) {

            Toast.makeText(signup.this, "Invalid input", Toast.LENGTH_LONG).show();
            return;
        }
        // newUser =new NewUser(username,email,password);
        // newUser.setCurrentActivity( this);
        // newUser.setNextActivity(new login());
        // if(newUser.signUp()){

        //     Intent i=new Intent(this,login.class);
        //     startActivity(i);
        //     finish();
        // }
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                Log.d("success register", String.valueOf(task.isSuccessful()));

//                Activity currentActivity =getCurrentActivity();
//                Activity nextActivity=getNextActivity();

                if (task.isSuccessful()) {
                    Toast.makeText(signup.this, "SignedUp Successfully", Toast.LENGTH_LONG).show();

                    AuthenticatedUser authenticatedUser = new AuthenticatedUser(username, email, password, "");
                    FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(authenticatedUser);


                    Intent i = new Intent(signup.this, login.class);
                    startActivity(i);
                    finish();

                } else if (!task.isSuccessful()) {


                    Log.d("firebase:", Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    Toast.makeText(signup.this, "signUp failed", Toast.LENGTH_LONG).show();


                }

            }

        });


    }

}