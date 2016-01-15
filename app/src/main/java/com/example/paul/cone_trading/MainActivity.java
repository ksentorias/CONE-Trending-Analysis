package com.example.paul.cone_trading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {


    String s_username;
    String s_password;
    EditText editTextUsername;
    EditText editTextPassword;
    ImageView toggleBtn;
    boolean toggler = true;
    boolean remember = true;
    Toast toast;
    public static Credential credentials = new Credential();


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


        editTextUsername.setGravity(Gravity.CENTER);
        editTextPassword.setGravity(Gravity.CENTER);


        editTextPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logIn();
                    return true;

                }
                return false;
            }
        });

        editTextUsername.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logIn();
                    return true;
                }
                return false;
            }
        });

        checkRemember();
    }

    public void onSubmit(View view) {
        logIn();
    }

    public void logIn() {
        s_username = editTextUsername.getText().toString().trim();
        s_password = editTextPassword.getText().toString().trim();

        credentials.setCredentials(s_username, s_password, remember);


        if (credentials.checkCredentials()) {
            if(remember){
                credentials.setUserName(this,s_username,remember);

                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
        }
        else {

            Toast.makeText(this, "Login Failed: Incorrect Password or Username", Toast.LENGTH_LONG).show();
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(findViewById(R.id.fieldLayout));
        }
    }

    public void doForgetPassword(View view) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Forgot your password?")
                .setMessage("Please contact the site administrator to change your password:\nadmin@c-one.com")
                .setPositiveButtonText("Close").show();
    }

    public void doSwitchRemember(View view) {
        if (!toggler) { //if toggle is off
            toggleBtn.setImageResource(R.drawable.ic_toggle_on);
            Toast.makeText(this, "Always remember is ON", Toast.LENGTH_SHORT).show();
            remember = true;
            toggler = !toggler;
        } else { //else if toggle is on
            toggleBtn.setImageResource(R.drawable.ic_toggle_off);
            Toast.makeText(this, "Always remember is OFF", Toast.LENGTH_SHORT).show();
            remember = false;
            toggler = !toggler;
        }


    }

    public void checkRemember(){
        if(credentials.getUserName(this)) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }
}
