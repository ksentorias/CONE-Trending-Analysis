package com.trendinganalysis.conetrading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity implements FetchDataListener {


    public static String category;
    public static int counter;
    final CharSequence Titles[] = {"Local Auction", "Foreign Supplier", "Local Market", "Sold"};
    final CharSequence TitlesforData[] = {"Local Auction", "Foreign Supplier", "Foreign Auction", "Local Market", "Sold"};
    final int Numboftabs = 4;
    public List<List<Products>> reportProducts = new ArrayList<>();
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    ProgressDialog dialog;
    DataHandler dataHandler = MainActivity.dataHandler;
    Credential credentials = MainActivity.credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        counter = 0;

        getReportData();

//        ---------------------------------------------------

    }

    public List<List<Products>> getReportProducts() {
        return reportProducts;
    }

    public void doReportLogout(View view) {
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


    public void getReportData() {
        dialog = ProgressDialog.show(this, "", "Generating Graph...");

//        String url = DataHandler.permalink + "selectEachData.php";

        for (int i = 0; i < 5; i++) {
            category = (String) TitlesforData[i];
            Log.i("sa getReport", category);
            FetchData task = new FetchData(this, this, this, dataHandler, (String) TitlesforData[i]);
            task.execute((String) TitlesforData[i]);
        }


    }

    @Override
    public void onFetchComplete(List<Products> data) {
        reportProducts.add(data);
        if (reportProducts.size() >= 5) {
            if (dialog != null) dialog.dismiss();
            setDataToChart(reportProducts);
        }
    }

    @Override
    public void onFetchFailure(String msg) {
        if (dialog != null) dialog.dismiss();
        Toast.makeText(this, "Failture mao ni ang msg: " + msg, Toast.LENGTH_LONG).show();
        Log.e("Failture", "mao ni ang msg: " + msg);
    }

    @Override
    public void onFetchComplete(int code) {

    }

    @Override
    public void onFetchComplete(CustomAdapter adapter) {

    }


    public void setDataToChart(List<List<Products>> reportProducts) {
        dataHandler.setReportLAProducts(reportProducts.get(0));
        dataHandler.setReportFSProducts(reportProducts.get(1));
        dataHandler.setReportFAProducts(reportProducts.get(2));
        dataHandler.setReportLMProducts(reportProducts.get(3));
        dataHandler.setReportSDProducts(reportProducts.get(4));

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

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


    }


}

class FetchData extends AsyncTask<String, Void, List<Products>> {
    DataHandler dataHandler;

    Activity activity;

    Context context;

    FetchDataListener listener;

    String msg;
    String category;

    DatabaseHandler db = MainActivity.databaseHandler;


    public FetchData(FetchDataListener listener, Activity activity, Context context, DataHandler dataHandler, String category) {
        this.listener = listener;
        this.activity = activity;
        this.context = context;
        this.dataHandler = dataHandler;
        this.category = category;
    }


    @Override
    protected List<Products> doInBackground(String... params) {


//
//        nameValuePairs.add(new BasicNameValuePair("category", category));
//        Log.e("nameValuePairs", ReportActivity.category);
//        try {
//            // create http connection
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(params[0]);
//
//            //connect
//            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//            //get response
//            HttpResponse httpResponse = httpClient.execute(httppost);
//            HttpEntity entity = httpResponse.getEntity();
//
//
//            if (entity == null) {
//                msg = "No response from server";
//                return null;
//            }
//
//            // get response content and convert it to json string
//            InputStream is = entity.getContent();
//            Log.e("pass 1", "connection success ");
//
//            return streamToString(is);
//
//        } catch (IOException e) {
//            msg = "No network connection or cannot connect to database server.";
//        }

        Log.i("Report category", "Source: " + category);

        return db.getreports(category);
    }

    @Override
    protected void onPostExecute(List<Products> pr) {

        if (pr == null) {
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }

//        try {
//
//            JSONArray aJson = new JSONArray(s);
//            // convert json string to json array
//            // create products list
//            reportProduct = new ArrayList<>();
//            reportProducts = ((ReportActivity) activity).getReportProducts();
//
//            //set number of results
//            for (int i = 0; i < aJson.length(); i++) {
//                JSONObject json = aJson.getJSONObject(i);
//                Products product = new Products();
//
//                product.setEngine(json.getString("product_engine"));
//                product.setDesc(json.getString("additional_description"));
//                product.setType(json.getString("product_type"));
//                product.setModel(json.getString("product_model"));
//                product.setMake(json.getString("product_make"));
//                product.setSize(json.getString("product_weight"));
//                product.setCategory(json.getString("product_source"));
//                product.setSeries(json.getString("product_series"));
//                product.setPrice_php(Float.parseFloat(json.getString("product_price_php")));
//                product.setPrice_jpy(Float.parseFloat(json.getString("product_price_jpy")));
//
//
//                try {
//                    String string = json.getString("source_date");
//                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    Date date = format.parse(string);
//                    product.setDate(date);
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                reportProduct.add(product);
//                Log.e("pass 2", "json to array setter success ");
//            }
//
//
//        } catch (JSONException e) {
//            msg = e + "\nNo Results Found";
//            if (listener != null) listener.onFetchFailure(msg);
//            return;
//        }
//

        if (listener != null) {
            //set code 1 for success
            listener.onFetchComplete(pr);
        }

//        Toast.makeText(context, titles, Toast.LENGTH_LONG).show();

        super.onPostExecute(pr);
    }
}