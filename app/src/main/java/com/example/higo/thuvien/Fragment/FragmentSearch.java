package com.example.higo.thuvien.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SearchView;

import com.example.higo.thuvien.Adapter.RecyclerViewAdapter;
import com.example.higo.thuvien.DAO.AuthorDAO;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.DAO.TheLoaiDAO;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.TheLoai;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;
    List<Book> listBook;
    RadioButton radTheLoai,radBookName,radAuthor;
    SearchView searchView ;
    private BookDAO bookDAO = new BookDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        addControls(view);
        addEvents();

        return view;
    }

    private void addEvents() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (radAuthor.isChecked()){
                    searchByAuthor(searchView.getQuery().toString());
                }
                else if(radBookName.isChecked()){
                    searchByBook(searchView.getQuery().toString());
                }
                else if(radTheLoai.isChecked()){
                    searchByTheLoai(searchView.getQuery().toString());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void searchByTheLoai(String searchString) {
        listBook.clear();
        theLoaiDAO.searchByName(searchString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    bookDAO.searchByType(data.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data:dataSnapshot.getChildren()) {
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchByBook(String searchString) {
        bookDAO.searchByName(searchString).addValueEventListener(new ValueEventListener() {
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

    private void searchByAuthor(String searchString) {
        listBook.clear();
        authorDAO.searchByName(searchString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    authorDAO.searchBookByAuthor(data.getKey().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot bookID:dataSnapshot.getChildren()){
                                bookDAO.searchByID(bookID.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Book book = dataSnapshot.getValue(Book.class);
                                        book.setId(dataSnapshot.getKey().toString());
                                        listBook.add(book);
                                        mRecyclerViewAdapter.notifyDataSetChanged();
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addControls(View view) {
        radAuthor = view.findViewById(R.id.radAuthor);
        radBookName = view.findViewById(R.id.radBookName);
        radTheLoai = view.findViewById(R.id.radTheLoai);
        searchView = view.findViewById(R.id.searchView);
        mRecyclerView = view.findViewById(R.id.recycleListBookSearch);
        listBook = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(listBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }
}
