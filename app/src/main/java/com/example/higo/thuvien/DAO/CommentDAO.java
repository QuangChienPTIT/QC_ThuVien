package com.example.higo.thuvien.DAO;


import com.example.higo.thuvien.Model.Comment;
import com.example.higo.thuvien.Model.SachMuon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentDAO  {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DatabaseReference rootComment = FirebaseDatabase.getInstance().getReference().child("Comment");
    public Query findByBook(String idBook){
        Query query = rootComment.child(idBook).orderByChild("time").limitToFirst(9);
        return query;
    }

    public void insert(String idbook,String content){
        Date toDay = new Date();
        Comment comment = new Comment();
        comment.setIdUser(user.getUid());
        comment.setContent(content);
        comment.setTime(dateFormat.format(toDay));
        rootComment.child(idbook).push().setValue(comment);
    }
}
