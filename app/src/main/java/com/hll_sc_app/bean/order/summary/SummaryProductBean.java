package com.hll_sc_app.bean.order.summary;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/28
 */

public class SummaryProductBean implements Parcelable {
    private String productName;
    private double productNum;
    private String productSpec;
    private String saleUnitName;

    protected SummaryProductBean(Parcel in) {
        productName = in.readString();
        productNum = in.readDouble();
        productSpec = in.readString();
        saleUnitName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeDouble(productNum);
        dest.writeString(productSpec);
        dest.writeString(saleUnitName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SummaryProductBean> CREATOR = new Creator<SummaryProductBean>() {
        @Override
        public SummaryProductBean createFromParcel(Parcel in) {
            return new SummaryProductBean(in);
        }

        @Override
        public SummaryProductBean[] newArray(int size) {
            return new SummaryProductBean[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getSaleUnitName() {
        return TextUtils.isEmpty(saleUnitName) ? "" : " " + saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }
}
