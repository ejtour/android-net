package com.hll_sc_app.app.info.other;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.groupInfo.GroupInfoResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

public class InfoOtherParam implements Parcelable {
    public static final Creator<InfoOtherParam> CREATOR = new Creator<InfoOtherParam>() {
        @Override
        public InfoOtherParam createFromParcel(Parcel in) {
            return new InfoOtherParam(in);
        }

        @Override
        public InfoOtherParam[] newArray(int size) {
            return new InfoOtherParam[size];
        }
    };
    private boolean editable;
    private List<GroupInfoResp.OtherLicensesBean> list;

    public InfoOtherParam(boolean editable, List<GroupInfoResp.OtherLicensesBean> list) {
        this.editable = editable;
        this.list = list;
    }

    protected InfoOtherParam(Parcel in) {
        editable = in.readByte() != 0;
        list = in.createTypedArrayList(GroupInfoResp.OtherLicensesBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (editable ? 1 : 0));
        dest.writeTypedList(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<GroupInfoResp.OtherLicensesBean> getList() {
        return list;
    }

    public void setList(List<GroupInfoResp.OtherLicensesBean> list) {
        this.list = list;
    }
}
