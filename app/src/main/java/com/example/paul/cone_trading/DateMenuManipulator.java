package com.example.paul.cone_trading;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Paul on 1/8/2016.
 */
public class DateMenuManipulator {

    //date pickers
    DatePickerDialog datepicker;

    //date formatter
    SimpleDateFormat dateFormatter;

    public String date;

    public void DateMenuManipulator(){


    }

    public DatePickerDialog doDatePicker(Context context){
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);


        Calendar newCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                setDate(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        return datepicker;
    }

    public void setDate(String date){
        String month = "";
        month = date.substring(0,2);
        switch (month){
            case "01":{month = "January";break;}
            case "02":{month = "February";break;}
            case "03":{month = "March";break;}
            case "04":{month = "April";break;}
            case "05":{month = "May";break;}
            case "06":{month = "June";break;}
            case "07":{month = "July";break;}
            case "08":{month = "August";break;}
            case "09":{month = "September";break;}
            case "10":{month = "October";break;}
            case "11":{month = "November";break;}
            case "12":{month = "December";break;}
        }

        //reformat date.
        date = month + " " + date.substring(3,4) + ", " + date.substring(5,date.length()+1);

        this.date = date;
    }

    public String getDate(){
        return date;
    }
}
