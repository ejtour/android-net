package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignSpecBean implements Parcelable {
    private String productPrice;
    private String saleUnitID;
    private String saleUnitName;
    private String specContent;
    private String specID;

    public static GoodsAssignSpecBean convertFromSpecsBean(SpecsBean bean) {
        GoodsAssignSpecBean specBean = new GoodsAssignSpecBean();
        specBean.setProductPrice(bean.getProductPrice());
        specBean.setSaleUnitID(bean.getSaleUnitID());
        specBean.setSaleUnitName(bean.getSaleUnitName());
        specBean.setSpecContent(bean.getSpecContent());
        specBean.setSpecID(bean.getSpecID());
        return specBean;
    }

    public GoodsAssignSpecBean() {
    }

    protected GoodsAssignSpecBean(Parcel in) {
        productPrice = in.readString();
        saleUnitID = in.readString();
        saleUnitName = in.readString();
        specContent = in.readString();
        specID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productPrice);
        dest.writeString(saleUnitID);
        dest.writeString(saleUnitName);
        dest.writeString(specContent);
        dest.writeString(specID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodsAssignSpecBean> CREATOR = new Creator<GoodsAssignSpecBean>() {
        @Override
        public GoodsAssignSpecBean createFromParcel(Parcel in) {
            return new GoodsAssignSpecBean(in);
        }

        @Override
        public GoodsAssignSpecBean[] newArray(int size) {
            return new GoodsAssignSpecBean[size];
        }
    };

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSaleUnitID() {
        return saleUnitID;
    }

    public void setSaleUnitID(String saleUnitID) {
        this.saleUnitID = saleUnitID;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }
}
