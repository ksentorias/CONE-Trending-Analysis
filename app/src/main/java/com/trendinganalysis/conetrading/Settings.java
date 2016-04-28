package com.trendinganalysis.conetrading;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class Settings extends AppCompatActivity {

    public static final String DB_HOST = "db_host";
    public static final String DB_NAME = "db_name";
    public static final String DB_USER = "db_user";
    public static final String DB_PASSWORD = "db_password";
    EditText host_field;
    EditText name_field;
    EditText user_field;
    EditText password_field;
    EditText domain_name_field;
    TextView validationText;
    LinearLayout validationLayout;
    ProgressDialog dialog;
    private DataHandler dataHandler = MainActivity.dataHandler;
    private SharedPrefs sharedPrefs = MainActivity.sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));


        validationText = (TextView) findViewById(R.id.validationText);
        host_field = (EditText) findViewById(R.id.host_field);
        name_field = (EditText) findViewById(R.id.name_field);
        user_field = (EditText) findViewById(R.id.user_field);
        password_field = (EditText) findViewById(R.id.password_field);
        domain_name_field = (EditText) findViewById(R.id.domain_name);


        validationLayout = (LinearLayout) findViewById(R.id.validationLayout);

        validationLayout.setVisibility(View.GONE);

        //set values

        String host = sharedPrefs.getValueStr(SharedPrefs.KEY_DB_HOST);
        String name = sharedPrefs.getValueStr(SharedPrefs.KEY_DB_NAME);
        String user = sharedPrefs.getValueStr(SharedPrefs.KEY_DB_USERNAME);
        String password = sharedPrefs.getValueStr(SharedPrefs.KEY_DB_PASSWORD);
        String domain_name = sharedPrefs.getValueStr(SharedPrefs.KEY_DOMAIN_NAME);

        domain_name_field.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateSettings();
                    return true;
                }
                return false;
            }
        });

        if (!isEmpty(host)) {
            host_field.setText(host);
        }
        if (!isEmpty(name)) {
            name_field.setText(name);
        }
        if (!isEmpty(user)) {
            user_field.setText(user);
        }
        if (!isEmpty(password)) {
            password_field.setText(password);
        }
        if (!isEmpty(domain_name)) {
            domain_name_field.setText(domain_name);
        }
    }

    public void doSaveSettings(View view) {
        validateSettings();
    }

    public void validateSettings() {
        if (isEmpty(domain_name_field)) {
            validationLayout.setVisibility(View.VISIBLE);
        } else {

            try {
                sharedPrefs.setValue(host_field.getText().toString(), SharedPrefs.KEY_DB_HOST);
                sharedPrefs.setValue(name_field.getText().toString(), SharedPrefs.KEY_DB_NAME);
                sharedPrefs.setValue(user_field.getText().toString(), SharedPrefs.KEY_DB_USERNAME);
                sharedPrefs.setValue(password_field.getText().toString(), SharedPrefs.KEY_DB_PASSWORD);

                if (!domain_name_field.getText().toString().contains("http://")) {
                    sharedPrefs.setValue("http://" + domain_name_field.getText().toString(), SharedPrefs.KEY_DOMAIN_NAME);
                } else
                    sharedPrefs.setValue(domain_name_field.getText().toString(), SharedPrefs.KEY_DOMAIN_NAME);

                validationText.setTextColor(Color.parseColor("#02b947"));
                validationText.setText("Settings Successfully Saved");
                validationLayout.setVisibility(View.VISIBLE);

                if (!sharedPrefs.getValueBol(SharedPrefs.KEY_CONFIGURED)) {
                    dialog = ProgressDialog.show(this, "", "Checking for updates...");
                    getDBSize();
                } else finish();

            } catch (Exception e) {
                dialog.dismiss();
                validationText.setText("Did not Successfully Saved");
                e.printStackTrace();
            }

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


                    Intent intent = new Intent(getApplicationContext(), UpdateManager.class);
                    startActivity(intent);

                } catch (Exception e) {
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

    public void doCancelSettings(View view) {
        finish();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    private boolean isEmpty(String txt) {
        return txt.trim().length() <= 0;
    }

}
