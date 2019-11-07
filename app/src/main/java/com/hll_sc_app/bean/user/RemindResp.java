package com.hll_sc_app.bean.user;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public class RemindResp {
    private String groupID;
    private int isOpen;
    private double remindTimes;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public double getRemindTimes() {
        return remindTimes;
    }

    public void setRemindTimes(double remindTimes) {
        this.remindTimes = remindTimes;
    }
}
