package com.example.paul.cone_trading;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Paul on 1/5/2016.
 */
public class SearchbyKeyword {

    public ArrayList<String[]> doSearch() {

        ArrayList<String[]> productList = new ArrayList<String[]>();


        return productList;
    }


    public void select() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", "new"));

        JSONParser jParser = new JSONParser();

        JSONObject json = jParser.getJsonFromUrl("http://webprojectupdates.com/c-one/test/readData.php", nameValuePairs);

        try {


        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }

    }




}
