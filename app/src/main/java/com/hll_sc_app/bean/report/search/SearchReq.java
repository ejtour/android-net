package com.hll_sc_app.bean.report.search;

public class SearchReq {

    private String searchWords;
    private String shopMallID;
    private int    size;
    private int    source;
    private int    type;


    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }

    public String getShopMallID() {
        return shopMallID;
    }

    public void setShopMallID(String shopMallID) {
        this.shopMallID = shopMallID;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
