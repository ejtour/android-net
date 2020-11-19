package com.hll_sc_app.bean.event;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/12
 */
public class RefreshStaffShopEvent {
    private int shopNum = -1;
    private String employeeID;

    public int getShopNum() {
        return shopNum;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public RefreshStaffShopEvent() {
    }

    public RefreshStaffShopEvent(String employeeID, int shopNum) {
        this.employeeID = employeeID;
        this.shopNum = shopNum;
    }
}
