package com.example.higo.thuvien.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.higo.thuvien.DAO.SachMuonDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.Comment;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TestActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rank);
//       String s= FirebaseDatabase.getInstance().getReference().child("Book").orderByKey().equalTo("novel").getRef().getKey();
//        FirebaseDatabase.getInstance().getReference().child("TypeBook").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                    dataSnapshot1.getKey();
//                    FirebaseDatabase.getInstance().getReference().child("TypeBook").child(dataSnapshot1.getKey()).orderByKey().equalTo("book001").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
//                                Log.e("sach thuoc loai",dataSnapshot2.getRef().getParent().getKey());
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                    //Log.e("Boookid   ",dataSnapshot1.getRef().getParent().getKey());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.child("Book").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    root.child("Book").child(dataSnapshot1.getKey()).child("typeBook").
                    child("novel").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()==null){
                                String s = dataSnapshot.getRef().getParent().getParent().getKey();
                                Log.e("Keyyy   ",s);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
