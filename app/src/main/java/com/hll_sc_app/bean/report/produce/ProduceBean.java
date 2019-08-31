package com.hll_sc_app.bean.report.produce;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
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

public class ProduceBean {
    protected String date;
    protected int standardSortNum;
    protected double standardSortHours;
    protected int vegetablesSortNum;
    protected double vegetablesSortHours;
    protected int vegetablesPackNum;
    protected double vegetablesPackHours;
    protected double totalCost;
    protected String weightEfficiency;
    protected String packageEfficiency;
    protected String amountEfficiency;

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
                ToastUtils.showShort(widget.getContext(), "position = " + widget.getTag() + " 明细跳转待添加");
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
                ToastUtils.showShort(widget.getContext(), "position = " + widget.getTag() + " 录入跳转待添加");
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
}
