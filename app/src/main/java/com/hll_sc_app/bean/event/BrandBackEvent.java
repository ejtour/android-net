package com.hll_sc_app.bean.event;

/**
 * 品牌回传上个页面
 *
 * @author zhuyingsong
 * @date 2019-06-25
 */
public class BrandBackEvent {
    private String name;
    private String id;

    public BrandBackEvent(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
