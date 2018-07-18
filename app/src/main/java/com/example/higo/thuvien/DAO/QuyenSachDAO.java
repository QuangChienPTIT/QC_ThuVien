package com.example.higo.thuvien.DAO;

import com.example.higo.thuvien.Model.QuyenSach;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class QuyenSachDAO {
    DatabaseReference roorQuyenSach = FirebaseDatabase.getInstance().getReference().child("QuyenSach");
    public void addQuyenSach(QuyenSach quyenSach){
        roorQuyenSach.push().setValue(quyenSach);
    }

    public Query getIdBook(String idQuyenSach){
        Query query = roorQuyenSach.child(idQuyenSach).child("idBook");
        return query;
    }

    public void capNhatQuyenSach(String idQuyenSach,Boolean dangMuon){
        roorQuyenSach.child(idQuyenSach).child("dangMuon").setValue(dangMuon);
    }


}
