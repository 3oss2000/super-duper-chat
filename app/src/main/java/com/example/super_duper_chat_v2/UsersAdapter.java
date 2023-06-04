package com.example.super_duper_chat_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.super_duper_chat_v2.R;
import com.example.super_duper_chat_v2.Friend;
import com.example.super_duper_chat_v2.User;

import java.io.File;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private Context context;
    private ArrayList<Friend> friends=null;
     private OnUserClickListener onUserClickListener;


    public UsersAdapter(Context context, ArrayList<Friend> friends, OnUserClickListener onUserClickListener) {
        this.context = context;
        this.friends = friends;
        this.onUserClickListener = onUserClickListener;
    }

    public interface OnUserClickListener{
        void onUserClicked(int position);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);

        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

        holder.textView.setText(friends.get(position).username);
        Glide.with(context).load(friends.get(position).profilePic).error(R.drawable.account_img).placeholder(R.drawable.account_img).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return this.friends.size();

    }

    class UserHolder extends RecyclerView.ViewHolder{


        private TextView textView;
        private ImageView imageView;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserClickListener.onUserClicked(getAdapterPosition());
                }
            });

            textView=itemView.findViewById(R.id.txt_username);
            imageView=itemView.findViewById(R.id.friend_img);




        }





    }
}
