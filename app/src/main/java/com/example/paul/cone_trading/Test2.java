package com.example.paul.cone_trading;

/**
 * Created by Paul on 1/5/2016.
 */
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class Test2 extends ListActivity implements FetchDataListener{
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        initView();
    }

    private void initView() {
        // show progress dialog
        dialog = ProgressDialog.show(this, "", "Loading...");
        String url = "http://webprojectupdates.com/c-one/test/readData.php";
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);
    }

    @Override
    public void onFetchComplete(List<ProductsIO> data) {
        // dismiss the progress dialog
        if(dialog != null)  dialog.dismiss();
        // create new adapter
        CustomAdapter adapter = new CustomAdapter(this, data);
        // set the adapter to list
        setListAdapter(adapter);
    }

    @Override
    public void onFetchFailure(String msg) {
        // dismiss the progress dialog
        if(dialog != null)  dialog.dismiss();
        // show failure message
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}