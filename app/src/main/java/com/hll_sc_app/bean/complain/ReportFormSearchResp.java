package com.hll_sc_app.bean.complain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReportFormSearchResp {
    private List<ShopMallBean> list;

    public List<ShopMallBean> getList() {
        return list;
    }

    public void setList(List<ShopMallBean> list) {
        this.list = list;
    }

    public static class ShopMallBean implements Parcelable {
        public static final Creator<ShopMallBean> CREATOR = new Creator<ShopMallBean>() {
            @Override
            public ShopMallBean createFromParcel(Parcel source) {
                return new ShopMallBean(source);
            }

            @Override
            public ShopMallBean[] newArray(int size) {
                return new ShopMallBean[size];
            }
        };
        private String shopmallID;
        private String name;

        public ShopMallBean() {
        }

        protected ShopMallBean(Parcel in) {
            this.shopmallID = in.readString();
            this.name = in.readString();
        }

        public String getShopmallID() {
            return shopmallID;
        }

        public void setShopmallID(String shopmallID) {
            this.shopmallID = shopmallID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.shopmallID);
            dest.writeString(this.name);
        }
    }
}
