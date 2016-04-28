package com.trendinganalysis.conetrading;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Ken on 2/11/2016.
 */
public class UpdateManager extends AppCompatActivity implements FetchDataListener {

    public static NumberFormat df2 = NumberFormat.getInstance();
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    ProgressDialog prgDialog;
    private SharedPrefs sharedPrefs = MainActivity.sharedPrefs;
    private DataHandler dataHandler = MainActivity.dataHandler;
    private Credential credentials = MainActivity.credentials;
    private CoordinatorLayout activityLayout;
    private TextView updateMessageTxt;
    private TextView updateStatusTxt;
    private TextView dbSizeTxt;
    private TextView remainingBytes;
    private TextView maxBytes;
    private Button syncButton;
    private LinearLayout bytesTrackLayout;
    private LinearLayout dbSizeLayout;

    private DonutProgress syncProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.update_manager);

        df2.setMaximumFractionDigits(2);
        df2.setMinimumFractionDigits(2);
        df2.setRoundingMode(RoundingMode.HALF_UP);

        activityLayout = (CoordinatorLayout) findViewById(R.id.activityLayout);
        updateMessageTxt = (TextView) findViewById(R.id.updateMessageTxt);
        updateStatusTxt = (TextView) findViewById(R.id.updateStatusTxt);
        dbSizeTxt = (TextView) findViewById(R.id.dbSizeTxt);
        remainingBytes = (TextView) findViewById(R.id.remainingBytes);
        maxBytes = (TextView) findViewById(R.id.maxBytes);

        syncButton = (Button) findViewById(R.id.syncButton);

        syncProgress = (DonutProgress) findViewById(R.id.syncProgress);

        dbSizeLayout = (LinearLayout) findViewById(R.id.dbSizeLayout);

        bytesTrackLayout = (LinearLayout) findViewById(R.id.bytesTrackLayout);


        syncProgress.setVisibility(View.GONE);
        bytesTrackLayout.setVisibility(View.GONE);


        //set databaseHandler size
        dbSizeTxt.setText(df2.format(dataHandler.getDbSize()));
        maxBytes.setText(df2.format(dataHandler.getDbSize()));

        Log.i("UpdateManager", "max sync progress: " + (int) dataHandler.getDbSize());

        dataHandler.setCancelUpdate(false);
        dataHandler.setUpdating(false);
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }


    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public void syncUsers() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(Settings.DB_HOST, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_HOST));
        params.put(Settings.DB_NAME, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_NAME));
        params.put(Settings.DB_USER, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_USERNAME));
        params.put(Settings.DB_PASSWORD, sharedPrefs.getValueStr(SharedPrefs.KEY_DB_PASSWORD));

        System.out.println("need Update params: " + params.toString());

        String url = sharedPrefs.getValueStr(SharedPrefs.KEY_DOMAIN_NAME) + "/php/getAllUsers.php";
        Log.i("getDBSize", "URL: " + url);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
