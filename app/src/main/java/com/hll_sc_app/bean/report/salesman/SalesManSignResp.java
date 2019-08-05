package com.hll_sc_app.bean.report.salesman;

import java.util.List;

public class SalesManSignResp {

    private int totalAddIntentCustomerNum;
    private int totalAddSignCustomerNum;
    private int totalAddSignShopNum;
    private int totalIntentCustomerNum;
    private int totalSignCustomerNum;
    private int totalSignShopNum;
    private int totalSize;

    private List<SalesManSignAchievement> records;

    public int getTotalAddIntentCustomerNum() {
        return totalAddIntentCustomerNum;
    }

    public void setTotalAddIntentCustomerNum(int totalAddIntentCustomerNum) {
        this.totalAddIntentCustomerNum = totalAddIntentCustomerNum;
    }

    public int getTotalAddSignCustomerNum() {
        return totalAddSignCustomerNum;
    }

    public void setTotalAddSignCustomerNum(int totalAddSignCustomerNum) {
        this.totalAddSignCustomerNum = totalAddSignCustomerNum;
    }

    public int getTotalAddSignShopNum() {
        return totalAddSignShopNum;
    }

    public void setTotalAddSignShopNum(int totalAddSignShopNum) {
        this.totalAddSignShopNum = totalAddSignShopNum;
    }

    public int getTotalIntentCustomerNum() {
        return totalIntentCustomerNum;
    }

    public void setTotalIntentCustomerNum(int totalIntentCustomerNum) {
        this.totalIntentCustomerNum = totalIntentCustomerNum;
    }

    public int getTotalSignCustomerNum() {
        return totalSignCustomerNum;
    }

    public void setTotalSignCustomerNum(int totalSignCustomerNum) {
        this.totalSignCustomerNum = totalSignCustomerNum;
    }

    public int getTotalSignShopNum() {
        return totalSignShopNum;
    }

    public void setTotalSignShopNum(int totalSignShopNum) {
        this.totalSignShopNum = totalSignShopNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<SalesManSignAchievement> getRecords() {
        return records;
    }

    public void setRecords(List<SalesManSignAchievement> records) {
        this.records = records;
    }
}
