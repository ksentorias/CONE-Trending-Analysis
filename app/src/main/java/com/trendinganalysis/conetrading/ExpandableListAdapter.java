package com.trendinganalysis.conetrading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    DataHandler dataHandler = MainActivity.dataHandler;
    int lastExpandedGroupPosition = -1;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private ArrayList<String> parentItems, child;


    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {


        final String childText = (String) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);


        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        Holder holder = new Holder(this._context);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_layout, null);
        }


        holder.title = (TextView) convertView.findViewById(R.id.typeTitle);
        holder.indicatorIco = (ImageView) convertView.findViewById(R.id.indicatorIco);
        holder.categoryIco = (ImageView) convertView.findViewById(R.id.headerIco);


        if (isExpanded) {
            holder.indicatorIco.setImageResource(R.drawable.ic_pull_up_arrow);
        } else {
            holder.indicatorIco.setImageResource(R.drawable.ic_pull_down_arrow);
        }

        holder.title.setText(headerTitle);

        switch (groupPosition) {
            case 0:
                holder.categoryIco.setImageResource(R.drawable.ic_maker);
                break;
            case 1:
                holder.categoryIco.setImageResource(R.drawable.ic_series);
                break;
            case 2:
                holder.categoryIco.setImageResource(R.drawable.ic_type);
                break;
            case 3:
                holder.categoryIco.setImageResource(R.drawable.ic_model);
                break;
            case 4:
                holder.categoryIco.setImageResource(R.drawable.ic_engine);
                break;
            case 5:
                holder.categoryIco.setImageResource(R.drawable.ic_size);
                break;
            case 6:
                holder.categoryIco.setImageResource(R.drawable.ic_source);
                break;
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
//        collapse the old expanded group, if not the same
        //as new group to expand
        if (groupPosition != lastExpandedGroupPosition) {
            ResultActivity.expListView.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }
}

class Holder extends View {
    TextView title;
    ImageView indicatorIco;
    ImageView categoryIco;

    public Holder(Context context) {
        super(context);
    }

}