//                    System.out.println("response from remote ddatabaseHandler (UserSync): " + response);

                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    // If no of array elements is not zero
                    if (arr.length() != 0) {
                        // Loop through each array element, get JSON object which has userid and username
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            UserModel user = new UserModel();

                            Log.i("syncUsers", "SERVER ID: " + json.getInt("ID") +
                                    "\nUSERNAME: " + json.getString("username") +
                                    "\nPASSWORD: " + json.getString("password") +
                                    "\nFIRSTNAME: " + json.getString("firstname") +
                                    "\nLASTNAME: " + json.getString("lastname") +
                                    "\nUSER_TYPE: " + json.getString("user_type"));

                            user.setServer_id(json.getInt("ID"));
                            user.setUsername(json.getString("username"));
                            user.setPassword(json.getString("password"));
                            user.setFirstname(json.getString("firstname"));
                            user.setLastname(json.getString("lastname"));
                            user.setUser_type(json.getString("user_type"));

                            Log.i("syncUsers", "SERVER ID: " + user.getServer_id() +
                                    "\nUSERNAME: " + user.getUsername() +
                                    "\nPASSWORD: " + user.getPassword() +
                                    "\nFIRSTNAME: " + user.getFirstname() +
                                    "\nLASTNAME: " + user.getLastname() +
                                    "\nUSER_TYPE: " + user.getUser_type());

                            databaseHandler.addUser(user);
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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


    // Method to Sync MySQL to SQLite DB
    public void syncSQLiteMySQLDB() {
        syncUsers();

        new SyncDB(this, getApplicationContext(), sharedPrefs.getValueStr(SharedPrefs.KEY_DOMAIN_NAME) + "/php/readData.php", 0, databaseHandler, syncProgress, remainingBytes).execute();
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if (requestCode == DataHandler.updateCode) {
            finish();
        } else if (requestCode == DataHandler.cancelSyncCode) {
            dataHandler.setCancelUpdate(false);
        }

    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == DataHandler.cancelSyncCode) {
            dataHandler.setCancelUpdate(true);
        }
    }

    public void doSync(View view) {

        if (syncButton.getText().equals("Continue")) {
            if (sharedPrefs.getValueBol(SharedPrefs.KEY_CONFIGURED)) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else {
                if (credentials.isRemember()) {
                    Intent intent = new Intent(this, SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
        } else {

            syncProgress.setVisibility(View.VISIBLE);
            bytesTrackLayout.setVisibility(View.VISIBLE);

            YoYo.with(Techniques.DropOut)
                    .duration(1000)
                    .playOn(findViewById(R.id.syncProgress));

            YoYo.with(Techniques.DropOut)
                    .duration(1000)
                    .playOn(findViewById(R.id.bytesTrackLayout));

            YoYo.with(Techniques.FlipOutX)
                    .duration(1000)
                    .playOn(findViewById(R.id.updateStatusTxt));


            YoYo.with(Techniques.FlipOutX)
                    .duration(1000)
                    .playOn(findViewById(R.id.updateMessageTxt));


            YoYo.with(Techniques.FlipOutX)
                    .duration(1000)
                    .playOn(findViewById(R.id.dbSizeLayout));

            updateStatusTxt.setText("Please Wait");

            YoYo.with(Techniques.FlipInX)
                    .duration(2000)
                    .playOn(findViewById(R.id.updateStatusTxt));


            updateStatusTxt.setVisibility(View.VISIBLE);

            YoYo.with(Techniques.FlipOutX)
                    .duration(1000)
                    .playOn(findViewById(R.id.syncButton));


            dataHandler.setUpdating(true);
            syncSQLiteMySQLDB();


        }

    }

    @Override
    public void onFetchComplete(List<Products> data) {

    }

    @Override
    public void onFetchFailure(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFetchComplete(int code) {
        if (code == 1) {

            syncButton.setVisibility(View.VISIBLE);

            YoYo.with(Techniques.FlipOutX)
                    .duration(1000)
                    .playOn(findViewById(R.id.updateStatusTxt));

            if (dataHandler.isCancelUpdate())
                updateStatusTxt.setText("Sync Cancelled");
            else
                updateStatusTxt.setText("Downloaded Successfully");


            YoYo.with(Techniques.FlipInX)
                    .duration(2000)
                    .playOn(findViewById(R.id.updateStatusTxt));

            syncButton.setText("Continue");

            YoYo.with(Techniques.FlipInX)
                    .duration(1000)
                    .playOn(findViewById(R.id.syncButton));
        }
    }

    @Override
    public void onFetchComplete(CustomAdapter adapter) {


    }

    public void doUpdateManagerLogout(View view) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout with the current account?")
                .setNegativeButtonText("No")
                .setRequestCode(DataHandler.logoutCode)
                .setPositiveButtonText("Yes").show();
    }

    public void doSettings(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    public void doSkip(View view) {
        if (dataHandler.isUpdating()) {
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setTitle("Product Sync")
                    .setMessage("Cancel the update?")
                    .setNegativeButtonText("No")
                    .setRequestCode(DataHandler.cancelSyncCode)
                    .setPositiveButtonText("Yes").show();
        } else {
            if (credentials.isRemember()) {
                Intent intent = new Intent(this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }


    }


}


class SyncDB extends AsyncTask<Void, String, String> {

    Context context;
    FetchDataListener listener;

    String response = "";
    String url = "";
    int startingID = 0;
    DatabaseHandler databaseHandler;
    DataHandler dataHandler = MainActivity.dataHandler;
    DonutProgress syncProgress;
    TextView progressBytes;

    SharedPrefs sharedPrefs = MainActivity.sharedPrefs;

    String msg;

    public SyncDB(FetchDataListener listener, Context context, String url, int startingID, DatabaseHandler databaseHandler, DonutProgress syncProgress, TextView progressBytes) {
        this.context = context;
        this.listener = listener;
        this.url = url;
        this.startingID = startingID;
        this.databaseHandler = databaseHandler;
        this.syncProgress = syncProgress;
        this.progressBytes = progressBytes;
    }

    @Override
    protected String doInBackground(Void... params) {

        if (startingID == 0) databaseHandler.truncatedb();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("db_host", "" + sharedPrefs.getValueStr(SharedPrefs.KEY_DB_HOST)));
        nameValuePairs.add(new BasicNameValuePair("db_name", "" + sharedPrefs.getValueStr(SharedPrefs.KEY_DB_NAME)));
        nameValuePairs.add(new BasicNameValuePair("db_user", "" + sharedPrefs.getValueStr(SharedPrefs.KEY_DB_USERNAME)));
        nameValuePairs.add(new BasicNameValuePair("db_password", "" + sharedPrefs.getValueStr(SharedPrefs.KEY_DB_PASSWORD)));

        nameValuePairs.add(new BasicNameValuePair("fromID", "" + startingID));

        try {
            // create http connection
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            //connect
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //get response
            HttpResponse httpResponse = httpClient.execute(httppost);
            HttpEntity entity = httpResponse.getEntity();

            if (entity == null) {
                msg = "No response from server";
            }

            // get response content and convert it to json string
            InputStream is = entity.getContent();
            Log.e("pass 1", "connection success ");

            response = streamToString(is);

        } catch (IOException e) {
            msg = "No network connection or cannot connect to database server.";
        }


        if (response == null) {
            if (listener != null) listener.onFetchFailure(msg);
        }

        try {
//            System.out.println("response from remote ddatabaseHandler: " + response);

            // Extract JSON array from the response
            JSONArray arr = new JSONArray(response);
            System.out.println(arr.length());
            // If no of array elements is not zero
            if (arr.length() != 0) {
                float rowCurrent = 0;
                float rowSize = 0;
                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < arr.length(); i++) {

                    if (dataHandler.isCancelUpdate()) break;

                    JSONObject json = arr.getJSONObject(i);
                    Products product = new Products();

                    product.setServerID(json.getInt("id"));
                    product.setProductID(json.getInt("product_id"));
                    product.setEngine(json.getString("product_engine"));
                    product.setDesc(json.getString("additional_description"));
                    product.setType(json.getString("product_type"));
                    product.setModel(json.getString("product_model"));
                    product.setMake(json.getString("product_make"));
                    product.setSize(json.getString("product_weight"));
                    product.setCategory(json.getString("product_source"));
                    product.setSeries(json.getString("product_series"));
                    product.setPrice_php(Float.parseFloat(json.getString("product_price_php")));
                    product.setPrice_jpy(Float.parseFloat(json.getString("product_price_jpy")));

                    try {
                        String string = json.getString("source_date");
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = format.parse(string);
                        product.setDate(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    databaseHandler.addProduct(product);

                    rowCurrent++;
                    rowSize = rowCurrent * 0.35f;

                    publishProgress("" + (((i + 1) * 100)) / arr.length(), "" + UpdateManager.df2.format(rowSize));


                }
                if (dataHandler.isCancelUpdate()) return null;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        // Set progress percentage
        syncProgress.setProgress(Integer.parseInt(progress[0]));
        progressBytes.setText(progress[1]);
        Log.i("SyncDB", "syncing at " + progress[0] + " where " + progress[1] + " / " + dataHandler.getDbSize());
    }

    @Override
    protected void onPostExecute(String s) {

        if (listener != null) {

            try {
                listener.onFetchComplete(1);

                //get current date and time for db syncing
                if (!dataHandler.isCancelUpdate()) {
                    Long date = new Date().getTime();
                    sharedPrefs.setValue(date, SharedPrefs.KEY_DATE_MODIFIED);
                    Log.i("SyncDB", "Date Modified: " + date);

                    //set app as configured
                    sharedPrefs.setValue(true, SharedPrefs.KEY_CONFIGURED);

                    //set app to not updating
                    dataHandler.setUpdating(false);

                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = "No Results Found. Your database maybe outdated :)";
                if (listener != null) listener.onFetchFailure(msg);
            }
        }


    }

    public String streamToString(final InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        try {
            String line;
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

