package com.hll_sc_app.bean.stockmanage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

public class CategoryThreeBean implements Parcelable {
    private String shopProductCategoryThreeID;
    private String shopProductCategoryThreeName;

    protected CategoryThreeBean(Parcel in) {
        shopProductCategoryThreeID = in.readString();
        shopProductCategoryThreeName = in.readString();
    }

    public CategoryThreeBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopProductCategoryThreeID);
        dest.writeString(shopProductCategoryThreeName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryThreeBean> CREATOR = new Creator<CategoryThreeBean>() {
        @Override
        public CategoryThreeBean createFromParcel(Parcel in) {
            return new CategoryThreeBean(in);
        }

        @Override
        public CategoryThreeBean[] newArray(int size) {
            return new CategoryThreeBean[size];
        }
    };

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
}
