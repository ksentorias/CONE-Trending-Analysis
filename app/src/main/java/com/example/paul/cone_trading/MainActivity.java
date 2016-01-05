package com.example.paul.cone_trading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    final static String permalink = "http://webprojectupdates.com/c-one/test/";

    String s_username;
    String s_password;
    EditText editTextUsername;
    EditText editTextPassword;
    ImageView toggleBtn;
    boolean toggler = true;
    boolean remember = false;
    Toast toast;
    Credential credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //displays current dimension of device
        //showDimension();

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        toggleBtn = (ImageView) findViewById(R.id.rememberSwitch);
        credentials = new Credential();


        editTextUsername.setGravity(Gravity.CENTER);
        editTextPassword.setGravity(Gravity.CENTER);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }

    public void onSubmit(View view) {
        s_username = editTextUsername.getText().toString().trim();
        s_password = editTextPassword.getText().toString().trim();

        credentials.setCredentials(s_username, s_password, remember);

////
        toast = Toast.makeText(this, "Login Failed: Incorrect Password or Username", Toast.LENGTH_SHORT);
        toast.show();


        if (credentials.checkCredentials()) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else {

            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(findViewById(R.id.fieldLayout));
        }


    }

    public void doForgetPassword(View view) {
        toast = Toast.makeText(this, "Forgot Password", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doSwitchRemember(View view) {
        if (!toggler) { //if toggle is off
            toggleBtn.setImageResource(R.drawable.ic_toggle_on);
            toast = Toast.makeText(this, "Always remember is OFF", Toast.LENGTH_SHORT);
            toast.show();
            remember = false;
            toggler = true;
        } else { //else if toggle is on
            toggleBtn.setImageResource(R.drawable.ic_toggle_off);
            toast = Toast.makeText(this, "Always remember is ON", Toast.LENGTH_SHORT);
            toast.show();
            remember = true;
            toggler = false;
        }


    }

    public void doTest(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void showDimension(){
        int density= getResources().getDisplayMetrics().densityDpi;

        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(this, "LDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(this, "MDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(this, "HDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Toast.makeText(this, "XHDPI", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
