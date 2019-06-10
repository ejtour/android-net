package com.hll_sc_app.bean.user;

/**
 * 分类 item
 *
 * @author zhuyingsong
 * @date 2019-06-10
 */
public class CategoryItem {
    private String imgUrl;
    private String categoryPID;
    private String categoryLevel;
    private String categoryName;
    private String categoryID;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategoryPID() {
        return categoryPID;
    }

    public void setCategoryPID(String categoryPID) {
        this.categoryPID = categoryPID;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
