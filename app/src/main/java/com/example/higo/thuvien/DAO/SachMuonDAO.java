package com.example.higo.thuvien.DAO;

import android.util.Log;

import com.example.higo.thuvien.Model.QuyenSach;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SachMuonDAO {
    DatabaseReference rootSachMuon = FirebaseDatabase.getInstance().getReference().child("SachMuon");
    DatabaseReference rootBook = FirebaseDatabase.getInstance().getReference().child("Book");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    QuyenSachDAO quyenSachDAO = new QuyenSachDAO();

    public SachMuonDAO() {
    }

    public void dangKyMuon(String idQuyenSach){
        Date toDay = new Date();
        SachMuon sachMuon = new SachMuon();
        sachMuon.setNgayDangKy(dateFormat.format(toDay));
        sachMuon.setIdQuyenSach(idQuyenSach);
        sachMuon.setIdUser(user.getUid());
        rootSachMuon.child(user.getUid()).push().setValue(sachMuon);
        quyenSachDAO.capNhatQuyenSach(idQuyenSach,true);

    }

    public void xacNhanMuon(SachMuon sachMuon){
        Date toDay = new Date();
        sachMuon.setNgayMuon(dateFormat.format(toDay));
        update(sachMuon);
    }

    public void xacNhanTra(SachMuon sachMuon){
        Date toDay = new Date();
        sachMuon.setNgayTra(dateFormat.format(toDay));
        update(sachMuon);
        quyenSachDAO.capNhatQuyenSach(sachMuon.getIdQuyenSach(),false);
    }

    public void delete(String idBook){
        rootSachMuon.child(user.getUid()).child(idBook).removeValue();
    }

    public void update(final SachMuon sachMuon){
        rootSachMuon.child(sachMuon.getIdUser()).orderByChild("idQuyenSach").equalTo(sachMuon.getIdQuyenSach()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    data.getRef().setValue(sachMuon);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public Query getListSachMuonByUser(String idUser){
        Query query = rootSachMuon.child(idUser);
        return query;

    }

}
