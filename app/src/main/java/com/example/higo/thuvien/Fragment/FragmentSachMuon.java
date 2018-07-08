package com.example.higo.thuvien.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.higo.thuvien.Adapter.SachMuonAdapter;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentSachMuon extends Fragment {

    ListView lvSachMuon;
    ArrayList<SachMuon> listSachMuon;
    SachMuonAdapter sachMuonAdapter;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference rootSachMuon = FirebaseDatabase.getInstance().getReference().child("SachMuon");
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sachmuon,container,false);
        addControls(view);


        return view;
    }

    private void addControls(View view) {
        lvSachMuon = view.findViewById(R.id.lv_SachMuon);
        listSachMuon = new ArrayList<>();
        sachMuonAdapter = new SachMuonAdapter(getActivity(),R.layout.item_listsachmuon,listSachMuon);
        lvSachMuon.setAdapter(sachMuonAdapter);
        rootSachMuon.orderByChild("ngayMuon").limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSachMuon.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    rootSachMuon.child(data.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data1:dataSnapshot.getChildren()){
                                SachMuon sachMuon = data1.getValue(SachMuon.class);
                                Log.e("Root  " ,data1.getRef().getParent().getParent().getKey());
                                listSachMuon.add(sachMuon);
                                Collections.sort(listSachMuon,new Comparator<SachMuon>() {
                                    public int compare(SachMuon o1, SachMuon o2) {
                                        return o1.getNgayMuon().toString().compareTo(o2.getNgayMuon().toString());
                                    }
                                });
                                Collections.reverse(listSachMuon);
                             }
                            sachMuonAdapter.notifyDataSetChanged();
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
