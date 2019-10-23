package com.hll_sc_app.bean.goodsdemand;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class GoodsDemandItem implements Parcelable {

    public static final Creator<GoodsDemandItem> CREATOR = new Creator<GoodsDemandItem>() {
        @Override
        public GoodsDemandItem createFromParcel(Parcel in) {
            return new GoodsDemandItem(in);
        }

        @Override
        public GoodsDemandItem[] newArray(int size) {
            return new GoodsDemandItem[size];
        }
    };
    private String actionTime;
    private String createTime;
    private String demandUrl;
    private String feedbackID;
    private String demandContent;
    private String id;
    private int demandType;

    public GoodsDemandItem() {
    }

    protected GoodsDemandItem(Parcel in) {
        actionTime = in.readString();
        createTime = in.readString();
        demandUrl = in.readString();
        feedbackID = in.readString();
        demandContent = in.readString();
        id = in.readString();
        demandType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actionTime);
        dest.writeString(createTime);
        dest.writeString(demandUrl);
        dest.writeString(feedbackID);
        dest.writeString(demandContent);
        dest.writeString(id);
        dest.writeInt(demandType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDemandUrl() {
        return demandUrl;
    }

    public void setDemandUrl(String demandUrl) {
        this.demandUrl = demandUrl;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getDemandContent() {
        return demandContent;
    }

    public void setDemandContent(String demandContent) {
        this.demandContent = demandContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDemandType() {
        return demandType;
    }

    public void setDemandType(int demandType) {
        this.demandType = demandType;
    }
}
