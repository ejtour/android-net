package com.hll_sc_app.bean.wallet;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/2
 */

public class BankBean implements Parcelable {
    private String bankCode;
    private String bankLogo;
    private String bankName;
    private String bankNo;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankCode);
        dest.writeString(this.bankLogo);
        dest.writeString(this.bankName);
        dest.writeString(this.bankNo);
    }

    public BankBean() {
    }

    protected BankBean(Parcel in) {
        this.bankCode = in.readString();
        this.bankLogo = in.readString();
        this.bankName = in.readString();
        this.bankNo = in.readString();
    }

    public static final Parcelable.Creator<BankBean> CREATOR = new Parcelable.Creator<BankBean>() {
        @Override
        public BankBean createFromParcel(Parcel source) {
            return new BankBean(source);
        }

        @Override
        public BankBean[] newArray(int size) {
            return new BankBean[size];
        }
    };
}
