package com.hll_sc_app.bean.user;

/**
 * @author baitianqi baitianqi@hualala.com
 *
 * version 1.0.0
 * Copyright (C) 2017-2018 hualala
 *           This program is protected by copyright laws.
 *           Program Name:哗啦啦商城
 *
 * Description:
 *
 * CreateTime: 2019/7/12 18:11
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 *
 */

public class GroupParame {
    private Integer parameType;
    private Integer parameValue;
    private Long groupID;

    public Integer getParameType() {
        return parameType;
    }

    public Integer getParameValue() {
        return parameValue;
    }

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public void setParameType(Integer parameType) {
        this.parameType = parameType;
    }

    public void setParameValue(Integer parameValue) {
        this.parameValue = parameValue;
    }
}
