package com.example.higo.thuvien.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.higo.thuvien.Adapter.DanhGiaAdapter;
import com.example.higo.thuvien.DAO.RateBookDAO;
import com.example.higo.thuvien.Model.RateBook;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhGiaActivity extends AppCompatActivity {
    private String idBook;
    private ArrayList<RateBook> listDanhGia;
    private DanhGiaAdapter danhGiaAdapter;
    private ListView lvDanhGia;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_danhgia);
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        lvDanhGia = findViewById(R.id.lvDanhGia);
        listDanhGia = new ArrayList();
        danhGiaAdapter = new DanhGiaAdapter(this,R.layout.item_danhgia,listDanhGia);
        lvDanhGia.setAdapter(danhGiaAdapter);
        Intent intent = getIntent();
        idBook = intent.getStringExtra("idBook");
        new RateBookDAO().getRateByIdBook(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    RateBook danhGia = data.getValue(RateBook.class);
                    danhGia.setIdUser(data.getKey());
                    listDanhGia.add(danhGia);
                }
                danhGiaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
