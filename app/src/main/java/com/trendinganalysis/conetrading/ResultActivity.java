package com.trendinganalysis.conetrading;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements ISimpleDialogListener {

    public static Snackbar snackbar;
    static ExpandableListView expListView;
    public Calendar newDate = Calendar.getInstance();
    //vars
    public String date;
    Toast toast;
    Credential credentials = MainActivity.credentials;
    DataHandler dataHandler = MainActivity.dataHandler;
    DatabaseHandler databaseHandler;
    //date pickers
    DatePickerDialog datepicker;

    //date formatter
    SimpleDateFormat dateFormatter;

    //dates and calendar
    Date dateFrom;
    Date dateTo;
    //date textviews
    TextView fromDate;
    TextView toDate;
    TextView textDate;
    //common views
    ImageView pullUpButton;
    TextView resultText;
    TextView resultText2;
    TextView resultText3;
    TextView currentPageText;
    TextView lastPageText;
    LinearLayout filter_settings_layout;
    LinearLayout filter_buttonLayout_toggled;
    LinearLayout filter_buttonLayout_normal;
    LinearLayout salesBtnLayout;
    LinearLayout paginationLayout;
    RelativeLayout resultBodyLayout;
    RelativeLayout nav_layout;
    Animation animationFadeIn = null;
    Animation animationFadeOut = null;
    EditText searchField;
    boolean toggler = true;
    boolean dateToggler;
    boolean dateError;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ExpandableRelativeLayout dateMenuLayout;
    private CoordinatorLayout coordinatorLayout;
    private Animation animUp;
    private Animation animDown;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainResultLayout);
        databaseHandler = new DatabaseHandler(getBaseContext());

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        //common views
        resultText = (TextView) findViewById(R.id.resultText);
        resultText2 = (TextView) findViewById(R.id.resultText2);
        resultText3 = (TextView) findViewById(R.id.resultText3);

        currentPageText = (TextView) findViewById(R.id.currentPageText);
        lastPageText = (TextView) findViewById(R.id.lastPageText);
        pullUpButton = (ImageView) findViewById(R.id.pullUpButton);

        filter_settings_layout = (LinearLayout) findViewById(R.id.filter_settings_layout);
        salesBtnLayout = (LinearLayout) findViewById(R.id.salesBtnLayout);
        paginationLayout = (LinearLayout) findViewById(R.id.paginationLayout);

        filter_buttonLayout_normal = (LinearLayout) findViewById(R.id.filter_buttonLayout_normal);
        filter_buttonLayout_toggled = (LinearLayout) findViewById(R.id.filter_buttonLayout_toggled);
        resultBodyLayout = (RelativeLayout) findViewById(R.id.resultBodyLayout);
        nav_layout = (RelativeLayout) findViewById(R.id.nav_layout);
        searchField = (EditText) findViewById(R.id.result_searchField);
        fromDate = (TextView) findViewById(R.id.fromTxtDateData);
        toDate = (TextView) findViewById(R.id.toTxtDateData);


        //animations
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);


        //drop down menus
        dateMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.dateMenu);

        searchField.setText(dataHandler.getKeyword() + "");

        filter_settings_layout.setVisibility(View.INVISIBLE);
        salesBtnLayout.setVisibility(View.INVISIBLE);
        filter_buttonLayout_toggled.setVisibility(View.INVISIBLE);
        paginationLayout.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);
        resultText2.setVisibility(View.INVISIBLE);
        resultText3.setVisibility(View.INVISIBLE);

        //start populating List;
        populateList();
        prepareListData();
        setCurrentDate();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                dataHandler.setRunWithData(false);
                currentPageText.setText("Page " + (mViewPager.getCurrentItem() + 1));
                lastPageText.setText(" of " + mViewPager.getAdapter().getCount());
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
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        dataHandler.setRunWithData(true);
                        dataHandler.setFilterSearch(false);
                        if (searchField.getText().toString().isEmpty()) {
                            SimpleDialogFragment.createBuilder(getBaseContext(), getSupportFragmentManager())
                                    .setTitle("No Input")
                                    .setMessage("Please provide an input keywords (E.g., Truck, Hydraulics and etc.)")
                                    .setRequestCode(DataHandler.noInputCode)
                                    .setPositiveButtonText("Close").show();
                            return false;
                        } else {
                            //set keyword data
                            dataHandler.setKeyword(searchField.getText().toString());
                            dismissFilterMenu();
                            populateList();
                            if (snackbar.isShown()) snackbar.dismiss();
//                            setResultNumber();
                            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mgr.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
                            return true;
                        }
                    }
                }
                return false;
            }
        });


        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //set listHeader based on clicked or selected child or list item
                listDataHeader.set(groupPosition, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));

                //update the child list on their new header or parent name.
                switch (groupPosition) {
                    case 0:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListMake());
                        break;
                    case 1:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListSeries());
                        break;
                    case 2:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListType());
                        break;
                    case 3:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListModel());
                        break;
                    case 4:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListEngine());
                        break;
                    case 5:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListWeight());
                        break;
                    case 6:
                        listDataChild.put(listDataHeader.get(groupPosition), dataHandler.getChildListSource());
                        break;
                }

                listAdapter.notifyDataSetChanged();
                parent.collapseGroup(groupPosition);

                return false;
            }
        });

        doDatepicker();
    }

    public void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Make");
        listDataHeader.add("Series");
        listDataHeader.add("Type");
        listDataHeader.add("Model");
        listDataHeader.add("Engine");
        listDataHeader.add("Weight");
        listDataHeader.add("Source");

        // Adding child data
        dataHandler.setChildListType(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_TYPE));
        dataHandler.setChildListMake(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_MAKE));
        dataHandler.setChildListModel(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_MODEL));
        dataHandler.setChildListEngine(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_ENGINE));
        dataHandler.setChildListWeight(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT));
        dataHandler.setChildListSeries(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_SERIES));
        dataHandler.setChildListSeries(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_SERIES));
        dataHandler.setChildListSource(databaseHandler.getMenuList(DatabaseHandler.C_ONE_UNIQUE_KEY_PRODUCT_SOURCE));


        listDataChild.put(listDataHeader.get(0), dataHandler.getChildListMake()); // Header, Child data
        listDataChild.put(listDataHeader.get(1), dataHandler.getChildListSeries());
        listDataChild.put(listDataHeader.get(2), dataHandler.getChildListType());
        listDataChild.put(listDataHeader.get(3), dataHandler.getChildListModel());
        listDataChild.put(listDataHeader.get(4), dataHandler.getChildListEngine());
        listDataChild.put(listDataHeader.get(5), dataHandler.getChildListWeight());
        listDataChild.put(listDataHeader.get(6), dataHandler.getChildListSource());


        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void doDatepicker() {
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

        datepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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

        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
    }

    private void setCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        dateFrom = new GregorianCalendar(2014, 12, 01).getTime();
        dateTo = new GregorianCalendar(2016, 11, 30).getTime();

        fromDate.setText(setDate(dateFormat.format(dateFrom), false));
        toDate.setText(setDate(dateFormat.format(dateTo), false));
    }

    public String setDate(String date, Boolean inverse) {
        if (!inverse) {
            String month;
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
        String sdate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sdate = dateFormat.format(date);
        return sdate;
    }

    public void setResultNumber() {

        if (dataHandler.getDataLength() > 1) {

            resultText2.setText(dataHandler.getDataLength() + "");
            resultText3.setText("results for you");
            currentPageText.setVisibility(View.VISIBLE);
            lastPageText.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            resultText2.setVisibility(View.VISIBLE);
            resultText3.setVisibility(View.VISIBLE);
            resultText3.invalidate();
            resultText2.invalidate();
            currentPageText.invalidate();
            lastPageText.invalidate();
        } else if ((dataHandler.getDataLength() == 1)) {

            resultText2.setText(dataHandler.getDataLength() + "");
            resultText3.setText("result for you");
            resultText.setVisibility(View.VISIBLE);
            resultText2.setVisibility(View.VISIBLE);
            resultText3.setVisibility(View.VISIBLE);
            resultText2.invalidate();
            resultText3.invalidate();

        } else {
            paginationLayout.setVisibility(View.INVISIBLE);
            currentPageText.setVisibility(View.INVISIBLE);
            lastPageText.setVisibility(View.INVISIBLE);
            resultText.setVisibility(View.INVISIBLE);
            resultText2.setVisibility(View.INVISIBLE);
            resultText3.setVisibility(View.INVISIBLE);
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
//            if(snackbar.isShown()) snackbar.dismiss();
            filter_settings_layout.requestFocus();
            filter_settings_layout.startAnimation(animDown);
            salesBtnLayout.startAnimation(animationFadeIn);
            filter_buttonLayout_normal.startAnimation(animationFadeOut);
            filter_buttonLayout_toggled.startAnimation(animationFadeIn);
            filter_settings_layout.setVisibility(View.VISIBLE);
            salesBtnLayout.setVisibility(View.VISIBLE);
            filter_buttonLayout_normal.setVisibility(View.INVISIBLE);
            filter_buttonLayout_toggled.setVisibility(View.VISIBLE);
            prepareListData();

            toggler = !toggler;
        }
    }

    public void exitFilterMenu(View view) {
        dismissFilterMenu();
    }

    public void dismissFilterMenu() {
        if (!toggler) {
            dateMenuLayout.collapse();
            filter_settings_layout.startAnimation(animUp);
            salesBtnLayout.startAnimation(animationFadeOut);
            filter_buttonLayout_normal.startAnimation(animationFadeIn);
            filter_buttonLayout_toggled.startAnimation(animationFadeOut);
            filter_settings_layout.setVisibility(View.INVISIBLE);
            salesBtnLayout.setVisibility(View.INVISIBLE);
            filter_buttonLayout_normal.setVisibility(View.VISIBLE);
            filter_buttonLayout_toggled.setVisibility(View.INVISIBLE);
            toggler = !toggler;
        }
    }

    public void doSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void doResultLogout(View view) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout with the current account?")
                .setNegativeButtonText("No")
                .setRequestCode(DataHandler.logoutCode)
                .setPositiveButtonText("Yes").show();
    }

    public void doShowFilterResult(View view) {
        dataHandler.setFilterSearch(true);
        dataHandler.setRunWithData(true);

        dismissFilterMenu();

        dataHandler.setDateFrom(getDateforDB(dateFrom));
        dataHandler.setDateTo(getDateforDB(dateTo));

        dataHandler.setKeyword(searchField.getText().toString());

        dataHandler.setMaker("" + listDataHeader.get(0));
        if ("Make".toLowerCase().trim().equals(dataHandler.getMaker().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getMaker().toLowerCase().trim()))
            dataHandler.setMaker("");

        dataHandler.setSeries("" + listDataHeader.get(1));
        if ("Series".toLowerCase().trim().equals(dataHandler.getSeries().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getSeries().toLowerCase().trim()))
            dataHandler.setSeries("");

        dataHandler.setType("" + listDataHeader.get(2));
        if ("Type".toLowerCase().trim().equals(dataHandler.getType().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getType().toLowerCase().trim()))
            dataHandler.setType("");

        dataHandler.setModel("" + listDataHeader.get(3));
        if ("Model".toLowerCase().trim().equals(dataHandler.getModel().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getModel().toLowerCase().trim()))
            dataHandler.setModel("");

        dataHandler.setEngine("" + listDataHeader.get(4));
        if ("Engine".toLowerCase().trim().equals(dataHandler.getEngine().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getEngine().toLowerCase().trim()))
            dataHandler.setEngine("");

        dataHandler.setSize("" + listDataHeader.get(5));
        if ("Weight".toLowerCase().trim().equals(dataHandler.getSize().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getSize().toLowerCase().trim()))
            dataHandler.setSize("");


        dataHandler.setSource("" + listDataHeader.get(6));
        if ("Source".toLowerCase().trim().equals(dataHandler.getSource().toLowerCase().trim()) || "Any".toLowerCase().trim().equals(dataHandler.getSource().toLowerCase().trim()))
            dataHandler.setSource("");


        populateList();

    }

    public void onClearText(View view) {
        searchField.setText("");
    }

    public void doDateMenu(View view) {
        if (dateMenuLayout.isExpanded()) pullUpButton.setImageResource(R.drawable.ic_pull_down);
        else pullUpButton.setImageResource(R.drawable.ic_pull_up);
        dateMenuLayout.toggle();
    }

    public void doToDateOption(View view) {
        dateToggler = false;
        datepicker.show();
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

    public void setNumberofPages(int count) {

        //initialized filter menulist it is putted here because this method is called after searches
//        initFilterMenuList();


        paginationLayout.setVisibility(View.VISIBLE);

        mSectionsPagerAdapter.setNumberofPage(count);
        mSectionsPagerAdapter.notifyDataSetChanged();


        currentPageText.setText("Page " + (mViewPager.getCurrentItem() + 1));

        lastPageText.setText(" of " + count);

    }

    public void setNavigation(int pages) {

        if (pages == 1) {
            paginationLayout.setVisibility(View.INVISIBLE);
        } else paginationLayout.setVisibility(View.VISIBLE);

    }

    public void showSnackBarMsg(String msg) {
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }


    @Override
    public void onNegativeButtonClicked(int requestCode) {

    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == DataHandler.logoutCode) {
            credentials.clearCredentials();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
    }


    public void doNextpage(View view) {
//        currentPageText.setText("Page " + (mViewPager.getCurrentItem() + 1));
//        lastPageText.setText(" of " + mViewPager.getAdapter().getCount());
        if (mViewPager.getCurrentItem() != mViewPager.getAdapter().getCount())
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void doPrevPage(View view) {
        if (mViewPager.getCurrentItem() != 0)
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }
}
