package com.hll_sc_app.bean.report.lack;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public class LackDetailsResp {
    private double totalDeliveryLackAmount;
    private String totalDeliveryLackRate;
    private double totalOriReserveAmount;
    private int totalSize;
    private List<LackDetailsBean> records;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add("- -");
        list.add("- -");
        list.add("- -");
        list.add("- -");
        list.add("- -");
        list.add(CommonUtils.formatMoney(totalDeliveryLackAmount)); // 缺货金额
        list.add(totalDeliveryLackRate); // 缺货率
        return list;
    }

    public double getTotalDeliveryLackAmount() {
        return totalDeliveryLackAmount;
    }

    public void setTotalDeliveryLackAmount(double totalDeliveryLackAmount) {
        this.totalDeliveryLackAmount = totalDeliveryLackAmount;
    }

    public String getTotalDeliveryLackRate() {
        return totalDeliveryLackRate;
    }

    public void setTotalDeliveryLackRate(String totalDeliveryLackRate) {
        this.totalDeliveryLackRate = totalDeliveryLackRate;
    }

    public double getTotalOriReserveAmount() {
        return totalOriReserveAmount;
    }

    public void setTotalOriReserveAmount(double totalOriReserveAmount) {
        this.totalOriReserveAmount = totalOriReserveAmount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<LackDetailsBean> getRecords() {
        return records;
    }

    public void setRecords(List<LackDetailsBean> records) {
        this.records = records;
    }
}
