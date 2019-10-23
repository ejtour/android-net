package com.hll_sc_app.bean.goodsdemand;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class GoodsDemandBean implements Parcelable {

    public static final Creator<GoodsDemandBean> CREATOR = new Creator<GoodsDemandBean>() {
        @Override
        public GoodsDemandBean createFromParcel(Parcel in) {
            return new GoodsDemandBean(in);
        }

        @Override
        public GoodsDemandBean[] newArray(int size) {
            return new GoodsDemandBean[size];
        }
    };
    private String productReply;
    private String supplyName;
    private String actionTime;
    private double marketPrice;
    private String purchaserPhone;
    private String placeProvince;
    private String productBrand;
    private int source;
    private String supplyPhone;
    private String productName;
    private String packMethod;
    private String purchaserID;
    private String productBrief;
    private String supplyID;
    private String purchaserUserID;
    private int action;
    private String placeCity;
    private String id;
    private String specContent;
    private String salesManID;
    private String actionBy;
    private String purchaserName;
    private String imgUrl;
    private String createBy;
    private String placeCityCode;
    private String createTime;
    private String customerID;
    private String producer;
    private String placeProvinceCode;
    private int status;
    private List<GoodsDemandItem> demandList;

    protected GoodsDemandBean(Parcel in) {
        productReply = in.readString();
        supplyName = in.readString();
        actionTime = in.readString();
        marketPrice = in.readDouble();
        purchaserPhone = in.readString();
        placeProvince = in.readString();
        productBrand = in.readString();
        source = in.readInt();
        supplyPhone = in.readString();
        productName = in.readString();
        packMethod = in.readString();
        purchaserID = in.readString();
        productBrief = in.readString();
        supplyID = in.readString();
        purchaserUserID = in.readString();
        action = in.readInt();
        placeCity = in.readString();
        id = in.readString();
        specContent = in.readString();
        salesManID = in.readString();
        actionBy = in.readString();
        purchaserName = in.readString();
        imgUrl = in.readString();
        createBy = in.readString();
        placeCityCode = in.readString();
        createTime = in.readString();
        customerID = in.readString();
        producer = in.readString();
        placeProvinceCode = in.readString();
        status = in.readInt();
        demandList = in.createTypedArrayList(GoodsDemandItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productReply);
        dest.writeString(supplyName);
        dest.writeString(actionTime);
        dest.writeDouble(marketPrice);
        dest.writeString(purchaserPhone);
        dest.writeString(placeProvince);
        dest.writeString(productBrand);
        dest.writeInt(source);
        dest.writeString(supplyPhone);
        dest.writeString(productName);
        dest.writeString(packMethod);
        dest.writeString(purchaserID);
        dest.writeString(productBrief);
        dest.writeString(supplyID);
        dest.writeString(purchaserUserID);
        dest.writeInt(action);
        dest.writeString(placeCity);
        dest.writeString(id);
        dest.writeString(specContent);
        dest.writeString(salesManID);
        dest.writeString(actionBy);
        dest.writeString(purchaserName);
        dest.writeString(imgUrl);
        dest.writeString(createBy);
        dest.writeString(placeCityCode);
        dest.writeString(createTime);
        dest.writeString(customerID);
        dest.writeString(producer);
        dest.writeString(placeProvinceCode);
        dest.writeInt(status);
        dest.writeTypedList(demandList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProductReply() {
        return productReply;
    }

    public void setProductReply(String productReply) {
        this.productReply = productReply;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPurchaserPhone() {
        return purchaserPhone;
    }

    public void setPurchaserPhone(String purchaserPhone) {
        this.purchaserPhone = purchaserPhone;
    }

    public String getPlaceProvince() {
        return placeProvince;
    }

    public void setPlaceProvince(String placeProvince) {
        this.placeProvince = placeProvince;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSupplyPhone() {
        return supplyPhone;
    }

    public void setSupplyPhone(String supplyPhone) {
        this.supplyPhone = supplyPhone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPackMethod() {
        return packMethod;
    }

    public void setPackMethod(String packMethod) {
        this.packMethod = packMethod;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public String getPurchaserUserID() {
        return purchaserUserID;
    }

    public void setPurchaserUserID(String purchaserUserID) {
        this.purchaserUserID = purchaserUserID;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }

    public String getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(String salesManID) {
        this.salesManID = salesManID;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getPlaceCityCode() {
        return placeCityCode;
    }

    public void setPlaceCityCode(String placeCityCode) {
        this.placeCityCode = placeCityCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getPlaceProvinceCode() {
        return placeProvinceCode;
    }

    public void setPlaceProvinceCode(String placeProvinceCode) {
        this.placeProvinceCode = placeProvinceCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GoodsDemandItem> getDemandList() {
        return demandList;
    }

    public void setDemandList(List<GoodsDemandItem> demandList) {
        this.demandList = demandList;
    }
}
