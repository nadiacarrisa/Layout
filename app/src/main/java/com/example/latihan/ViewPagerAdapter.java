package com.example.latihan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                new ChildFragment1(),
                new ChildFragment2(),
                new firebase_fragment(),
                new ChildFragment3Insert()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tab ;
        if(position==0){
            tab="HOME";
        }
        else if(position==1){
            tab="ABOUT";
        }
        else if(position==2){
            tab="Firebase";
        }
        else {
            tab="DATA";
        }
        return tab;
    }
}