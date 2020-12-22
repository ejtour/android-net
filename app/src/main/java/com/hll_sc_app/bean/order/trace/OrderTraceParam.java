package com.hll_sc_app.bean.order.trace;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.bean.order.OrderResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceParam implements Parcelable {
    private final List<OrderTraceBean> list;
    private final String title;
    private final String cancelRole;
    private final String deliverTitle;
    private final int deliverType;
    private final String driverName;
    private final String latGaoDe;
    private final String lonGaoDe;
    private final String mobilePhone;
    private final String plateNumber;
    private final String targetAddress;
    private final String expressName;
    private final String expressNo;
    private final String subBillID;
    private final int subBillStatus;

    public OrderTraceParam(OrderResp resp, List<OrderTraceBean> list) {
        OrderTraceBean first = new OrderTraceBean();
        this.targetAddress = TextUtils.isEmpty(resp.getTargetAddress()) ? "采购商暂未提供收货地址，请联系采购商" : resp.getTargetAddress();
        first.setOpTypeName(String.format("【收货地址】%s", targetAddress));
        first.setSupplyTitle(first.getOpTypeName());
        this.list = new ArrayList<>();
        this.list.add(first);
        this.cancelRole = OrderHelper.getCancelRole(resp.getCanceler());
        this.deliverType = resp.getDeliverType();
        this.driverName = resp.getDriverName();
        this.latGaoDe = resp.getLatGaoDe();
        this.lonGaoDe = resp.getLonGaoDe();
        this.mobilePhone = resp.getMobilePhone();
        this.plateNumber = resp.getPlateNumber();
        this.subBillStatus = resp.getSubBillStatus();
        this.subBillID = resp.getSubBillID();
        this.expressName = resp.getExpressName();
        this.expressNo = resp.getExpressNo();
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

    protected OrderTraceParam(Parcel in) {
        list = in.createTypedArrayList(OrderTraceBean.CREATOR);
        title = in.readString();
        cancelRole = in.readString();
        deliverTitle = in.readString();
        deliverType = in.readInt();
        driverName = in.readString();
        latGaoDe = in.readString();
        lonGaoDe = in.readString();
        mobilePhone = in.readString();
        plateNumber = in.readString();
        targetAddress = in.readString();
        expressName = in.readString();
        expressNo = in.readString();
        subBillID = in.readString();
        subBillStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
        dest.writeString(title);
        dest.writeString(cancelRole);
        dest.writeString(deliverTitle);
        dest.writeInt(deliverType);
        dest.writeString(driverName);
        dest.writeString(latGaoDe);
        dest.writeString(lonGaoDe);
        dest.writeString(mobilePhone);
        dest.writeString(plateNumber);
        dest.writeString(targetAddress);
        dest.writeString(expressName);
        dest.writeString(expressNo);
        dest.writeString(subBillID);
        dest.writeInt(subBillStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTraceParam> CREATOR = new Creator<OrderTraceParam>() {
        @Override
        public OrderTraceParam createFromParcel(Parcel in) {
            return new OrderTraceParam(in);
        }

        @Override
        public OrderTraceParam[] newArray(int size) {
            return new OrderTraceParam[size];
        }
    };

    public List<OrderTraceBean> getList() {
        return list;
    }

    public String getTitle() {
        return title;
    }

    public String getCancelRole() {
        return cancelRole;
    }

    public String getDeliverTitle() {
        return deliverTitle;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getLatGaoDe() {
        return latGaoDe;
    }

    public String getLonGaoDe() {
        return lonGaoDe;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getExpressName() {
        return expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public int getSubBillStatus() {
        return subBillStatus;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public String getTargetAddress() {
        return targetAddress;
    }
}
