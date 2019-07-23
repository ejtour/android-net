package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品的 自定义分类
 *
 * @author zhuyingsong
 * @date 2019-06-17
 */
public class CopyCategoryBean implements Parcelable {
    public static final Creator<CopyCategoryBean> CREATOR = new Creator<CopyCategoryBean>() {
        @Override
        public CopyCategoryBean createFromParcel(Parcel source) {
            return new CopyCategoryBean(source);
        }

        @Override
        public CopyCategoryBean[] newArray(int size) {
            return new CopyCategoryBean[size];
        }
    };
    /**
     * 自定义一级分类ID（店铺商品二级分类ID）
     */
    private String shopProductCategorySubID;
    /**
     * 一级分类名称
     */
    private String shopProductCategorySubName;
    /**
     * 自定义二级分类ID（店铺商品三级分类ID
     */
    private String shopProductCategoryThreeID;
    /**
     * 二级分类名称
     */
    private String shopProductCategoryThreeName;

    public CopyCategoryBean() {
    }

    protected CopyCategoryBean(Parcel in) {
        this.shopProductCategorySubID = in.readString();
        this.shopProductCategorySubName = in.readString();
        this.shopProductCategoryThreeID = in.readString();
        this.shopProductCategoryThreeName = in.readString();
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

    public String getShopProductCategoryThreeID() {
        return shopProductCategoryThreeID;
    }

    public void setShopProductCategoryThreeID(String shopProductCategoryThreeID) {
        this.shopProductCategoryThreeID = shopProductCategoryThreeID;
    }

    public String getShopProductCategoryThreeName() {
        return shopProductCategoryThreeName;
    }

    public void setShopProductCategoryThreeName(String shopProductCategoryThreeName) {
        this.shopProductCategoryThreeName = shopProductCategoryThreeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopProductCategorySubID);
        dest.writeString(this.shopProductCategorySubName);
        dest.writeString(this.shopProductCategoryThreeID);
        dest.writeString(this.shopProductCategoryThreeName);
    }
}
