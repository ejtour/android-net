package com.hll_sc_app.bean.report.produce;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public class ProduceTotal {
    private int standardSortNum;
    private double standardSortHours;
    private int vegetablesSortNum;
    private double vegetablesSortHours;
    private int vegetablesPackNum;
    private double vegetablesPackHours;
    private double totalCost;
    private String weightEfficiency;
    private String packageEfficiency;
    private String amountEfficiency;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计"); // 日期
        list.add("- -"); // 操作
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
