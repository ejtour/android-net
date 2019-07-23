package com.hll_sc_app.bean.agreementprice.quotation;

/**
 * 比例模板
 *
 * @author zhuyingsong
 * @date 2019-07-10
 */
public class CategoryRatioListBean {
    private String shopProductCategorySubID;
    private String shopProductCategorySubName;
    private String shopProductCategoryThreeName;
    private String shopProductCategoryThreeID;
    private String ratio;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getShopProductCategorySubID() {
        return shopProductCategorySubID;
    }

    public void setShopProductCategorySubID(String shopProductCategorySubID) {
        this.shopProductCategorySubID = shopProductCategorySubID;
    }

    public String getShopProductCategorySubName() {
        return shopProductCategorySubName;
    }

    public void setShopProductCategorySubName(String shopProductCategorySubName) {
        this.shopProductCategorySubName = shopProductCategorySubName;
    }

    public String getShopProductCategoryThreeName() {
        return shopProductCategoryThreeName;
    }

    public void setShopProductCategoryThreeName(String shopProductCategoryThreeName) {
        this.shopProductCategoryThreeName = shopProductCategoryThreeName;
    }

    public String getShopProductCategoryThreeID() {
        return shopProductCategoryThreeID;
    }

    public void setShopProductCategoryThreeID(String shopProductCategoryThreeID) {
        this.shopProductCategoryThreeID = shopProductCategoryThreeID;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
