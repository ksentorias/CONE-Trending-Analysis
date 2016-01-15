package com.example.paul.cone_trading;

/**
 * Created by Paul on 1/5/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


public class Test2 extends AppCompatActivity implements View.OnClickListener {

    boolean child1 =true;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, Test2.class));
    }

    private Button mExpandButton;
    private Button mMoveChildButton;
    private Button mMoveChildButton2;
    private Button mMoveTopButton;
    private Button mSetCloseHeihgtButton;
    private ExpandableRelativeLayout mExpandLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);

//        getSupportActionBar().setTitle(Test2.class.getSimpleName());

        mExpandButton = (Button) findViewById(R.id.expandButton);
        mMoveChildButton = (Button) findViewById(R.id.moveChildButton);
        mMoveChildButton2 = (Button) findViewById(R.id.moveChildButton2);
        mMoveTopButton = (Button) findViewById(R.id.moveTopButton);
        mSetCloseHeihgtButton = (Button) findViewById(R.id.setCloseHeightButton);
        mExpandLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);

        mExpandButton.setOnClickListener(this);
        mMoveChildButton.setOnClickListener(this);
        mMoveChildButton2.setOnClickListener(this);
        mMoveTopButton.setOnClickListener(this);
        mSetCloseHeihgtButton.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.expandButton:
                mExpandLayout.toggle();
            break;
            case R.id.moveChildButton:
                if(child1) {mExpandLayout.moveChild(0); child1 = false;}
                else {
                    child1 = true;
                    mExpandLayout.move(1);
                    mExpandLayout.move(2);
                }
                break;
            case R.id.moveChildButton2:
                mExpandLayout.moveChild(1);
                break;
            case R.id.moveTopButton:
                mExpandLayout.move(0);
                break;
            case R.id.setCloseHeightButton:
                mExpandLayout.setClosePosition(mExpandLayout.getCurrentPosition());
                break;
        }
    }
}