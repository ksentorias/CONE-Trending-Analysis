package com.example.paul.cone_trading;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    EditText editTextSearch;
    Toast toast;

    //LinearLayout filterLayout=(LinearLayout)this.findViewById(R.id.filter_layout);

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    ImageView nav_img;
    TextView resultText2;
    private Animation animUp;
    private Animation animDown;
    LinearLayout filter_settings_layout;
    LinearLayout filter_buttonLayout_toggled;
    LinearLayout filter_buttonLayout_normal;
    LinearLayout salesBtnLayout;
    Animation animationFadeIn = null;
    Animation animationFadeOut = null;
    EditText searchField;
    ScrollView filterScrollView;
    boolean toggler = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        resultText2 = (TextView) findViewById(R.id.resultText2);
        nav_img = (ImageView) findViewById(R.id.nav_img);
        filter_settings_layout = (LinearLayout) findViewById(R.id.filter_settings_layout);
        salesBtnLayout = (LinearLayout) findViewById(R.id.salesBtnLayout);
        filter_buttonLayout_normal = (LinearLayout) findViewById(R.id.filter_buttonLayout_normal);
        filter_buttonLayout_toggled = (LinearLayout) findViewById(R.id.filter_buttonLayout_toggled);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        searchField = (EditText) findViewById(R.id.result_searchField);
        filterScrollView = (ScrollView) findViewById(R.id.filterScrollView);

        //hide keybaord
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);



        new AsycncTasker().execute();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);




        filter_settings_layout.setVisibility(View.INVISIBLE);
        salesBtnLayout.setVisibility(View.INVISIBLE);
        filter_buttonLayout_toggled.setVisibility(View.INVISIBLE);



        //filterLayout.setVisibility(LinearLayout.INVISIBLE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

                switch (mViewPager.getCurrentItem() + 1) {
                    case 1:
                        try {
                            resultText2.setText(mViewPager.getCurrentItem());
                        } catch (Exception err) {
                            Log.w(err.toString(), "sa switch case ni siya");
                        }
                        nav_img.setImageResource(R.drawable.nav_p_1);
                        break;
                    case 2:
                        try {
                            resultText2.setText(mViewPager.getCurrentItem());
                        } catch (Exception err) {
                            Log.w(err.toString(), "sa switch case ni siya");
                        }
                        nav_img.setImageResource(R.drawable.nav_p_2);
                        break;
                    case 3:
                        try {
                            resultText2.setText(mViewPager.getCurrentItem());
                        } catch (Exception err) {
                            Log.w(err.toString(), "sa switch case ni siya");
                        }
                        nav_img.setImageResource(R.drawable.nav_p_3);
                        break;
                    case 4:
                        try {
                            resultText2.setText(mViewPager.getCurrentItem());
                        } catch (Exception err) {
                            Log.w(err.toString(), "sa switch case ni siya");
                        }
                        nav_img.setImageResource(R.drawable.nav_p_4);
                        break;
                    case 5:
                        try {
                            resultText2.setText(mViewPager.getCurrentItem());
                        } catch (Exception err) {
                            Log.w(err.toString(), "sa switch case ni siya");
                        }
                        nav_img.setImageResource(R.drawable.nav_p_5);
                        break;
                }

                filter_settings_layout.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
                    @Override
                    public void onSwipeRight() {
                        // Whatever
                    }
                });

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                //     tvHeader.setText("Page " + (arg0 + 1));
            }

        });

    }

    public void gotoReport(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void onClearText(View view) {

        searchField.setText("");
    }

    public void enterFilterMenu(View view) {
        if(toggler){
            filter_settings_layout.requestFocus();
            filter_settings_layout.startAnimation(animDown);
            salesBtnLayout.startAnimation(animationFadeIn);
            filter_buttonLayout_normal.startAnimation(animationFadeOut);
            filter_buttonLayout_toggled.startAnimation(animationFadeIn);
            filter_settings_layout.setVisibility(View.VISIBLE);
            salesBtnLayout.setVisibility(View.VISIBLE);
            filterScrollView.setVisibility(View.VISIBLE);
            filter_buttonLayout_normal.setVisibility(View.INVISIBLE);
            filter_buttonLayout_toggled.setVisibility(View.VISIBLE);
            toggler = false;
        }
    }

    public void exitFilterMenu(View view) {
        if (!toggler) {
            filter_settings_layout.startAnimation(animUp);
            salesBtnLayout.startAnimation(animationFadeOut);
            filter_buttonLayout_normal.startAnimation(animationFadeIn);
            filter_buttonLayout_toggled.startAnimation(animationFadeOut);
            filter_settings_layout.setVisibility(View.INVISIBLE);
            salesBtnLayout.setVisibility(View.INVISIBLE);
            filterScrollView.setVisibility(View.GONE);
            filter_buttonLayout_normal.setVisibility(View.VISIBLE);
            filter_buttonLayout_toggled.setVisibility(View.INVISIBLE);
            toggler = true;
        }
    }

    public void doResultSettings(View view) {
        toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doResultLogout(View view) {
        toast = Toast.makeText(this, "Logout", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doShowFilterResult(View view) {
        toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
        toast.show();
    }



    //View Pager
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).



            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
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

    }

    /**
     * A placeholder fragment containing a simple view.
     */

    class AsycncTasker extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            searchField.setText("Starting...");
        }

        @Override
        protected Boolean doInBackground(Void... values) {

            Log.e("Pass Reload", "Processing..");

            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }


        @Override
        protected void onPostExecute(Boolean result) {

            searchField.setText("tapos na...");

        }
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ListView lv;

        private static final int [] prgmImages={R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date,R.drawable.ic_date};
        private static final String [] prgmNameList={"Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack","Brand New Hydraulic jack"};



        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.result_list_fragment, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            lv = (ListView) rootView.findViewById(R.id.list_result_data);
            lv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));



            return rootView;
        }
    }
}
