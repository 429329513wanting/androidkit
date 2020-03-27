package com.example.uplibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.uplibrary.fragment.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class VPFragmentActivity extends AppCompatActivity {

    ViewPager viewPager;
    List<CardFragment>fragments = new ArrayList<>();
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpfragment);
        viewPager = findViewById(R.id.view_pager);

        for (int i=0;i<10;i++){

            CardFragment fragment = new CardFragment();
            Bundle bundle = new Bundle();
            bundle.putString("position",i+"");
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapter = new MyAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        private List<CardFragment>fragments;
        private FragmentManager fragmentManager;

        public MyAdapter(FragmentManager fm,List<CardFragment>fragments) {
            super(fm);
            this.fragments = fragments;
            this.fragmentManager = fm;

        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }

        public void setAllFragments(List<CardFragment>fs){

            if (this.fragments != null){

                FragmentTransaction ft = fragmentManager.beginTransaction();
                for (Fragment f : fragments){
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fragmentManager.executePendingTransactions();
            }
            this.fragments = fs;
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
