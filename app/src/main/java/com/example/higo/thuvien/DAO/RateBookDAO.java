package com.example.higo.thuvien.DAO;

import com.example.higo.thuvien.Model.RateBook;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RateBookDAO {
    DatabaseReference rootRate = FirebaseDatabase.getInstance().getReference().child("Rate");
    public void insert(RateBook rate,String idBook){
        rootRate.child(idBook).push().setValue(rate);
    }

    public Query getRateByIdBook(String idBook){
        Query query = rootRate.child(idBook);
        return query;
    }
}
