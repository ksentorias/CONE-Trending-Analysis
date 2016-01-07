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
public class DefaultResultMessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.default_result_layout, container, false);
        return v;
    }

    public static DefaultResultMessageFragment newInstance() {

        DefaultResultMessageFragment f = new DefaultResultMessageFragment();
        Bundle b = new Bundle();
        f.setArguments(b);

        return f;
    }
}
