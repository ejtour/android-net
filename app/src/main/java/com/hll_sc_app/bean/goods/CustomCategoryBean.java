package com.hll_sc_app.bean.goods;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询自定义分类列表item
 *
 * @author zhuyingsong
 * @date 2019-06-18
 */
public class CustomCategoryBean {
    /**
     * 税率
     */
    private String taxRate;
    /**
     * 分类父级ID
     */
    private String shopCategoryPID;
    /**
     * 分类级别
     */
    private String categoryLevel;
    private String groupID;
    /**
     * 关联分类ID
     */
    private String extCategoryID;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 分类ID
     */
    private String id;
    /**
     * 类型
     */
    private String type;
    /**
     * 分类名称
     */
    private String categoryName;
    private boolean checked;
    private List<CustomCategoryBean> subList;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getShopCategoryPID() {
        return shopCategoryPID;
    }

    public void setShopCategoryPID(String shopCategoryPID) {
        this.shopCategoryPID = shopCategoryPID;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getExtCategoryID() {
        return extCategoryID;
    }

    public void setExtCategoryID(String extCategoryID) {
        this.extCategoryID = extCategoryID;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CustomCategoryBean> getSubList() {
        if (subList == null) subList = new ArrayList<>();
        return subList;
    }

    public void setSubList(List<CustomCategoryBean> subList) {
        this.subList = subList;
    }
}
