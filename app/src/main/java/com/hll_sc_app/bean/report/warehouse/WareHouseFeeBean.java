package com.hll_sc_app.bean.report.warehouse;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class WareHouseFeeBean implements IStringArrayGenerator {

    private int sequenceNo;
    /**
     * 合作结束时间
     */
    private String endDate;
    /**
     * 已收服务费
     */
    private double paymentAmount;
    /**
     * 货主ID
     */
    private String shipperID;
    /**
     * 货主名称
     */
    private String shipperName;
    /**
     * 合作开始时间
     */
    private String startDate;
    /**
     * 收费模式【0手工录入服务费3按照货值的百分比 4 按照配送数量收费 5 按货位收费】
     */
    private int    termType;
    /**
     * 应收服务费
     */
    private double totalPrice;
    /**
     * 未收服务费
     */
    private double unPaymentAmount;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getTermType() {
        return termType;
    }

    public void setTermType(int termType) {
        this.termType = termType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnPaymentAmount() {
        return unPaymentAmount;
    }

    public void setUnPaymentAmount(double unPaymentAmount) {
        this.unPaymentAmount = unPaymentAmount;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(sequenceNo));
        list.add(shipperName); // 货主集团
        list.add(String.format("%s - %s", DateUtil.getReadableTime(startDate, Constants.SLASH_YYYY_MM_DD),
                DateUtil.getReadableTime(endDate, Constants.SLASH_YYYY_MM_DD)));// 合作时长
        list.add(getMode(termType)); // 收费模式
        list.add(CommonUtils.formatMoney(totalPrice)); // 应收服务费
        list.add(CommonUtils.formatMoney(paymentAmount)); //已收服务费
        list.add(CommonUtils.formatMoney(unPaymentAmount)); // 未收服务费
        return list;
    }

    private static String getMode(int type) {
        switch (type) {
            case 0:
                return "手工录入服务费";
            case 3:
                return "按照货值的百分比";
            case 4:
                return "按照配送数量收费";
            case 5:
                return "按货位收费";
            default:
                return "";
        }
    }
}
