package com.hll_sc_app.bean.order.trace;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceParam implements Parcelable {
    private List<OrderTraceBean> list;
    private String title;
    private String deliverTitle;

    public OrderTraceParam(String address, List<OrderTraceBean> list) {
        OrderTraceBean first = new OrderTraceBean();
        first.setOpTypeName(String.format("【收货地址】%s", TextUtils.isEmpty(address) ? "" : address));
        this.list = new ArrayList<>();
        this.list.add(first);
        if (!CommonUtils.isEmpty(list)) {
            title = list.get(0).getOpTypeName();
            switch (list.get(0).getDeliverType()) {
                case 1:
                    deliverTitle = "供应商自有物流配送";
                    break;
                case 2:
                    deliverTitle = "供应商自提";
                    break;
                case 3:
                    deliverTitle = "供应商第三方配送";
                    break;
                default:
                    deliverTitle = "供应商";
            }
            this.list.addAll(list);
        }
    }

    public List<OrderTraceBean> getList() {
        return list;
    }

    public void setList(List<OrderTraceBean> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeliverTitle() {
        return deliverTitle;
    }

    public void setDeliverTitle(String deliverTitle) {
        this.deliverTitle = deliverTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeString(this.title);
        dest.writeString(this.deliverTitle);
    }

    protected OrderTraceParam(Parcel in) {
        this.list = in.createTypedArrayList(OrderTraceBean.CREATOR);
        this.title = in.readString();
        this.deliverTitle = in.readString();
    }

    public static final Parcelable.Creator<OrderTraceParam> CREATOR = new Parcelable.Creator<OrderTraceParam>() {
        @Override
        public OrderTraceParam createFromParcel(Parcel source) {
            return new OrderTraceParam(source);
        }

        @Override
        public OrderTraceParam[] newArray(int size) {
            return new OrderTraceParam[size];
        }
    };
}
