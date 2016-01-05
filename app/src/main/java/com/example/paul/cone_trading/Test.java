package com.example.paul.cone_trading;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Test extends Activity {



    String name;
    String username;
    String password;
    String email;
    InputStream is = null;
    String result = null;
    String line = null;

    EditText e_username;
    EditText e_name;
    EditText e_password;
    EditText e_email;
    ProgressBar progressbar;
    TextView loadingText;
    TextView tv_name;
    TextView tv_username;
    TextView tv_password;
    TextView tv_email;
    int code;
    int run_code = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        e_username = (EditText) findViewById(R.id.username);
        e_name = (EditText) findViewById(R.id.name);
        e_password = (EditText) findViewById(R.id.password);
        e_email = (EditText) findViewById(R.id.email);


        loadingText = (TextView) findViewById(R.id.loadingText);
        tv_name = (TextView) findViewById(R.id.t_name);
        tv_username = (TextView) findViewById(R.id.t_username);
        tv_password = (TextView) findViewById(R.id.t_password);
        tv_email = (TextView) findViewById(R.id.t_email);


        progressbar = (ProgressBar) findViewById(R.id.progressBar);


    }

    public void onCreateData(View view) {


        username = e_username.getText().toString();
        name = e_name.getText().toString();
        password = e_password.getText().toString();
        email = e_email.getText().toString();

        Toast.makeText(getApplicationContext(), "Create Data", Toast.LENGTH_SHORT).show();


        new AsycncTasker().execute(1);
    }

    public void onSearchData(View view) {

        username = e_username.getText().toString();


        Toast.makeText(getApplicationContext(), "Search Data", Toast.LENGTH_SHORT).show();


        new AsycncTasker().execute(2);
    }

    public void doUpdate(View view) {
        username = e_username.getText().toString();
        name = e_name.getText().toString();
        password = e_password.getText().toString();
        email = e_email.getText().toString();

        Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();


        new AsycncTasker().execute(3);
    }

    public void doDelete(View view) {

        username = e_username.getText().toString();

        Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();


        new AsycncTasker().execute(4);

    }


    class AsycncTasker extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            loadingText.setText("Starting...");


        }

        @Override
        protected Integer doInBackground(Integer... params) {

            switch (params[0]) {
                case 1: {
                    insert();
                    break;
                }
                case 2: {
                    select();
                    break;
                }
                case 3: {
                    update();
                    break;
                }
                case 4: {
                    delete();
                    break;
                }
                default: {
                    Log.e("SWITCH ERROR", "in background sa default nabutang");
                    break;
                }
            }

            return params[0];
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }


        @Override
        protected void onPostExecute(Integer resultFromProcess) {

            switch (resultFromProcess) {
                case 1: {
                    loadingText.setText("Inserted Successfully");
                    break;
                }
                case 2: {

                    tv_username.setText(username);
                    tv_name.setText(name);
                    tv_password.setText(password);
                    tv_email.setText(email);

                    loadingText.setText("Selected Successfully");
                    break;
                }
                case 3: {
                    tv_username.setText(username);
                    tv_name.setText(name);
                    tv_password.setText(password);
                    tv_email.setText(email);

                    loadingText.setText(result);
                    break;
                }
                case 4: {
                    loadingText.setText(result);
                    break;
                }
                default: {
                    break;
                }
            }

        }

        public void insert() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));

            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJsonFromUrl("http://webprojectupdates.com/c-one/test/insert.php", nameValuePairs);

            try {
                code = (json.getInt("code"));

                if (code == 1) {
                    result = "Inserted Successfully";
                    Log.e("pass 3", "Inserted Successfully");

                } else {
                    result = "Inserted unsuccessfully";

                    Log.e("pass 3", "Sorry, Try Again");
//                    Toast.makeText(getBaseContext(), "Sorry, Try Again",
//                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }


        public void select() {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username", username));

            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJsonFromUrl("http://webprojectupdates.com/c-one/test/select.php", nameValuePairs);

            try {
                name = (json.getString("name"));
                username = (json.getString("username"));
                password = (json.getString("password"));
                email = (json.getString("email"));
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }

        }

        public void update() {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("email", email));

            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJsonFromUrl("http://webprojectupdates.com/c-one/test/update.php", nameValuePairs);

            try {
                code = (json.getInt("code"));

                if (code == 1) {
                    Log.e("pass 3", "Inserted Successfully");
                    result = "Updated dadto!";

                } else {
                    Log.e("pass 3", "Sorry, Try Again");
                    result = "sorry dadto!";
                }

            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }

        public void delete() {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username", username));

            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJsonFromUrl("http://webprojectupdates.com/c-one/test/delete.php", nameValuePairs);

            try {
                code = (json.getInt("code"));

                if (code == 1) {
                    Log.e("pass 3", "Deleted Successfully");
                    result = "Deleted success!";

                } else {
                    Log.e("pass 3", "Sorry, Try Again");
                    result = "Deleted imong dagway!";
                }

            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
        }
    }


}
