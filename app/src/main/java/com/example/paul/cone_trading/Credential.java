package com.example.paul.cone_trading;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Paul on 12/16/2015.
 */
public class Credential {

    String username = "";
    String password = "";
    boolean always_remember = false;

    static final String PREF_USER_NAME= "username";
    static final String PREF_REMEMBER= "isRemember";

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

    public void setCredentials(String username, String password, boolean always_remember){
        this.username = username;
        this.password = password;
        this.always_remember = always_remember;
    }

    public boolean checkCredentials()
    {
        boolean pass = false;

        if("admin".equals(this.username) && "admin".equals(this.password)) pass = true;
        else pass = false;


        return pass;
    }

}
