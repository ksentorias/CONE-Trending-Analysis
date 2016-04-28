package com.trendinganalysis.conetrading;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ken on 12/3/2015.
 */
public class RobotoTextViewLightItalic extends TextView {

    public RobotoTextViewLightItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-LightItalic.ttf"));
    }
}
