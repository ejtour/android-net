package com.hll_sc_app.bean.event;

/**
 * 合同管理
 */
public class ContractManageEvent {
    private boolean isRefreshList;

    public ContractManageEvent(boolean isRefreshList) {
        this.isRefreshList = isRefreshList;
    }

    public boolean isRefreshList() {
        return isRefreshList;
    }

    public void setRefreshList(boolean refreshList) {
        isRefreshList = refreshList;
    }
}
