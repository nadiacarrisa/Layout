package com.example.latihan;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class home_viewpager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_viewpager);
        ViewPager vpgr = findViewById(R.id.view_pager);
        vpgr.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tl = findViewById(R.id.tab_layout);
        tl.setupWithViewPager(vpgr);
    }
}


