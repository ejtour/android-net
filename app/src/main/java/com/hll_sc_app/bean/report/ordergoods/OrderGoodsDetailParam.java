package com.hll_sc_app.bean.report.ordergoods;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.filter.DateParam;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class OrderGoodsDetailParam extends DateParam implements Parcelable {
    private OrderGoodsBean bean;

    public OrderGoodsBean getBean() {
        return bean;
    }

    public void setBean(OrderGoodsBean bean) {
        this.bean = bean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getStartDate() != null ? this.getStartDate().getTime() : -1);
        dest.writeLong(this.getEndDate() != null ? this.getEndDate().getTime() : -1);
        dest.writeParcelable(this.bean, flags);
    }

    public OrderGoodsDetailParam() {
    }

    protected OrderGoodsDetailParam(Parcel in) {
        long tmpStartDate = in.readLong();
        this.setStartDate(tmpStartDate == -1 ? null : new Date(tmpStartDate));
        long tmpEndDate = in.readLong();
        this.setEndDate(tmpEndDate == -1 ? null : new Date(tmpEndDate));
        this.bean = in.readParcelable(OrderGoodsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderGoodsDetailParam> CREATOR = new Parcelable.Creator<OrderGoodsDetailParam>() {
        @Override
        public OrderGoodsDetailParam createFromParcel(Parcel source) {
            return new OrderGoodsDetailParam(source);
        }

        @Override
        public OrderGoodsDetailParam[] newArray(int size) {
            return new OrderGoodsDetailParam[size];
        }
    };
}
