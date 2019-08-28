package com.hll_sc_app.bean.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 营销中心
 */
public class MarketingEvent {
    private int target;
    private String searchText;
    private boolean isRefresh;

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(@Target int target) {
        this.target = target;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }



    @IntDef({Target.MARKETING_PRODUCT_LIST, Target.MARKETING_SELECT_PRODUCT_LIST, Target.MARKETING_PRODUCT_DETAIL, Target.MARKETING_COUPON_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Target {
        /**
         * 商品促销列表页
         */
        int MARKETING_PRODUCT_LIST = 0;
        /***
         * 商品促销查看活动商品列表页
         */
        int MARKETING_SELECT_PRODUCT_LIST = 1;

        /**
         * 商品促销详情
         */
        int MARKETING_PRODUCT_DETAIL = 2;

        /**
         * 优惠券列表
         */
        int MARKETING_COUPON_LIST = 3;
    }
}
