package com.example.super_duper_chat_v2;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class AuthenticatedUser extends RegisteredUser implements Serializable {


    public ArrayList<Friend> friends=new ArrayList<>();
    public ArrayList<Message> messages=new ArrayList<>();

  @Exclude public UserSettings settings;

      public AuthenticatedUser() {
        super();
        this.fetchAuthenticatedUserEmail();
        settings=new UserSettings();


      }

    public AuthenticatedUser(String username, String email,String password,String profilePic) {
        super(username, email,password,profilePic);
        this.fetchAuthenticatedUserEmail();
        settings=new UserSettings();


    }







    @Exclude public static boolean isThereCurrentUser(){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Log.d("isThereCurrentUser: ",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            return true;
        }
        return false;

    }

    @Exclude public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }


    @Exclude public void fetchAuthenticatedUserEmail(){
        this.email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }



   @Exclude public void fetchDataAndFriends(){



        friends=new ArrayList<>();

       this.email=FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    // in order not to add current user in list of friends
                    if(!Objects.equals(Objects.requireNonNull(dataSnapshot.getValue(Friend.class)).email, AuthenticatedUser.this.email)) {
                     friends.add(dataSnapshot.getValue(Friend.class));
                    }else{

                       AuthenticatedUser.this.username= dataSnapshot.getValue(AuthenticatedUser.class).username;
                       AuthenticatedUser.this.profilePic=dataSnapshot.getValue(AuthenticatedUser.class).profilePic;



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }


        });


   }

//post message to firebase
    public void sendMessage(String chatRoomId,String receiverEmail,String messageContent){

          Message messageToBeSent=new Message(this.email,receiverEmail,messageContent);

        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).push().setValue(messageToBeSent);




    }

//add the message to the arrayList messages to be shown on the screen
    public void addMessage(Message message){

          this.messages.add(message);

    }




}
