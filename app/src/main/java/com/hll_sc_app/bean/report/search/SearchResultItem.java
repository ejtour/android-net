package com.hll_sc_app.bean.report.search;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResultItem implements Parcelable {

    private String name;
    private String shopMallId;
    private int  type; //0-搜索集团 1-搜索门店

    protected SearchResultItem(Parcel in) {
        name = in.readString();
        shopMallId = in.readString();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shopMallId);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchResultItem> CREATOR = new Creator<SearchResultItem>() {
        @Override
        public SearchResultItem createFromParcel(Parcel in) {
            return new SearchResultItem(in);
        }

        @Override
        public SearchResultItem[] newArray(int size) {
            return new SearchResultItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopMallId() {
        return shopMallId;
    }

    public void setShopMallId(String shopMallId) {
        this.shopMallId = shopMallId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
