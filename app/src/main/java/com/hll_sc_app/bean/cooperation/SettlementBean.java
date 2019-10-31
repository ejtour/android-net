package com.hll_sc_app.bean.cooperation;

import java.util.List;

/**
 * 结算方式
 *
 * @author zhuyingsong
 * @date 2019-07-18
 */
public class SettlementBean {
    /**
     * 微信直连 0-未启用,1-启用
     */
    private String wechatStatus;

    /**
     * 铁金库 0-未启用,1-启用
     */
    private String openStatus;

    /**
     * 储值卡 0-未启用,1-启用
     */
    private String cardStatus;

    /**
     * 在线支付 -1-未开通,0-未启用,1-启用
     */
    private String onlinePayment;
    /**
     * 货到付款 0-未启用,1-启用
     */
    private String cashPayment;
    /**
     * 账期支付日期:按周：0-周末
     */
    private String payTerm;
    /**
     * 在线支付方式列表
     */
    private String onlinePayMethod;
    /**
     * 结算日
     */
    private String settleDate;
    /**
     * 账期支付方式:0-未设置,1-按周,2-按月
     */
    private String payTermType;
    /**
     * 账期支付 0-未启用,1-启用
     */
    private String accountPayment;
    /**
     * 货到付款方式列表
     */
    private String codPayMethod;
    private List<String> payTypeEnum;


    public String getWechatStatus() {
        return wechatStatus;
    }

    public void setWechatStatus(String wechatStatus) {
        this.wechatStatus = wechatStatus;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(String onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    }

    public String getPayTerm() {
        return payTerm;
    }

    public void setPayTerm(String payTerm) {
        this.payTerm = payTerm;
    }

    public String getOnlinePayMethod() {
        return onlinePayMethod;
    }

    public void setOnlinePayMethod(String onlinePayMethod) {
        this.onlinePayMethod = onlinePayMethod;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getPayTermType() {
        return payTermType;
    }

    public void setPayTermType(String payTermType) {
        this.payTermType = payTermType;
    }

    public String getAccountPayment() {
        return accountPayment;
    }

    public void setAccountPayment(String accountPayment) {
        this.accountPayment = accountPayment;
    }

    public String getCodPayMethod() {
        return codPayMethod;
    }

    public void setCodPayMethod(String codPayMethod) {
        this.codPayMethod = codPayMethod;
    }

    public List<String> getPayTypeEnum() {
        return payTypeEnum;
    }

    public void setPayTypeEnum(List<String> payTypeEnum) {
        this.payTypeEnum = payTypeEnum;
    }
}
