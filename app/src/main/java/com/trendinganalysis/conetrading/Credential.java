package com.trendinganalysis.conetrading;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ken on 12/16/2015.
 */
public class Credential {

    Context context;
    Activity activity;

    private SharedPrefs sharedPrefs = MainActivity.sharedPrefs;

    public Credential(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setUserName(String userName, String password, boolean remember) {
        sharedPrefs.setValue(userName, SharedPrefs.KEY_USERNAME);
        sharedPrefs.setValue(password, SharedPrefs.KEY_PASSWORD);
        sharedPrefs.setValue(remember, SharedPrefs.KEY_ALWAYS_REMEMBER);
    }

    public void clearCredentials() {
        sharedPrefs.remove(SharedPrefs.KEY_USERNAME);
        sharedPrefs.remove(SharedPrefs.KEY_PASSWORD);
        sharedPrefs.remove(SharedPrefs.KEY_ALWAYS_REMEMBER);
    }

    public boolean isRemember() {
        return sharedPrefs.getValueBol(SharedPrefs.KEY_ALWAYS_REMEMBER);
    }

    public String getUsername() {
        return sharedPrefs.getValueStr(SharedPrefs.KEY_USERNAME);
    }

    public String getPassword() {
        return sharedPrefs.getValueStr(SharedPrefs.KEY_PASSWORD);
    }
}



