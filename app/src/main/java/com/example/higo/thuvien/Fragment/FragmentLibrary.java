package com.example.higo.thuvien.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.higo.thuvien.Adapter.RecyclerViewAdapter;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentLibrary extends Fragment  {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;
    List<Book> data;
    BookDAO bookDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_library,container,false);
        mRecyclerView = view.findViewById(R.id.recycleListBook);
        data = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();
        //Query bookRoot = new BookDAO().listBook();
        Query bookRoot =  new BookDAO().searchByName("Harry Potter và hòn đá phù thủy");
        bookRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas:dataSnapshot.getChildren()){
                    Book book = datas.getValue(Book.class);
                    Log.e("bookname",book.getBookName());
                    data.add(book);
                    Log.e("Key book",datas.getKey().toString());
                }
                mRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
