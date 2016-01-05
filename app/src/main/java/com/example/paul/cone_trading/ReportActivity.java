package com.example.paul.cone_trading;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

public class ReportActivity extends AppCompatActivity {

    Toolbar toolbar;
    Toast toast;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SampleFragmentPagerAdapter adapter_x;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Local Auctions","Foreign Suppliers","Foreign Auctions", "Local Market", "Sold"};
    int Numboftabs =5;
    EditText report_searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        report_searchField = (EditText) findViewById(R.id.report_searchField);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(adapter);
//
//        // Give the PagerSlidingTabStrip the ViewPager
//        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        // Attach the view pager to the tab strip
//        tabsStrip.setViewPager(pager);
//


        //hide keyvboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(report_searchField.getWindowToken(), 0);



        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }

            public int getDividerColor(int position) {
                return getResources().getColor(R.color.white);
            }

        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

//        ---------------------------------------------------



    }


    public void doReportSettings(View view) {
        toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doReportLogout(View view) {
        toast = Toast.makeText(this, "Logout", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onClearTextReport(View view) {
        report_searchField.setText("");
    }
}
