package com.hll_sc_app.bean.report.req;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class ReportExportReq<T> {
    private String pv;
    private String email;
    private int isBindEmail;
    private T reqParams;

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(int isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public T getReqParams() {
        return reqParams;
    }

    public void setReqParams(T reqParams) {
        this.reqParams = reqParams;
    }
}
