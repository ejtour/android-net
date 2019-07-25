package com.hll_sc_app.bean.account;

/**
 * 修改主账号绑定的请求
 * @author zc
 */
public class UnbindMainAccountReq {

    private String groupID;
    private String newLoginPhone;
    private String newVeriCode;
    private String originLoginPhone;
    private String originVeriCode;
    private String newPWD;
    private String checkLoginPWD;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getNewLoginPhone() {
        return newLoginPhone;
    }

    public void setNewLoginPhone(String newLoginPhone) {
        this.newLoginPhone = newLoginPhone;
    }

    public String getNewVeriCode() {
        return newVeriCode;
    }

    public void setNewVeriCode(String newVeriCode) {
        this.newVeriCode = newVeriCode;
    }

    public String getOriginLoginPhone() {
        return originLoginPhone;
    }

    public void setOriginLoginPhone(String originLoginPhone) {
        this.originLoginPhone = originLoginPhone;
    }

    public String getOriginVeriCode() {
        return originVeriCode;
    }

    public void setOriginVeriCode(String originVeriCode) {
        this.originVeriCode = originVeriCode;
    }

    public String getNewPWD() {
        return newPWD;
    }

    public void setNewPWD(String newPWD) {
        this.newPWD = newPWD;
    }

    public String getCheckLoginPWD() {
        return checkLoginPWD;
    }

    public void setCheckLoginPWD(String checkLoginPWD) {
        this.checkLoginPWD = checkLoginPWD;
    }
}
