package com.hll_sc_app.bean.report.produce;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceDetailBean extends ProduceBean implements Parcelable {
    private String classes;
    private String inputPer;
    private String coopGroupName;
    private String hoursFee;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getInputPer() {
        return inputPer;
    }

    public void setInputPer(String inputPer) {
        this.inputPer = inputPer;
    }

    public String getCoopGroupName() {
        return coopGroupName;
    }

    public void setCoopGroupName(String coopGroupName) {
        this.coopGroupName = coopGroupName;
    }

    public String getHoursFee() {
        return hoursFee;
    }

    public void setHoursFee(String hoursFee) {
        this.hoursFee = hoursFee;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.classes);
        dest.writeString(this.inputPer);
        dest.writeString(this.coopGroupName);
        dest.writeString(this.hoursFee);
        dest.writeString(this.date);
        dest.writeInt(this.standardSortNum);
        dest.writeDouble(this.standardSortHours);
        dest.writeInt(this.vegetablesSortNum);
        dest.writeDouble(this.vegetablesSortHours);
        dest.writeInt(this.vegetablesPackNum);
        dest.writeDouble(this.vegetablesPackHours);
        dest.writeDouble(this.totalCost);
        dest.writeString(this.weightEfficiency);
        dest.writeString(this.packageEfficiency);
        dest.writeString(this.amountEfficiency);
    }

    public ProduceDetailBean() {
    }

    protected ProduceDetailBean(Parcel in) {
        this.classes = in.readString();
        this.inputPer = in.readString();
        this.coopGroupName = in.readString();
        this.hoursFee = in.readString();
        this.date = in.readString();
        this.standardSortNum = in.readInt();
        this.standardSortHours = in.readDouble();
        this.vegetablesSortNum = in.readInt();
        this.vegetablesSortHours = in.readDouble();
        this.vegetablesPackNum = in.readInt();
        this.vegetablesPackHours = in.readDouble();
        this.totalCost = in.readDouble();
        this.weightEfficiency = in.readString();
        this.packageEfficiency = in.readString();
        this.amountEfficiency = in.readString();
    }

    public static final Parcelable.Creator<ProduceDetailBean> CREATOR = new Parcelable.Creator<ProduceDetailBean>() {
        @Override
        public ProduceDetailBean createFromParcel(Parcel source) {
            return new ProduceDetailBean(source);
        }

        @Override
        public ProduceDetailBean[] newArray(int size) {
            return new ProduceDetailBean[size];
        }
    };
}
