package com.example.higo.thuvien.DAO;

import android.util.Log;

import com.example.higo.thuvien.Model.Book;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public Query searchByID(String bookID){
        Query rootBookID = root.child("Book").child(bookID);
        return rootBookID;
    }


    public Query listBookByidType(String idType){
        Query query = root.child("Book").orderByChild("idType").equalTo(idType);
        return query;
    }

    public void updateSlConLai(final String idBook){
        searchByID(idBook).getRef().child("slConLai").addListenerForSingleValueEvent(new ValueEventListener() {
            int slConLai;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                slConLai = dataSnapshot.getValue(int.class);
                slConLai = slConLai-1;
                searchByID(idBook).getRef().child("slConLai").setValue(slConLai);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Query getQuyenSachByBook(String idBook){
        Query query = FirebaseDatabase.getInstance().getReference().child("Book/"+idBook+"/QuyenSach");
        return query;
    }

    public Query getBookByQuyenSach(String idQuyenSach){
        Query query = FirebaseDatabase.getInstance().getReference().child("Book").orderByChild("QuyenSach/"+idQuyenSach+"/tinhTrang").startAt(1);
        return query;
    }


    public Query searchByName(String name){
        Query query = root.child("Book").orderByChild("name").startAt(name).endAt(name+"\uf8ff").limitToFirst(20);
        return query;
    }

    public Query searchByType(String idType){
        Query query = root.child("Book").orderByChild("idType").equalTo(idType);
        return query;
    }

    public Query getAllBook(){
        Query bookRoot = FirebaseDatabase.getInstance().getReference().child("Book").orderByChild("name").limitToLast(100);
        return bookRoot;
    }

    public void addSachYeuThich(String idBook,String idUser){
        root.child("Book/"+idBook+"/YeuThich/"+idUser).setValue(true);
    }

    public Query getSachYeuThich(String idUser){
        Query query = root.child("Book").orderByChild("YeuThich/"+idUser).equalTo(true);
        return query;
    }


}
