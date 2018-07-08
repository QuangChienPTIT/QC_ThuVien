package com.example.higo.thuvien.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SachDaMuonDAO {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("SachDaMuon");
    public void searchByUser(String idUser){
        root.orderByChild("idUser").orderByChild(idUser);
    }
}
