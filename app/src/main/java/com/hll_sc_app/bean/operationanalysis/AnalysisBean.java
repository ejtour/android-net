package com.hll_sc_app.bean.operationanalysis;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class AnalysisBean {
    private int activePurchaserNum;
    private int activeSupplyNum;
    private float averageShopTradeAmount;
    private float averageTradeAmount;
    private int coopActiveGroupNum;
    private int coopActiveShopNum;
    private int coopGroupNum;
    private int coopIncrGroupNum;
    private int coopIncrShopNum;
    private int coopShopNum;
    private String date;
    private int incrPurchaserNum;
    private int incrPurchaserStoreNum;
    private int incrSupplyNum;
    private int purchaserNum;
    private int purchaserStoreNum;
    private int shopNum;
    private int supplyNum;
    private int validOrderNum;
    private float validTradeAmount;
    private float relativeRatio;

    public String getDateRange(int timeType) {
        Date date = DateUtil.parse(this.date);
        return String.format("%s - %s", CalendarUtils.format(date, Constants.SLASH_MM_DD),
                CalendarUtils.format(
                        timeType == 2 ? CalendarUtils.getWeekDate(date, 0, 7) : CalendarUtils.getLastDateInMonth(date)
                        , Constants.SLASH_MM_DD));
    }

    public float getRelativeRatio() {
        return relativeRatio;
    }

    public void setRelativeRatio(float relativeRatio) {
        this.relativeRatio = relativeRatio;
    }

    public int getActivePurchaserNum() {
        return activePurchaserNum;
    }

    public void setActivePurchaserNum(int activePurchaserNum) {
        this.activePurchaserNum = activePurchaserNum;
    }

    public int getActiveSupplyNum() {
        return activeSupplyNum;
    }

    public void setActiveSupplyNum(int activeSupplyNum) {
        this.activeSupplyNum = activeSupplyNum;
    }

    public float getAverageShopTradeAmount() {
        return averageShopTradeAmount;
    }

    public void setAverageShopTradeAmount(float averageShopTradeAmount) {
        this.averageShopTradeAmount = averageShopTradeAmount;
    }

    public float getAverageTradeAmount() {
        return averageTradeAmount;
    }

    public void setAverageTradeAmount(float averageTradeAmount) {
        this.averageTradeAmount = averageTradeAmount;
    }

    public int getCoopActiveGroupNum() {
        return coopActiveGroupNum;
    }

    public void setCoopActiveGroupNum(int coopActiveGroupNum) {
        this.coopActiveGroupNum = coopActiveGroupNum;
    }

    public int getCoopActiveShopNum() {
        return coopActiveShopNum;
    }

    public void setCoopActiveShopNum(int coopActiveShopNum) {
        this.coopActiveShopNum = coopActiveShopNum;
    }

    public int getCoopGroupNum() {
        return coopGroupNum;
    }

    public void setCoopGroupNum(int coopGroupNum) {
        this.coopGroupNum = coopGroupNum;
    }

    public int getCoopIncrGroupNum() {
        return coopIncrGroupNum;
    }

    public void setCoopIncrGroupNum(int coopIncrGroupNum) {
        this.coopIncrGroupNum = coopIncrGroupNum;
    }

    public int getCoopIncrShopNum() {
        return coopIncrShopNum;
    }

    public void setCoopIncrShopNum(int coopIncrShopNum) {
        this.coopIncrShopNum = coopIncrShopNum;
    }

    public int getCoopShopNum() {
        return coopShopNum;
    }

    public void setCoopShopNum(int coopShopNum) {
        this.coopShopNum = coopShopNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIncrPurchaserNum() {
        return incrPurchaserNum;
    }

    public void setIncrPurchaserNum(int incrPurchaserNum) {
        this.incrPurchaserNum = incrPurchaserNum;
    }

    public int getIncrPurchaserStoreNum() {
        return incrPurchaserStoreNum;
    }

    public void setIncrPurchaserStoreNum(int incrPurchaserStoreNum) {
        this.incrPurchaserStoreNum = incrPurchaserStoreNum;
    }

    public int getIncrSupplyNum() {
        return incrSupplyNum;
    }

    public void setIncrSupplyNum(int incrSupplyNum) {
        this.incrSupplyNum = incrSupplyNum;
    }

    public int getPurchaserNum() {
        return purchaserNum;
    }

    public void setPurchaserNum(int purchaserNum) {
        this.purchaserNum = purchaserNum;
    }

    public int getPurchaserStoreNum() {
        return purchaserStoreNum;
    }

    public void setPurchaserStoreNum(int purchaserStoreNum) {
        this.purchaserStoreNum = purchaserStoreNum;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(int supplyNum) {
        this.supplyNum = supplyNum;
    }

    public int getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(int validOrderNum) {
        this.validOrderNum = validOrderNum;
    }

    public float getValidTradeAmount() {
        return validTradeAmount;
    }

    public void setValidTradeAmount(float validTradeAmount) {
        this.validTradeAmount = validTradeAmount;
    }
}
