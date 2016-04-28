package com.trendinganalysis.conetrading;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class RobotoEditTextViewBold extends EditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public RobotoEditTextViewBold(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RobotoEditTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public RobotoEditTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        super.setTypeface(tf);
    }
}