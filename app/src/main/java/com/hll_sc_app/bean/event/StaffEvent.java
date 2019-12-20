package com.hll_sc_app.bean.event;

import com.hll_sc_app.bean.staff.EmployeeBean;

/**
 * 员工管理
 */
public class StaffEvent {
    private int linkShopNum = -1;
    private EmployeeBean employeeBean;
    private boolean isRefreshList;

    public StaffEvent(EmployeeBean employeeBean, boolean isRefreshList) {
        this.employeeBean = employeeBean;
        this.isRefreshList = isRefreshList;
    }

    public StaffEvent(int linkShopNum, boolean isRefreshList) {
        this.linkShopNum = linkShopNum;
        this.isRefreshList = isRefreshList;
    }

    public int getLinkShopNum() {
        return linkShopNum;
    }

    public void setLinkShopNum(int linkShopNum) {
        this.linkShopNum = linkShopNum;
    }

    public EmployeeBean getEmployeeBean() {
        return employeeBean;
    }

    public void setEmployeeBean(EmployeeBean employeeBean) {
        this.employeeBean = employeeBean;
    }

    public boolean isRefreshList() {
        return isRefreshList;
    }

    public void setRefreshList(boolean refreshList) {
        isRefreshList = refreshList;
    }
}
