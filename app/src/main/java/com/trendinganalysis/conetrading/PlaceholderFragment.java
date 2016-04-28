package com.trendinganalysis.conetrading;

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
public class PlaceholderFragment extends Fragment implements FetchDataListener {

    /*keyword used across the system*/
    public static String keyword;
    private static int pageNumber;




    public FetchDataListener fetchDataListener = this;

    View thisView;
    View errorView;
    View rootView;
    View defaultView;
    boolean fail;
    CustomAdapter adapter;

    DataHolder dataHolder = SearchActivity.dataHolder;

    public static  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


    ProgressDialog dialog;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView lv;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        pageNumber = sectionNumber;

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_list_fragment, container, false);

        lv = (ListView) rootView.findViewById(android.R.id.list);

        initView();

        thisView = rootView;

        return thisView;
    }


    private void initView() {

        if (dataHolder.isRunWithData()) {
            // show progress dialog
            dialog = ProgressDialog.show(getContext(), "", "Searching...");


            String url = DataHolder.permalink + "readData.php";
            dataHolder.setReportData(false);
            getKeywordValuePair();
            FetchDataTask task = new FetchDataTask(this, getActivity());
            task.execute(url);

//            Toast.makeText(getContext(), dataHolder.getKeyword() + " " + dataHolder.getDateFrom() + " " + dataHolder.getDateTo() + " " + dataHolder.getType() + " " + dataHolder.getMaker() + " " + dataHolder.getModel() + " " + dataHolder.getSize() + " filter:" + dataHolder.isFilterSearch() + " data:" + dataHolder.isRunWithData(), Toast.LENGTH_LONG).show();

        } else {
            if (getArguments().getInt(ARG_SECTION_NUMBER) <= dataHolder.getProduct().size()) {
                adapter = new CustomAdapter(getContext(), dataHolder.getProduct().get(getArguments().getInt(ARG_SECTION_NUMBER)));
                lv.setAdapter(adapter);
                lv.setEmptyView(errorView);
            }
        }

    }

    @Override
    public void onFetchComplete(List<Products> data) {
        // dismiss the progress dialog
        if (dialog != null) dialog.dismiss();

        dataHolder.setProduct(splitData(data));

//        Toast.makeText(getContext(),  "data size: "+ dataHolder.getProduct().size()+" current cursor: "+ getArguments().getInt(ARG_SECTION_NUMBER), Toast.LENGTH_LONG).show();

        if (dataHolder.getProduct().size() == 1) {

            adapter = new CustomAdapter(getContext(), dataHolder.getProduct().get(0));
            lv.setAdapter(adapter);
            lv.setEmptyView(errorView);

            if (getActivity() instanceof ResultActivity) {
                ((ResultActivity) getActivity()).setNumberofPages(1);
                ((ResultActivity) getActivity()).setNavigation(1);
            }
        } else {
            adapter = new CustomAdapter(getContext(), dataHolder.getProduct().get(getArguments().getInt(ARG_SECTION_NUMBER)));
            lv.setAdapter(adapter);
            lv.setEmptyView(errorView);

            if (getActivity() instanceof ResultActivity) {
                ((ResultActivity) getActivity()).setNumberofPages(dataHolder.getProduct().size());
                ((ResultActivity) getActivity()).setNavigation(dataHolder.getProduct().size());
            }
        }


    }

    @Override
    public void onFetchFailure(String msg) {
        fail = false;
        // dismiss the progress dialog
        if (dialog != null) dialog.dismiss();
        // show failure message
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchComplete(int code) {

    }


    public void getKeywordValuePair() {
        nameValuePairs.add(new BasicNameValuePair("keyword", dataHolder.getKeyword()));
        nameValuePairs.add(new BasicNameValuePair("dateFrom", dataHolder.getDateFrom()));
        nameValuePairs.add(new BasicNameValuePair("dateTo", dataHolder.getDateTo()));
        nameValuePairs.add(new BasicNameValuePair("type", dataHolder.getType()));
        nameValuePairs.add(new BasicNameValuePair("maker", dataHolder.getMaker()));
        nameValuePairs.add(new BasicNameValuePair("model", dataHolder.getModel()));
            nameValuePairs.add(new BasicNameValuePair("weight", dataHolder.getSize()));

        if (dataHolder.isFilterSearch())
            nameValuePairs.add(new BasicNameValuePair("dofilter", "filter"));
        else {
            nameValuePairs.add(new BasicNameValuePair("dofilter", "nofilter"));
        }

        dataHolder.setNameValuePairs(nameValuePairs);
    }

    public List<List<Products>> splitData(List<Products> data) {
        List<List<Products>> splittedData = new ArrayList<List<Products>>();
        List<Products> currentSplit = null;
        for (int i = 0; i < data.size(); i++) {
            if (i % 10 == 0) {
                currentSplit = new ArrayList<Products>();
                splittedData.add(currentSplit);
            }
            currentSplit.add(data.get(i));
        }
        return splittedData;
    }


    @Override
    public void onNegativeButtonClicked(int requestCode) {

    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {

    }
}