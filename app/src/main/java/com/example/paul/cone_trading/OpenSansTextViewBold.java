package com.example.paul.cone_trading;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Paul on 12/3/2015.
 */
public class OpenSansTextViewBold extends TextView {

    public OpenSansTextViewBold(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Bold.ttf"));
    }
}