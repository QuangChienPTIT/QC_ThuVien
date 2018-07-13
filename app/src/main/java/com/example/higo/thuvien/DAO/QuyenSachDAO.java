package com.example.higo.thuvien.DAO;

import com.example.higo.thuvien.Model.QuyenSach;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuyenSachDAO {
    DatabaseReference roorQuyenSach = FirebaseDatabase.getInstance().getReference().child("QuyenSach");
    public void addQuyenSach(QuyenSach quyenSach){
        roorQuyenSach.push().setValue(quyenSach);
    }
}
