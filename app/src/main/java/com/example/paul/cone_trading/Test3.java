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


public class Test3 extends AppCompatActivity {

    boolean child1 =true;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, Test2.class));
    }


    private ExpandableRelativeLayout dateMenuLayout;
    private ExpandableRelativeLayout typeMenuLayout;
    private ExpandableRelativeLayout makerMenuLayout;
    private ExpandableRelativeLayout modelMenuLayout;
    private ExpandableRelativeLayout sizeMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3);

        dateMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.dateMenu);
        typeMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.typeMenu);
        makerMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.makerMenu);
        modelMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.modelMenu);
        sizeMenuLayout = (ExpandableRelativeLayout) findViewById(R.id.sizeMenu);
    }

    public void doDateMenu(View view) {
        dateMenuLayout.toggle();
    }

    public void doTypeMenu(View view) {
        typeMenuLayout.toggle();
    }

    public void doMakerMenu(View view) {
        makerMenuLayout.toggle();
    }

    public void doModelMenu(View view) {
        modelMenuLayout.toggle();
    }

    public void doSizeMenu(View view) {
        sizeMenuLayout.toggle();
    }
}