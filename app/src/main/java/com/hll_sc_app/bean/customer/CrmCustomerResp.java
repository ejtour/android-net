package com.hll_sc_app.bean.customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/15
 */

public class CrmCustomerResp {
    private int myCustomerNum;
    private int allCustomerNum;
    private int highSeaCustomerNum;

    public int getMyCustomerNum() {
        return myCustomerNum;
    }

    public void setMyCustomerNum(int myCustomerNum) {
        this.myCustomerNum = myCustomerNum;
    }

    public int getAllCustomerNum() {
        return allCustomerNum;
    }

    public void setAllCustomerNum(int allCustomerNum) {
        this.allCustomerNum = allCustomerNum;
    }

    public int getHighSeaCustomerNum() {
        return highSeaCustomerNum;
    }

    public void setHighSeaCustomerNum(int highSeaCustomerNum) {
        this.highSeaCustomerNum = highSeaCustomerNum;
    }
}
