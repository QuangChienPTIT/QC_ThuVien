package com.example.higo.thuvien.DAO;

import android.app.DownloadManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AuthorDAO {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public Query searchByID(String id){
        Query query = root.child("Author").child(id);
        return query;
    }
}
