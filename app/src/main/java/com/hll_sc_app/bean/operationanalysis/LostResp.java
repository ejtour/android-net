package com.hll_sc_app.bean.operationanalysis;

import android.text.TextUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class LostResp {
    private String loseRate;
    private String lostAmountShopName;
    private String lostBillShopName;
    private int lostShopNum;
    private double lostTradeMostOrderAmount;
    private int lostTradeMostOrderNum;
    private String oldUserLostRate;
    private double preBillHighestAmount;
    private int preBillOrderNum;

    public String getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(String loseRate) {
        this.loseRate = loseRate;
    }

    public String getLostAmountShopName() {
        return TextUtils.isEmpty(lostAmountShopName) ? "---" : lostAmountShopName;
    }

    public void setLostAmountShopName(String lostAmountShopName) {
        this.lostAmountShopName = lostAmountShopName;
    }

    public String getLostBillShopName() {
        return TextUtils.isEmpty(lostBillShopName) ? "---" : lostBillShopName;
    }

    public void setLostBillShopName(String lostBillShopName) {
        this.lostBillShopName = lostBillShopName;
    }

    public int getLostShopNum() {
        return lostShopNum;
    }

    public void setLostShopNum(int lostShopNum) {
        this.lostShopNum = lostShopNum;
    }

    public double getLostTradeMostOrderAmount() {
        return lostTradeMostOrderAmount;
    }

    public void setLostTradeMostOrderAmount(double lostTradeMostOrderAmount) {
        this.lostTradeMostOrderAmount = lostTradeMostOrderAmount;
    }

    public int getLostTradeMostOrderNum() {
        return lostTradeMostOrderNum;
    }

    public void setLostTradeMostOrderNum(int lostTradeMostOrderNum) {
        this.lostTradeMostOrderNum = lostTradeMostOrderNum;
    }

    public String getOldUserLostRate() {
        return oldUserLostRate;
    }

    public void setOldUserLostRate(String oldUserLostRate) {
        this.oldUserLostRate = oldUserLostRate;
    }

    public double getPreBillHighestAmount() {
        return preBillHighestAmount;
    }

    public void setPreBillHighestAmount(double preBillHighestAmount) {
        this.preBillHighestAmount = preBillHighestAmount;
    }

    public int getPreBillOrderNum() {
        return preBillOrderNum;
    }

    public void setPreBillOrderNum(int preBillOrderNum) {
        this.preBillOrderNum = preBillOrderNum;
    }
}
