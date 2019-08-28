package com.hll_sc_app.bean.event;

import com.hll_sc_app.bean.cooperation.CooperationShopsListResp;

import java.util.ArrayList;

/**
 * 营销优惠券：添加分发 选择合作客户的搜索
 * 集团和门店
 */
public class MarketingSelectShopEvent {
    //0:集团，1：用户
    private int scope;
    private String searchText;
    private SelecShops mSelecShops;

    public MarketingSelectShopEvent(int scope, String searchText, SelecShops selecShops) {
        this.scope = scope;
        this.searchText = searchText;
        this.mSelecShops = selecShops;
    }

    public SelecShops getSelecShops() {
        return mSelecShops;
    }

    public void setSelecShops(SelecShops mSelecShops) {
        this.mSelecShops = mSelecShops;
    }

    public boolean isGroupScope() {
        return scope == 0;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }


    public static class SelecShops {
        public SelecShops(boolean isSelectAll, ArrayList<CooperationShopsListResp.ShopListBean> selectShops) {
            this.isSelectAll = isSelectAll;
            this.selectShops = selectShops;
        }

        private boolean isSelectAll;
        private ArrayList<CooperationShopsListResp.ShopListBean> selectShops;

        public boolean isSelectAll() {
            return isSelectAll;
        }

        public void setSelectAll(boolean selectAll) {
            isSelectAll = selectAll;
        }

        public ArrayList<CooperationShopsListResp.ShopListBean> getSelectShops() {
            return selectShops;
        }

        public void setSelectShops(ArrayList<CooperationShopsListResp.ShopListBean> selectShops) {
            this.selectShops = selectShops;
        }
    }
}
