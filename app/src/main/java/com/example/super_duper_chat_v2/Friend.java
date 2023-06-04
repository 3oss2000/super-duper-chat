package com.example.super_duper_chat_v2;

import android.util.Log;

import java.io.Serializable;

public class Friend extends RegisteredUser implements Serializable{


    @Override
    public void logIn() {
        System.out.println(" Cannot login from friend instance");
    }


}