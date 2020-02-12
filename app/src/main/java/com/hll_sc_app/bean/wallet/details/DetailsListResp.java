package com.hll_sc_app.bean.wallet.details;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public class DetailsListResp {
    private int pageCount;
    private int pageNo;
    private int pageSize;
    private int totalSize;
    private String transSalesCommissionSum; // 交易费用
    private String transAmountSum; // 交易金额
    private List<DetailsRecord> records;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getTransSalesCommissionSum() {
        return transSalesCommissionSum;
    }

    public void setTransSalesCommissionSum(String transSalesCommissionSum) {
        this.transSalesCommissionSum = transSalesCommissionSum;
    }

    public String getTransAmountSum() {
        return transAmountSum;
    }

    public void setTransAmountSum(String transAmountSum) {
        this.transAmountSum = transAmountSum;
    }

    public List<DetailsRecord> getRecords() {
        return records;
    }

    public void setRecords(List<DetailsRecord> records) {
        this.records = records;
    }

}
