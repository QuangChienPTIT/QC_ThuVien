package com.example.higo.thuvien.DAO;

import com.example.higo.thuvien.Model.SachMuon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SachMuonDAO {
    DatabaseReference rootSachMuon = FirebaseDatabase.getInstance().getReference().child("SachMuon");
    DatabaseReference rootBook = FirebaseDatabase.getInstance().getReference().child("Book");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/yy HH:mm:ss");

    public SachMuonDAO() {
    }

    public void insert(String idBook){
        Date toDay = new Date();
        SachMuon sachMuon = new SachMuon();
        sachMuon.setNgayMuon(dateFormat.format(toDay));
        sachMuon.setIdBook(idBook);
        sachMuon.setIdUser(user.getUid());
        rootSachMuon.child(user.getUid()).push().setValue(sachMuon);
        new BookDAO().updateSlConLai(idBook);
    }

    public void delete(String idBook){
        rootSachMuon.child(user.getUid()).child(idBook).removeValue();
    }

    public Query searchByBook(String idBook){
        Query query = rootSachMuon.child(user.getUid()).orderByChild("idBook").equalTo(idBook);
        return query;
    }
}
