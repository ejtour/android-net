package com.hll_sc_app.bean.report.receive;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDiffDetailsResp {
    private double totalInspectionLackAmount;
    private String totalInspectionLackRate;
    private int totalSize;
    private List<ReceiveDiffDetailsBean> records = new ArrayList<>();

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add("- - - -"); // 规格/单位
        list.add("- -"); // 发货量
        list.add("- -"); // 发货金额
        list.add("- -"); // 收货量
        list.add("- -"); // 差异量
        list.add(CommonUtils.formatMoney(totalInspectionLackAmount)); // 差异金额
        list.add(totalInspectionLackRate); // 差异率
        return list;
    }

    public double getTotalInspectionLackAmount() {
        return totalInspectionLackAmount;
    }

    public void setTotalInspectionLackAmount(double totalInspectionLackAmount) {
        this.totalInspectionLackAmount = totalInspectionLackAmount;
    }

    public String getTotalInspectionLackRate() {
        return totalInspectionLackRate;
    }

    public void setTotalInspectionLackRate(String totalInspectionLackRate) {
        this.totalInspectionLackRate = totalInspectionLackRate;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ReceiveDiffDetailsBean> getRecords() {
        return records;
    }

    public void setRecords(List<ReceiveDiffDetailsBean> records) {
        this.records = records;
    }
}
