package com.example.super_duper_chat_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = findViewById(R.id.login);

        login_button.setOnClickListener(view -> {
            Intent login_intent = new Intent(this, login.class);
            startActivity(login_intent);
        });

        signup_button = findViewById(R.id.signup);

        signup_button.setOnClickListener(view -> {
            Intent signup_intent = new Intent(this, signup.class);
            startActivity(signup_intent);
        });

    }
}