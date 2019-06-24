package com.hll_sc_app.bean.event;

/**
 * 搜索词传递
 *
 * @author zhuyingsong
 * @date 2019-06-24
 */
public class BrandSearchEvent {
    private String name;

    public BrandSearchEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
