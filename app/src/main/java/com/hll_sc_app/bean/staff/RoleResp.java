package com.hll_sc_app.bean.staff;

import java.util.List;

/**
 * 角色
 *
 * @author zhuyingsong
 * @date 2019-07-26
 */
public class RoleResp {
    private int totalSize;
    private List<RoleBean> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<RoleBean> getRecords() {
        return records;
    }

    public void setRecords(List<RoleBean> records) {
        this.records = records;
    }
}
