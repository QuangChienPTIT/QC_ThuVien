package com.example.higo.thuvien.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.higo.thuvien.Fragment.FragmentAccount;
import com.example.higo.thuvien.Fragment.FragmentBookshelf;
import com.example.higo.thuvien.Fragment.FragmentForum;
import com.example.higo.thuvien.Fragment.FragmentLibrary;
import com.example.higo.thuvien.Fragment.FragmentRank;
import com.example.higo.thuvien.Fragment.FragmentSachMuon;
import com.example.higo.thuvien.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment currentfragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    public void addControls(){
        bottomNavigationView= findViewById(R.id.botton_navigation);
        //currentfragment = new FragmentLibrary();
    }
    public void addEvents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_thuvien:
                    currentfragment = new FragmentLibrary();
                    switchFragment(currentfragment);
                    return true;
                case R.id.nav_tusach:
                    currentfragment = new FragmentBookshelf();
                    switchFragment(currentfragment);
                    return true;
                case R.id.nav_rank:
                    currentfragment = new FragmentRank();
                    switchFragment(currentfragment);
                    return true;
                case R.id.nav_forum:
//                    currentfragment = new FragmentForum();
////                    switchFragment(currentfragment);
                    Intent intent = new Intent(MainActivity.this,BookTypeActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_account:
                    currentfragment = new FragmentSachMuon() ;
                    switchFragment(currentfragment);
                    return true;

            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragment, fragment);
        transaction.commit();
    }



}
