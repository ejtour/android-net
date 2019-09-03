package com.hll_sc_app.bean.report.warehouse;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.google.zxing.common.StringUtils;
import com.hll_sc_app.bean.enums.ReportWareHouseServiceFeePayModelEnum;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WareHouseServiceFeeItem implements IStringArrayGenerator {

    private int sequenceNo;
    /**
     * 合作结束时间
     */
    private String endDate;
    /**
     * 已收服务费
     */
    private String paymentAmount;
    /**
     * 货主ID
     */
    private long   shipperID;
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
    private String totalPrice;
    /**
     * 未收服务费
     */
    private String unPaymentAmount;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public long getShipperID() {
        return shipperID;
    }

    public void setShipperID(long shipperID) {
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUnPaymentAmount() {
        return unPaymentAmount;
    }

    public void setUnPaymentAmount(String unPaymentAmount) {
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
        list.add(getSequenceNo()+"");
        list.add(getShipperName()); // 货主集团
        list.add(
                String.format("%s - %s",
                        StringUtil.isEmpty(getStartDate())?"":
                        CalendarUtils.getDateFormatString(getStartDate()+"",CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD),
                        StringUtil.isEmpty(getEndDate())?"":
                        CalendarUtils.getDateFormatString(getEndDate()+"",CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)));// 合作时长
        list.add(ReportWareHouseServiceFeePayModelEnum.getServiceFeePayModeDescByCode(getTermType())); // 收费模式
        list.add(CommonUtils.formatMoney(Double.parseDouble(getTotalPrice()))); // 应收服务费
        list.add(CommonUtils.formatMoney(Double.parseDouble(getPaymentAmount()))); //已收服务费
        list.add(CommonUtils.formatMoney(Double.parseDouble(getUnPaymentAmount()))); // 未收服务费
        return list;
    }
}
