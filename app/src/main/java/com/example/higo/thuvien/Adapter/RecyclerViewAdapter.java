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
import android.widget.Toast;

import com.example.higo.thuvien.Activity.LoginActivity;
import com.example.higo.thuvien.Activity.ReviewActivity;
import com.example.higo.thuvien.DAO.AuthorDAO;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.Model.Author;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.QuyenSach;
import com.example.higo.thuvien.Model.TheLoai;
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
//        int sl = book.getSlConLai();
//        if(sl>0){
//            holder.txtSoLuongCon.setText("Tình trạng : Còn sách");
//        }
//        else
//            holder.txtSoLuongCon.setText("Tình trạng : Hết sách");
        bookDAO.soLuongSachConLai(book.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int soLuong=0;
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    QuyenSach quyenSach = data.getValue(QuyenSach.class);
                    if(quyenSach.getTinhTrang()!=3&&!quyenSach.isDangMuon()){
                        soLuong++;
                    }
                    holder.txtSoLuongCon.setText("Số lượng còn lại : "+ soLuong);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Picasso.get().load(book.getImgURL().toString()).into(holder.imgBook);
//        new AuthorDAO().searchByIdBook(book.getId()).addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                holder.txtAuthorName.setText("");
////                for(DataSnapshot data:dataSnapshot.getChildren()){
////                    new AuthorDAO().searchTacGiaById(data.getKey()).addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            String s = holder.txtAuthorName.getText().toString();
////                            if(TextUtils.isEmpty(s))
////                                holder.txtAuthorName.setText(dataSnapshot.getValue(Author.class).getName());
////                            else
////                                holder.txtAuthorName.setText(s + " , "+dataSnapshot.getValue(Author.class).getName());
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////
////                        }
////                    });
////                }
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });

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

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtBookName;
        TextView txtAuthorName;
        TextView txtSoLuongCon;
        ImageView imgBook;
        Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            txtAuthorName = itemView.findViewById(R.id.txtAuthorName);
            txtSoLuongCon = itemView.findViewById(R.id.txtSoLuongCon);
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
