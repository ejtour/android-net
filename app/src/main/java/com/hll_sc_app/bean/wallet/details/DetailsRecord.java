package com.hll_sc_app.bean.wallet.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */
public class DetailsRecord implements Parcelable {
    /**
     * 账户流水号
     */
    private String accountOrderNo;
    /**
     * 请求时间
     */
    private String accountTime;
    /**
     * 交易类型
     * CORRECT(冲正), RECHARGE(充值), ACCOUNTED(入账),
     * INCREASE(调增), OTHER_INCREASE(其他增加), UNFREEZE_FUND(解冻资金);
     * CONSUMPTION(消费), REFUND(退款), WITHDRAWALS(提现),
     * DECREASE(调减), OTHER_DECREASE(其他调减); FREEZE_FUND(冻结资金)
     */
    private String accountType;
    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 资金变动方向 入金INCREASE 出金DECREASE
     */
    private String direction;
    /**
     * 名称|备注
     */
    private String explains;
    /**
     * 充值单号
     */
    private String payOrderKey;
    /**
     * 付款集团
     */
    private String purchaserName;
    /**
     * 入账金额
     */
    private double settleAccount;
    /**
     * 付款门店
     */
    private String shopName;
    /**
     * 交易流水号
     */
    private String tradeOrderNo;

    /**
     * 交易类型描述
     */
    private String transTypeDesc;

    /**
     * 交易后余额
     */
    private String transAfterBalance;

    /**
     * 交易费用
     */
    private String transSalesCommission;

    public String getTransSalesCommission() {
        return transSalesCommission;
    }

    public void setTransSalesCommission(String transSalesCommission) {
        this.transSalesCommission = transSalesCommission;
    }

    public String getTransAfterBalance() {
        return transAfterBalance;
    }

    public void setTransAfterBalance(String transAfterBalance) {
        this.transAfterBalance = transAfterBalance;
    }

    public String getTransTypeDesc() {
        return transTypeDesc;
    }

    public void setTransTypeDesc(String transTypeDesc) {
        this.transTypeDesc = transTypeDesc;
    }

    /**
     * 获取金额变更
     */
    public String getDelta() {
        return (direction) + CommonUtils.formatMoney(settleAccount);
    }

    public String getAccountOrderNo() {
        return accountOrderNo;
    }

    public void setAccountOrderNo(String accountOrderNo) {
        this.accountOrderNo = accountOrderNo;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getPayOrderKey() {
        return payOrderKey;
    }

    public void setPayOrderKey(String payOrderKey) {
        this.payOrderKey = payOrderKey;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(double settleAccount) {
        this.settleAccount = settleAccount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public DetailsRecord() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountOrderNo);
        dest.writeString(this.accountTime);
        dest.writeString(this.accountType);
        dest.writeString(this.businessNo);
        dest.writeString(this.direction);
        dest.writeString(this.explains);
        dest.writeString(this.payOrderKey);
        dest.writeString(this.purchaserName);
        dest.writeDouble(this.settleAccount);
        dest.writeString(this.shopName);
        dest.writeString(this.tradeOrderNo);
        dest.writeString(this.transTypeDesc);
        dest.writeString(this.transAfterBalance);
        dest.writeString(this.transSalesCommission);
    }

    protected DetailsRecord(Parcel in) {
        this.accountOrderNo = in.readString();
        this.accountTime = in.readString();
        this.accountType = in.readString();
        this.businessNo = in.readString();
        this.direction = in.readString();
        this.explains = in.readString();
        this.payOrderKey = in.readString();
        this.purchaserName = in.readString();
        this.settleAccount = in.readDouble();
        this.shopName = in.readString();
        this.tradeOrderNo = in.readString();
        this.transTypeDesc = in.readString();
        this.transAfterBalance = in.readString();
        this.transSalesCommission = in.readString();
    }

    public static final Creator<DetailsRecord> CREATOR = new Creator<DetailsRecord>() {
        @Override
        public DetailsRecord createFromParcel(Parcel source) {
            return new DetailsRecord(source);
        }

        @Override
        public DetailsRecord[] newArray(int size) {
            return new DetailsRecord[size];
        }
    };
}
