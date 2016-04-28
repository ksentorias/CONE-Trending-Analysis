package com.trendinganalysis.conetrading;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ken on 4/20/2016.
 */
public class WelcomeActivity extends AppCompatActivity {
    DataHandler dataHandler = MainActivity.dataHandler;

    private TextView warningText;
    private Button proceedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        proceedButton = (Button) findViewById(R.id.proceedButton);
        warningText = (TextView) findViewById(R.id.warningText);

        warningText.setVisibility(View.INVISIBLE);
        proceedButton.setEnabled(false);

        if (checkInternetConnection()) {
            proceedButton.setEnabled(true);
        } else {
            proceedButton.setEnabled(false);
            proceedButton.setBackgroundResource(R.drawable.rounded_button_disabled);
            warningText.setVisibility(View.VISIBLE);
        }

    }

    public void doProceed(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
