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
    String standardSortNum;
    String standardSortHours;
    String vegetablesSortNum;
    String vegetablesSortHours;
    String vegetablesPackNum;
    String vegetablesPackHours;
    String totalCost;
    String weightEfficiency;
    String packageEfficiency;
    String amountEfficiency;
    private int deliveryPackageQty;
    private double orderQtyPackageWeight;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(createText()); // 操作
        list.add(standardSortNum); // 标品分拣人数
        list.add(CommonUtils.formatNumber(standardSortHours)); // 标品分拣工时
        list.add(vegetablesSortNum); // 蔬果分拣人数
        list.add(CommonUtils.formatNumber(vegetablesSortHours)); // 蔬果分拣工时
        list.add(vegetablesPackNum); // 蔬果打包人数
        list.add(CommonUtils.formatNumber(vegetablesPackHours)); // 蔬果打包工时
        list.add(CommonUtils.formatMoney(CommonUtils.getDouble(totalCost))); //生产费用(元)
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
                    ProduceDetailsActivity.start((Activity) widget.getContext(), ((ProduceBean) widget.getTag()).getDate());
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
                    PeopleEffectInputActivity.start((Activity) widget.getContext(), (ProduceBean) widget.getTag());
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

    public String getStandardSortNum() {
        return standardSortNum;
    }

    public void setStandardSortNum(String standardSortNum) {
        this.standardSortNum = standardSortNum;
    }

    public String getStandardSortHours() {
        return standardSortHours;
    }

    public void setStandardSortHours(String standardSortHours) {
        this.standardSortHours = standardSortHours;
    }

    public String getVegetablesSortNum() {
        return vegetablesSortNum;
    }

    public void setVegetablesSortNum(String vegetablesSortNum) {
        this.vegetablesSortNum = vegetablesSortNum;
    }

    public String getVegetablesSortHours() {
        return vegetablesSortHours;
    }

    public void setVegetablesSortHours(String vegetablesSortHours) {
        this.vegetablesSortHours = vegetablesSortHours;
    }

    public String getVegetablesPackNum() {
        return vegetablesPackNum;
    }

    public void setVegetablesPackNum(String vegetablesPackNum) {
        this.vegetablesPackNum = vegetablesPackNum;
    }

    public String getVegetablesPackHours() {
        return vegetablesPackHours;
    }

    public void setVegetablesPackHours(String vegetablesPackHours) {
        this.vegetablesPackHours = vegetablesPackHours;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
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

    public int getDeliveryPackageQty() {
        return deliveryPackageQty;
    }

    public void setDeliveryPackageQty(int deliveryPackageQty) {
        this.deliveryPackageQty = deliveryPackageQty;
    }

    public double getOrderQtyPackageWeight() {
        return orderQtyPackageWeight;
    }

    public void setOrderQtyPackageWeight(double orderQtyPackageWeight) {
        this.orderQtyPackageWeight = orderQtyPackageWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeInt(this.deliveryPackageQty);
        dest.writeDouble(this.orderQtyPackageWeight);
    }

    public ProduceBean() {
    }

    protected ProduceBean(Parcel in) {
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
        this.deliveryPackageQty = in.readInt();
        this.orderQtyPackageWeight = in.readDouble();
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
