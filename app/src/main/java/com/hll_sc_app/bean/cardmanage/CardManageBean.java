package com.hll_sc_app.bean.cardmanage;

import android.os.Parcel;
import android.os.Parcelable;

public class CardManageBean implements Parcelable {
    private double balance;
    private double cashBalance;
    private double frozenAmount;
    private double giftBalance;
    private String cardNo;
    //卡状态 1-启用 2-冻结 3-注销
    private int cardStatus;
    private int cardType;
    //卡消费模式 1-先消费现金卡值、2-先消费赠送卡值、3-现金卡值和赠送卡值同时使用（按比例）
    private int consumptionModel;
    private String groupID;
    private String groupImgUrl;
    private String groupName;
    private String id;
    private String openCardBy;
    private String openCardTime;
    private String purchaserID;
    private String purchaserImgUrl;
    private String purchaserName;


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public double getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(double frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public double getGiftBalance() {
        return giftBalance;
    }

    public void setGiftBalance(double giftBalance) {
        this.giftBalance = giftBalance;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getConsumptionModel() {
        return consumptionModel;
    }

    public void setConsumptionModel(int consumptionModel) {
        this.consumptionModel = consumptionModel;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupImgUrl() {
        return groupImgUrl;
    }

    public void setGroupImgUrl(String groupImgUrl) {
        this.groupImgUrl = groupImgUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenCardBy() {
        return openCardBy;
    }

    public void setOpenCardBy(String openCardBy) {
        this.openCardBy = openCardBy;
    }

    public String getOpenCardTime() {
        return openCardTime;
    }

    public void setOpenCardTime(String openCardTime) {
        this.openCardTime = openCardTime;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserImgUrl() {
        return purchaserImgUrl;
    }

    public void setPurchaserImgUrl(String purchaserImgUrl) {
        this.purchaserImgUrl = purchaserImgUrl;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.balance);
        dest.writeDouble(this.cashBalance);
        dest.writeDouble(this.frozenAmount);
        dest.writeDouble(this.giftBalance);
        dest.writeString(this.cardNo);
        dest.writeInt(this.cardStatus);
        dest.writeInt(this.cardType);
        dest.writeInt(this.consumptionModel);
        dest.writeString(this.groupID);
        dest.writeString(this.groupImgUrl);
        dest.writeString(this.groupName);
        dest.writeString(this.id);
        dest.writeString(this.openCardBy);
        dest.writeString(this.openCardTime);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserImgUrl);
        dest.writeString(this.purchaserName);
    }

    public CardManageBean() {
    }

    protected CardManageBean(Parcel in) {
        this.balance = in.readDouble();
        this.cashBalance = in.readDouble();
        this.frozenAmount = in.readDouble();
        this.giftBalance = in.readDouble();
        this.cardNo = in.readString();
        this.cardStatus = in.readInt();
        this.cardType = in.readInt();
        this.consumptionModel = in.readInt();
        this.groupID = in.readString();
        this.groupImgUrl = in.readString();
        this.groupName = in.readString();
        this.id = in.readString();
        this.openCardBy = in.readString();
        this.openCardTime = in.readString();
        this.purchaserID = in.readString();
        this.purchaserImgUrl = in.readString();
        this.purchaserName = in.readString();
    }

    public static final Creator<CardManageBean> CREATOR = new Creator<CardManageBean>() {
        @Override
        public CardManageBean createFromParcel(Parcel source) {
            return new CardManageBean(source);
        }

        @Override
        public CardManageBean[] newArray(int size) {
            return new CardManageBean[size];
        }
    };
}
