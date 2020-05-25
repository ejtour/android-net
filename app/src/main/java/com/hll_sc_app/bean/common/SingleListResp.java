package com.hll_sc_app.bean.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class SingleListResp<T> {
    @SerializedName(value = "list", alternate = {"records", "detailList", "purchaserList", "productList", "dataSource", "warehouseStoreProductList"})
    private List<T> records;
    @SerializedName(value = "total", alternate = {"size", "totalSize"})
    private int totalSize;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
