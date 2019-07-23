package com.hll_sc_app.bean.account;

/**
 * 修改密码
 *
 * @author zc
 */
public class ChangePwdReq {
    public static final int PURCHASER = 0;
    public static final int SUPPLYER = 1;
    private String loginPWD;
    private String newLoginPWD;
    private String checkLoginPWD;
    private int flag;

    public String getLoginPWD() {
        return loginPWD;
    }

    public void setLoginPWD(String loginPWD) {
        this.loginPWD = loginPWD;
    }

    public String getNewLoginPWD() {
        return newLoginPWD;
    }

    public void setNewLoginPWD(String newLoginPWD) {
        this.newLoginPWD = newLoginPWD;
    }

    public String getCheckLoginPWD() {
        return checkLoginPWD;
    }

    public void setCheckLoginPWD(String checkLoginPWD) {
        this.checkLoginPWD = checkLoginPWD;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
