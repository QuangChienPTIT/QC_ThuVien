package com.example.higo.thuvien.DAO;

import android.util.Log;

import com.example.higo.thuvien.Activity.LoginActivity;
import com.example.higo.thuvien.Model.QuyenSach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QuyenSachDAO {
    DatabaseReference rootQuyenSach = FirebaseDatabase.getInstance().getReference().child("QuyenSach");
    DatabaseReference rootBook = FirebaseDatabase.getInstance().getReference().child("Book");
    public void addQuyenSach(QuyenSach quyenSach){
        rootQuyenSach.push().setValue(quyenSach);
    }

    public Query getIdBook(String idQuyenSach){
        Query query = rootQuyenSach.child(idQuyenSach).child("idBook");
        return query;
    }

    public Query getBookbyIdQuyenSach(String idQuyenSach){
        Query query =rootBook.orderByChild("QuyenSach/"+idQuyenSach+"/tinhTrang").startAt(1);
        return query;
    }

    public void capNhatQuyenSach(final String idQuyenSach, final Boolean dangMuon){
//        roorQuyenSach.child(idQuyenSach).child("dangMuon").setValue(dangMuon);
        rootBook.orderByChild("QuyenSach/"+idQuyenSach+"/tinhTrang").startAt(1).endAt(2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String idBook = dataSnapshot.getKey();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    rootBook.child(dataSnapshot1.getKey()).child("QuyenSach/"+idQuyenSach).child("dangMuon").setValue(dangMuon);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
