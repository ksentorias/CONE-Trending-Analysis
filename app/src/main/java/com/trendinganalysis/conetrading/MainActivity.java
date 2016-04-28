package com.trendinganalysis.conetrading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchDataListener {


    String s_username;
    String s_password;
    EditText editTextUsername;
    EditText editTextPassword;
    ImageView toggleBtn;
    boolean toggler = true;
    boolean remember = true;
    Toast toast;

    ProgressDialog dialog;

    public static Credential credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //displays current dimension of device
        //showDimension();


        credentials = new Credential(getApplicationContext(), getParent());

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
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

        checkRemember();
    }

    public void onSubmit(View view) {
        logIn();
    }

    public void logIn() {
        s_username = editTextUsername.getText().toString().trim();
        s_password = editTextPassword.getText().toString().trim();

        credentials.setCredentials(s_username, s_password);

        checkCredentials(s_username, s_password);


    }

    public void checkCredentials(boolean access){

        if (access) {
            if(remember){
                credentials.setUserName(this,s_username,remember);

                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
        }
        else {

            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(findViewById(R.id.fieldLayout));
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

    public void checkRemember(){
        if(credentials.getUserName(this)) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }

    public void checkCredentials(String username, String password) {
        dialog = ProgressDialog.show(MainActivity.this, "", "Checking your account...");

        String url = DataHolder.permalink + "checkCredentials.php";

        FetchCredentialsData task = new FetchCredentialsData(this, getBaseContext(), username,  password);
        task.execute(url);


    }


    @Override
    public void onFetchComplete(List<Products> data) {

    }

    @Override
    public void onFetchFailure(String msg) {
        if (dialog != null) dialog.dismiss();
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        Log.e("Failure sa login page", "mao ni ang msg: " + msg);
        if (dialog != null) dialog.dismiss();

        checkCredentials(false);

    }

    @Override
    public void onFetchComplete(int code) {
        if (dialog != null) dialog.dismiss();

        checkCredentials(true);


    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {

    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {

    }

}

class FetchCredentialsData extends AsyncTask<String, Void, String> {

    Context context;
    FetchDataListener listener;

    String user = "";
    String pass = "";

    String msg;
    int code;

    public FetchCredentialsData(FetchDataListener listener, Context context , String user, String pass) {
        this.context = context;
        this.listener = listener;
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected String doInBackground(String... params) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        nameValuePairs.add(new BasicNameValuePair("user", user));
        nameValuePairs.add(new BasicNameValuePair("pass", pass));
        try {
            // create http connection
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            //connect
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //get response
            HttpResponse httpResponse = httpClient.execute(httppost);
            HttpEntity entity = httpResponse.getEntity();


            if (entity == null) {
                msg = "No response from server";
                return null;
            }

            // get response content and convert it to json string
            InputStream is = entity.getContent();
            Log.e("pass 1", "connection success ");

            return streamToString(is);

        } catch (IOException e) {
            msg = "No network connection or cannot connect to database server.";
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if (s == null) {
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }
        try {
            JSONObject json_data = new JSONObject(s);

            code=(json_data.getInt("code"));

            if(code == 1){

                if (listener != null) {
                    //set code 1 for success
                    listener.onFetchComplete(code);
                }
            }
            else{
                msg = "Login Failed: Incorrect Password or Username";
                if (listener != null) listener.onFetchFailure(msg);
            }
        } catch (JSONException e) {
            msg = e + "\nNo User Found";
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }





        super.onPostExecute(s);
    }

    public String streamToString(final InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw e;
            }
        }

        return sb.toString();
    }
}


