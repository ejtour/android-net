package com.hll_sc_app.bean.report.inspectLack;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InspectLackItem implements IStringArrayGenerator {

    private long date;
    private String inspectionLackAmount;
    private String inspectionLackKindNum;
    private String inspectionLackNum;
    private String inspectionLackRate;
    private String inspectionOrderNum;
    private String inspectionTotalAmount;
    private String oriDeliveryTradeAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(CalendarUtils.getDateFormatString(getDate() + "", CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(getInspectionOrderNum()); // 收货单数
        list.add(CommonUtils.formatMoney(Double.parseDouble(getInspectionTotalAmount()))); // 收货金额
        list.add(CommonUtils.formatMoney(Double.parseDouble(getOriDeliveryTradeAmount()))); // 原发货金额
        list.add(getInspectionLackKindNum()); //收货差异商品数
        list.add(CommonUtils.formatNumber(getInspectionLackNum())); // 收货差异量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getInspectionLackAmount()))); // 收货差异金额
        list.add(CommonUtils.formatNumber(new BigDecimal(getInspectionLackRate()).multiply(BigDecimal.valueOf(100)).toPlainString()) + "%");//收货差异率
        return list;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getInspectionLackAmount() {
        return inspectionLackAmount;
    }

    public void setInspectionLackAmount(String inspectionLackAmount) {
        this.inspectionLackAmount = inspectionLackAmount;
    }

    public String getInspectionLackKindNum() {
        return inspectionLackKindNum;
    }

    public void setInspectionLackKindNum(String inspectionLackKindNum) {
        this.inspectionLackKindNum = inspectionLackKindNum;
    }

    public String getInspectionLackNum() {
        return inspectionLackNum;
    }

    public void setInspectionLackNum(String inspectionLackNum) {
        this.inspectionLackNum = inspectionLackNum;
    }

    public String getInspectionLackRate() {
        return inspectionLackRate;
    }

    public void setInspectionLackRate(String inspectionLackRate) {
        this.inspectionLackRate = inspectionLackRate;
    }

    public String getInspectionOrderNum() {
        return inspectionOrderNum;
    }

    public void setInspectionOrderNum(String inspectionOrderNum) {
        this.inspectionOrderNum = inspectionOrderNum;
    }

    public String getInspectionTotalAmount() {
        return inspectionTotalAmount;
    }

    public void setInspectionTotalAmount(String inspectionTotalAmount) {
        this.inspectionTotalAmount = inspectionTotalAmount;
    }

    public String getOriDeliveryTradeAmount() {
        return oriDeliveryTradeAmount;
    }

    public void setOriDeliveryTradeAmount(String oriDeliveryTradeAmount) {
        this.oriDeliveryTradeAmount = oriDeliveryTradeAmount;
    }
}
