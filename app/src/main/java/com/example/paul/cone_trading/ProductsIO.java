package com.example.paul.cone_trading;

public class ProductsIO {

    private String title;
    private String desc;
    private String type;
    private String maker;
    private String model;
    private String size;
    private String category;
    private int price_php;
    private int price_jpy;

    private long totalDl;
    private int rating;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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

    public String getMaker() {
        return maker;
    }
    public void setMaker(String maker) {
        this.maker = maker;
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

    public int getPrice_php() {
        return price_php;
    }
    public void setPrice_php(int price_php) {
        this.price_php = price_php;
    }

    public int getPrice_jpy() {
        return price_jpy;
    }
    public void setPrice_jpy(int price_jpy) {
        this.price_jpy = price_jpy;
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
