package com.hll_sc_app.bean.goodsdemand;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class GoodsDemandBean implements Parcelable {

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

    public GoodsDemandReq covertToReq() {
        GoodsDemandReq req = new GoodsDemandReq();
        req.setId(id);
        req.setSupplyID(supplyID);
        req.setSupplyName(supplyName);
        req.setSupplyPhone(supplyPhone);
        req.setPurchaserID(purchaserID);
        req.setPurchaserName(purchaserName);
        req.setProductName(productName);
        req.setProductBrief(productBrief);

        req.setDemandList(demandList);
        req.setImgUrl(imgUrl);
        req.setMarketPrice(String.valueOf(marketPrice));
        req.setPackMethod(packMethod);
        req.setPlaceCity(placeCity);
        req.setPlaceCityCode(placeCityCode);
        req.setPlaceProvince(placeProvince);
        req.setPlaceProvinceCode(placeProvinceCode);
        req.setProducer(producer);
        req.setProductBrand(productBrand);
        req.setSpecContent(specContent);
        return req;
    }

    private String productReplySale;

    public String getProductReplySale() {
        return productReplySale;
    }

    public void setProductReplySale(String productReplySale) {
        this.productReplySale = productReplySale;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productReply);
        dest.writeString(this.supplyName);
        dest.writeString(this.actionTime);
        dest.writeDouble(this.marketPrice);
        dest.writeString(this.purchaserPhone);
        dest.writeString(this.placeProvince);
        dest.writeString(this.productBrand);
        dest.writeInt(this.source);
        dest.writeString(this.supplyPhone);
        dest.writeString(this.productName);
        dest.writeString(this.packMethod);
        dest.writeString(this.purchaserID);
        dest.writeString(this.productBrief);
        dest.writeString(this.supplyID);
        dest.writeString(this.purchaserUserID);
        dest.writeInt(this.action);
        dest.writeString(this.placeCity);
        dest.writeString(this.id);
        dest.writeString(this.specContent);
        dest.writeString(this.salesManID);
        dest.writeString(this.actionBy);
        dest.writeString(this.purchaserName);
        dest.writeString(this.imgUrl);
        dest.writeString(this.createBy);
        dest.writeString(this.placeCityCode);
        dest.writeString(this.createTime);
        dest.writeString(this.customerID);
        dest.writeString(this.producer);
        dest.writeString(this.placeProvinceCode);
        dest.writeInt(this.status);
        dest.writeTypedList(this.demandList);
        dest.writeString(this.productReplySale);
    }

    public GoodsDemandBean() {
    }

    protected GoodsDemandBean(Parcel in) {
        this.productReply = in.readString();
        this.supplyName = in.readString();
        this.actionTime = in.readString();
        this.marketPrice = in.readDouble();
        this.purchaserPhone = in.readString();
        this.placeProvince = in.readString();
        this.productBrand = in.readString();
        this.source = in.readInt();
        this.supplyPhone = in.readString();
        this.productName = in.readString();
        this.packMethod = in.readString();
        this.purchaserID = in.readString();
        this.productBrief = in.readString();
        this.supplyID = in.readString();
        this.purchaserUserID = in.readString();
        this.action = in.readInt();
        this.placeCity = in.readString();
        this.id = in.readString();
        this.specContent = in.readString();
        this.salesManID = in.readString();
        this.actionBy = in.readString();
        this.purchaserName = in.readString();
        this.imgUrl = in.readString();
        this.createBy = in.readString();
        this.placeCityCode = in.readString();
        this.createTime = in.readString();
        this.customerID = in.readString();
        this.producer = in.readString();
        this.placeProvinceCode = in.readString();
        this.status = in.readInt();
        this.demandList = in.createTypedArrayList(GoodsDemandItem.CREATOR);
        this.productReplySale = in.readString();
    }

    public static final Creator<GoodsDemandBean> CREATOR = new Creator<GoodsDemandBean>() {
        @Override
        public GoodsDemandBean createFromParcel(Parcel source) {
            return new GoodsDemandBean(source);
        }

        @Override
        public GoodsDemandBean[] newArray(int size) {
            return new GoodsDemandBean[size];
        }
    };
}
