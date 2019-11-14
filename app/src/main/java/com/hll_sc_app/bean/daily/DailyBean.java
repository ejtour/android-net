package com.hll_sc_app.bean.daily;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class DailyBean implements Parcelable {

    public static final Creator<DailyBean> CREATOR = new Creator<DailyBean>() {
        @Override
        public DailyBean createFromParcel(Parcel in) {
            return new DailyBean(in);
        }

        @Override
        public DailyBean[] newArray(int size) {
            return new DailyBean[size];
        }
    };
    private int readStatus;
    private String employeeName;
    private String actionTime;
    private String needHelp;
    private String receiver;
    private String actionBy;
    private String imgUrls;
    private String groupID;
    private String employeeID;
    private String remark;
    private String tomorrowPlan;
    private String employeeCode;
    private String createBy;
    private String createTime;
    private int replyNum;
    private int action;
    private int replyStatus;
    private String id;
    private String todayWork;

    protected DailyBean(Parcel in) {
        readStatus = in.readInt();
        employeeName = in.readString();
        actionTime = in.readString();
        needHelp = in.readString();
        receiver = in.readString();
        actionBy = in.readString();
        imgUrls = in.readString();
        groupID = in.readString();
        employeeID = in.readString();
        remark = in.readString();
        tomorrowPlan = in.readString();
        employeeCode = in.readString();
        createBy = in.readString();
        createTime = in.readString();
        replyNum = in.readInt();
        action = in.readInt();
        replyStatus = in.readInt();
        id = in.readString();
        todayWork = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(readStatus);
        dest.writeString(employeeName);
        dest.writeString(actionTime);
        dest.writeString(needHelp);
        dest.writeString(receiver);
        dest.writeString(actionBy);
        dest.writeString(imgUrls);
        dest.writeString(groupID);
        dest.writeString(employeeID);
        dest.writeString(remark);
        dest.writeString(tomorrowPlan);
        dest.writeString(employeeCode);
        dest.writeString(createBy);
        dest.writeString(createTime);
        dest.writeInt(replyNum);
        dest.writeInt(action);
        dest.writeInt(replyStatus);
        dest.writeString(id);
        dest.writeString(todayWork);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getNeedHelp() {
        return needHelp;
    }

    public void setNeedHelp(String needHelp) {
        this.needHelp = needHelp;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTomorrowPlan() {
        return tomorrowPlan;
    }

    public void setTomorrowPlan(String tomorrowPlan) {
        this.tomorrowPlan = tomorrowPlan;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
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

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTodayWork() {
        return todayWork;
    }

    public void setTodayWork(String todayWork) {
        this.todayWork = todayWork;
    }
}
