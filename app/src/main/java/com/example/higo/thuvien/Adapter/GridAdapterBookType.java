package com.example.higo.thuvien.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapterBookType extends BaseAdapter {
    private List<Book> listBook;
    private int layout;
    private Context context;

    public GridAdapterBookType(Context context,int layout,List<Book> listBook ) {
        this.listBook = listBook;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listBook.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        private TextView txtTenSach;
        private ImageView imgHinh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_book,viewGroup,false);
            viewHolder.imgHinh = view.findViewById(R.id.imgHinh);
            viewHolder.txtTenSach = view.findViewById(R.id.txtTen);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Book book = listBook.get(i);
        Picasso.get().load(book.getImgURL()).into(viewHolder.imgHinh);
        viewHolder.txtTenSach.setText(book.getName());
        return view;
    }
}
