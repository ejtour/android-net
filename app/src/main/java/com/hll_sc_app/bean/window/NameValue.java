package com.hll_sc_app.bean.window;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class NameValue implements Parcelable {
    public static final Creator<NameValue> CREATOR = new Creator<NameValue>() {
        @Override
        public NameValue createFromParcel(Parcel in) {
            return new NameValue(in);
        }

        @Override
        public NameValue[] newArray(int size) {
            return new NameValue[size];
        }
    };
    private String name;
    private String value;

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    protected NameValue(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
