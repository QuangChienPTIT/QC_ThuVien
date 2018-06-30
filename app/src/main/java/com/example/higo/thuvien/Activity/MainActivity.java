package com.example.higo.thuvien.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.higo.thuvien.Fragment.FragmentLibrary;
import com.example.higo.thuvien.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentLibrary fragmentLibrary = new FragmentLibrary();
        transaction.add(R.id.mainFragment,fragmentLibrary);
        transaction.commit();
    }
}
