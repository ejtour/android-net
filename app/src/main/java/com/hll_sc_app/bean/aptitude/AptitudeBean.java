package com.hll_sc_app.bean.aptitude;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeBean implements Parcelable {
    private String id;
    private String aptitudeName;
    private String aptitudeType;
    private String aptitudeUrl;
    private String endTime;
    private String createBy;
    private String createTime;
    private String checkTime;
    private int productNum;
    private String groupID;
    private List<AptitudeProductBean> aptitudeList;
    private transient boolean selectable = true;
    private transient boolean selected;

    public AptitudeBean() {
    }

    protected AptitudeBean(Parcel in) {
        id = in.readString();
        aptitudeName = in.readString();
        aptitudeType = in.readString();
        aptitudeUrl = in.readString();
        endTime = in.readString();
        createBy = in.readString();
        createTime = in.readString();
        checkTime = in.readString();
        productNum = in.readInt();
        groupID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(aptitudeName);
        dest.writeString(aptitudeType);
        dest.writeString(aptitudeUrl);
        dest.writeString(endTime);
        dest.writeString(createBy);
        dest.writeString(createTime);
        dest.writeString(checkTime);
        dest.writeInt(productNum);
        dest.writeString(groupID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AptitudeBean> CREATOR = new Creator<AptitudeBean>() {
        @Override
        public AptitudeBean createFromParcel(Parcel in) {
            return new AptitudeBean(in);
        }

        @Override
        public AptitudeBean[] newArray(int size) {
            return new AptitudeBean[size];
        }
    };

    public String getAptitudeName() {
        return aptitudeName;
    }

    public void setAptitudeName(String aptitudeName) {
        this.aptitudeName = aptitudeName;
    }

    public String getAptitudeType() {
        return aptitudeType;
    }

    public void setAptitudeType(String aptitudeType) {
        this.aptitudeType = aptitudeType;
    }

    public String getAptitudeUrl() {
        return aptitudeUrl;
    }

    public void setAptitudeUrl(String aptitudeUrl) {
        this.aptitudeUrl = aptitudeUrl;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCheckTime() {
        return "0".equals(checkTime) ? null : checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<AptitudeProductBean> getAptitudeList() {
        return aptitudeList;
    }

    public void setAptitudeList(List<AptitudeProductBean> aptitudeList) {
        this.aptitudeList = aptitudeList;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
