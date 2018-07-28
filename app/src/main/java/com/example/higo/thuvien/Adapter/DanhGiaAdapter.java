package com.example.higo.thuvien.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.thuvien.DAO.UserDAO;
import com.example.higo.thuvien.Model.Comment;
import com.example.higo.thuvien.Model.RateBook;
import com.example.higo.thuvien.Model.User;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhGiaAdapter extends ArrayAdapter<RateBook> {

    Context context;
    int resource;
    ArrayList<RateBook> listRateBook;
    public DanhGiaAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RateBook> objects) {
        super(context,resource,objects);
        this.context = context;
        this.resource = resource;
        this.listRateBook = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_danhgia,parent,false);
        final TextView txtUserDanhGia = convertView.findViewById(R.id.txtUserDanhGia);
        TextView txtContentDanhGia = convertView.findViewById(R.id.txtContentDanhGia);
        TextView txtSao = convertView.findViewById(R.id.txtSao);
        final CircleImageView imgUserDanhGia = convertView.findViewById(R.id.imgUserDanhGia);
        RateBook rateBook = listRateBook.get(position);
        txtContentDanhGia.setText(rateBook.getContent());
        txtSao.setText(rateBook.getRate()+" sao");
        new UserDAO().searchByID(rateBook.getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                txtUserDanhGia.setText(dataSnapshot.getValue(User.class).getLastName());
                Picasso.get().load(dataSnapshot.getValue(User.class).getImgURL()).into(imgUserDanhGia);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return convertView;
    }
}


