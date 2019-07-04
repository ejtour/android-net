package com.hll_sc_app.bean.event;

/**
 * 搜索词传递
 *
 * @author zhuyingsong
 * @date 2019-07-01
 */
public class GoodsRelevanceListSearchEvent {
    private String name;

    public GoodsRelevanceListSearchEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
