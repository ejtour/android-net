package com.hll_sc_app.bean.report.req;

import java.util.HashMap;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class ReportExportReq {
    private String pv;
    private String email;
    private int isBindEmail;
    private String reqParams;

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

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }
}
