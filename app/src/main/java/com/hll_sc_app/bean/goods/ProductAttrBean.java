package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 商品属性
 *
 * @author zhuyingsong
 * @date 2019-06-14
 */
public class ProductAttrBean implements Parcelable {
    public static final String WIDGET_AREA = "area";
    public static final String WIDGET_COMBOBOX = "combobox";
    public static final String WIDGET_DATE = "date";
    public static final String WIDGET_BRAND = "brand";
    public static final String WIDGET_INPUT = "input";
    public static final Creator<ProductAttrBean> CREATOR = new Creator<ProductAttrBean>() {
        @Override
        public ProductAttrBean createFromParcel(Parcel source) {
            return new ProductAttrBean(source);
        }

        @Override
        public ProductAttrBean[] newArray(int size) {
            return new ProductAttrBean[size];
        }
    };
    private String keyNote;
    private String widget;
    private String attrKey;
    private String tip;
    private String attrValue;
    private String id;
    private String pubAttrValue;
    private String currAttrValue;
    private List<RegexBean> regexs;
    private boolean select;

    public ProductAttrBean() {
    }

    protected ProductAttrBean(Parcel in) {
        this.keyNote = in.readString();
        this.widget = in.readString();
        this.attrKey = in.readString();
        this.tip = in.readString();
        this.attrValue = in.readString();
        this.id = in.readString();
        this.pubAttrValue = in.readString();
        this.currAttrValue = in.readString();
        this.regexs = in.createTypedArrayList(RegexBean.CREATOR);
        this.select = in.readByte() != 0;
    }

    public String getCurrAttrValue() {
        return currAttrValue;
    }

    public void setCurrAttrValue(String currAttrValue) {
        this.currAttrValue = currAttrValue;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getKeyNote() {
        return keyNote;
    }

    public void setKeyNote(String keyNote) {
        this.keyNote = keyNote;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPubAttrValue() {
        return pubAttrValue;
    }

    public void setPubAttrValue(String pubAttrValue) {
        this.pubAttrValue = pubAttrValue;
    }

    public List<RegexBean> getRegexs() {
        return regexs;
    }

    public void setRegexs(List<RegexBean> regexs) {
        this.regexs = regexs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.keyNote);
        dest.writeString(this.widget);
        dest.writeString(this.attrKey);
        dest.writeString(this.tip);
        dest.writeString(this.attrValue);
        dest.writeString(this.id);
        dest.writeString(this.pubAttrValue);
        dest.writeString(this.currAttrValue);
        dest.writeTypedList(this.regexs);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    public static class RegexBean implements Parcelable {
        public static final Parcelable.Creator<RegexBean> CREATOR = new Parcelable.Creator<RegexBean>() {
            @Override
            public RegexBean createFromParcel(Parcel source) {
                return new RegexBean(source);
            }

            @Override
            public RegexBean[] newArray(int size) {
                return new RegexBean[size];
            }
        };
        private String regex;
        private String tip;

        public RegexBean() {
        }

        protected RegexBean(Parcel in) {
            this.regex = in.readString();
            this.tip = in.readString();
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.regex);
            dest.writeString(this.tip);
        }
    }
}


