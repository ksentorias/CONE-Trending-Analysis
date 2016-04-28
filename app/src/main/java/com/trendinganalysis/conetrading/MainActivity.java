package com.trendinganalysis.conetrading;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    public static final DataHandler dataHandler = new DataHandler();
    public static DatabaseHandler databaseHandler;
    public static SharedPrefs sharedPrefs;
    public static Credential credentials;
    String s_username;
    String s_password;
    EditText editTextUsername;
    EditText editTextPassword;
    boolean doubleBackToExitPressedOnce = false;
    RobotoButtonBold submitButton;
    ImageView toggleBtn;
    boolean toggler = true;
    boolean remember = true;
    ProgressDialog dialog;
    ProgressDialog updateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = new SharedPrefs(this);

        SharedPrefs.initializeInstance(this);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        databaseHandler = new DatabaseHandler(getBaseContext());

        credentials = new Credential(getApplicationContext(), this);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        submitButton = (RobotoButtonBold) findViewById(R.id.submit_button);

        toggleBtn = (ImageView) findViewById(R.id.rememberSwitch);
        editTextUsername.setGravity(Gravity.CENTER);
        editTextPassword.setGravity(Gravity.CENTER);


        editTextPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logIn();
                    return true;

                }
                return false;
            }
        });

        editTextUsername.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logIn();
                    return true;
                }
                return false;
            }
        });

        //set ddatabaseHandler size
//            getDBSize();

        if (!credentials.getUsername().isEmpty()) {
            Log.i("MainActivity", "U: " + credentials.getUsername() + "/ P: " + credentials.getPassword());
            editTextUsername.setText(credentials.getUsername());
            editTextPassword.setText(credentials.getPassword());
        }

        //if the app is configured
        if (sharedPrefs.getValueBol(SharedPrefs.KEY_CONFIGURED)) {

        } else {
//            getDBSize();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);

        }

    }

    public void getDBSize() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(Settings.DB_HOST, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_HOST));
        params.put(Settings.DB_NAME, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_NAME));
        params.put(Settings.DB_USER, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_USERNAME));
        params.put(Settings.DB_PASSWORD, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_PASSWORD));

        System.out.println("params: " + params.toString());

        String url = sharedPrefs.getValueStr(SharedPrefs.KEY_DOMAIN_NAME) + "/php/checkDBSize.php";
        Log.i("getDBSize", "URL: " + url);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println("sucess sa dbcheck");
                try {
                    JSONObject json_data = new JSONObject(response);
                    double size = (json_data.getDouble("size"));
                    dataHandler.setDbSize(size);
                    Log.i("checkDBSize", "DB Size: " + dataHandler.getDbSize());
                    dialog.dismiss();
                    updateDialog.dismiss();

                    Intent intent = new Intent(getApplicationContext(), UpdateManager.class);
                    startActivity(intent);

                } catch (Exception e) {
                    updateDialog.dismiss();
                    Log.e("Fail 3", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                dialog.dismiss();
                System.out.println("failed sa dbcheck");
                if (statusCode == 404) {
                    Log.e("getDBSize", "ERROR 404");
                } else if (statusCode == 500) {
                    Log.e("getDBSize", "ERROR 500");
                } else {
                    Log.e("getDBSize", "ERROR OCCURED!  content: " + content + "\nstatus: " + statusCode + "\nerror: " + error.toString());
                }
            }
        });

    }

    public void checkNeedUpdate() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date datelocal = new Date(sharedPrefs.getValueLong(SharedPrefs.KEY_DATE_MODIFIED));

        Log.i("checkNeedUpdate", sdf.format(datelocal));

        final Date dateModifiedLocal = datelocal;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(Settings.DB_HOST, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_HOST));
        params.put(Settings.DB_NAME, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_NAME));
        params.put(Settings.DB_USER, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_USERNAME));
        params.put(Settings.DB_PASSWORD, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_PASSWORD));

        System.out.println("need Update params: " + params.toString());

        String url = sharedPrefs.getValueStr(SharedPrefs.KEY_DOMAIN_NAME) + "/php/getDateModified.php";
        Log.i("getDBSize", "URL: " + url);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println("success sa dbcheck");

                try {
                    JSONObject json_data = new JSONObject(response);
                    long date_modified = (json_data.getLong("date_modified"));
                    date_modified = date_modified * 1000;
                    Date dateremote = new Date(date_modified);

                    Log.i("needUpdate", "date_modified (long)(remote): " + date_modified);
                    Log.i("needUpdate", "date_modified (long)(local): " + sharedPrefs.getValueLong(SharedPrefs.KEY_DATE_MODIFIED));


                    Log.i("needUpdate", "date_modified (remote): " + sdf.format(dateremote));
                    Log.i("needUpdate", "date_modified (local): " + sdf.format(dateModifiedLocal));

                    if (dateremote.after(dateModifiedLocal)) {
                        Log.i("needUpdate", "WEE NEED BLOODY UPDATES");
                        getDBSize();
                    } else {
                        Log.i("needUpdate", "WEE DONT NEED BLOODY UPDATES");
                        updateDialog.dismiss();
                        Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                        startActivity(intent);

                    }

                } catch (Exception e) {
                    updateDialog.dismiss();
                    Log.e("Fail 3", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                System.out.println("failed sa dbcheck");
                if (statusCode == 404) {
                    Log.e("getDBSize", "ERROR 404");
                } else if (statusCode == 500) {
                    Log.e("getDBSize", "ERROR 500");
                } else {
                    Log.e("getDBSize", "ERROR OCCURED!  content: " + content + "\nstatus: " + statusCode + "\nerror: " + error.toString());
                }
            }
        });

    }

    public void onSubmit(View view) {
        logIn();
    }

    public void logIn() {
        s_username = editTextUsername.getText().toString().trim();
        s_password = editTextPassword.getText().toString().trim();


        dialog = ProgressDialog.show(MainActivity.this, "", "Checking your account...");

        if (databaseHandler.checkCredentials(s_username, s_password)) {

            // save it to sharedprefs
            credentials.setUserName(s_username, s_password, remember);


            if (checkInternetConnection()) {
                dialog.dismiss();
                updateDialog = ProgressDialog.show(MainActivity.this, "", "Checking for updates...");

                checkNeedUpdate();

            } else {
                Log.i("logIn", "NO INTERNET CONNECTION");
                dialog.dismiss();
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);


            }

        } else {
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setTitle("Incorrect Credentials")
                    .setMessage("Username or  password you entered is incorrect. Please try again")
                    .setCancelable(true)
                    .show();
            dialog.dismiss();


        }
    }

    public void doForgetPassword(View view) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Forgot your password?")
                .setMessage("Please contact the site administrator to change your password:\nadmin@c-one.com")
                .setPositiveButtonText("Close").show();
    }

    public void doSwitchRemember(View view) {
        if (!toggler) { //if toggle is off
            toggleBtn.setImageResource(R.drawable.ic_toggle_on);
            Toast.makeText(this, "Always remember is ON", Toast.LENGTH_SHORT).show();
            remember = true;
            toggler = !toggler;
        } else { //else if toggle is on
            toggleBtn.setImageResource(R.drawable.ic_toggle_off);
            Toast.makeText(this, "Always remember is OFF", Toast.LENGTH_SHORT).show();
            remember = false;
            toggler = !toggler;
        }


    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void doSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
