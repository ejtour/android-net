package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignDetailBean implements Parcelable {

    private String imgUrl;
    private String productID;
    private String productName;
    private List<GoodsAssignSpecBean> specs;

    public static GoodsAssignDetailBean convertFromGoodsBean(GoodsBean bean, SpecsBean spec) {
        GoodsAssignDetailBean detailBean = new GoodsAssignDetailBean();
        detailBean.setImgUrl(bean.getImgUrl());
        detailBean.setProductID(bean.getProductID());
        detailBean.setProductName(bean.getProductName());
        ArrayList<GoodsAssignSpecBean> specs = new ArrayList<>();
        detailBean.specs = specs;
        specs.add(GoodsAssignSpecBean.convertFromSpecsBean(spec));
        return detailBean;
    }

    public GoodsAssignDetailBean() {
    }

    protected GoodsAssignDetailBean(Parcel in) {
        imgUrl = in.readString();
        productID = in.readString();
        productName = in.readString();
        specs = in.createTypedArrayList(GoodsAssignSpecBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
        dest.writeString(productID);
        dest.writeString(productName);
        dest.writeTypedList(specs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodsAssignDetailBean> CREATOR = new Creator<GoodsAssignDetailBean>() {
        @Override
        public GoodsAssignDetailBean createFromParcel(Parcel in) {
            return new GoodsAssignDetailBean(in);
        }

        @Override
        public GoodsAssignDetailBean[] newArray(int size) {
            return new GoodsAssignDetailBean[size];
        }
    };

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<GoodsAssignSpecBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<GoodsAssignSpecBean> specs) {
        this.specs = specs;
    }
}
