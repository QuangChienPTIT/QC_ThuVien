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

import com.example.higo.thuvien.Adapter.RecyclerViewAdapter;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
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
    List<Book> listBook;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_library,container,false);
        mRecyclerView = view.findViewById(R.id.recycleListBook);
        listBook = new ArrayList<>();

        mRecyclerViewAdapter = new RecyclerViewAdapter(listBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();
        Book book = new Book();
//        book = new BookDAO().searchByName2("Harry Potter và phòng chứa bí mật");
        final Query bookRoot = new BookDAO().getAllBook();
        bookRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listBook.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    book.setId(data.getKey().toString());
                    listBook.add(book);
                }
                mRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

    private void thiNghiem() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Book").child("book001").child("type");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference().child("Type").child(datas.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("Type name ",dataSnapshot.child("name").getValue().toString());
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
    }

}
