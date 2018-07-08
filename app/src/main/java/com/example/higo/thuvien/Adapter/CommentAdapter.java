package com.example.higo.thuvien.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.thuvien.DAO.UserDAO;
import com.example.higo.thuvien.Model.Comment;
import com.example.higo.thuvien.Model.User;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends ArrayAdapter<Comment> {

    Context context;
    int resource;
    ArrayList<Comment> listComment;
    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listComment = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
        final TextView txtUserComment = convertView.findViewById(R.id.txtUserComment);
        TextView txtContentComment = convertView.findViewById(R.id.txtContentComment);
        TextView txtTimeComment = convertView.findViewById(R.id.txtTimeComment);
        final CircleImageView imgUserComment = convertView.findViewById(R.id.imgUserComment);
        Comment comment = listComment.get(position);
        txtTimeComment.setText(comment.getTime());
        txtContentComment.setText(comment.getContent());
        new UserDAO().searchByID(comment.getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUserComment.setText(dataSnapshot.getValue(User.class).getName());
                Picasso.get().load(dataSnapshot.getValue(User.class).getImgURL()).into(imgUserComment);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return convertView;
    }
}


