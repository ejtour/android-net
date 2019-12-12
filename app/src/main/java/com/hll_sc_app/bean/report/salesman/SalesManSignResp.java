package com.hll_sc_app.bean.report.salesman;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SalesManSignResp {

    private int totalAddIntentCustomerNum;
    private int totalAddSignCustomerNum;
    private int totalAddSignShopNum;
    private int totalIntentCustomerNum;
    private int totalSignCustomerNum;
    private int totalSignShopNum;
    private int totalSize;


    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add(String.valueOf(totalSize)); // 数量
        list.add(String.valueOf(totalIntentCustomerNum)); // 意向客户
        list.add(String.valueOf(totalSignCustomerNum)); // 签约客户
        list.add(String.valueOf(totalSignShopNum)); // 签约门店
        list.add(String.valueOf(totalAddIntentCustomerNum)); // 新增意向客户
        list.add(String.valueOf(totalAddSignCustomerNum)); // 新增签约客户
        list.add(String.valueOf(totalAddSignShopNum)); // 新增签约门店
        return list;
    }

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
