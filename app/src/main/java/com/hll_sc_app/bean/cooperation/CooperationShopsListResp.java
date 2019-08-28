package com.hll_sc_app.bean.cooperation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 查询合作关系店铺列表响应
 */
public class CooperationShopsListResp {

    private int shopTotal;
    private PageInfoBean pageInfo;
    private int newTotal;
    private List<ShopListBean> shopList;

    public int getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(int shopTotal) {
        this.shopTotal = shopTotal;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public int getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(int newTotal) {
        this.newTotal = newTotal;
    }

    public List<ShopListBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopListBean> shopList) {
        this.shopList = shopList;
    }

    public static class PageInfoBean {
        /**
         * total : 1
         * pages : 1
         * pageSize : 9999
         * pageNum : 1
         */

        private int total;
        private int pages;
        private int pageSize;
        private int pageNum;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
    }

    public static class ShopListBean implements Parcelable {

        private String salesmanName;
        private String salesmanID;
        private String cooperationThirdShopID;
        private String agreeTime;
        private String salesmanPhone;
        private String cooperationTime;
        private String shopName;
        private String accountPeriod;
        private String purchaserName;
        private String settlementWay;
        private String purchaserID;
        private int accountPeriodType;
        private String manageTime;
        private String shopID;

        public String getSalesmanName() {
            return salesmanName;
        }

        public void setSalesmanName(String salesmanName) {
            this.salesmanName = salesmanName;
        }

        public String getSalesmanID() {
            return salesmanID;
        }

        public void setSalesmanID(String salesmanID) {
            this.salesmanID = salesmanID;
        }

        public String getCooperationThirdShopID() {
            return cooperationThirdShopID;
        }

        public void setCooperationThirdShopID(String cooperationThirdShopID) {
            this.cooperationThirdShopID = cooperationThirdShopID;
        }

        public String getAgreeTime() {
            return agreeTime;
        }

        public void setAgreeTime(String agreeTime) {
            this.agreeTime = agreeTime;
        }

        public String getSalesmanPhone() {
            return salesmanPhone;
        }

        public void setSalesmanPhone(String salesmanPhone) {
            this.salesmanPhone = salesmanPhone;
        }

        public String getCooperationTime() {
            return cooperationTime;
        }

        public void setCooperationTime(String cooperationTime) {
            this.cooperationTime = cooperationTime;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getAccountPeriod() {
            return accountPeriod;
        }

        public void setAccountPeriod(String accountPeriod) {
            this.accountPeriod = accountPeriod;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getSettlementWay() {
            return settlementWay;
        }

        public void setSettlementWay(String settlementWay) {
            this.settlementWay = settlementWay;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public int getAccountPeriodType() {
            return accountPeriodType;
        }

        public void setAccountPeriodType(int accountPeriodType) {
            this.accountPeriodType = accountPeriodType;
        }

        public String getManageTime() {
            return manageTime;
        }

        public void setManageTime(String manageTime) {
            this.manageTime = manageTime;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.salesmanName);
            dest.writeString(this.salesmanID);
            dest.writeString(this.cooperationThirdShopID);
            dest.writeString(this.agreeTime);
            dest.writeString(this.salesmanPhone);
            dest.writeString(this.cooperationTime);
            dest.writeString(this.shopName);
            dest.writeString(this.accountPeriod);
            dest.writeString(this.purchaserName);
            dest.writeString(this.settlementWay);
            dest.writeString(this.purchaserID);
            dest.writeInt(this.accountPeriodType);
            dest.writeString(this.manageTime);
            dest.writeString(this.shopID);
        }

        public ShopListBean() {
        }

        protected ShopListBean(Parcel in) {
            this.salesmanName = in.readString();
            this.salesmanID = in.readString();
            this.cooperationThirdShopID = in.readString();
            this.agreeTime = in.readString();
            this.salesmanPhone = in.readString();
            this.cooperationTime = in.readString();
            this.shopName = in.readString();
            this.accountPeriod = in.readString();
            this.purchaserName = in.readString();
            this.settlementWay = in.readString();
            this.purchaserID = in.readString();
            this.accountPeriodType = in.readInt();
            this.manageTime = in.readString();
            this.shopID = in.readString();
        }

        public static final Creator<ShopListBean> CREATOR = new Creator<ShopListBean>() {
            @Override
            public ShopListBean createFromParcel(Parcel source) {
                return new ShopListBean(source);
            }

            @Override
            public ShopListBean[] newArray(int size) {
                return new ShopListBean[size];
            }
        };
    }
}
