package com.hll_sc_app.bean.order.transfer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class OrderResultResp implements Parcelable {
    private List<InventoryBean> records;
    private List<InventoryBean> shelfFlowRecords;

    public List<InventoryBean> getRecords() {
        return records;
    }

    public void setRecords(List<InventoryBean> records) {
        this.records = records;
    }

    public List<InventoryBean> getShelfFlowRecords() {
        return shelfFlowRecords;
    }

    public void setShelfFlowRecords(List<InventoryBean> shelfFlowRecords) {
        this.shelfFlowRecords = shelfFlowRecords;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.records);
        dest.writeTypedList(this.shelfFlowRecords);
    }

    public OrderResultResp() {
    }

    protected OrderResultResp(Parcel in) {
        this.records = in.createTypedArrayList(InventoryBean.CREATOR);
        this.shelfFlowRecords = in.createTypedArrayList(InventoryBean.CREATOR);
    }

    public static final Parcelable.Creator<OrderResultResp> CREATOR = new Parcelable.Creator<OrderResultResp>() {
        @Override
        public OrderResultResp createFromParcel(Parcel source) {
            return new OrderResultResp(source);
        }

        @Override
        public OrderResultResp[] newArray(int size) {
            return new OrderResultResp[size];
        }
    };
}
