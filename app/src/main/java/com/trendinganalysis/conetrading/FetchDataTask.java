package com.trendinganalysis.conetrading;

/**
 * Created by Ken on 1/5/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class FetchDataTask extends AsyncTask<String, Void, List<Products>> {
    Activity activity;
    Context context;
    DatabaseHandler databaseHandler = MainActivity.databaseHandler;
    DataHandler dataHandler = MainActivity.dataHandler;
    List<Products> products = new ArrayList<>();
    private FetchDataListener listener = null;
    private String msg;


    public FetchDataTask(FetchDataListener listener, Activity activity, Context context) {
        this.listener = listener;
        this.activity = activity;
        this.context = context;
    }

    @Override
    protected List<Products> doInBackground(String... params) {
        if (params == null) return null;

        List<Products> temp = new ArrayList<>();

        if (dataHandler.isFilterSearch()) {
            temp = databaseHandler.secondLevelSearch(
                    dataHandler.getMaker(),
                    dataHandler.getType(),
                    dataHandler.getModel(),
                    dataHandler.getEngine(),
                    dataHandler.getSize(),
                    dataHandler.getDateFrom(),
                    dataHandler.getDateTo(),
                    dataHandler.getSeries(),
                    dataHandler.getSource());
        } else {
            try {
                temp = databaseHandler.firstLevelSearch(dataHandler.getKeyword());
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        dataHandler.setDataLength(temp.size());


        return temp;
    }

    @Override
    protected void onPostExecute(List<Products> listresult) {
        if (listresult == null) {
            dataHandler.setDataLength(0);
            if (activity instanceof ResultActivity) {
                ((ResultActivity) activity).setResultNumber();
            }
            if (listener != null) listener.onFetchFailure(msg);
            return;
        }


        //notify the activity that fetch data has been complete
        if (activity instanceof ResultActivity) {
            ((ResultActivity) activity).setResultNumber();
            ((ResultActivity) activity).prepareListData();
        }

        if (listener != null) {

            try {
                listener.onFetchComplete(listresult);
                dataHandler.setDataLength(listresult.size());

            } catch (Exception e) {
                e.printStackTrace();
                dataHandler.setDataLength(0);
                if (activity instanceof ResultActivity) {
                    ((ResultActivity) activity).setResultNumber();
                }
                msg = "No Results Found. Your database maybe outdated :)";
                if (listener != null) listener.onFetchFailure(msg);
            }
        }

    }

}