package com.hll_sc_app.bean.event;

/**
 * 搜索词传递
 *
 * @author zhuyingsong
 * @date 2019-06-12
 */
public class GoodsSearchEvent {
    private String name;

    public GoodsSearchEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
