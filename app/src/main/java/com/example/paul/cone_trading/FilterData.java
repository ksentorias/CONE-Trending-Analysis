package com.example.paul.cone_trading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Paul on 1/8/2016.
 */
public class FilterData {

    String[] product = new String[6];

    public void setFilterData(String dateFrom,String dateTo, String type,String maker,String model,String size){
        product[0]=dateFormatter(dateFrom);
        product[1]=dateFormatter(dateTo);
        product[2]=type;
        product[3]=maker;
        product[4]=model;
        product[5]=size;

    }

    public String[] getFilterData(){
        return product;
    }

    private String dateFormatter(String date){
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");

        SimpleDateFormat format2 = new SimpleDateFormat("MMMMM dd, yyyy");

        String dateString = "";
        try {
            Date ddate = format2.parse(date);
            dateString = format1.format(ddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }



}
