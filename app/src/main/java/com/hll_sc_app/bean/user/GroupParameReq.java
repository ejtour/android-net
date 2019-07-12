package com.hll_sc_app.bean.user;

/**
 * 查询集团参数
 *
 * @author zhuyingsong
 * @date 2018/12/19
 */
public class GroupParameReq {
    /**
     * 集团ID
     */
    private String groupID;

    private String parameTypes;

    private String flag = "1";

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getParameTypes() {
        return parameTypes;
    }

    public void setParameTypes(String parameTypes) {
        this.parameTypes = parameTypes;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
