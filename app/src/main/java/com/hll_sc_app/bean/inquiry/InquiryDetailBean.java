package com.hll_sc_app.bean.inquiry;

import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

public class InquiryDetailBean {
    private double enquiryPrice;
    private String goodsCode;
    private String goodsDesc;
    private String goodsID;
    private String goodsName;
    private double goodsNum;
    private String id;
    private String imgUrl;
    private String purchaseUnit;
    private String standardUnit;
    private double taxRate;

    public QuotationDetailBean convertToQuotationDetail() {
        QuotationDetailBean quotationDetailBean = new QuotationDetailBean();
        quotationDetailBean.setProductID(goodsID);
        quotationDetailBean.setProductName(goodsName);
        quotationDetailBean.setProductDesc(goodsDesc);
        quotationDetailBean.setPrice(CommonUtils.formatNumber(enquiryPrice));
        quotationDetailBean.setTaxRate(String.valueOf(taxRate));
        quotationDetailBean.setSaleUnitName(purchaseUnit);
        return quotationDetailBean;
    }

    public double getEnquiryPrice() {
        return enquiryPrice;
    }

    public void setEnquiryPrice(double enquiryPrice) {
        this.enquiryPrice = enquiryPrice;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(String standardUnit) {
        this.standardUnit = standardUnit;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
