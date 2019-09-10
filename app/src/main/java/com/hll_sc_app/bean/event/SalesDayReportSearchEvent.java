package com.hll_sc_app.bean.event;

/**
 * 销售日报传递
 * @author chukun
 * @date 2019-08-05
 */
public class SalesDayReportSearchEvent {
    private String searchWord;

    public SalesDayReportSearchEvent(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
