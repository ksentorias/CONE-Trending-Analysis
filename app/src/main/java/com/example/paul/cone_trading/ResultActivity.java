package com.example.paul.cone_trading;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.view.ViewPager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ResultActivity extends AppCompatActivity{

    private ProgressDialog dialog;

    ListActivity listactivity;

    Toast toast;
    private SectionsPagerAdapter mSectionsPagerAdapter;

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        String a = null;

        searchField.setText(""+DataHolder.code+"");

        filter_settings_layout.setVisibility(View.INVISIBLE);
        salesBtnLayout.setVisibility(View.INVISIBLE);
        filter_buttonLayout_toggled.setVisibility(View.INVISIBLE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

                switch (mViewPager.getCurrentItem() + 1) {
                    case 1:
                        nav_img.setImageResource(R.drawable.nav_p_1);
                        break;
                    case 2:
                        nav_img.setImageResource(R.drawable.nav_p_2);
                        break;
                    case 3:
                        nav_img.setImageResource(R.drawable.nav_p_3);
                        break;
                    case 4:
                        nav_img.setImageResource(R.drawable.nav_p_4);
                        break;
                    case 5:
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

    public void onClearText(View view) {searchField.setText("");}

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

    public void doResultSettings(View view) {toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT); toast.show(); }

    public void doResultLogout(View view) {toast = Toast.makeText(this, "Logout", Toast.LENGTH_SHORT); toast.show(); }

    public void doShowFilterResult(View view) {toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT); toast.show();}

    public Context getContext(){
        return this;
    }

}
