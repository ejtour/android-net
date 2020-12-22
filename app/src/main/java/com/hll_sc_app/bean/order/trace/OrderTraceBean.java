package com.hll_sc_app.bean.order.trace;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceBean implements Parcelable {
    private String createBy;
    private int opType;
    private int source;
    private String title;
    private int deliverType;
    private String opTime;
    private String opTypeName;
    private String billID;
    private String id;
    private CharSequence extra;
    private String supplyTitle;

    protected OrderTraceBean(Parcel in) {
        createBy = in.readString();
        opType = in.readInt();
        source = in.readInt();
        title = in.readString();
        deliverType = in.readInt();
        opTime = in.readString();
        opTypeName = in.readString();
        billID = in.readString();
        id = in.readString();
        supplyTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createBy);
        dest.writeInt(opType);
        dest.writeInt(source);
        dest.writeString(title);
        dest.writeInt(deliverType);
        dest.writeString(opTime);
        dest.writeString(opTypeName);
        dest.writeString(billID);
        dest.writeString(id);
        dest.writeString(supplyTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTraceBean> CREATOR = new Creator<OrderTraceBean>() {
        @Override
        public OrderTraceBean createFromParcel(Parcel in) {
            return new OrderTraceBean(in);
        }

        @Override
        public OrderTraceBean[] newArray(int size) {
            return new OrderTraceBean[size];
        }
    };

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public CharSequence getExtra() {
        return extra;
    }

    public void setExtra(CharSequence extra) {
        this.extra = extra;
    }

    public String getSupplyTitle() {
        return supplyTitle;
    }

    public void setSupplyTitle(String supplyTitle) {
        this.supplyTitle = supplyTitle;
    }

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

    public OrderTraceBean() {
    }
}
