package com.hll_sc_app.bean.report.marketing;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */

public class MarketingShopBean implements IStringArrayGenerator {
    private int sequenceNo;
    private String shopName;
    private String purchaserName;
    private int billNum;
    private double saleAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(sequenceNo));
        list.add(shopName);
        list.add(purchaserName);
        list.add(CommonUtils.formatNum(billNum));
        list.add("¥" + CommonUtils.formatMoney(saleAmount));
        return list;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }
}
