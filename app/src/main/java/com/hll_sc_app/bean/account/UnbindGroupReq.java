package com.hll_sc_app.bean.account;

/**
 * 解绑集团
 *
 * @author zc
 */
public class UnbindGroupReq {

    public static final int PURCHASER = 0;
    public static final int SUPPLYER = 1;

    private String groupID;
    private int grouptype;
    private String originLoginPhone;
    private String originVeriCode;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(int grouptype) {
        this.grouptype = grouptype;
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
}
