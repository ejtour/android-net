package com.hll_sc_app.bean.wallet;

/**
 * 钱包实名认证数据
 * @author zc
 */
public class WalletInfoReq {
    private String groupID;
    private int groupType=1;

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
