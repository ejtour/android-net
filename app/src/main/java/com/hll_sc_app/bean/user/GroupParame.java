package com.hll_sc_app.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author baitianqi baitianqi@hualala.com
 *
 * version 1.0.0
 * Copyright (C) 2017-2018 hualala
 *           This program is protected by copyright laws.
 *           Program Name:哗啦啦商城
 *
 * Description:
 *
 * CreateTime: 2019/7/12 18:11
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 *
 */

public class GroupParame implements Parcelable {
    private Integer parameType;
    private Integer parameValue;
    private Long groupID;
    private String parameTitle;
    private String paramContent;

    public String getParameTitle() {
        return parameTitle;
    }

    public void setParameTitle(String parameTitle) {
        this.parameTitle = parameTitle;
    }

    public String getParamContent() {
        return paramContent;
    }

    public void setParamContent(String paramContent) {
        this.paramContent = paramContent;
    }

    public Integer getParameType() {
        return parameType;
    }

    public Integer getParameValue() {
        return parameValue;
    }

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public void setParameType(Integer parameType) {
        this.parameType = parameType;
    }

    public void setParameValue(Integer parameValue) {
        this.parameValue = parameValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.parameType);
        dest.writeValue(this.parameValue);
        dest.writeValue(this.groupID);
        dest.writeString(this.parameTitle);
        dest.writeString(this.paramContent);
    }

    public GroupParame() {
    }

    protected GroupParame(Parcel in) {
        this.parameType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.parameValue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.groupID = (Long) in.readValue(Long.class.getClassLoader());
        this.parameTitle = in.readString();
        this.paramContent = in.readString();
    }

    public static final Parcelable.Creator<GroupParame> CREATOR = new Parcelable.Creator<GroupParame>() {
        @Override
        public GroupParame createFromParcel(Parcel source) {
            return new GroupParame(source);
        }

        @Override
        public GroupParame[] newArray(int size) {
            return new GroupParame[size];
        }
    };
}
