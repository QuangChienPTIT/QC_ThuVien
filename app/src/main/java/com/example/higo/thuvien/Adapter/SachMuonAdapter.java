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
import com.example.higo.thuvien.DAO.UserDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.Model.User;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SachMuonAdapter extends ArrayAdapter<SachMuon>{
    private Context context;
    private ArrayList<SachMuon> listSachMuon;
    private int resource;

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
        new BookDAO().searchByID(sachMuon.getIdBook().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtTenSach.setText(dataSnapshot.getValue(Book.class).getName().toString());
                Picasso.get().load(dataSnapshot.getValue(Book.class).getImgURL()).into(imgSachMuon);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new UserDAO().searchByID(sachMuon.getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUserName.setText(dataSnapshot.getValue(User.class).getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        txtNgayMuon.setText(sachMuon.getNgayMuon());

        return convertView;
    }

}
