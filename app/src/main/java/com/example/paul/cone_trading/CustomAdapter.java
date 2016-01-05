package com.example.paul.cone_trading;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class CustomAdapter extends BaseAdapter{
    private final String [] result;
    private final Context context;
    private final int [] imageId;
    private static LayoutInflater inflater=null;

    public CustomAdapter(ResultActivity.PlaceholderFragment list_result_activity, String[] prgmNameList, int[] prgmImages) {

        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=list_result_activity.getContext();
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // our ViewHolder.
// caches our TextView
    static class ViewHolderItem {
        TextView textViewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
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

    public class Holder
    {
        TextView txt_title;
        TextView txt_desc;
        TextView txt_type;
        TextView txt_maker;
        TextView txt_model;
        TextView txt_size;
        TextView txt_category;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.result_list_row, null);

            Holder holder=new Holder();
            //rowView = inflater.inflate(R.layout.result_list_row, null);

//            holder.img=(ImageView) convertView.findViewById(R.id.imageView1)
//            holder.img.setImageResource(imageId[position]);

            holder.txt_title=(TextView) convertView.findViewById(R.id.txtTitle);
            holder.txt_desc=(TextView) convertView.findViewById(R.id.txtDesc);
            holder.txt_type=(TextView) convertView.findViewById(R.id.typeData);
            holder.txt_maker=(TextView) convertView.findViewById(R.id.makerData);
            holder.txt_model=(TextView) convertView.findViewById(R.id.modelData);
            holder.txt_size=(TextView) convertView.findViewById(R.id.sizeData);
            holder.txt_category=(TextView) convertView.findViewById(R.id.categoryData);

            holder.txt_title.setText(result[position]+" "+position);
//            holder.txt_desc.setText(result[position]+" "+position);
//            holder.txt_type.setText(result[position]+" "+position);
//            holder.txt_maker.setText(result[position]+" "+position);
//            holder.txt_model.setText(result[position]+" "+position);
//            holder.txt_size.setText(result[position]+" "+position);
//            holder.txt_category.setText(result[position]+" "+position);

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, result[position]+" "+position+ " is Selected", Toast.LENGTH_LONG).show();
                }
            });
        }

        ViewHolderItem viewHolder;

    /*
     * The convertView argument is essentially a "ScrapView" as described is Lucas post
     * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
     * It will have a non-null value when ListView is asking you recycle the row layout.
     * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
     */


        return convertView;
    }

}