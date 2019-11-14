package com.hll_sc_app.bean.daily;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class DailyEditReq implements Parcelable {
    public static final Creator<DailyEditReq> CREATOR = new Creator<DailyEditReq>() {
        @Override
        public DailyEditReq createFromParcel(Parcel in) {
            return new DailyEditReq(in);
        }

        @Override
        public DailyEditReq[] newArray(int size) {
            return new DailyEditReq[size];
        }
    };
    private String employeeCode;
    private String employeeID;
    private String employeeName;
    private String groupID;
    private String imgurls;
    private String needHelp;
    private String receiver;
    private String remark;
    private String todayWork;
    private String tomorrowPlan;

    public DailyEditReq() {
        UserBean user = GreenDaoUtils.getUser();
        employeeCode = user.getEmployeeCode();
        employeeID = user.getEmployeeID();
        employeeName = user.getEmployeeName();
        groupID = user.getGroupID();
    }

    protected DailyEditReq(Parcel in) {
        employeeCode = in.readString();
        employeeID = in.readString();
        employeeName = in.readString();
        groupID = in.readString();
        imgurls = in.readString();
        needHelp = in.readString();
        receiver = in.readString();
        remark = in.readString();
        todayWork = in.readString();
        tomorrowPlan = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employeeCode);
        dest.writeString(employeeID);
        dest.writeString(employeeName);
        dest.writeString(groupID);
        dest.writeString(imgurls);
        dest.writeString(needHelp);
        dest.writeString(receiver);
        dest.writeString(remark);
        dest.writeString(todayWork);
        dest.writeString(tomorrowPlan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getImgurls() {
        return imgurls;
    }

    public void setImgurls(String imgurls) {
        this.imgurls = imgurls;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTodayWork() {
        return todayWork;
    }

    public void setTodayWork(String todayWork) {
        this.todayWork = todayWork;
    }

    public String getTomorrowPlan() {
        return tomorrowPlan;
    }

    public void setTomorrowPlan(String tomorrowPlan) {
        this.tomorrowPlan = tomorrowPlan;
    }
}
