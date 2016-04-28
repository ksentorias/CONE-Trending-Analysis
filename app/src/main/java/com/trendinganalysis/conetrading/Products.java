package com.trendinganalysis.conetrading;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Products {

    private String engine;
    private String desc;
    private String type;
    private String make;
    private String model;
    private String size;
    private String category;
    private String series;
    private Date date;
    private int day;
    private int year;
    private int month;
    private float price_php;
    private float price_jpy;

    private long totalDl;
    private int rating;


    public String getEngine() {return engine;}

    public void setEngine(String engine) {this.engine = engine;}

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {return make;}

    public void setMake(String make) {this.make = make;}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        year = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
    }

    public Date getDate() {
        return date;
    }

    public int getDay() {
        return day;
    }

    public String getMonth() {
        return new SimpleDateFormat("MMM").format(date);
    }


    public int getIntMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.MONTH)+1;
    }

    public int getYear() {
        return year;
    }

    public float getPrice_php() {
        return price_php;
    }

    public void setPrice_php(float price_php) {
        this.price_php = price_php;
    }

    public float getPrice_jpy() {
        return price_jpy;
    }

    public void setPrice_jpy(float price_jpy) {
        this.price_jpy = price_jpy;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public long getTotalDl() {
        return totalDl;
    }

    public void setTotalDl(long totalDl) {
        this.totalDl = totalDl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

//    public String getIcon() {
//        return icon;
//    }
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }

}
