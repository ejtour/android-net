package com.hll_sc_app.bean.report.orderGoods;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class OrderGoodsDetailParam implements Parcelable {
    private Date startDate;
    private Date endDate;
    private OrderGoodsBean bean;

    public String getFormatStartDate() {
        return startDate == null ? null : CalendarUtils.toLocalDate(startDate);
    }

    public String getFormatEndDate() {
        return endDate == null ? null : CalendarUtils.toLocalDate(endDate);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

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
        dest.writeLong(this.startDate != null ? this.startDate.getTime() : -1);
        dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
        dest.writeParcelable(this.bean, flags);
    }

    public OrderGoodsDetailParam() {
    }

    protected OrderGoodsDetailParam(Parcel in) {
        long tmpStartDate = in.readLong();
        this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
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
