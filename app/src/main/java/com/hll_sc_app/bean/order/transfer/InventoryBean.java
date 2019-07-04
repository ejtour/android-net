package com.hll_sc_app.bean.order.transfer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class InventoryBean implements Parcelable {
    private String id;
    private double orderNum;
    private String productCode;
    private String productName;
    private String productSpec;
    private String saleUnitName;
    private String skuCode;
    private double stockNum;
    private transient int flag;
    private transient double goodsNum;

    public double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(double orderNum) {
        this.orderNum = orderNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public double getStockNum() {
        return stockNum;
    }

    public void setStockNum(double stockNum) {
        this.stockNum = stockNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.orderNum);
        dest.writeString(this.productCode);
        dest.writeString(this.productName);
        dest.writeString(this.productSpec);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.skuCode);
        dest.writeDouble(this.stockNum);
    }

    public InventoryBean() {
    }

    protected InventoryBean(Parcel in) {
        this.id = in.readString();
        this.orderNum = in.readDouble();
        this.productCode = in.readString();
        this.productName = in.readString();
        this.productSpec = in.readString();
        this.saleUnitName = in.readString();
        this.skuCode = in.readString();
        this.stockNum = in.readDouble();
    }

    public static final Parcelable.Creator<InventoryBean> CREATOR = new Parcelable.Creator<InventoryBean>() {
        @Override
        public InventoryBean createFromParcel(Parcel source) {
            return new InventoryBean(source);
        }

        @Override
        public InventoryBean[] newArray(int size) {
            return new InventoryBean[size];
        }
    };
}
