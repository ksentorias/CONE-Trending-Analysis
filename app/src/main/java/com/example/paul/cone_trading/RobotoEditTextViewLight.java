package com.example.paul.cone_trading;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class RobotoEditTextViewLight extends EditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public RobotoEditTextViewLight(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RobotoEditTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public RobotoEditTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Light.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Light.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Light.ttf");
        super.setTypeface(tf);
    }
}