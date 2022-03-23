package com.hll_sc_app.bean.inquiry;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

public class InquiryBean implements Parcelable {
    private String enquiryEndTime;
    private String enquiryNo;
    private int enquiryNum;
    private String enquiryStartTime;
    private int enquiryStatus;
    private String extGroupID;
    private String extShopID;
    private String groupID;
    private String id;
    private String linkTel;
    private String linkman;
    private String purchaserID;
    private String purchaserName;
    private String shopID;
    private String shopName;
    private String cycleEndDate;
    private String cycleStartDate;
    private int enquiryType;
    private List<InquiryDetailBean> detailList;

    public QuotationReq convertToQuotationReq() {
        QuotationReq req = new QuotationReq();
        QuotationBean bean = new QuotationBean();
        List<QuotationDetailBean> list = new ArrayList<>();
        req.setQuotation(bean);
        req.setList(list);
        bean.setIsWarehouse("0");
        bean.setPurchaserID(purchaserID);
        bean.setPurchaserName(purchaserName);
        bean.setShopIDNum("1");
        bean.setSource("1");
        bean.setShopIDs(shopID);
        bean.setShopName(shopName);
        bean.setExtGroupID(extGroupID);
        for (InquiryDetailBean inquiryDetailBean : detailList) {
            list.add(inquiryDetailBean.convertToQuotationDetail());
        }
        return req;
    }

    protected InquiryBean(Parcel in) {
        enquiryStatus = in.readInt();
        id = in.readString();
        purchaserName = in.readString();
        enquiryType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(enquiryStatus);
        dest.writeString(id);
        dest.writeString(purchaserName);
        dest.writeInt(enquiryType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InquiryBean> CREATOR = new Creator<InquiryBean>() {
        @Override
        public InquiryBean createFromParcel(Parcel in) {
            return new InquiryBean(in);
        }

        @Override
        public InquiryBean[] newArray(int size) {
            return new InquiryBean[size];
        }
    };

    public String getEnquiryEndTime() {
        return enquiryEndTime;
    }

    public void setEnquiryEndTime(String enquiryEndTime) {
        this.enquiryEndTime = enquiryEndTime;
    }

    public String getEnquiryNo() {
        return enquiryNo;
    }

    public void setEnquiryNo(String enquiryNo) {
        this.enquiryNo = enquiryNo;
    }

    public int getEnquiryNum() {
        return enquiryNum;
    }

    public void setEnquiryNum(int enquiryNum) {
        this.enquiryNum = enquiryNum;
    }

    public String getEnquiryStartTime() {
        return enquiryStartTime;
    }

    public void setEnquiryStartTime(String enquiryStartTime) {
        this.enquiryStartTime = enquiryStartTime;
    }

    public int getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(int enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
    }

    public String getExtShopID() {
        return extShopID;
    }

    public void setExtShopID(String extShopID) {
        this.extShopID = extShopID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
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

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<InquiryDetailBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InquiryDetailBean> detailList) {
        this.detailList = detailList;
    }

    public String getCycleEndDate() {
        return cycleEndDate;
    }

    public void setCycleEndDate(String cycleEndDate) {
        this.cycleEndDate = cycleEndDate;
    }

    public String getCycleStartDate() {
        return cycleStartDate;
    }

    public void setCycleStartDate(String cycleStartDate) {
        this.cycleStartDate = cycleStartDate;
    }

    public int getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(int enquiryType) {
        this.enquiryType = enquiryType;
    }
}
