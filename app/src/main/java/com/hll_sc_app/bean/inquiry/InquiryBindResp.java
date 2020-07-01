package com.hll_sc_app.bean.inquiry;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;

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

    protected InquiryBindResp(Parcel in) {
        extGroupID = in.readString();
        groupID = in.readString();
        id = in.readString();
        operateModel = in.readString();
        purchaserID = in.readString();
        purchaserName = in.readString();
        shopID = in.readString();
        shopName = in.readString();
        detailList = in.createTypedArrayList(TransferDetailBean.CREATOR);
        unbindList = in.createTypedArrayList(TransferDetailBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(extGroupID);
        dest.writeString(groupID);
        dest.writeString(id);
        dest.writeString(operateModel);
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeString(shopID);
        dest.writeString(shopName);
        dest.writeTypedList(detailList);
        dest.writeTypedList(unbindList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InquiryBindResp> CREATOR = new Creator<InquiryBindResp>() {
        @Override
        public InquiryBindResp createFromParcel(Parcel in) {
            return new InquiryBindResp(in);
        }

        @Override
        public InquiryBindResp[] newArray(int size) {
            return new InquiryBindResp[size];
        }
    };

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
        for (TransferDetailBean detailBean : detailList) {
            list.add(detailBean.convertToQuotationDetail());
        }
        return req;
    }

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

}
