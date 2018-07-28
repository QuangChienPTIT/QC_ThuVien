package com.example.higo.thuvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.thuvien.Activity.ReviewActivity;
import com.example.higo.thuvien.DAO.AuthorDAO;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.DAO.SachMuonDAO;
import com.example.higo.thuvien.DAO.TheLoaiDAO;
import com.example.higo.thuvien.Model.Author;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    List<Book> data = new ArrayList<>();
    BookDAO bookDAO = new BookDAO();
    long soLuongSach;

    public RecyclerViewAdapter(List<Book> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_listbook,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Book book = data.get(position);
        holder.txtBookName.setText(book.getName());
        holder.txtTheLoai.setText(0 + "");
        bookDAO.getQuyenSachByBook(book.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                soLuongSach = dataSnapshot.getChildrenCount();
                final ArrayList<String> sachKhaDung = new ArrayList<>();
                for(final DataSnapshot data:dataSnapshot.getChildren()) {
                    new SachMuonDAO().searchByQuyenSach(data.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sachKhaDung.remove(data.getKey());
                            boolean khaDung = true;
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                if(dataSnapshot1.child("ngayTra").getValue()==null){
                                    sachKhaDung.remove(data.getKey());
                                    khaDung = false;
                                    break;
                                }
                            }
                            if(khaDung){
                                sachKhaDung.add(data.getKey().toString());
                            }
                            holder.txtSoLuong.setText(sachKhaDung.size()+"");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Picasso.get().load(book.getImgURL().toString()).into(holder.imgBook);

        new AuthorDAO().searchByIdBook(book.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.txtAuthorName.setText("");
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String s = holder.txtAuthorName.getText().toString();
                            if(TextUtils.isEmpty(s))
                                holder.txtAuthorName.setText(data.getValue(Author.class).getName());
                            else
                                holder.txtAuthorName.setText(s + " , "+data.getValue(Author.class).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new TheLoaiDAO().searchTypeByidType(book.getIdType()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.txtTheLoai.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtBookName;
        TextView txtAuthorName;
        TextView txtTheLoai;
        TextView txtSoLuong;
        ImageView imgBook;
        Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            txtAuthorName = itemView.findViewById(R.id.txtAuthorName);
            txtTheLoai = itemView.findViewById(R.id.txtItemTheLoai);
            txtSoLuong = itemView.findViewById(R.id.txtItemSoLuong);
            imgBook = itemView.findViewById(R.id.imgBook);
            this.context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Book book = data.get(position);
            Intent intent = new Intent(this.context, ReviewActivity.class);
            intent.putExtra("idBook",book.getId());
            context.startActivity(intent);
        }
    }
}
