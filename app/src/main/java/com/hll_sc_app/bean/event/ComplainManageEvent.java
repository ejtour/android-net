package com.hll_sc_app.bean.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ComplainManageEvent {
    private @TARGET
    int target;
    private @EVENT
    int event;

    private String searchContent;

    public ComplainManageEvent(@TARGET int target, @EVENT int event) {
        this.target = target;
        this.event = event;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @IntDef({TARGET.LIST, TARGET.DETAIL, TARGET.SELECT_PURCHASER_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TARGET {
        int LIST = 1;
        int DETAIL = 2;
        int SELECT_PURCHASER_LIST = 3;
    }

    @IntDef({EVENT.REFRESH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EVENT {
        int REFRESH = 1;
    }
}
