package com.hll_sc_app.bean.order.trace;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceBean implements Parcelable {
    private int opType;
    private int source;
    private String title;
    private int deliverType;
    private String opTime;
    private String opTypeName;
    private String billID;
    private String id;

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOpTypeName() {
        return opTypeName;
    }

    public void setOpTypeName(String opTypeName) {
        this.opTypeName = opTypeName;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.opType);
        dest.writeInt(this.source);
        dest.writeString(this.title);
        dest.writeInt(this.deliverType);
        dest.writeString(this.opTime);
        dest.writeString(this.opTypeName);
        dest.writeString(this.billID);
        dest.writeString(this.id);
    }

    public OrderTraceBean() {
    }

    protected OrderTraceBean(Parcel in) {
        this.opType = in.readInt();
        this.source = in.readInt();
        this.title = in.readString();
        this.deliverType = in.readInt();
        this.opTime = in.readString();
        this.opTypeName = in.readString();
        this.billID = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<OrderTraceBean> CREATOR = new Parcelable.Creator<OrderTraceBean>() {
        @Override
        public OrderTraceBean createFromParcel(Parcel source) {
            return new OrderTraceBean(source);
        }

        @Override
        public OrderTraceBean[] newArray(int size) {
            return new OrderTraceBean[size];
        }
    };
}
