package com.example.android.miwok;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sagar on 20/12/17.
 */

public class FragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_NUMBER = 4;
    String titles[] = {"Numbers", "Family", "Colors", "Phrases"};

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if(position == 0)
            return new NumbersFragment();
        else if(position == 1)
            return  new FamilyFragment();
        else if(position == 2)
            return new ColorsFragment();
        else return new PhrasesFragment();
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
