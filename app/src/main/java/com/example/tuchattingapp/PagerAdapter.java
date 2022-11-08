package com.example.tuchattingapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabcount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new chatfragment();


            case 1:
                return new statusfragment();

            case 2:
                return new roomfragment();


            case 3:
                return new callFragment();


            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
