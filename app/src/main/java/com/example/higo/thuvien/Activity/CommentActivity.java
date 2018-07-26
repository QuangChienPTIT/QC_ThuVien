package com.example.higo.thuvien.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.higo.thuvien.Adapter.CommentAdapter;
import com.example.higo.thuvien.DAO.CommentDAO;
import com.example.higo.thuvien.Model.Comment;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private EditText edComment ;
    private ImageView imgSendComment;
    private String idBook;
    private ArrayList<Comment> listComment;
    private CommentAdapter commentAdapter;
    private ListView lvComment;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss    dd/MM/yyyy");
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comment);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contentComment = edComment.getText().toString();
                if(!TextUtils.isEmpty(contentComment)){
                    Comment comment = new Comment();
                    comment.setIdBook(idBook);
                    comment.setIdUser(FirebaseAuth.getInstance().getUid());
                    new CommentDAO().insert(idBook,contentComment);
                }
            }
        });
    }

    private void addControls() {
        edComment = findViewById(R.id.edComment);
        imgSendComment = findViewById(R.id.imgSendComment);
        lvComment = findViewById(R.id.lvComment);
        listComment = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,R.layout.item_comment,listComment);
        lvComment.setAdapter(commentAdapter);
        Intent intent = getIntent();
        idBook = intent.getStringExtra("idBook");

        new CommentDAO().findByBook(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listComment.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Comment comment = data.getValue(Comment.class);
                    listComment.add(comment);
                    Collections.sort(listComment,new Comparator<Comment>() {
                        public int compare(Comment o1, Comment o2) {
                            return o1.getTime().toString().compareTo(o2.getTime().toString());
                        }
                    });
                    Collections.reverse(listComment);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
