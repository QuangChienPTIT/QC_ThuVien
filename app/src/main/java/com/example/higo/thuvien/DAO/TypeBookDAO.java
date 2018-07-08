package com.example.higo.thuvien.DAO;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TypeBookDAO {

    public Query searchByIdBook(String idBook){
        Query query = FirebaseDatabase.getInstance().getReference().child("TypeBook").orderByChild(idBook).equalTo(true);
        return query;
    }

    public Query searchTypeByidType(String idType){
        Query query = FirebaseDatabase.getInstance().getReference().child("Type").child(idType);
        return query;
    }

    public Query listBookByidType(String idType){
        Query query = FirebaseDatabase.getInstance().getReference().child("TypeBook").child(idType);
        return query;
    }
}
