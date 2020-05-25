package com.hll_sc_app.bean.stockmanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

public class CategorySubBean implements Parcelable {
    private String shopProductCategorySubID;
    private String shopProductCategorySubName;
    private transient boolean isSelect;
    private List<CategoryThreeBean> threeList;

    protected CategorySubBean(Parcel in) {
        shopProductCategorySubID = in.readString();
        shopProductCategorySubName = in.readString();
        threeList = in.createTypedArrayList(CategoryThreeBean.CREATOR);
    }

    public CategorySubBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopProductCategorySubID);
        dest.writeString(shopProductCategorySubName);
        dest.writeTypedList(threeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategorySubBean> CREATOR = new Creator<CategorySubBean>() {
        @Override
        public CategorySubBean createFromParcel(Parcel in) {
            return new CategorySubBean(in);
        }

        @Override
        public CategorySubBean[] newArray(int size) {
            return new CategorySubBean[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public List<CategoryThreeBean> getThreeList() {
        return threeList;
    }

    public void setThreeList(List<CategoryThreeBean> threeList) {
        this.threeList = threeList;
    }
}
