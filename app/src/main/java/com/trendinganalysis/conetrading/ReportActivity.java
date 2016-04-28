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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

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

public class ReportActivity extends AppCompatActivity implements FetchDataListener {

    final CharSequence Titles[] = {"Local Auction", "Foreign Supplier", "Foreign Auction", "Local Market", "Sold"};
    final int Numboftabs = 5;

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    EditText report_searchField;
    ProgressDialog dialog;

    public List<List<Products>> reportProducts = new ArrayList<List<Products>>();

    public static String category;
    public static int counter;


    DataHolder dataHolder = SearchActivity.dataHolder;
    Credential credentials = MainActivity.credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        report_searchField = (EditText) findViewById(R.id.report_searchField);
        counter = 0;


        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(adapter);
//
//        // Give the PagerSlidingTabStrip the ViewPager
//        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        // Attach the view pager to the tab strip
//        tabsStrip.setViewPager(pager);
//


        //hide keyvboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(report_searchField.getWindowToken(), 0);


        getReportData();

//        ---------------------------------------------------

    }

    public List<List<Products>> getReportProducts() {
        return reportProducts;
    }

    public void setReportProducts(List<List<Products>> reportProducts) {
        this.reportProducts = reportProducts;
    }

    public void doReportLogout(View view) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout with the current account?")
                .setNegativeButtonText("No")
                .setRequestCode(DataHolder.logoutCode)
                .setPositiveButtonText("Yes").show();
    }

    public void onClearTextReport(View view) {
        report_searchField.setText("");
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

    public void test(View view) {
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public void getReportData() {
        dialog = ProgressDialog.show(this, "", "Generating Graph...");

        String url = DataHolder.permalink + "selectEachData.php";

        for (int i = 0; i < 5; i++) {
            category = (String) Titles[i];
            Log.i("sa getReport", category);
            FetchData task = new FetchData(this, this, this, dataHolder, (String) Titles[i]);
            task.execute(url);
        }


    }

    @Override
    public void onFetchComplete(List<Products> data) {
        reportProducts.add(data);
        dataHolder.setReportProducts(reportProducts);
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


    public void setDataToChart(List<List<Products>> reportProducts) {
        dataHolder.setReportLAProducts(reportProducts.get(0));
        dataHolder.setReportFSProducts(reportProducts.get(1));
        dataHolder.setReportFAProducts(reportProducts.get(2));
        dataHolder.setReportLMProducts(reportProducts.get(3));
        dataHolder.setReportSDProducts(reportProducts.get(4));

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

class FetchData extends AsyncTask<String, Void, String> {
    DataHolder dataHolder;

    private List<Products> reportProduct;
    private List<List<Products>> reportProducts;


    Activity activity;

    Context context;

    FetchDataListener listener;

    int counter;
    String msg;
    String category;

    public FetchData(FetchDataListener listener, Activity activity, Context context, DataHolder dataHolder, String category) {
        this.listener = listener;
        this.activity = activity;
        this.context = context;
        this.dataHolder = dataHolder;
        this.category = category;
    }

    @Override
    protected String doInBackground(String... params) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        nameValuePairs.add(new BasicNameValuePair("category", category));
        Log.e("nameValuePairs", ReportActivity.category);
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
            JSONArray aJson = new JSONArray(s);
            // convert json string to json array
            // create products list
            reportProduct = new ArrayList<Products>();
            reportProducts = ((ReportActivity) activity).getReportProducts();

            //set number of results
            for (int i = 0; i < aJson.length(); i++) {
                JSONObject json = aJson.getJSONObject(i);
                Products product = new Products();

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


                reportProduct.add(product);
                Log.e("pass 2", "json to array setter success ");
            }


        } catch (JSONException e) {
            msg = e + "\nNo Results Found";
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }


        if (listener != null) {
            //set code 1 for success
            listener.onFetchComplete(reportProduct);
        }

//        Toast.makeText(context, titles, Toast.LENGTH_LONG).show();

        super.onPostExecute(s);
    }

    public List<Products> getReportProduct() {
        return reportProduct;
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