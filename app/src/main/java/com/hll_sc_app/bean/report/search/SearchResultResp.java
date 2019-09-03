package com.hll_sc_app.bean.report.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResultResp {

    private List<SearchResultItem> list  = new ArrayList<>();


    public List<SearchResultItem> getList() {
        return list;
    }

    public void setList(List<SearchResultItem> list) {
        this.list = list;
    }
}
