package com.example.paul.cone_trading;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ResultActivity extends AppCompatActivity implements ISimpleDialogListener {

    Toast toast;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Credential credentials = MainActivity.credentials;
    DataHolder dataHolder = SearchActivity.dataHolder;
    private ViewPager mViewPager;

    private ExpandableRelativeLayout dateMenuLayout;
    private ExpandableRelativeLayout typeMenuLayout;
    private ExpandableRelativeLayout makerMenuLayout;
    private ExpandableRelativeLayout modelMenuLayout;
    private ExpandableRelativeLayout sizeMenuLayout;

    //date pickers
    DatePickerDialog datepicker;

    //date formatter
    SimpleDateFormat dateFormatter;

    //dates
    Date dateFrom;
    Date dateTo;

    //date textviews
    TextView fromDate;
    TextView toDate;
    TextView textDate;

    //common views
    ImageView nav_img;
    TextView resultText2;
    TextView textView;
    private Animation animUp;
    private Animation animDown;
    LinearLayout filter_settings_layout;
    LinearLayout filter_buttonLayout_toggled;
    LinearLayout filter_buttonLayout_normal;
    LinearLayout salesBtnLayout;
    RelativeLayout resultBodyLayout;
    Animation animationFadeIn = null;
    Animation animationFadeOut = null;
    EditText searchField;
    ScrollView filterScrollView;
    Spinner typeSpinner;
    Spinner makerSpinner;
    Spinner modelSpinner;
    Spinner sizeSpinner;


    //vars
    public String date;
    boolean toggler = true;
    boolean dateToggler;
    boolean dateError;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        //start populating List;
        populateList();

        //common views
        resultText2 = (TextView) findViewById(R.id.resultText2);
        textView = (TextView) findViewById(R.id.textView);
        nav_img = (ImageView) findViewById(R.id.nav_img);
        filter_settings_layout = (LinearLayout) findViewById(R.id.filter_settings_layout);
        salesBtnLayout = (LinearLayout) findViewById(R.id.salesBtnLayout);
        filter_buttonLayout_normal = (LinearLayout) findViewById(R.id.filter_buttonLayout_normal);
        filter_buttonLayout_toggled = (LinearLayout) findViewById(R.id.filter_buttonLayout_toggled);
        resultBodyLayout = (RelativeLayout) findViewById(R.id.resultBodyLayout);
        searchField = (EditText) findViewById(R.id.result_searchField);
        filterScrollView = (ScrollView) findViewById(R.id.filterScrollView);
        fromDate = (TextView) findViewById(R.id.fromTxtDateData);
        toDate = (TextView) findViewById(R.id.toTxtDateData);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        makerSpinner = (Spinner) findViewById(R.id.makerSpinner);
        modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        sizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);

        //animations
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);


        //drop down menus
        dateMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.dateMenu);
        typeMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.typeMenu);
        makerMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.makerMenu);
        modelMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.modelMenu);
        sizeMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.sizeMenu);


        searchField.setText(dataHolder.getKeyword() + "");

        filter_settings_layout.setVisibility(View.INVISIBLE);
        salesBtnLayout.setVisibility(View.INVISIBLE);
        filter_buttonLayout_toggled.setVisibility(View.INVISIBLE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

                dataHolder.setRunWithData(false);

//                searchField.setText("" + mViewPager.getCurrentItem());

                int currentPointer = mViewPager.getCurrentItem() + 1;


                if (currentPointer == 6) currentPointer = 1;
                if (currentPointer == 7) currentPointer = 2;
                if (currentPointer == 8) currentPointer = 3;
                if (currentPointer == 9) currentPointer = 4;
                if (currentPointer == 10) currentPointer = 5;
                if (currentPointer == 11) currentPointer = 1;
                if (currentPointer == 12) currentPointer = 2;
                if (currentPointer == 14) currentPointer = 3;
                if (currentPointer == 15) currentPointer = 4;
                if (currentPointer == 16) currentPointer = 5;
                if (currentPointer == 17) currentPointer = 1;
                if (currentPointer == 18) currentPointer = 2;
                if (currentPointer == 19) currentPointer = 3;
                if (currentPointer == 20) currentPointer = 4;


                switch (currentPointer) {
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

        searchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return false;
                    }
                    else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        dataHolder.setRunWithData(true);
                        dataHolder.setFilterSearch(false);
                        if(searchField.getText().toString().isEmpty()){
                            SimpleDialogFragment.createBuilder(getBaseContext(), getSupportFragmentManager())
                                    .setTitle("No Input")
                                    .setMessage("Please provide an input keywords (E.g., Truck, Hydraulics and etc.)")
                                    .setRequestCode(DataHolder.noInputCode)
                                    .setPositiveButtonText("Close").show();
                            return false;
                        }
                        else{
                            //set keyword data
                            dataHolder.setKeyword(searchField.getText().toString());
                            populateList();
                            setResultNumber();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        doDatepicker();
        //set number of search results
    }

    private void doDatepicker() {
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date = dateFormatter.format(newDate.getTime());

                if (dateToggler) {
                    dateFrom = newDate.getTime();
                    date = dateFormatter.format(dateFrom);
                    textDate.setText(setDate(date, false));
                } else {
                    dateTo = newDate.getTime();

                    if (dateFrom == null) dateFrom = new Date();

                    if (dateFrom.compareTo(dateTo) > 0) {
                        newDate.setTime(dateFrom);
                        newDate.add(Calendar.DATE, 1);
                        dateTo = newDate.getTime();
                        date = dateFormatter.format(dateTo);
                        textDate.setText(setDate(date, false));
                        dateError = true;
                    } else {
                        date = dateFormatter.format(dateTo);
                        textDate.setText(setDate(date, false));
                        dateError = false;
                    }
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar newDate = Calendar.getInstance();
        Date date = new Date();
        newDate.setTime(date);
        newDate.add(Calendar.DATE, 1);

        dateFrom = date;
        dateTo = newDate.getTime();

        fromDate.setText(setDate(dateFormat.format(date), false));
        toDate.setText(setDate(dateFormat.format(newDate.getTime()), false));
    }

    public String setDate(String date, Boolean inverse) {
        if (!inverse) {
            String month = "";
            month = date.substring(0, 2);
            switch (month) {
                case "01": {
                    month = "January";
                    break;
                }
                case "02": {
                    month = "February";
                    break;
                }
                case "03": {
                    month = "March";
                    break;
                }
                case "04": {
                    month = "April";
                    break;
                }
                case "05": {
                    month = "May";
                    break;
                }
                case "06": {
                    month = "June";
                    break;
                }
                case "07": {
                    month = "July";
                    break;
                }
                case "08": {
                    month = "August";
                    break;
                }
                case "09": {
                    month = "September";
                    break;
                }
                case "10": {
                    month = "October";
                    break;
                }
                case "11": {
                    month = "November";
                    break;
                }
                case "12": {
                    month = "December";
                    break;
                }
            }

            //reformat date.
            date = month + " " + date.substring(3, 5) + ", " + date.substring(6, date.length());
            this.date = date;
        }


        return date;
    }

    public String getDateforDB(Date date) {
        String sdate = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sdate = dateFormat.format(date);
        return sdate;
    }

    public void setResultNumber() {
        if (dataHolder.getDataLength() >= 0) {
            resultText2.setText(dataHolder.getDataLength() + "");
            resultText2.invalidate();
        }
    }

    public void populateList() {

        //inflate again list
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public void enterFilterMenu(View view) {
        if (toggler) {
            setCurrentDate();
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
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout with the current account?")
                .setNegativeButtonText("No")
                .setRequestCode(DataHolder.logoutCode)
                .setPositiveButtonText("Yes").show();
    }

    public void doShowFilterResult(View view) {
        dataHolder.setFilterSearch(true);
        dataHolder.setRunWithData(true);

        dataHolder.setDateFrom(getDateforDB(dateFrom));
        dataHolder.setDateTo(getDateforDB(dateTo));

        dataHolder.setKeyword(searchField.getText().toString());

        dataHolder.setType(typeSpinner.getSelectedItem().toString());
        if ("Select Type".toLowerCase().trim().equals(dataHolder.getType().toLowerCase().trim()))
            dataHolder.setType("");

        dataHolder.setMaker(makerSpinner.getSelectedItem().toString());
        if ("Select Maker".toLowerCase().trim().equals(dataHolder.getMaker().toLowerCase().trim()))
            dataHolder.setMaker("");

        dataHolder.setModel(modelSpinner.getSelectedItem().toString());
        if ("Select Model".toLowerCase().trim().equals(dataHolder.getModel().toLowerCase().trim()))
            dataHolder.setModel("");

        dataHolder.setSize(sizeSpinner.getSelectedItem().toString());
        if ("Select Size".toLowerCase().trim().equals(dataHolder.getSize().toLowerCase().trim()))
            dataHolder.setSize("");

        populateList();

    }

    public void onClearText(View view) {
        searchField.setText("");
    }

    public void doDateMenu(View view) {
        dateMenuLayout.toggle();
    }

    public void doTypeMenu(View view) {
        typeMenuLayout.toggle();
    }

    public void doMakerMenu(View view) {
        makerMenuLayout.toggle();
    }

    public void doModelMenu(View view) {
        modelMenuLayout.toggle();
    }

    public void doSizeMenu(View view) {
        sizeMenuLayout.toggle();
    }

    public void doToDateOption(View view) {
        dateToggler = false;
        datepicker.show();
//        if(dateError){
//            Toast.makeText(this, "Date To, cannot be larger than Date From", Toast.LENGTH_SHORT).show();
//            textDate = toDate;
//        }
//        else textDate = toDate;
        //Toast.makeText(this, "Date To, cannot be larger than Date From", Toast.LENGTH_SHORT).show();
        textDate = toDate;

    }

    public void doFromDateOption(View view) {
        dateToggler = true;
        datepicker.show();
        textDate = fromDate;
    }

    public void gotoReport(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public int getPageIndex(int index){

        if(index>5){
            if((index % 5)+1==2)index = 1;
        }

        return index;

    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {

    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == DataHolder.logoutCode) {
            credentials.clearCredentials(getBaseContext());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }
}
