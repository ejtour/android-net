package com.hll_sc_app.bean.warehouse;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.priceratio.PageInfoBean;

import java.util.List;

/**
 * 代仓门店管理
 *
 * @author zhuyingsong
 * @date 2019-08-07
 */
public class ShipperShopResp {
    private PageInfoBean pageInfo;
    private List<ShopBean> purchaserList;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<ShopBean> getPurchaserList() {
        return purchaserList;
    }

    public void setPurchaserList(List<ShopBean> purchaserList) {
        this.purchaserList = purchaserList;
    }

    public static class ShopBean implements Parcelable {
        private String purchaserID;
        private String shopNum;
        private String purchaserLogo;
        private String purchaserName;

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getShopNum() {
            return shopNum;
        }

        public void setShopNum(String shopNum) {
            this.shopNum = shopNum;
        }

        public String getPurchaserLogo() {
            return purchaserLogo;
        }

        public void setPurchaserLogo(String purchaserLogo) {
            this.purchaserLogo = purchaserLogo;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public static final Parcelable.Creator<ShopBean> CREATOR = new Parcelable.Creator<ShopBean>() {
            @Override
            public ShopBean createFromParcel(Parcel source) {
                return new ShopBean(source);
            }

            @Override
            public ShopBean[] newArray(int size) {
                return new ShopBean[size];
            }
        };

        public ShopBean() {
        }

        protected ShopBean(Parcel in) {
            this.purchaserID = in.readString();
            this.shopNum = in.readString();
            this.purchaserLogo = in.readString();
            this.purchaserName = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.purchaserID);
            dest.writeString(this.shopNum);
            dest.writeString(this.purchaserLogo);
            dest.writeString(this.purchaserName);
        }
    }
}
