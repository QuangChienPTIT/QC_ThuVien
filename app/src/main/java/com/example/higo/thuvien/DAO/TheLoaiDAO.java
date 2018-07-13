package com.example.higo.thuvien.DAO;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TheLoaiDAO {
    public Query searchByIdBook(String idBook){
        Query query = FirebaseDatabase.getInstance().getReference().child("TypeBook").orderByChild(idBook).equalTo(true);
        return query;
    }

    public Query searchTypeByidType(String idType){
        Query query = FirebaseDatabase.getInstance().getReference().child("Type").child(idType);
        return query;
    }


}
