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
    private Date date_modified;
    private int day;
    private int year;
    private int month;
    private double price_php;
    private double price_jpy;

    private long totalDl;
    private int rating;
    private int serverID;
    private int dbID;
    private int productID;

    public Products() {

    }


    public Products(String engine, String desc, String type, String make, String model, String size, String category, String series, Date date, int day, int year, int month, float price_php, float price_jpy) {
        this.engine = engine;
        this.desc = desc;
        this.type = type;
        this.make = make;
        this.model = model;
        this.size = size;
        this.category = category;
        this.series = series;
        this.date = date;
        this.day = day;
        this.year = year;
        this.month = month;
        this.price_php = price_php;
        this.price_jpy = price_jpy;
    }

    public Products(String engine, String desc, String type, String make, String model, String size, String category, String series, Date date, float price_php, float price_jpy, int serverID) {
        this.engine = engine;
        this.desc = desc;
        this.type = type;
        this.make = make;
        this.model = model;
        this.size = size;
        this.category = category;
        this.series = series;
        this.date = date;
        this.price_php = price_php;
        this.price_jpy = price_jpy;
        this.serverID = serverID;
    }


    public Date getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Date date_modified) {
        this.date_modified = date_modified;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        year = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
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

        return cal.get(Calendar.MONTH) + 1;
    }

    public int getYear() {
        return year;
    }

    public double getPrice_php() {
        return price_php;
    }

    public void setPrice_php(float price_php) {
        this.price_php = price_php;
    }

    public double getPrice_jpy() {
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
