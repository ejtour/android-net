package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 报价单新增参数
 *
 * @author zhuyingsong
 * @date 2019-07-10
 */
public class QuotationReq implements Parcelable {
    private QuotationBean quotation;
    private List<QuotationDetailBean> list;

    public QuotationBean getQuotation() {
        return quotation;
    }

    public void setQuotation(QuotationBean quotation) {
        this.quotation = quotation;
    }

    public List<QuotationDetailBean> getList() {
        return list;
    }

    public void setList(List<QuotationDetailBean> list) {
        this.list = list;
    }

    public static final Parcelable.Creator<QuotationReq> CREATOR = new Parcelable.Creator<QuotationReq>() {
        @Override
        public QuotationReq createFromParcel(Parcel source) {
            return new QuotationReq(source);
        }

        @Override
        public QuotationReq[] newArray(int size) {
            return new QuotationReq[size];
        }
    };

    public QuotationReq() {
    }

    protected QuotationReq(Parcel in) {
        this.quotation = in.readParcelable(QuotationBean.class.getClassLoader());
        this.list = in.createTypedArrayList(QuotationDetailBean.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.quotation, flags);
        dest.writeTypedList(this.list);
    }
}
