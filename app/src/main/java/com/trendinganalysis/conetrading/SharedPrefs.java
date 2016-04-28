package com.trendinganalysis.conetrading;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ken on 4/27/2016.
 */
public class SharedPrefs {

    public static final String KEY_USERNAME = "com.conetrading.trendinganalysis.username";
    public static final String KEY_PASSWORD = "com.conetrading.trendinganalysis.password";
    public static final String KEY_ALWAYS_REMEMBER = "ccom.conetrading.trendinganalysis.always-remeber";
    public static final String KEY_CONFIGURED = "com.conetrading.trendinganalysis.configured";
    public static final String KEY_DB_USERNAME = "com.conetrading.trendinganalysis.db-username";
    public static final String KEY_DB_PASSWORD = "com.conetrading.trendinganalysis.db-password";
    public static final String KEY_DB_NAME = "com.conetrading.trendinganalysis.db-name";
    public static final String KEY_DB_HOST = "com.conetrading.trendinganalysis.db-name";
    public static final String KEY_DOMAIN_NAME = "com.conetrading.trendinganalysis.domain-name";
    public static final String KEY_DATE_MODIFIED = "com.conetrading.trendinganalysis.date-modified";
    private static final String PREF_NAME = "com.conetrading.trendinganalysis.Preference";
    public static SharedPrefs sInstance;
    public final SharedPreferences mPref;

    public SharedPrefs(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefs(context);
        }
    }

    public static synchronized SharedPrefs getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(SharedPrefs.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setValue(int value, String KEY_VALUE) {
        mPref.edit()
                .putInt(KEY_VALUE, value)
                .commit();
    }

    public void setValue(long value, String KEY_VALUE) {
        mPref.edit()
                .putLong(KEY_VALUE, value)
                .commit();
    }

    public void setValue(boolean value, String KEY_VALUE) {
        mPref.edit()
                .putBoolean(KEY_VALUE, value)
                .commit();
    }

    public void setValue(String value, String KEY_VALUE) {
        mPref.edit()
                .putString(KEY_VALUE, value)
                .commit();
    }

    public int getValueInt(String KEY_VALUE) {
        return mPref.getInt(KEY_VALUE, 0);
    }

    public long getValueLong(String KEY_VALUE) {
        return mPref.getLong(KEY_VALUE, 0);
    }

    public boolean getValueBol(String KEY_VALUE) {
        return mPref.getBoolean(KEY_VALUE, false);
    }

    public String getValueStr(String KEY_VALUE) {
        return mPref.getString(KEY_VALUE, "");
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

}
