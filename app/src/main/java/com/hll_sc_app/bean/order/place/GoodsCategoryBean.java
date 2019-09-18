package com.hll_sc_app.bean.order.place;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/16
 */

public class GoodsCategoryBean {

    private double taxRate;
    private String shopCategoryPID;
    private int categoryLevel;
    private String id;
    private int type;
    private String categoryName;
    private List<GoodsCategoryBean> subList;

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getShopCategoryPID() {
        return shopCategoryPID;
    }

    public void setShopCategoryPID(String shopCategoryPID) {
        this.shopCategoryPID = shopCategoryPID;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<GoodsCategoryBean> getSubList() {
        return subList;
    }

    public void setSubList(List<GoodsCategoryBean> subList) {
        this.subList = subList;
    }
}
