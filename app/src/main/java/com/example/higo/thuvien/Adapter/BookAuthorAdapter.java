package com.example.higo.thuvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.thuvien.Activity.ReviewActivity;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAuthorAdapter  extends RecyclerView.Adapter<BookAuthorAdapter.RecyclerViewHolder>{
    List<Book> data = new ArrayList<>();
    BookDAO bookDAO = new BookDAO();

    public BookAuthorAdapter(List<Book> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_book,parent,false);
        return new BookAuthorAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Book book = data.get(position);
        holder.txtTenSach.setText(book.getName());
        Picasso.get().load(book.getImgURL()).into(holder.imgHinh);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtTenSach;
        private ImageView imgHinh;
        Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.imgHinh);
            txtTenSach = itemView.findViewById(R.id.txtTen);
            context = itemView.getContext();
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
