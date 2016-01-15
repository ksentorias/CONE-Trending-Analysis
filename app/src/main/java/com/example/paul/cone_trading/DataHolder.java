package com.example.paul.cone_trading;

import java.util.List;

/**
 * Created by Paul on 1/7/2016.
 */
public class DataHolder {
    final static int logoutCode = 1;
    final static int noInputCode = 2;
    final static String permalink = "http://webprojectupdates.com/c-one/test/";
    private int dataLength;
    private String keyword;
    private String type;
    private String model;
    private String maker;
    private String size;
    private String category;
    private String dateTo;
    private String dateFrom;
    private boolean filterSearch;
    private boolean runWithData;
    public List<List<Products>> products;

    public boolean isRunWithData() {
        return runWithData;
    }

    public void setRunWithData(boolean runWithData) {
        this.runWithData = runWithData;
    }

    public List<List<Products>> getProduct() {
        return products;
    }

    public void setProduct(List<List<Products>> products) {
        this.products = products;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
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

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public boolean isFilterSearch() {
        return filterSearch;
    }

    public void setFilterSearch(boolean filterSearch) {
        this.filterSearch = filterSearch;
    }
}
