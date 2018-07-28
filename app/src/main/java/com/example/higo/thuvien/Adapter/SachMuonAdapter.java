package com.example.higo.thuvien.Adapter;


import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.DAO.QuyenSachDAO;
import com.example.higo.thuvien.DAO.UserDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.Model.User;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SachMuonAdapter extends ArrayAdapter<SachMuon>{
    private Context context;
    private ArrayList<SachMuon> listSachMuon;
    private int resource;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss    dd/MM/yyyy");

    public SachMuonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SachMuon> listSachMuon) {
        super(context, resource, listSachMuon);
        this.context = context;
        this.resource=resource;
        this.listSachMuon=listSachMuon;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,parent,false);
        final TextView txtTenSach = convertView.findViewById(R.id.txtTenSachMuon);
        final TextView txtUserName = convertView.findViewById(R.id.txtUserNameMuon);
        final ImageView imgSachMuon = convertView.findViewById(R.id.imgSachMuon);
        TextView txtNgayMuon = convertView.findViewById(R.id.txtNgayMuon);
        SachMuon sachMuon = listSachMuon.get(position);

        new QuyenSachDAO().getBookbyIdQuyenSach(sachMuon.getIdQuyenSach()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    txtTenSach.setText(dataSnapshot1.child("name").getValue().toString());
                    Picasso.get().load(dataSnapshot1.child("imgURL").getValue().toString()).into(imgSachMuon);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new UserDAO().searchByID(sachMuon.getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUserName.setText(dataSnapshot.getValue(User.class).getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(sachMuon.getNgayTra()!=null){
            long time = Long.parseLong(sachMuon.getNgayTra());
            txtNgayMuon.setText("Ngày trả : "+dateFormat.format(time));
        }
        else if(sachMuon.getNgayMuon()!=null){
            long time = Long.parseLong(sachMuon.getNgayMuon());
            txtNgayMuon.setText("Ngày mượn : "+dateFormat.format(time));
        }
        else {
            long time = Long.parseLong(sachMuon.getNgayDangKy());
            txtNgayMuon.setText("Ngày đăng ký : "+dateFormat.format(time));
        }

        return convertView;
    }

}
