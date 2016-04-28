package com.trendinganalysis.conetrading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

class CustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater;
    ProgressDialog dialog;
    NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
    private List<Products> items;
    private String[] result;
    private Context context;
    //    DecimalFormat nf = new DecimalFormat("##,##,##,##,##,##,###.##");
//    NumberFormat nf = DecimalFormat.getInstance();
    private int[] imageId;


    public CustomAdapter(PlaceholderFragment list_result_activity, String[] prgmNameList, int[] prgmImages) {

        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = list_result_activity.getContext();
        imageId = prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomAdapter(Context context, List<Products> items) {
        this.context = context;
        this.items = items;
    }

    public static String replaceString(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String currencySymbol = formatter.getCurrency().getSymbol();
        String moneyString = formatter.format(value);
        return moneyString.replace(currencySymbol, "");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Products productIO = items.get(position);


        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.result_list_row, null);
        }

        Holder holder = new Holder();

        if (productIO != null) {
            holder.txt_engine = (TextView) convertView.findViewById(R.id.txtEngine);
            holder.txt_desc = (TextView) convertView.findViewById(R.id.txtDesc);
            holder.txt_type = (TextView) convertView.findViewById(R.id.typeData);
            holder.txt_maker = (TextView) convertView.findViewById(R.id.makerData);
            holder.txt_model = (TextView) convertView.findViewById(R.id.modelData);
            holder.txt_size = (TextView) convertView.findViewById(R.id.sizeData);
            holder.txt_category = (TextView) convertView.findViewById(R.id.categoryData);
            holder.txt_price_php = (TextView) convertView.findViewById(R.id.pesoPriceData);
            holder.txt_price_jpy = (TextView) convertView.findViewById(R.id.yenPriceData);
            holder.txt_year = (TextView) convertView.findViewById(R.id.yearText);
            holder.txt_month = (TextView) convertView.findViewById(R.id.monthText);
            holder.txt_day = (TextView) convertView.findViewById(R.id.dayText);
            holder.txt_series = (TextView) convertView.findViewById(R.id.seriesData);


            if (holder.txt_engine != null) holder.txt_engine.setText(productIO.getEngine());
            if (holder.txt_desc != null) holder.txt_desc.setText(productIO.getDesc());
//            if (holder.txt_type != null) holder.txt_type.setText(productIO.getType());
            if (holder.txt_maker != null)
                holder.txt_maker.setText(productIO.getMake() + " " + productIO.getSeries() + " " + productIO.getType());
            if (holder.txt_model != null) holder.txt_model.setText(productIO.getModel());
            if (holder.txt_size != null) holder.txt_size.setText(productIO.getSize());
            if (holder.txt_category != null) holder.txt_category.setText(productIO.getCategory());
            if (holder.txt_year != null) holder.txt_year.setText("" + productIO.getYear());
            if (holder.txt_month != null)
                holder.txt_month.setText(productIO.getMonth().toUpperCase());
            if (holder.txt_day != null) holder.txt_day.setText("" + productIO.getDay());
//            if (holder.txt_series != null) holder.txt_series.setText("" + productIO.getSeries());
            if (holder.txt_price_php != null) {
                holder.txt_price_php.setText(replaceString(productIO.getPrice_php()));
            }
            if (holder.txt_price_jpy != null) {
                holder.txt_price_jpy.setText(replaceString(productIO.getPrice_jpy()));
            }

        }
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, productIO.getTitle() + " " + position + " is Selected", Toast.LENGTH_LONG).show();
//            }
//        });

        return convertView;
    }

    public class Holder {
        TextView txt_engine;
        TextView txt_desc;
        TextView txt_type;
        TextView txt_maker;
        TextView txt_model;
        TextView txt_size;
        TextView txt_category;
        TextView txt_price_php;
        TextView txt_price_jpy;
        TextView txt_month;
        TextView txt_day;
        TextView txt_year;
        TextView txt_series;

    }


}