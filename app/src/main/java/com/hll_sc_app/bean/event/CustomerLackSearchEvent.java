package com.hll_sc_app.bean.event;

/**
 * 搜索词传递
 *
 * @author chukun
 * @date 2019-08-05
 */
public class CustomerLackSearchEvent {
    private String searchWord;

    public CustomerLackSearchEvent(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
