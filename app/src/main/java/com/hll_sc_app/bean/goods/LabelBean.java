package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 行业标签 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-21
 */
public class LabelBean implements Parcelable {
    public static final Parcelable.Creator<LabelBean> CREATOR = new Parcelable.Creator<LabelBean>() {
        @Override
        public LabelBean createFromParcel(Parcel source) {
            return new LabelBean(source);
        }

        @Override
        public LabelBean[] newArray(int size) {
            return new LabelBean[size];
        }
    };
    private String actionTime;
    private String createby;
    /**
     * 标签ID
     */
    private String labelID;
    private String productUseCount;
    private String actionBy;
    private String createTime;
    private String templateUseCount;
    private String action;
    private String labelStatus;
    private String id;
    /**
     * 行业标签名称
     */
    private String labelName;
    private String labelGroupId;

    public LabelBean() {
    }

    protected LabelBean(Parcel in) {
        this.actionTime = in.readString();
        this.createby = in.readString();
        this.labelID = in.readString();
        this.productUseCount = in.readString();
        this.actionBy = in.readString();
        this.createTime = in.readString();
        this.templateUseCount = in.readString();
        this.action = in.readString();
        this.labelStatus = in.readString();
        this.id = in.readString();
        this.labelName = in.readString();
        this.labelGroupId = in.readString();
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }

    public String getProductUseCount() {
        return productUseCount;
    }

    public void setProductUseCount(String productUseCount) {
        this.productUseCount = productUseCount;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTemplateUseCount() {
        return templateUseCount;
    }

    public void setTemplateUseCount(String templateUseCount) {
        this.templateUseCount = templateUseCount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelGroupId() {
        return labelGroupId;
    }

    public void setLabelGroupId(String labelGroupId) {
        this.labelGroupId = labelGroupId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.createby);
        dest.writeString(this.labelID);
        dest.writeString(this.productUseCount);
        dest.writeString(this.actionBy);
        dest.writeString(this.createTime);
        dest.writeString(this.templateUseCount);
        dest.writeString(this.action);
        dest.writeString(this.labelStatus);
        dest.writeString(this.id);
        dest.writeString(this.labelName);
        dest.writeString(this.labelGroupId);
    }
}
