package com.trendinganalysis.conetrading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;

public class SearchActivity extends AppCompatActivity implements ISimpleDialogListener {

    Credential credentials = MainActivity.credentials;
    DataHandler dataHandler = MainActivity.dataHandler;
    Toast toast;
    private CoordinatorLayout activityLayout;
    private EditText editTextSearch;
    private String updateMessage = "";
    private String updateAction = "";
    private int snackbarLength = 0;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activityLayout = (CoordinatorLayout) findViewById(R.id.activityLayout);


//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editTextSearch = (EditText) findViewById(R.id.search_searchField);

        editTextSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return false;
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();

                        if (editTextSearch.getText().toString().isEmpty()) {
                            SimpleDialogFragment.createBuilder(getBaseContext(), getSupportFragmentManager())
                                    .setTitle("No Input")
                                    .setMessage("Please provide an input keywords (E.g., Truck, Hydraulics and etc.)")
                                    .setRequestCode(DataHandler.noInputCode)
                                    .setPositiveButtonText("Close").show();
                            return false;
                        } else {
                            search();
                            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mgr.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

//        Timer timer = new Timer();
//
//        timer.schedule(new TimerTask() {
//            public void run() {
//                showUpdateMessage();
//            }
//        }, 3000);


    }


    public void doSearchLogout(View view) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout with the current account?")
                .setNegativeButtonText("No")
                .setRequestCode(DataHandler.logoutCode)
                .setPositiveButtonText("Yes").show();
    }

    public void doSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void doSearch(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
        } else {
            mLastClickTime = SystemClock.elapsedRealtime();

            if (editTextSearch.getText().toString().isEmpty()) {
                SimpleDialogFragment.createBuilder(getBaseContext(), getSupportFragmentManager())
                        .setTitle("No Input")
                        .setMessage("Please provide an input keywords (E.g., Truck, Hydraulics and etc.)")
                        .setRequestCode(DataHandler.noInputCode)
                        .setPositiveButtonText("Close").show();
            } else {
                search();
            }
        }
    }

    public void search() {
        dataHandler.setRunWithData(true);
        dataHandler.setFilterSearch(false);
        dataHandler.setKeyword(editTextSearch.getText().toString());
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
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
}
