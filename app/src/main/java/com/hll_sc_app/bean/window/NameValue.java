package com.hll_sc_app.bean.window;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;

import java.util.Arrays;

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
    private transient boolean enable;

    public NameValue(String name, String value) {
        this(name, value, true);
    }

    public NameValue(String name, String value, boolean enable) {
        this.name = name;
        this.value = value;
        this.enable = enable;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameValue nameValue = (NameValue) o;
        return UIUtils.equals(name, nameValue.name)
                && UIUtils.equals(value, nameValue.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{name, value});
    }
}
