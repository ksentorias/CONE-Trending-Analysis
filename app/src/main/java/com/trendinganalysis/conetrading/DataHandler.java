package com.trendinganalysis.conetrading;

import android.util.Log;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ken on 1/7/2016.
 */
public class DataHandler {
    final static int logoutCode = 1;
    final static int noInputCode = 2;
    final static int updateCode = 3;
    final static int cancelSyncCode = 4;
    private int dataLength;
    private String keyword;
    private String type;
    private String model;
    private String maker;
    private String size;
    private String engine;
    private String series;
    private String dateTo;
    private String dateFrom;
    private String source;


    private double dbSize;

    private boolean filterSearch;
    private boolean runWithData;
    private boolean cancelUpdate;
    private boolean updating;


    private List<String> childListType;
    private List<String> childListMake;
    private List<String> childListModel;
    private List<String> childListEngine;
    private List<String> childListWeight;
    private List<String> childListSeries;
    private List<String> childListSource;
    private List<List<Products>> products;
    private List<Products> reportLAProducts;
    private List<Products> reportFSProducts;
    private List<Products> reportFAProducts;
    private List<Products> reportLMProducts;
    private List<Products> reportSDProducts;
    private String initialSearchQuery;

    public DataHandler() {
    }

    public List<String> getChildListSeries() {
        return childListSeries;
    }

    public void setChildListSeries(List<String> childListSeries) {
        this.childListSeries = childListSeries;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getInitialSearchQuery() {
        return initialSearchQuery;
    }

    public void setInitialSearchQuery(String initialSearchQuery) {
        this.initialSearchQuery = initialSearchQuery;
    }

    public List<String> getChildListType() {
        return childListType;
    }

    public void setChildListType(List<String> childListType) {
        this.childListType = childListType;
    }

    public List<String> getChildListMake() {
        return childListMake;
    }

    public void setChildListMake(List<String> childListMake) {
        this.childListMake = childListMake;
    }

    public List<String> getChildListModel() {
        return childListModel;
    }

    public void setChildListModel(List<String> childListModel) {
        this.childListModel = childListModel;
    }

    public List<String> getChildListEngine() {
        return childListEngine;
    }

    public void setChildListEngine(List<String> childListEngine) {
        this.childListEngine = childListEngine;
    }

    public List<String> getChildListWeight() {
        return childListWeight;
    }

    public void setChildListWeight(List<String> childListWeight) {
        this.childListWeight = childListWeight;
    }

    public List<String> getChildListSource() {
        return childListSource;
    }

    public void setChildListSource(List<String> childListSource) {
        this.childListSource = childListSource;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

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
        StringTokenizer st = new StringTokenizer(keyword);
        int i = 0;
        while (st.hasMoreElements()) {
            Log.i("String Tokenizer", st.nextElement() + " " + i);
            i++;
        }

        if (i == 1) {
            this.keyword = keyword.replaceAll("\\s", "");
            Log.i("String Tokenizer", "New Keyword: " + this.keyword);
        } else if (i > 1) this.keyword = keyword;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public double getDbSize() {
        return dbSize;
    }

    public void setDbSize(double dbSize) {
        this.dbSize = dbSize;
    }

    public boolean isCancelUpdate() {
        return cancelUpdate;
    }

    public void setCancelUpdate(boolean cancelUpdate) {
        this.cancelUpdate = cancelUpdate;
    }

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
}
