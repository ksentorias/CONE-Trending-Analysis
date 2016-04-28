package com.trendinganalysis.conetrading;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ken on 12/3/2015.
 */
public class RobotoButtonBold extends Button {

    public RobotoButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf"));
    }
}
