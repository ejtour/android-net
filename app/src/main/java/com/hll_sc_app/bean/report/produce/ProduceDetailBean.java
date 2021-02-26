package com.hll_sc_app.bean.report.produce;

import android.app.Activity;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.hll_sc_app.app.report.produce.input.ProduceInputActivity;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceDetailBean extends ProduceBean implements Parcelable {
    private String classes;
    private String inputPer;
    private String coopGroupName;
    private double hoursFee;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(createText()); // 操作
        list.add(classes); // 班次
        list.add(String.valueOf(standardSortNum)); // 标品分拣人数
        list.add(CommonUtils.formatNumber(standardSortHours)); // 标品分拣工时
        list.add(String.valueOf(vegetablesSortNum)); // 蔬果分拣人数
        list.add(CommonUtils.formatNumber(vegetablesSortHours)); // 蔬果分拣工时
        list.add(String.valueOf(vegetablesPackNum)); // 蔬果打包人数
        list.add(CommonUtils.formatNumber(vegetablesPackHours)); // 蔬果打包工时
        list.add(CommonUtils.formatMoney(CommonUtils.getDouble(totalCost))); // 费用合计(元)
        return list;
    }

    private SpannableString createText() {
        SpannableString ss = new SpannableString("修改");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (widget.getTag() instanceof ProduceDetailBean) {
                    ProduceDetailBean bean = (ProduceDetailBean) widget.getTag();
                    ProduceInputActivity.start(((Activity) widget.getContext()), bean.classes, bean.getDate());
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2));
                ds.setUnderlineText(false);
            }
        }, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

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

    public double getHoursFee() {
        return hoursFee;
    }

    public void setHoursFee(double hoursFee) {
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
        dest.writeDouble(this.hoursFee);
        dest.writeString(this.date);
        dest.writeString(this.standardSortNum);
        dest.writeString(this.standardSortHours);
        dest.writeString(this.vegetablesSortNum);
        dest.writeString(this.vegetablesSortHours);
        dest.writeString(this.vegetablesPackNum);
        dest.writeString(this.vegetablesPackHours);
        dest.writeString(this.totalCost);
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
        this.hoursFee = in.readDouble();
        this.date = in.readString();
        this.standardSortNum = in.readString();
        this.standardSortHours = in.readString();
        this.vegetablesSortNum = in.readString();
        this.vegetablesSortHours = in.readString();
        this.vegetablesPackNum = in.readString();
        this.vegetablesPackHours = in.readString();
        this.totalCost = in.readString();
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

    public void generateTotalCost() {
        this.totalCost = CommonUtils.addDouble(
                CommonUtils.mulDouble(hoursFee,
                        CommonUtils.mulDouble(CommonUtils.getInt(standardSortNum), CommonUtils.getDouble(standardSortHours)).doubleValue(),
                        4).doubleValue(),
                CommonUtils.mulDouble(hoursFee,
                        CommonUtils.mulDouble(CommonUtils.getInt(vegetablesSortNum), CommonUtils.getDouble(vegetablesSortHours)).doubleValue(),
                        4).doubleValue(),
                CommonUtils.mulDouble(hoursFee,
                        CommonUtils.mulDouble(CommonUtils.getInt(vegetablesPackNum), CommonUtils.getDouble(vegetablesPackHours)).doubleValue(),
                        4).doubleValue())
                .toPlainString();
    }
}
