package com.example.higo.thuvien.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.higo.thuvien.Adapter.RecyclerViewAdapter;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityYeuThich extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;
    List<Book> listBook;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeuthich);

        mRecyclerView = findViewById(R.id.recycleListBookYeuThich);
        listBook = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(listBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        new BookDAO().getSachYeuThich(user.getUid()).addValueEventListener(new ValueEventListener() {
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
    }
}
