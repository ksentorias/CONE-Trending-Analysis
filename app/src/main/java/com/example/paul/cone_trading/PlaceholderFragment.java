package com.example.paul.cone_trading;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/6/2016.
 */
public class PlaceholderFragment extends Fragment implements FetchDataListener{

    /*keyword used across the system*/
    public static  String keyword;

    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


    ProgressDialog dialog;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView lv;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);


        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.result_list_fragment, container, false);
        View errorView = inflater.inflate(R.layout.not_found_layout, container, false);

//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));




        lv = (ListView) rootView.findViewById(android.R.id.list);
        initView();




        return rootView;
    }


    private void initView() {
        // show progress dialog
        dialog = ProgressDialog.show(getContext(), "", "Searching...");
        String url = "http://webprojectupdates.com/c-one/test/readData.php";

//        nameValuePairs.add(new BasicNameValuePair("keyword", getKeyword()));
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);
    }

    @Override
    public void onFetchComplete(List<ProductsIO> data) {
        DataHolder.code = 1;
        // dismiss the progress dialog
        if(dialog != null)  dialog.dismiss();
        // create new adapter
        CustomAdapter adapter = new CustomAdapter(getContext(), data);
        // set the adapter to list
//        setListAdapter(adapter);
        lv.setAdapter(adapter);


    }

    @Override
    public void onFetchFailure(String msg) {
        // dismiss the progress dialog
        if(dialog != null)  dialog.dismiss();
        // show failure message
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

        //set code 0 for nah :(
    }

    public  ArrayList<NameValuePair> getKeywordValuePair(){
        nameValuePairs.add(new BasicNameValuePair("keyword", DataHolder.keyword));
        return nameValuePairs;
    }


}