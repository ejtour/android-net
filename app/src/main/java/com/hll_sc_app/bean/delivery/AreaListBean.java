package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 区
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class AreaListBean implements Parcelable {

    /**
     * areaCode : 110101
     * flag : 3
     * areaName : 东城区
     */

    private String areaCode;
    /**
     * 数据状态标识 1-不可选 2-可选未选中 3-可选已选中
     */
    private String flag;
    private String areaName;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public static final Parcelable.Creator<AreaListBean> CREATOR = new Parcelable.Creator<AreaListBean>() {
        @Override
        public AreaListBean createFromParcel(Parcel source) {
            return new AreaListBean(source);
        }

        @Override
        public AreaListBean[] newArray(int size) {
            return new AreaListBean[size];
        }
    };

    public AreaListBean() {
    }

    protected AreaListBean(Parcel in) {
        this.areaCode = in.readString();
        this.flag = in.readString();
        this.areaName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areaCode);
        dest.writeString(this.flag);
        dest.writeString(this.areaName);
    }
}
