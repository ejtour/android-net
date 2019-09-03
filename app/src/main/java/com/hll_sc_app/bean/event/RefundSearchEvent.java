package com.hll_sc_app.bean.event;

public class RefundSearchEvent {

    private String searchWord;

    public RefundSearchEvent(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
