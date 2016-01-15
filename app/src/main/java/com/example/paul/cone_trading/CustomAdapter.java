package com.example.paul.cone_trading;

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

class CustomAdapter extends BaseAdapter{
    private List<Products> items;
    private String[] result;
    private Context context;
    private int[] imageId;
    private static LayoutInflater inflater;
    ProgressDialog dialog;

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

    public class Holder {
        TextView txt_title;
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

        //rowView = inflater.inflate(R.layout.result_list_row, null);

//            holder.img=(ImageView) convertView.findViewById(R.id.imageView1);
//            holder.img.setImageResource(imageId[position]);

        if (productIO != null) {
            holder.txt_title = (TextView) convertView.findViewById(R.id.txtTitle);
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

            if (holder.txt_title != null) holder.txt_title.setText(productIO.getTitle());
            if (holder.txt_desc != null) holder.txt_desc.setText(productIO.getDesc());
            if (holder.txt_type != null) holder.txt_type.setText(productIO.getType());
            if (holder.txt_maker != null) holder.txt_maker.setText(productIO.getMaker());
            if (holder.txt_model != null) holder.txt_model.setText(productIO.getModel());
            if (holder.txt_size != null) holder.txt_size.setText(productIO.getSize());
            if (holder.txt_category != null) holder.txt_category.setText(productIO.getCategory());
            if (holder.txt_year != null) holder.txt_year.setText(""+productIO.getYear());
            if (holder.txt_month != null) holder.txt_month.setText(productIO.getMonth().toUpperCase());
            if (holder.txt_day != null) holder.txt_day.setText(""+productIO.getDay());
            if (holder.txt_price_php != null) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                holder.txt_price_php.setText(nf.format(productIO.getPrice_php()));
            }
            if (holder.txt_price_jpy != null) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                holder.txt_price_jpy.setText(nf.format(productIO.getPrice_jpy()));
            }

        }

//        convertView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, productIO.getTitle() + " " + position + " is Selected", Toast.LENGTH_LONG).show();
//            }
//        });

        return convertView;
    }
}