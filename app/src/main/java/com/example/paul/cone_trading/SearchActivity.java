package com.example.paul.cone_trading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    Toast toast;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editTextSearch = (EditText)findViewById(R.id.search_searchField);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);

    }


    public void doSearchLogout(View view) {
        toast = Toast.makeText(this, "Logout", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doSearchSettings(View view){
        toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doSearch(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
}
