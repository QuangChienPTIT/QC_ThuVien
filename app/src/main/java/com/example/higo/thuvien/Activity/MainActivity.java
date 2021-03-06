package com.example.higo.thuvien.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.higo.thuvien.Fragment.FragmentAccount;
import com.example.higo.thuvien.Fragment.FragmentBookshelf;
import com.example.higo.thuvien.Fragment.FragmentLibrary;
import com.example.higo.thuvien.Fragment.FragmentRank;
import com.example.higo.thuvien.Fragment.FragmentSachMuon;
import com.example.higo.thuvien.Fragment.FragmentSearch;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment currentfragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    public void addControls(){
        bottomNavigationView= findViewById(R.id.botton_navigation);
        txtTitle =findViewById(R.id.txtTitle);

        bottomNavigationView.setSelectedItemId(R.id.nav_thuvien);
        currentfragment = new FragmentLibrary();
        switchFragment(currentfragment);
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
                    txtTitle.setText("Thư viện");
                    currentfragment = new FragmentLibrary();
                    switchFragment(currentfragment);
                    return true;
                case R.id.nav_tusach:
                    if(user!=null) {
                        txtTitle.setText("Tủ sách");
                        currentfragment = new FragmentBookshelf();
                        switchFragment(currentfragment);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    return true;
                case R.id.nav_search:
                    txtTitle.setText("Tìm kiếm");
                    currentfragment = new FragmentSearch();
                    switchFragment(currentfragment);
                    return true;
                case R.id.nav_forum:
//                    currentfragment = new FragmentForum();
////                    switchFragment(currentfragment);
                    Intent intent = new Intent(MainActivity.this,BookTypeActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_account:
                    txtTitle.setText("Tài khoản");
                    currentfragment = new FragmentAccount() ;
                    switchFragment(currentfragment);
                    return true;

            }
            return false;
        }
    };

    private void switchFragment(android.support.v4.app.Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragment, fragment);
        transaction.commit();
    }



}
