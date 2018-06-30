package com.example.higo.thuvien.DAO;

import android.util.Log;

import com.example.higo.thuvien.Model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    List<Book> data;
    public Query searchByID(String bookID){

        Query rootBookID = root.child("Book").child(bookID);
        return rootBookID;
    }

    public Query searchByName(String bookName){
        Query rootBookName = root.child("Book").orderByChild("bookName").equalTo(bookName);
        return rootBookName;
    }

    public Query listBook( ){
        Query bookRoot = FirebaseDatabase.getInstance().getReference().child("Book");
        return bookRoot;
    }



}
