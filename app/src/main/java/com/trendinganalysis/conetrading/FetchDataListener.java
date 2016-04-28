package com.trendinganalysis.conetrading;


import com.avast.android.dialogs.iface.ISimpleDialogListener;

import java.util.List;

public interface FetchDataListener extends ISimpleDialogListener{
    public void onFetchComplete(List<Products> data);
    public void onFetchFailure(String msg);
    public void onFetchComplete(int code);


}
