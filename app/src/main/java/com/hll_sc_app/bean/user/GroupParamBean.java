package com.hll_sc_app.bean.user;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/12
 */

public class GroupParamBean {
    private String groupID;
    private String id;
    private int parameType;
    private int parameValue;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getParameType() {
        return parameType;
    }

    public void setParameType(int parameType) {
        this.parameType = parameType;
    }

    public int getParameValue() {
        return parameValue;
    }

    public void setParameValue(int parameValue) {
        this.parameValue = parameValue;
    }
}
