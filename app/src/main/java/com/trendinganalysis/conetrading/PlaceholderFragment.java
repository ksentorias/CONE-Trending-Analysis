package com.trendinganalysis.conetrading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 1/6/2016.
 */
public class PlaceholderFragment extends Fragment implements FetchDataListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    /*keyword used across the system*/
    public static String keyword;
    private static int pageNumber;
    View thisView;
    View errorView;
    View rootView;
    boolean fail;
    CustomAdapter adapter;
    DataHandler dataHandler = MainActivity.dataHandler;
    ProgressDialog dialog;
    private ListView lv;

    public PlaceholderFragment() {
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_list_fragment, container, false);

        lv = (ListView) rootView.findViewById(android.R.id.list);

        initView();

        thisView = rootView;

        return thisView;
    }


    private void initView() {

        if (dataHandler.isRunWithData()) {
            // show progress dialog
            dialog = ProgressDialog.show(getContext(), "", "Searching...");

//            String url = DataHandler.permalink + "readData.php";
//            dataHandler.setReportData(false);
//            getKeywordValuePair();

            FetchDataTask task = new FetchDataTask(this, getActivity(), getContext());
            task.execute("this is just dummy");

//            Toast.makeText(getContext(), dataHandler.getKeyword() + " " + dataHandler.getDateFrom() + " " + dataHandler.getDateTo() + " " + dataHandler.getType() + " " + dataHandler.getMaker() + " " + dataHandler.getModel() + " " + dataHandler.getEngine() + " " + dataHandler.getSize() + " filter:" + dataHandler.isFilterSearch() + " data:" + dataHandler.isRunWithData(), Toast.LENGTH_LONG).show();

        } else {


            //initialized nextpages of viewpager in asynctask
            InitializedViewPagerViews initializedViewPagerViews = new InitializedViewPagerViews(this, getActivity(), getContext(), dataHandler.getProduct().get(getArguments().getInt(ARG_SECTION_NUMBER)));
            if (getArguments().getInt(ARG_SECTION_NUMBER) <= dataHandler.getProduct().size()) {
                initializedViewPagerViews.execute("dummy data");
            }
        }

    }

    @Override
    public void onFetchComplete(List<Products> data) {
        // dismiss the progress dialog
        if (dialog != null) dialog.dismiss();

        dataHandler.setProduct(splitData(data));

//        Toast.makeText(getContext(),  "data size: "+ dataHandler.getProduct().size()+" current cursor: "+ getArguments().getInt(ARG_SECTION_NUMBER), Toast.LENGTH_LONG).show();

        //if product result only 1 data get only product in index 0
        if (dataHandler.getProduct().size() == 1) {

            adapter = new CustomAdapter(getContext(), dataHandler.getProduct().get(0));
            lv.setAdapter(adapter);
            lv.setEmptyView(errorView);

            if (getActivity() instanceof ResultActivity) {
//                ((ResultActivity) getActivity()).setResultNumber();
                ((ResultActivity) getActivity()).setNumberofPages(1);
                ((ResultActivity) getActivity()).setNavigation(1);

            }
        }
        //thus, if more than 1, get all base on SECTION_NUMBER
        else {
            adapter = new CustomAdapter(getContext(), dataHandler.getProduct().get(getArguments().getInt(ARG_SECTION_NUMBER)));
            lv.setAdapter(adapter);
            lv.setEmptyView(errorView);

            if (getActivity() instanceof ResultActivity) {
//                ((ResultActivity) getActivity()).setResultNumber();
                ((ResultActivity) getActivity()).setNumberofPages(dataHandler.getProduct().size());
                ((ResultActivity) getActivity()).setNavigation(dataHandler.getProduct().size());

            }
        }

        dataHandler.setRunWithData(false);


    }

    @Override
    public void onFetchFailure(String msg) {
        fail = false;
        // dismiss the progress dialog
        if (dialog != null) dialog.dismiss();
        // show failure message
//        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

        if (getActivity() instanceof ResultActivity) {
            ((ResultActivity) getActivity()).showSnackBarMsg(msg);

        }


    }

    @Override
    public void onFetchComplete(int code) {

    }

    @Override
    public void onFetchComplete(CustomAdapter adapter) {
        lv.setAdapter(adapter);
        lv.setEmptyView(errorView);
    }


    public List<List<Products>> splitData(List<Products> data) {
        List<List<Products>> splittedData = new ArrayList<>();
        List<Products> currentSplit = null;
        for (int i = 0; i < data.size(); i++) {
            if (i % 10 == 0) {
                currentSplit = new ArrayList<>();
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


class InitializedViewPagerViews extends AsyncTask<String, Void, CustomAdapter> {

    List<Products> products;
    private Activity activity;
    private Context context;
    private FetchDataListener listener = null;
    private CustomAdapter adapter;

    public InitializedViewPagerViews(FetchDataListener listener, Activity activity, Context context, List<Products> products) {
        this.listener = listener;
        this.activity = activity;
        this.context = context;
        this.products = products;
    }

    @Override
    protected CustomAdapter doInBackground(String... params) {
        adapter = new CustomAdapter(context, products);
        return adapter;
    }

    @Override
    protected void onPostExecute(CustomAdapter adapter) {
        if (listener != null) {
            listener.onFetchComplete(adapter);
        }
    }


}