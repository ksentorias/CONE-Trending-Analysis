package com.trendinganalysis.conetrading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by Paul on 12/16/2015.
 */
public class Credential{

    String username = "";
    String password = "";

    Context context;
    Activity activity;


    static final String PREF_USER_NAME= "username";
    static final String PREF_REMEMBER= "isRemember";

    public Credential(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }



    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void setUserName(Context ctx, String userName, boolean remember)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putBoolean(PREF_REMEMBER, remember);
        editor.commit();
    }

    public void clearCredentials(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

    public boolean getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_REMEMBER, false);
    }

    public void setCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }





}



