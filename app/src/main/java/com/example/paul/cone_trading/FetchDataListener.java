package com.example.paul.cone_trading;


import java.util.List;

public interface FetchDataListener {
    public void onFetchComplete(List<Products> data);
    public void onFetchFailure(String msg);
}
