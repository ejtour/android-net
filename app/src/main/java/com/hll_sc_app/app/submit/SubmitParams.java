package com.hll_sc_app.app.submit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/14
 */

public class SubmitParams implements Parcelable {
    /**
     * 成功提交标题
     */
    private String title;
    /**
     * 成功提交描述
     */
    private String desc = "请等待供应商审核完成后完成退款\n或者在详情中联系供应商解决";

    /**
     * 跳转详情需要的 id
     */
    private String detailsId;

    /**
     * 详情路径
     */
    private String detailsPath;

    /**
     * 返回类型
     */
    private BackType backType;

    public SubmitParams() {
    }

    public SubmitParams(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDetailsId() {
        return detailsId;
    }

    public BackType getBackType() {
        return backType;
    }

    public void setBackType(BackType backType) {
        this.backType = backType;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getDetailsPath() {
        return detailsPath;
    }

    public void setDetailsPath(String detailsPath) {
        this.detailsPath = detailsPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.detailsId);
        dest.writeString(this.detailsPath);
        dest.writeInt(this.backType == null ? -1 : this.backType.ordinal());
    }

    protected SubmitParams(Parcel in) {
        this.title = in.readString();
        this.desc = in.readString();
        this.detailsId = in.readString();
        this.detailsPath = in.readString();
        int tmpBackType = in.readInt();
        this.backType = tmpBackType == -1 ? null : BackType.values()[tmpBackType];
    }

    public static final Creator<SubmitParams> CREATOR = new Creator<SubmitParams>() {
        @Override
        public SubmitParams createFromParcel(Parcel source) {
            return new SubmitParams(source);
        }

        @Override
        public SubmitParams[] newArray(int size) {
            return new SubmitParams[size];
        }
    };
}
