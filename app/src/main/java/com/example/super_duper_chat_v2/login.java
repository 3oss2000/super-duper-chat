package com.example.super_duper_chat_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
//    EditText usernameView;
    TextInputLayout emailView;
    TextInputLayout passwordView ;
    Button signButton ;
    RegisteredUser registeredUser;
    Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailView=findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        signButton = findViewById(R.id.login);
        cancelButton = findViewById(R.id.cancel);

        if(AuthenticatedUser.isThereCurrentUser()){

            Intent i=new Intent(this,friends.class);

            AuthenticatedUser authenticatedUser=new AuthenticatedUser();
            i.putExtra("authUser",authenticatedUser);

            startActivity(i);

            finish();

        }

        signButton.setOnClickListener(view -> {
            handleLogIn();
        });
        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }

    
private void handleLogIn(){

        String email=emailView.getEditText().getText().toString();
        String password=passwordView.getEditText().getText().toString();

        if(password.isEmpty() || email.isEmpty()){

            Toast.makeText(this,"Invalid input",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            registeredUser =new RegisteredUser("",email,password,"");
            registeredUser.setCurrentActivity(this);
            registeredUser.setNextActivity(new friends());
            registeredUser.logIn();
        }
    }
}