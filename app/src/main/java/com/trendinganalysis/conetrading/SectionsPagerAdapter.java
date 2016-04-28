package com.trendinganalysis.conetrading;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ken on 1/6/2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public int numberofPage = 1;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment placement = PlaceholderFragment.newInstance(position);
        return placement;
    }

    @Override
    public int getCount() {
        return numberofPage;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "SECTION 1";
//                case 1:
//                    return "SECTION 2";
//                case 2:
//                    return "SECTION 3";
//            }
        return null;
    }

    public int getNumberofPage() {
        return numberofPage;
    }

    public void setNumberofPage(int numberofPage) {
        this.numberofPage = numberofPage;


    }
}