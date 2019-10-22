package com.hll_sc_app.bean.goodsdemand;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/22
 */

public class GoodsDemandReq implements Parcelable {
    public static final Creator<GoodsDemandReq> CREATOR = new Creator<GoodsDemandReq>() {
        @Override
        public GoodsDemandReq createFromParcel(Parcel in) {
            return new GoodsDemandReq(in);
        }

        @Override
        public GoodsDemandReq[] newArray(int size) {
            return new GoodsDemandReq[size];
        }
    };
    private List<GoodsDemandItem> demandList;
    private String imgUrl;
    private String marketPrice;
    private String packMethod;
    private String placeCity;
    private String placeCityCode;
    private String placeProvince;
    private String placeProvinceCode;
    private String producer;
    private String productBrand;
    private String productBrief;
    private String productName;
    private String purchaserID;
    private String purchaserName;
    private int source = 1;
    private String specContent;
    private String supplyID;
    private String supplyName;
    private String supplyPhone;

    public GoodsDemandReq() {
    }

    protected GoodsDemandReq(Parcel in) {
        imgUrl = in.readString();
        marketPrice = in.readString();
        packMethod = in.readString();
        placeCity = in.readString();
        placeCityCode = in.readString();
        placeProvince = in.readString();
        placeProvinceCode = in.readString();
        producer = in.readString();
        productBrand = in.readString();
        productBrief = in.readString();
        productName = in.readString();
        purchaserID = in.readString();
        purchaserName = in.readString();
        source = in.readInt();
        specContent = in.readString();
        supplyID = in.readString();
        supplyName = in.readString();
        supplyPhone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
        dest.writeString(marketPrice);
        dest.writeString(packMethod);
        dest.writeString(placeCity);
        dest.writeString(placeCityCode);
        dest.writeString(placeProvince);
        dest.writeString(placeProvinceCode);
        dest.writeString(producer);
        dest.writeString(productBrand);
        dest.writeString(productBrief);
        dest.writeString(productName);
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeInt(source);
        dest.writeString(specContent);
        dest.writeString(supplyID);
        dest.writeString(supplyName);
        dest.writeString(supplyPhone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<GoodsDemandItem> getDemandList() {
        return demandList;
    }

    public void setDemandList(List<GoodsDemandItem> demandList) {
        this.demandList = demandList;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPackMethod() {
        return packMethod;
    }

    public void setPackMethod(String packMethod) {
        this.packMethod = packMethod;
    }

    public String getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }

    public String getPlaceCityCode() {
        return placeCityCode;
    }

    public void setPlaceCityCode(String placeCityCode) {
        this.placeCityCode = placeCityCode;
    }

    public String getPlaceProvince() {
        return placeProvince;
    }

    public void setPlaceProvince(String placeProvince) {
        this.placeProvince = placeProvince;
    }

    public String getPlaceProvinceCode() {
        return placeProvinceCode;
    }

    public void setPlaceProvinceCode(String placeProvinceCode) {
        this.placeProvinceCode = placeProvinceCode;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getSupplyPhone() {
        return supplyPhone;
    }

    public void setSupplyPhone(String supplyPhone) {
        this.supplyPhone = supplyPhone;
    }
}
