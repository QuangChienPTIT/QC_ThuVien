package com.example.higo.thuvien.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.higo.thuvien.Activity.ReviewActivity;
import com.example.higo.thuvien.Adapter.SachMuonAdapter;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.DAO.SachMuonDAO;
import com.example.higo.thuvien.Model.SachMuon;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentSachDangKy extends Fragment {
    ListView lvSachMuon;
    ArrayList<SachMuon> listSachMuon;
    SachMuonAdapter sachMuonAdapter;
    Bundle bundle;
    private SachMuonDAO sachMuonDAO = new SachMuonDAO();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference rootSachMuon = FirebaseDatabase.getInstance().getReference().child("SachMuon");

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sachmuon, container, false);
            addControls(view);
            addEvents();


        return view;
    }

    private void addEvents() {
        lvSachMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final SachMuon sachMuon = listSachMuon.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Hủy đăng ký");
                builder.setMessage("Bạn có muốn hủy đăng ký");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sachMuonDAO.huyDangKy(sachMuon.getId(),getContext());
                        //Toast.makeText(getContext(), "Hủy đăng ký thành công", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();

                    }
                });
                builder.setNegativeButton("Từ chối", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void addControls(View view) {
        lvSachMuon = view.findViewById(R.id.lv_SachMuon);
        listSachMuon = new ArrayList<>();
        sachMuonAdapter = new SachMuonAdapter(getActivity(), R.layout.item_listsachmuon, listSachMuon);
        lvSachMuon.setAdapter(sachMuonAdapter);

        sachMuonDAO.getListSachMuonByUser(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSachMuon.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("ngayMuon").getValue() == null) {
                        SachMuon sachMuon = data.getValue(SachMuon.class);
                        sachMuon.setId(data.getKey());
                        listSachMuon.add(sachMuon);
                    }
                }

                //sapXepSachMuon(listSachMuon);
                sachMuonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sapXepSachMuon(ArrayList<SachMuon> listSachMuon) {
        Collections.sort(listSachMuon, new Comparator<SachMuon>() {
            public int compare(SachMuon o1, SachMuon o2) {
                return o1.getNgayDangKy().toString().compareTo(o2.getNgayMuon().toString());
            }
        });
        //Collections.reverse(listSachMuon);
    }


}

