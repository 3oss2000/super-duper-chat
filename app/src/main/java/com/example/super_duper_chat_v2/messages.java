package com.example.super_duper_chat_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.super_duper_chat_v2.MessageAdapter;
import com.example.super_duper_chat_v2.AuthenticatedUser;
import com.example.super_duper_chat_v2.Friend;
import com.example.super_duper_chat_v2.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class messages extends AppCompatActivity {



    private RecyclerView recyclerView;
    private EditText editMessageText;
    private TextView txtChattingWith;
    private ProgressBar progressBar;
    private ImageView friendImagetoolBar;
    private ImageView sendMessageImg;
    private AuthenticatedUser authenticatedUser;


    private MessageAdapter messageAdapter;


    String roomMateUserName,chatRoomId,roomMateEmail,roomMateImg,myImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        recyclerView=findViewById(R.id.recyclerMessages);
        editMessageText=findViewById(R.id.editText);
        txtChattingWith =findViewById(R.id.txt_chatting_with);
        progressBar=findViewById(R.id.progressMessagesBar);

        sendMessageImg=findViewById(R.id.imgSendMessage);
        friendImagetoolBar=findViewById(R.id.img_toolbar);

        authenticatedUser= (AuthenticatedUser) getIntent().getSerializableExtra("authenticatedUser");

        authenticatedUser.fetchDataAndFriends();

        roomMateEmail=getIntent().getStringExtra("emailOfRoomMate");
        roomMateImg=getIntent().getStringExtra("imgOfRoomMate");
        myImg=getIntent().getStringExtra("myImg");




        sendMessageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authenticatedUser.sendMessage(chatRoomId,roomMateEmail,editMessageText.getText().toString());
                editMessageText.setText("");
            }
        });


        roomMateUserName=getIntent().getStringExtra("usernameOfRoomMate");


        txtChattingWith.setText(roomMateUserName);


        messageAdapter=new MessageAdapter(authenticatedUser.messages,myImg,roomMateImg,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);


        Glide.with(this).load(roomMateImg).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(friendImagetoolBar);


        setUpChatRoom();





    }





    private void setUpChatRoom(){

        FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String myusername= Objects.requireNonNull(snapshot.getValue(AuthenticatedUser.class)).username;

                if(roomMateUserName.compareTo(myusername)>=0){
                    chatRoomId=myusername+roomMateUserName;

                }else{

                    chatRoomId=roomMateUserName+myusername;

                }


                ListenForMessages(chatRoomId);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }



    private void ListenForMessages(String chatRoomId){
        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                authenticatedUser.messages.clear();

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    messages.this.authenticatedUser.addMessage(dataSnapshot.getValue(Message.class));

                }

                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(authenticatedUser.messages.size()-1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



}