package com.hll_sc_app.bean.report.warehouse;

import java.util.List;

public class WareHouseServiceFeeResp {

    private int  totalSize;
    private List<WareHouseServiceFeeItem> dataSource;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<WareHouseServiceFeeItem> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<WareHouseServiceFeeItem> dataSource) {
        this.dataSource = dataSource;
    }
}
