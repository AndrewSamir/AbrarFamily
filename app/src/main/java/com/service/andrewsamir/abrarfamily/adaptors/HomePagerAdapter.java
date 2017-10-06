package com.example.andrewsamir.abrarfamily.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.andrewsamir.abrarfamily.Fragments.AbsentFragment;
import com.example.andrewsamir.abrarfamily.Fragments.BirthdateFragment;
import com.example.andrewsamir.abrarfamily.Fragments.EftkadFragment;
import com.example.andrewsamir.abrarfamily.Fragments.KashfFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new KashfFragment();
            case 1:
                return new AbsentFragment();
            case 2:
                return new EftkadFragment();
            case 3:
                return new BirthdateFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }


}
