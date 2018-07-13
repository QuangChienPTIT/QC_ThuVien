package com.example.higo.thuvien.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.higo.thuvien.Fragment.FragmentBookType;
import com.example.higo.thuvien.Model.TheLoai;
import com.example.higo.thuvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BookTypeActivity extends AppCompatActivity {
    private TabLayout tabType;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        addControls();
    }

    private void addControls() {
        tabType = findViewById(R.id.tabType);
        viewPager = findViewById(R.id.viewpaperBookType);
        setupViewPaper();
        tabType.setupWithViewPager(viewPager);

    }

    private void setupViewPaper() {

         adapter = new ViewPagerAdapter(getSupportFragmentManager());
         //adapter.addFragment(new OneFragment(),"Dac biet");
         //viewPager.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("Type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    adapter.addFragment(new FragmentBookType(data.getKey().toString()), data.getValue(TheLoai.class).getName());
                }
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
