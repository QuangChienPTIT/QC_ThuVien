package com.example.higo.thuvien.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.higo.thuvien.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentBookshelf extends Fragment{
    private TabLayout tabBookShelf;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_bookshelf,container,false);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
        tabBookShelf = view.findViewById(R.id.tabBookShelf);
        viewPager = view.findViewById(R.id.viewpaperBookshelf);
        setupViewPaper();
        tabBookShelf.setupWithViewPager(viewPager);

    }

    private void setupViewPaper() {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentSachDangKy(),"Đã đăng ký");
        adapter.addFragment(new FragmentSachMuon(),"Đang Mượn");
        adapter.addFragment(new FragmentSachDaTra(),"Đã Mượn");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

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
