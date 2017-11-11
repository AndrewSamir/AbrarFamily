package com.service.andrewsamir.main.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.service.andrewsamir.main.Fragments.AbsentFragment;
import com.service.andrewsamir.main.Fragments.BirthdateFragment;
import com.service.andrewsamir.main.Fragments.EftkadFragment;
import com.service.andrewsamir.main.Fragments.KashfFragment;

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
