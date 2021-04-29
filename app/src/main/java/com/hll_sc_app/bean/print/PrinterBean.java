package com.hll_sc_app.bean.print;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
public class PrinterBean implements Parcelable {
    private String deviceName;
    private String deviceID;
    private String deviceCode;
    private String operator;
    private String groupID;
    private String resultCode;
    private String returnMsg;

    public PrinterBean() {
    }

    protected PrinterBean(Parcel in) {
        deviceName = in.readString();
        deviceID = in.readString();
        deviceCode = in.readString();
        operator = in.readString();
        groupID = in.readString();
        resultCode = in.readString();
        returnMsg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceName);
        dest.writeString(deviceID);
        dest.writeString(deviceCode);
        dest.writeString(operator);
        dest.writeString(groupID);
        dest.writeString(resultCode);
        dest.writeString(returnMsg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PrinterBean> CREATOR = new Creator<PrinterBean>() {
        @Override
        public PrinterBean createFromParcel(Parcel in) {
            return new PrinterBean(in);
        }

        @Override
        public PrinterBean[] newArray(int size) {
            return new PrinterBean[size];
        }
    };

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
