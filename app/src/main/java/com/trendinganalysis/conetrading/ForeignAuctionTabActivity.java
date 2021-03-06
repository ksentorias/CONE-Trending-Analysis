package com.trendinganalysis.conetrading;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ForeignAuctionTabActivity extends Fragment {

    public LineChart mChart;
    public int[] CONECOLORS = {
            Color.rgb(254, 150, 1), Color.rgb(204, 0, 99), Color.rgb(134, 38, 155),
            Color.rgb(0, 210, 241), Color.rgb(0, 183, 150), Color.rgb(247, 249, 96),
            Color.rgb(43, 35, 1), Color.rgb(177, 235, 0)};
    DataHandler datahandler = MainActivity.dataHandler;
    private int[] mColors = new int[]{
            CONECOLORS[0],
            CONECOLORS[1],
            CONECOLORS[2],
            CONECOLORS[3],
            CONECOLORS[4],
            CONECOLORS[5],
            CONECOLORS[6],
            CONECOLORS[7]
    };
    private TextView textPriceXAxis;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_activity_foreign_auction, container, false);
        mChart = (LineChart) v.findViewById(R.id.chart1);

        textPriceXAxis = (TextView) v.findViewById(R.id.rotated);


        mChart.setDrawGridBackground(false);
        mChart.setDescription("source: c-onetrading.com");


        mChart.getAxisRight().setEnabled(false);

        //        create a custom MarkerView (extend MarkerView) and specify the layout
//         to use for it
        GraphMarkerView mv = new GraphMarkerView(getContext(), R.layout.graph_marker);

        // set the marker to the chart
        mChart.setMarkerView(mv);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) <= Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            xAxis.setTextSize(getResources().getDimension(R.dimen.nano_plus_text) - 10f);
        } else xAxis.setTextSize(getResources().getDimension(R.dimen.nano_plus_text));
        xAxis.setDrawGridLines(false);

//        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setSpaceBetweenLabels(1);
        xAxis.setDrawAxisLine(true);

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setDrawGridLines(true);
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) <= Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            yAxis.setTextSize(getResources().getDimension(R.dimen.nano_plus_text) - 10f);
        } else yAxis.setTextSize(getResources().getDimension(R.dimen.nano_plus_text));
        yAxis.setDrawAxisLine(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDoubleTapToZoomEnabled(true);


        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);


        mChart.setBorderWidth(10.0f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) <= Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            l.setFormSize(getResources().getDimension(R.dimen.nano_plus_text) - 10f);
            l.setTextSize(getResources().getDimension(R.dimen.nano_plus_text) - 10f);
        } else {
            l.setFormSize(getResources().getDimension(R.dimen.nano_plus_text));
            l.setTextSize(getResources().getDimension(R.dimen.nano_plus_text));
        }
        l.setXEntrySpace(10.0f);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);


        setData(datahandler.getReportFAProducts());
        mChart.animateXY(2000, 2000);

        return v;
    }

    public void setData(List<Products> productData) {

        if (!productData.isEmpty()) {
            mChart.resetTracking();

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            int productCounter = 1;
            int colorNumber = 0;
            int maxMonth = 0;


            ArrayList<Entry> values = new ArrayList<>();

            for (Products products : productData) {

                //heighest month for the chart
                if (products.getIntMonth() > maxMonth) maxMonth = products.getIntMonth();

                values.add(new Entry((float) products.getPrice_php(), products.getIntMonth()));


                if (products != productData.get(productData.size() - 1)) {
                    if (products.getProductID() != productData.get(productCounter).getProductID()) {

                        createLineData(products.getEngine(), values, dataSets, colorNumber, maxMonth);
                        values = new ArrayList<>();
                        colorNumber++;

                    }
                } else {
                    createLineData(products.getEngine(), values, dataSets, colorNumber, maxMonth);
                    values = new ArrayList<>();
                    colorNumber++;
                }

                productCounter++;
            }

        } else textPriceXAxis.setVisibility(View.INVISIBLE);
    }

    public void createLineData(String title, ArrayList<Entry> values, ArrayList<ILineDataSet> dataSets, int colorNumber, int maxMonth) {

        LineDataSet d = new LineDataSet(values, title);
        d.setLineWidth(5f);
        d.setCircleSize(5f);
        d.setDrawCircles(true);
        d.setDrawCircleHole(false);
        d.setDrawValues(false);

        int color = mColors[colorNumber % mColors.length];
        d.setColor(color);
        d.setCircleColor(color);
        dataSets.add(d);

        //set max month
        List<String> months;

        if (maxMonth < 12) {
            months = getMonths().subList(0, maxMonth + 1);
            //if month is less than DEC, add space and the end of list
            months.add("");

        } else months = getMonths();

        LineData data = new LineData(months, dataSets);
        mChart.setData(data);
        mChart.invalidate();
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<>();
        m.add("");
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Oct");
        m.add("Nov");
        m.add("Dec");
        m.add("");

        return m;
    }
}