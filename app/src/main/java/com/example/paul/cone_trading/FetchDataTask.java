package com.example.paul.cone_trading;

/**
 * Created by Paul on 1/5/2016.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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

public class FetchDataTask extends AsyncTask<String, Void, String> {
    private final FetchDataListener listener;
    private String msg;
    Activity activity;
    PlaceholderFragment placeholderFragment = new PlaceholderFragment();
    DataHolder dataHolder = SearchActivity.dataHolder;

    public FetchDataTask(FetchDataListener listener, Activity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        if (params == null) return null;

        // get url from params
        String url = params[0];

        try {
            // create http connection
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            //connect
            httppost.setEntity(new UrlEncodedFormEntity(placeholderFragment.getKeywordValuePair()));

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
            msg = "No Network Connection";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String sJson) {
//        JSONArray aJson = null;
//        try {
//            aJson = new JSONArray(sJson);
//            DataHolder.dataLength = aJson.length();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        if (sJson == null) {
            dataHolder.setDataLength(0);
            if(activity instanceof ResultActivity) {
                ((ResultActivity) activity).setResultNumber();
            }
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }

        try {
            JSONArray aJson = new JSONArray(sJson);
            dataHolder.setDataLength(aJson.length());
            // convert json string to json array
            // create products list
            List<Products> products = new ArrayList<Products>();
            //set number of results
            for (int i = 0; i < aJson.length(); i++) {
                JSONObject json = aJson.getJSONObject(i);
                Products product = new Products();

                product.setTitle(json.getString("product_title"));
                product.setDesc(json.getString("product_desc"));
                product.setType(json.getString("product_type"));
                product.setModel(json.getString("product_model"));
                product.setMaker(json.getString("product_maker"));
                product.setSize(json.getString("product_size"));
                product.setCategory(json.getString("product_category"));
                product.setPrice_php(Integer.parseInt(json.getString("product_price_php")));
                product.setPrice_jpy(Integer.parseInt(json.getString("product_price_jpy")));

                try {
                    String string = json.getString("product_date");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(string);
                    product.setFullDate(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                product.setTotalDl(Long.parseLong(json.getString("total_dl")));
//                product.setRating(Integer.parseInt(json.getString("rating")));
//                product.setIcon(json.getString("icon"));

                // add the app to products list
                products.add(product);
                Log.e("pass 2", "json to array setter success ");
            }

            //notify the activity that fetch data has been complete
            if(activity instanceof ResultActivity) {
                ((ResultActivity) activity).setResultNumber();
            }

            if (listener != null){
                //set code 1 for success
                listener.onFetchComplete(products);
            }


        } catch (JSONException e) {
            dataHolder.setDataLength(0);
            if(activity instanceof ResultActivity) {
                ((ResultActivity) activity).setResultNumber();
            }
            msg = "No Results Found";
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }
    }

    /**
     * This function will convert response stream into json string
     *
     * @param is response string
     * @return json string
     * @throws IOException
     */
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