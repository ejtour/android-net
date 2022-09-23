package com.hll_sc_app.bean.inquiry;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.app.inquiry.detail.InquiryDetailActivity;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/20
 */

public class InquiryBindResp implements Parcelable {
    private String extGroupID;
    private String groupID;
    private String id;
    private String operateModel;
    private String purchaserID;
    private String purchaserName;
    private String shopID;
    private String shopName;

    private List<TransferDetailBean> detailList;
    private List<TransferDetailBean> unbindList;
    public static final Creator<InquiryBindResp> CREATOR = new Creator<InquiryBindResp>() {
        @Override
        public InquiryBindResp createFromParcel(Parcel source) {
            return new InquiryBindResp(source);
        }

        @Override
        public InquiryBindResp[] newArray(int size) {
            return new InquiryBindResp[size];
        }
    };
    private List<InquiryBean> enquiryShops;

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
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

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
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

    public List<TransferDetailBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TransferDetailBean> detailList) {
        this.detailList = detailList;
    }

    public List<TransferDetailBean> getUnbindList() {
        return unbindList;
    }

    public void setUnbindList(List<TransferDetailBean> unbindList) {
        this.unbindList = unbindList;
    }

    public InquiryBindResp() {
    }

    protected InquiryBindResp(Parcel in) {
        this.extGroupID = in.readString();
        this.groupID = in.readString();
        this.id = in.readString();
        this.operateModel = in.readString();
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.shopID = in.readString();
        this.shopName = in.readString();
        this.detailList = in.createTypedArrayList(TransferDetailBean.CREATOR);
        this.unbindList = in.createTypedArrayList(TransferDetailBean.CREATOR);
        this.enquiryShops = in.createTypedArrayList(InquiryBean.CREATOR);
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
        for (TransferDetailBean detailBean : detailList) {
            list.add(detailBean.convertToQuotationDetail());
        }
        return req;
    }

    public List<InquiryBean> getEnquiryShops() {
        return enquiryShops;
    }

    public void setEnquiryShops(List<InquiryBean> enquiryShops) {
        this.enquiryShops = enquiryShops;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.extGroupID);
        dest.writeString(this.groupID);
        dest.writeString(this.id);
        dest.writeString(this.operateModel);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.shopID);
        dest.writeString(this.shopName);
        dest.writeTypedList(this.detailList);
        dest.writeTypedList(this.unbindList);
        dest.writeTypedList(this.enquiryShops);
    }

    public void readFromParcel(Parcel source) {
        this.extGroupID = source.readString();
        this.groupID = source.readString();
        this.id = source.readString();
        this.operateModel = source.readString();
        this.purchaserID = source.readString();
        this.purchaserName = source.readString();
        this.shopID = source.readString();
        this.shopName = source.readString();
        this.detailList = source.createTypedArrayList(TransferDetailBean.CREATOR);
        this.unbindList = source.createTypedArrayList(TransferDetailBean.CREATOR);
        this.enquiryShops = source.createTypedArrayList(InquiryBean.CREATOR);
    }
}
