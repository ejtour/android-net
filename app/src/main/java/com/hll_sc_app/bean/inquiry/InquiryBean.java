package com.hll_sc_app.bean.inquiry;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.hll_sc_app.app.inquiry.detail.InquiryDetailActivity;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.citymall.util.CommonUtils;

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
    private int enquiryShopNum;
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
    @SerializedName(value = "shopID", alternate = {"shopId"})
    private String shopID;
    private String shopName;
    private String cycleEndDate;
    private String cycleStartDate;
    private int enquiryType;
    private List<InquiryDetailBean> detailList;
    private List<InquiryBean> enquiryShops;

    public static final Creator<InquiryBean> CREATOR = new Creator<InquiryBean>() {
        @Override
        public InquiryBean createFromParcel(Parcel source) {
            return new InquiryBean(source);
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

    public InquiryBean() {
    }

    protected InquiryBean(Parcel in) {
        this.enquiryEndTime = in.readString();
        this.enquiryNo = in.readString();
        this.enquiryNum = in.readInt();
        this.enquiryShopNum = in.readInt();
        this.enquiryStartTime = in.readString();
        this.enquiryStatus = in.readInt();
        this.extGroupID = in.readString();
        this.extShopID = in.readString();
        this.groupID = in.readString();
        this.id = in.readString();
        this.linkTel = in.readString();
        this.linkman = in.readString();
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.shopID = in.readString();
        this.shopName = in.readString();
        this.cycleEndDate = in.readString();
        this.cycleStartDate = in.readString();
        this.enquiryType = in.readInt();
        this.detailList = new ArrayList<InquiryDetailBean>();
        in.readList(this.detailList, InquiryDetailBean.class.getClassLoader());
        this.enquiryShops = in.createTypedArrayList(InquiryBean.CREATOR);
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

    public QuotationReq convertToQuotationReq() {
        QuotationReq req = new QuotationReq();
        QuotationBean bean = new QuotationBean();
        List<QuotationDetailBean> list = new ArrayList<>();
        req.setQuotation(bean);
        req.setList(list);
        bean.setIsWarehouse("0");
        bean.setPurchaserID(purchaserID);
        bean.setPurchaserName(purchaserName);
        bean.setSource("1");
        List<String> shopIDs = new ArrayList<>();
        List<String> shopNames = new ArrayList<>();
        if (!CommonUtils.isEmpty(enquiryShops)) {
            for (InquiryBean inquiryBean : enquiryShops) {
                shopIDs.add(inquiryBean.getShopID());
                if (!TextUtils.isEmpty(inquiryBean.getLinkman())) {
                    shopNames.add(inquiryBean.getShopName() + InquiryDetailActivity.DIVISION_FLAG + inquiryBean.getLinkman() +
                            "/" + inquiryBean.getLinkTel());
                } else {
                    shopNames.add(inquiryBean.getShopName());
                }
            }
        }
        bean.setShopIDNum(String.valueOf(shopIDs.size()));
        bean.setShopIDs(TextUtils.join(",", shopIDs));
        bean.setShopNames(TextUtils.join(",", shopNames));
        bean.setShopName(shopName);
        bean.setExtGroupID(extGroupID);
        for (InquiryDetailBean inquiryDetailBean : detailList) {
            list.add(inquiryDetailBean.convertToQuotationDetail());
        }
        return req;
    }

    public int getEnquiryShopNum() {
        return enquiryShopNum;
    }

    public List<InquiryBean> getEnquiryShops() {
        return enquiryShops;
    }

    public void setEnquiryShops(List<InquiryBean> enquiryShops) {
        this.enquiryShops = enquiryShops;
    }

    public void setEnquiryShopNum(int enquiryShopNum) {
        this.enquiryShopNum = enquiryShopNum;
    }

    public List<InquiryDetailBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InquiryDetailBean> detailList) {
        this.detailList = detailList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.enquiryEndTime);
        dest.writeString(this.enquiryNo);
        dest.writeInt(this.enquiryNum);
        dest.writeInt(this.enquiryShopNum);
        dest.writeString(this.enquiryStartTime);
        dest.writeInt(this.enquiryStatus);
        dest.writeString(this.extGroupID);
        dest.writeString(this.extShopID);
        dest.writeString(this.groupID);
        dest.writeString(this.id);
        dest.writeString(this.linkTel);
        dest.writeString(this.linkman);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.shopID);
        dest.writeString(this.shopName);
        dest.writeString(this.cycleEndDate);
        dest.writeString(this.cycleStartDate);
        dest.writeInt(this.enquiryType);
        dest.writeList(this.detailList);
        dest.writeTypedList(this.enquiryShops);
    }

    public void readFromParcel(Parcel source) {
        this.enquiryEndTime = source.readString();
        this.enquiryNo = source.readString();
        this.enquiryNum = source.readInt();
        this.enquiryShopNum = source.readInt();
        this.enquiryStartTime = source.readString();
        this.enquiryStatus = source.readInt();
        this.extGroupID = source.readString();
        this.extShopID = source.readString();
        this.groupID = source.readString();
        this.id = source.readString();
        this.linkTel = source.readString();
        this.linkman = source.readString();
        this.purchaserID = source.readString();
        this.purchaserName = source.readString();
        this.shopID = source.readString();
        this.shopName = source.readString();
        this.cycleEndDate = source.readString();
        this.cycleStartDate = source.readString();
        this.enquiryType = source.readInt();
        this.detailList = new ArrayList<InquiryDetailBean>();
        source.readList(this.detailList, InquiryDetailBean.class.getClassLoader());
        this.enquiryShops = source.createTypedArrayList(InquiryBean.CREATOR);
    }
}
