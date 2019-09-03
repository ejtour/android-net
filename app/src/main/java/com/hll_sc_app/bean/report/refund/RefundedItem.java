package com.hll_sc_app.bean.report.refund;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class RefundedItem implements IStringArrayGenerator {

    /**
     * 账期金额
     */
    private String  accountAmount;
    /**
     * 银行金额
     */
    private String  bankCardAmount;
    /**
     * 订单数
     */
    private long    billNum;
    /**
     * 现金金额
     */
    private String  cashAmount;
    /**
     * 在线金额
     */
    private String  onLineAmount;
    /**
     * 退货日期
     */
    private String  refundBillDate;
    /**
     * 退货单数
     */
    private long    refundBillNum;
    /**
     * 退货集团数
     */
    private long    refundGroupNum;
    /**
     * 退货商品数
     */
    private long    refundProductNum;
    /**
     * 退货占比
     */
    private String  refundProportion;
    /**
     * 退货门店数
     */
    private long    refundShopNum;
    /**
     * 小计金额
     */
    private String  subRefundAmount;
    /**
     * 订货金额
     */
    private String  totalBillAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(CalendarUtils.getDateFormatString(getRefundBillDate() + "", CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD));// 日期
        list.add(getRefundBillNum() + ""); // 退单数
        list.add(getRefundGroupNum() + "/" + getRefundShopNum()); // 退货客户数
        list.add(getRefundProductNum() + ""); //退货商品数
        list.add(CommonUtils.formatMoney(Double.parseDouble(getCashAmount()))); // 现金
        list.add(CommonUtils.formatMoney(Double.parseDouble(getBankCardAmount()))); //银行卡
        list.add(CommonUtils.formatMoney(Double.parseDouble(getOnLineAmount()))); //线上
        list.add(CommonUtils.formatMoney(Double.parseDouble(getAccountAmount()))); //账期
        list.add(CommonUtils.formatMoney(Double.parseDouble(getSubRefundAmount()))); //小计
        list.add(getBillNum() + "");//小计
        list.add(getRefundProportion());//退货占比
        return list;
    }

    public String getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(String accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getBankCardAmount() {
        return bankCardAmount;
    }

    public void setBankCardAmount(String bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public long getBillNum() {
        return billNum;
    }

    public void setBillNum(long billNum) {
        this.billNum = billNum;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getOnLineAmount() {
        return onLineAmount;
    }

    public void setOnLineAmount(String onLineAmount) {
        this.onLineAmount = onLineAmount;
    }

    public String getRefundBillDate() {
        return refundBillDate;
    }

    public void setRefundBillDate(String refundBillDate) {
        this.refundBillDate = refundBillDate;
    }

    public long getRefundBillNum() {
        return refundBillNum;
    }

    public void setRefundBillNum(long refundBillNum) {
        this.refundBillNum = refundBillNum;
    }

    public long getRefundGroupNum() {
        return refundGroupNum;
    }

    public void setRefundGroupNum(long refundGroupNum) {
        this.refundGroupNum = refundGroupNum;
    }

    public long getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(long refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public String getRefundProportion() {
        return refundProportion;
    }

    public void setRefundProportion(String refundProportion) {
        this.refundProportion = refundProportion;
    }

    public long getRefundShopNum() {
        return refundShopNum;
    }

    public void setRefundShopNum(long refundShopNum) {
        this.refundShopNum = refundShopNum;
    }

    public String getSubRefundAmount() {
        return subRefundAmount;
    }

    public void setSubRefundAmount(String subRefundAmount) {
        this.subRefundAmount = subRefundAmount;
    }

    public String getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(String totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }
}
