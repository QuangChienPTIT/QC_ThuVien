package com.example.higo.thuvien.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.higo.thuvien.Activity.ReviewActivity;
import com.example.higo.thuvien.Adapter.GridAdapterBookType;
import com.example.higo.thuvien.Adapter.RecyclerViewAdapter;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
@SuppressLint("ValidFragment")
public class FragmentBookType extends Fragment {
    private String idType;


    public FragmentBookType(String idType) {
        this.idType = idType;
    }

    private GridView gridView;
    private List<Book> listBook;
    private GridAdapterBookType adapterBookType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_booktype,container,false);
        addControls(view);
        //add danh sach sach theo idType
        new BookDAO().listBookByidType(idType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listBook.clear();
                for (final DataSnapshot data:dataSnapshot.getChildren()){
                    new BookDAO().searchByID(data.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Book book = dataSnapshot.getValue(Book.class);
                            book.setId(dataSnapshot.getKey());
                            listBook.add(book);
                            adapterBookType.notifyDataSetChanged();
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

        addEvents();
        return view;
    }

    private void addEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ReviewActivity.class);
                intent.putExtra("idBook",listBook.get(i).getId());
                startActivity(intent);
            }
        });
    }

    private void addControls(View view) {
        gridView = view.findViewById(R.id.grid_book);
        listBook = new ArrayList<>();
        adapterBookType = new GridAdapterBookType(getContext(),R.layout.item_book,listBook);
        gridView.setAdapter(adapterBookType);
    }
}
