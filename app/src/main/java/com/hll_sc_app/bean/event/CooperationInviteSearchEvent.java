package com.hll_sc_app.bean.event;

/**
 * 合作采购商-搜索词传递
 *
 * @author zhuyingsong
 * @date 2019-07-23
 */
public class CooperationInviteSearchEvent {
    private String name;

    public CooperationInviteSearchEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
