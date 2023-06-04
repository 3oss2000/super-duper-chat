package com.example.super_duper_chat_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.super_duper_chat_v2.R;
import com.example.super_duper_chat_v2.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {


    private ArrayList<Message> messages;
    private String senderImg,receiverImg;
    private Context context;

    public MessageAdapter(ArrayList<Message> messages, String senderImg, String receiverImg, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.receiverImg = receiverImg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(context).inflate(R.layout.message_holder,parent,false);

        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

        holder.textMessage.setText(messages.get(position).getContent());

        ConstraintLayout constraintLayout=holder.constraintLayout;


        boolean isMe=messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())? true:false;

        if(isMe){

            Glide.with(context).load(senderImg).error(R.drawable.account_img).placeholder(R.drawable.account_img).into(holder.profileImageView);


            ConstraintSet constraintSet=new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.clear(R.id.profile_cardView,ConstraintSet.LEFT);
            constraintSet.clear(R.id.txt_message,ConstraintSet.LEFT);
            constraintSet.connect(R.id.profile_cardView,ConstraintSet.RIGHT,R.id.cc_layout,ConstraintSet.RIGHT,0);
            constraintSet.connect(R.id.txt_message,ConstraintSet.RIGHT,R.id.profile_cardView,ConstraintSet.LEFT,0);
             constraintSet.applyTo(constraintLayout);

        }else{

            Glide.with(context).load(receiverImg).error(R.drawable.account_img).placeholder(R.drawable.account_img).into(holder.profileImageView);


            ConstraintSet constraintSet=new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.clear(R.id.profile_cardView,ConstraintSet.RIGHT);
            constraintSet.clear(R.id.txt_message,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.profile_cardView,ConstraintSet.LEFT,R.id.cc_layout,ConstraintSet.LEFT,0);
            constraintSet.connect(R.id.txt_message,ConstraintSet.LEFT,R.id.profile_cardView,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(constraintLayout);




        }











    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {




        ConstraintLayout constraintLayout;
        TextView textMessage;
        ImageView profileImageView;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.cc_layout);
            textMessage=itemView.findViewById(R.id.txt_message);
            profileImageView=itemView.findViewById(R.id.small_profile_image);




        }
    }
}
