package com.hll_sc_app.bean.wallet;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public class WalletStatusResp implements Parcelable {
    /**
     * 未开通 1
     * 开通审核中 2
     * 开通审核成功 3 目前来说 3不会出现 直接为5 签约是自动开启的
     * 开通审核失败 4
     * 签约认证成功 5
     */
    public final static int STATUS_NOT_OPEN = 1;
    public final static int STATUS_VERIFYING = 2;
    public final static int STATUS_VERIFY_SUCCESS = 3;
    public final static int STATUS_VERIFY_FAIL = 4;
    public final static int STATUS_AUTH_SUCCESS = 5;

    /**
     * 认证状态
     * 未认证 1
     * 认证中 2
     * 认证成功 3
     * 认证失败 4
     */
    public final static int CERTIFY_NOT = 1;
    public final static int CERTIFY_ING = 2;
    public final static int CERTIFY_SUCCESS = 3;
    public final static int CERTIFY_FAIL = 4;

    private float balance;
    private int certifyStatus;
    private float withdrawalAmount;
    private String settleUnitID;
    private String settleUnitName;
    private float frozenAmount;
    private String merchantNo;
    private int status;

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getCertifyStatus() {
        return certifyStatus;
    }

    public void setCertifyStatus(int certifyStatus) {
        this.certifyStatus = certifyStatus;
    }

    public float getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(float withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getSettleUnitID() {
        return settleUnitID;
    }

    public void setSettleUnitID(String settleUnitID) {
        this.settleUnitID = settleUnitID;
    }

    public String getSettleUnitName() {
        return settleUnitName;
    }

    public void setSettleUnitName(String settleUnitName) {
        this.settleUnitName = settleUnitName;
    }

    public float getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(float frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.balance);
        dest.writeInt(this.certifyStatus);
        dest.writeFloat(this.withdrawalAmount);
        dest.writeString(this.settleUnitID);
        dest.writeString(this.settleUnitName);
        dest.writeFloat(this.frozenAmount);
        dest.writeString(this.merchantNo);
        dest.writeInt(this.status);
    }

    public WalletStatusResp() {
    }

    protected WalletStatusResp(Parcel in) {
        this.balance = in.readFloat();
        this.certifyStatus = in.readInt();
        this.withdrawalAmount = in.readFloat();
        this.settleUnitID = in.readString();
        this.settleUnitName = in.readString();
        this.frozenAmount = in.readFloat();
        this.merchantNo = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<WalletStatusResp> CREATOR = new Parcelable.Creator<WalletStatusResp>() {
        @Override
        public WalletStatusResp createFromParcel(Parcel source) {
            return new WalletStatusResp(source);
        }

        @Override
        public WalletStatusResp[] newArray(int size) {
            return new WalletStatusResp[size];
        }
    };
}
