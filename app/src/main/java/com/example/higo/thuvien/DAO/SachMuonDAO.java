package com.example.higo.thuvien.DAO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.higo.thuvien.Model.QuyenSach;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        sachMuon.setNgayDangKy(toDay.getTime()+"");
        sachMuon.setIdQuyenSach(idQuyenSach);
        sachMuon.setIdUser(user.getUid());
//        rootSachMuon.child(user.getUid()).push().setValue(sachMuon);
        rootSachMuon.push().setValue(sachMuon);
//        quyenSachDAO.capNhatQuyenSach(idQuyenSach,true);

    }

    public void xacNhanMuon(SachMuon sachMuon){
        Date toDay = new Date();
        sachMuon.setNgayMuon(toDay.getTime()+"");
        update(sachMuon);
    }

    public void xacNhanTra(SachMuon sachMuon){
        Date toDay = new Date();
        sachMuon.setNgayTra(toDay.getTime()+"");
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

    public void huyDangKy(String id, final Context context){
        rootSachMuon.child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Hủy đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Hủy đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public Query getListSachMuonByUser(String idUser){
//        Query query = rootSachMuon.child(idUser);
        Query query= rootSachMuon.orderByChild("idUser").equalTo(idUser);
        return query;
    }

    public Query getListSachDangMuon(String idUser){
        Query query= rootSachMuon.orderByChild("idUser").equalTo(idUser);
        return query;
    }

    public Query searchByQuyenSach(String idQuyenSach){
        Query query = rootSachMuon.orderByChild("idQuyenSach").equalTo(idQuyenSach);
        return query;
    }

}
