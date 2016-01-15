package com.example.paul.cone_trading;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.paul.cone_trading.ForeignSupplierTabActivity;
import com.example.paul.cone_trading.LocalAuctionsTabActivity;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        Fragment selected_tab = null;

        switch (position){

            case 0:
                LocalAuctionsTabActivity local_auctions = new LocalAuctionsTabActivity();
                //selected_tab = local_auctions;
                break;

            case 1:
                ForeignSupplierTabActivity foreign_supplier = new ForeignSupplierTabActivity();
                selected_tab =  foreign_supplier;
                break;

            case 2:
                ForeignAuctionTabActivity foreign_auction = new ForeignAuctionTabActivity();
                selected_tab = foreign_auction;
                break;

            case 3:
                LocalMarketTabActivity local_market = new LocalMarketTabActivity();
                selected_tab = local_market;
                break;

            case 4:
                SoldTabActivity sold = new SoldTabActivity();
                selected_tab =  sold;
                break;

        }

        return selected_tab;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}