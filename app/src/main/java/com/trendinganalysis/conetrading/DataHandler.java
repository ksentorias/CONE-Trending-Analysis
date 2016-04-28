package com.trendinganalysis.conetrading;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/7/2016.
 */
public class DataHandler {
    final static int logoutCode = 1;
    final static int noInputCode = 2;
    final static String permalink = "http://webprojectupdates.com/c-one/php/";
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
    private boolean reportData;
    private List<List<Products>> products;
    private List<List<Products>> reportProducts;
    private List<Products> reportLAProducts;
    private List<Products> reportFSProducts;
    private List<Products> reportFAProducts;
    private List<Products> reportLMProducts;
    private List<Products> reportSDProducts;
    ArrayList<NameValuePair> nameValuePairs;

    public boolean isRunWithData() {
        return runWithData;
    }

    public void setRunWithData(boolean runWithData) {
        this.runWithData = runWithData;
    }

    public List <List<Products>> getProduct() {
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

    public boolean isReportData() {
        return reportData;
    }

    public void setReportData(boolean reportData) {
        this.reportData = reportData;
    }

    public ArrayList<NameValuePair> getNameValuePairs() {
        return nameValuePairs;
    }

    public void setNameValuePairs(ArrayList<NameValuePair> nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

    public List<Products> getReportLAProducts() {
        return reportLAProducts;
    }

    public void setReportLAProducts(List<Products> reportLAProducts) {
        this.reportLAProducts = reportLAProducts;
    }

    public List<Products> getReportFSProducts() {
        return reportFSProducts;
    }

    public void setReportFSProducts(List<Products> reportFSProducts) {
        this.reportFSProducts = reportFSProducts;
    }

    public List<Products> getReportFAProducts() {
        return reportFAProducts;
    }

    public void setReportFAProducts(List<Products> reportFAProducts) {
        this.reportFAProducts = reportFAProducts;
    }

    public List<Products> getReportLMProducts() {
        return reportLMProducts;
    }

    public void setReportLMProducts(List<Products> reportLMProducts) {
        this.reportLMProducts = reportLMProducts;
    }

    public List<Products> getReportSDProducts() {
        return reportSDProducts;
    }

    public void setReportSDProducts(List<Products> reportSDProducts) {
        this.reportSDProducts = reportSDProducts;
    }

    public List<List<Products>> getReportProducts() {
        return reportProducts;
    }

    public void setReportProducts(List<List<Products>> reportProducts) {
        this.reportProducts = reportProducts;
    }
}
