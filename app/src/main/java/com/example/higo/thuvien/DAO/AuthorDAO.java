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

    public Query searchByIdBook(String idBook){
//        Query query = FirebaseDatabase.getInstance().getReference().child("AuthorBook").orderByChild(idBook).equalTo(true);
        Query query = root.child("Author").orderByChild("Book/"+idBook).equalTo(true);
        return query;
    }

    public Query searchTacGiaById(String idAuthor){
        Query query = FirebaseDatabase.getInstance().getReference().child("Author").child(idAuthor);
        return query;
    }

    public Query searchByName(String name){
        Query query=root.child("Author").orderByChild("name").startAt(name).endAt(name+"\uf8ff");
        return query;
    }

    public Query searchBookByAuthor(String idAuthor){
        //Query query=root.child("AuthorBook").child(idAuthor);
        Query query = root.child("Author/"+idAuthor+"/Book");
        return query;
    }
}
