package com.example.paul.cone_trading;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Paul on 1/7/2016.
 */
public class NoDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.not_found_layout, container, false);
        return v;
    }

    public static NoDataFragment newInstance() {

        NoDataFragment f = new NoDataFragment();
        Bundle b = new Bundle();
        f.setArguments(b);

        return f;
    }
}
