package com.hll_sc_app.bean.print;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/26
 */
public class PrintStatusBean {
    private int status;
    private String bill_no;
    private String resultMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
