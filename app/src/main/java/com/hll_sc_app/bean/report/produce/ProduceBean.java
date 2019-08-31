package com.hll_sc_app.bean.report.produce;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.hll_sc_app.app.report.produce.details.ProduceDetailsActivity;
import com.hll_sc_app.app.report.produce.input.maneffect.PeopleEffectInputActivity;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public class ProduceBean implements IStringArrayGenerator, Parcelable {
    String date;
    int standardSortNum;
    double standardSortHours;
    int vegetablesSortNum;
    double vegetablesSortHours;
    int vegetablesPackNum;
    double vegetablesPackHours;
    double totalCost;
    String weightEfficiency;
    String packageEfficiency;
    String amountEfficiency;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(createText()); // 操作
        list.add(String.valueOf(standardSortNum)); // 标品分拣人数
        list.add(CommonUtils.formatNumber(standardSortHours)); // 标品分拣工时
        list.add(String.valueOf(vegetablesSortNum)); // 蔬果分拣人数
        list.add(CommonUtils.formatNumber(vegetablesSortHours)); // 蔬果分拣工时
        list.add(String.valueOf(vegetablesPackNum)); // 蔬果打包人数
        list.add(CommonUtils.formatNumber(vegetablesPackHours)); // 蔬果打包工时
        list.add(CommonUtils.formatMoney(totalCost)); //生产费用(元)
        list.add(weightEfficiency); // 称重人效
        list.add(packageEfficiency); // 包裹人效
        list.add(amountEfficiency); // 金额人效
        return list;
    }

    private SpannableString createText() {
        SpannableString ss = new SpannableString("明细  录入人效");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (widget.getTag() instanceof ProduceBean) {
                    ProduceDetailsActivity.start(((ProduceBean) widget.getTag()).getDate());
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2));
                ds.setUnderlineText(false);
            }
        }, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (widget.getTag() instanceof ProduceBean)
                    PeopleEffectInputActivity.start((ProduceBean) widget.getTag());
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2));
                ds.setUnderlineText(false);
            }
        }, 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStandardSortNum() {
        return standardSortNum;
    }

    public void setStandardSortNum(int standardSortNum) {
        this.standardSortNum = standardSortNum;
    }

    public double getStandardSortHours() {
        return standardSortHours;
    }

    public void setStandardSortHours(double standardSortHours) {
        this.standardSortHours = standardSortHours;
    }

    public int getVegetablesSortNum() {
        return vegetablesSortNum;
    }

    public void setVegetablesSortNum(int vegetablesSortNum) {
        this.vegetablesSortNum = vegetablesSortNum;
    }

    public double getVegetablesSortHours() {
        return vegetablesSortHours;
    }

    public void setVegetablesSortHours(double vegetablesSortHours) {
        this.vegetablesSortHours = vegetablesSortHours;
    }

    public int getVegetablesPackNum() {
        return vegetablesPackNum;
    }

    public void setVegetablesPackNum(int vegetablesPackNum) {
        this.vegetablesPackNum = vegetablesPackNum;
    }

    public double getVegetablesPackHours() {
        return vegetablesPackHours;
    }

    public void setVegetablesPackHours(double vegetablesPackHours) {
        this.vegetablesPackHours = vegetablesPackHours;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getWeightEfficiency() {
        return weightEfficiency;
    }

    public void setWeightEfficiency(String weightEfficiency) {
        this.weightEfficiency = weightEfficiency;
    }

    public String getPackageEfficiency() {
        return packageEfficiency;
    }

    public void setPackageEfficiency(String packageEfficiency) {
        this.packageEfficiency = packageEfficiency;
    }

    public String getAmountEfficiency() {
        return amountEfficiency;
    }

    public void setAmountEfficiency(String amountEfficiency) {
        this.amountEfficiency = amountEfficiency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    public ProduceBean() {
    }

    protected ProduceBean(Parcel in) {
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

    public static final Parcelable.Creator<ProduceBean> CREATOR = new Parcelable.Creator<ProduceBean>() {
        @Override
        public ProduceBean createFromParcel(Parcel source) {
            return new ProduceBean(source);
        }

        @Override
        public ProduceBean[] newArray(int size) {
            return new ProduceBean[size];
        }
    };
}
