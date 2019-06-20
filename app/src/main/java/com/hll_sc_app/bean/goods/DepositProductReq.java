package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 押金商品-用于请求参数
 *
 * @author zhuyingsong
 * @date 2019-06-20
 */
public class DepositProductReq implements Parcelable {
    public static final Parcelable.Creator<DepositProductReq> CREATOR = new Parcelable.Creator<DepositProductReq>() {
        @Override
        public DepositProductReq createFromParcel(Parcel source) {
            return new DepositProductReq(source);
        }

        @Override
        public DepositProductReq[] newArray(int size) {
            return new DepositProductReq[size];
        }
    };
    private String depositProductID;
    private String depositSpecID;
    private String depositProductName;
    private String depositSpecName;
    private String depositNum;

    public DepositProductReq() {
    }

    protected DepositProductReq(Parcel in) {
        this.depositProductID = in.readString();
        this.depositSpecID = in.readString();
        this.depositProductName = in.readString();
        this.depositSpecName = in.readString();
        this.depositNum = in.readString();
    }

    public static List<DepositProductReq> createDepositProductReq(List<DepositProductBean> list) {
        List<DepositProductReq> listReq = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (DepositProductBean bean : list) {
                DepositProductReq depositProductReq = new DepositProductReq();
                depositProductReq.setDepositProductID(bean.getProductID());
                depositProductReq.setDepositSpecID(bean.getSpecID());
                depositProductReq.setDepositProductName(bean.getProductName());
                depositProductReq.setDepositSpecName(bean.getSpecContent());
                depositProductReq.setDepositNum(bean.getDepositNum());
                listReq.add(depositProductReq);
            }
        }
        return listReq;
    }

    public static List<DepositProductBean> createDepositProductBean(List<DepositProductReq> list) {
        List<DepositProductBean> listReq = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (DepositProductReq depositProductReq : list) {
                DepositProductBean bean = new DepositProductBean();
                bean.setProductID(depositProductReq.getDepositProductID());
                bean.setSpecID(depositProductReq.getDepositSpecID());
                bean.setProductName(depositProductReq.getDepositProductName());
                bean.setSpecContent(depositProductReq.getDepositSpecName());
                bean.setDepositNum(depositProductReq.getDepositNum());
                listReq.add(bean);
            }
        }
        return listReq;
    }

    public String getDepositProductID() {
        return depositProductID;
    }

    public void setDepositProductID(String depositProductID) {
        this.depositProductID = depositProductID;
    }

    public String getDepositSpecID() {
        return depositSpecID;
    }

    public void setDepositSpecID(String depositSpecID) {
        this.depositSpecID = depositSpecID;
    }

    public String getDepositProductName() {
        return depositProductName;
    }

    public void setDepositProductName(String depositProductName) {
        this.depositProductName = depositProductName;
    }

    public String getDepositSpecName() {
        return depositSpecName;
    }

    public void setDepositSpecName(String depositSpecName) {
        this.depositSpecName = depositSpecName;
    }

    public String getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(String depositNum) {
        this.depositNum = depositNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.depositProductID);
        dest.writeString(this.depositSpecID);
        dest.writeString(this.depositProductName);
        dest.writeString(this.depositSpecName);
        dest.writeString(this.depositNum);
    }
}
