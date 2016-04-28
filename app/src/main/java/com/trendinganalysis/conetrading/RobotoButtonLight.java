package com.trendinganalysis.conetrading;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Paul on 12/3/2015.
 */
public class RobotoButtonLight extends Button {

    public RobotoButtonLight(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed-Light.ttf"));
    }
}
