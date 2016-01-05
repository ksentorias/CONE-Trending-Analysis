package com.example.paul.cone_trading;

/**
 * Created by Paul on 12/16/2015.
 */
public class Credential {

    String username = "";
    String password = "";
    boolean always_remember = false;

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
