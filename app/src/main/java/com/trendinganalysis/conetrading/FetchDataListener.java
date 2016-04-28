package com.trendinganalysis.conetrading;


import com.avast.android.dialogs.iface.ISimpleDialogListener;

import java.util.List;

public interface FetchDataListener extends ISimpleDialogListener {
    void onFetchComplete(List<Products> data);

    void onFetchFailure(String msg);

    void onFetchComplete(int code);

    void onFetchComplete(CustomAdapter adapter);


}
