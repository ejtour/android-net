package com.hll_sc_app.bean.rank;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class OrgRankBean {
    private String shopID;
    @SerializedName(value = "shopName",alternate = "purchaserName")
    private String name;
    @SerializedName(value = "validTradeAmount",alternate = "tradeAmount")
    private double amount;

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
