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
    private List<PurchaserBean> purchaserList;
    private List<ShopBean> shopList;

    public List<ShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopBean> shopList) {
        this.shopList = shopList;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<PurchaserBean> getPurchaserList() {
        return purchaserList;
    }

    public void setPurchaserList(List<PurchaserBean> purchaserList) {
        this.purchaserList = purchaserList;
    }

    public static class PurchaserBean implements Parcelable {
        public static final Creator<PurchaserBean> CREATOR = new Creator<PurchaserBean>() {
            @Override
            public PurchaserBean createFromParcel(Parcel source) {
                return new PurchaserBean(source);
            }

            @Override
            public PurchaserBean[] newArray(int size) {
                return new PurchaserBean[size];
            }
        };
        private String purchaserID;
        private String shopNum;
        private String purchaserLogo;
        private String purchaserName;
        private String warehouseId;

        public PurchaserBean() {
        }

        protected PurchaserBean(Parcel in) {
            this.purchaserID = in.readString();
            this.shopNum = in.readString();
            this.purchaserLogo = in.readString();
            this.purchaserName = in.readString();
            this.warehouseId = in.readString();
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

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
            dest.writeString(this.warehouseId);
        }
    }

    public static class ShopBean implements Parcelable {
        public static final Creator<ShopBean> CREATOR = new Creator<ShopBean>() {
            @Override
            public ShopBean createFromParcel(Parcel source) {
                return new ShopBean(source);
            }

            @Override
            public ShopBean[] newArray(int size) {
                return new ShopBean[size];
            }
        };
        private String shopName;
        private String shopID;
        private boolean select;

        public ShopBean() {
        }

        protected ShopBean(Parcel in) {
            this.shopName = in.readString();
            this.shopID = in.readString();
            this.select = in.readByte() != 0;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.shopName);
            dest.writeString(this.shopID);
            dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        }
    }
}
