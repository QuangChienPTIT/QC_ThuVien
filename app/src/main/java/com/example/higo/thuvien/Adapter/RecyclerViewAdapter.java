package com.example.higo.thuvien.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    List<Book> data = new ArrayList<>();

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
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txtBookName.setText(data.get(position).getBookName());
        Picasso.get().load(data.get(position).getImgURL().toString()).into(holder.imgBook);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txtBookName;
        ImageView imgBook;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            imgBook = itemView.findViewById(R.id.imgBook);
        }
    }
}
