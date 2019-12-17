package com.hll_sc_app.bean.report.loss;

import android.text.TextUtils;

import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class LossBean implements IStringArrayGenerator {

    private String sequenceNo;
    /**
     * 新增流失门店数
     */
    private int addCustomerLoseNum;
    /**
     * 单均
     */
    private double averageAmount;
    /**
     * 下单门店数
     */
    private int billShopNums;
    /**
     * 流失门店数
     */
    private int customerLoseNum;
    /**
     * 客户流失率
     */
    private String customerLoseRate;
    /**
     * 日期
     */
    private String date;
    /**
     * 最后下单时间
     */
    private String lastBillTime;
    /**
     * 联系人
     */
    private String linkMan;
    /**
     * 联系方式
     */
    private String linkPhone;
    /**
     * 采购商集团名称
     */
    private String purchaserName;
    /**
     * 销售金额
     */
    private double salesAmount;
    /**
     * 销售代表
     */
    private String salesManName;
    /**
     * 门店下单数
     */
    private int shopBillNums;
    /**
     * 门店名称
     */
    private String shopName;

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getAddCustomerLoseNum() {
        return addCustomerLoseNum;
    }

    public void setAddCustomerLoseNum(int addCustomerLoseNum) {
        this.addCustomerLoseNum = addCustomerLoseNum;
    }

    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    public int getBillShopNums() {
        return billShopNums;
    }

    public void setBillShopNums(int billShopNums) {
        this.billShopNums = billShopNums;
    }

    public int getCustomerLoseNum() {
        return customerLoseNum;
    }

    public void setCustomerLoseNum(int customerLoseNum) {
        this.customerLoseNum = customerLoseNum;
    }

    public String getCustomerLoseRate() {
        return customerLoseRate;
    }

    public void setCustomerLoseRate(String customerLoseRate) {
        this.customerLoseRate = customerLoseRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastBillTime() {
        return lastBillTime;
    }

    public void setLastBillTime(String lastBillTime) {
        this.lastBillTime = lastBillTime;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getSalesManName() {
        return salesManName;
    }

    public void setSalesManName(String salesManName) {
        this.salesManName = salesManName;
    }

    public int getShopBillNums() {
        return shopBillNums;
    }

    public void setShopBillNums(int shopBillNums) {
        this.shopBillNums = shopBillNums;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        if (TextUtils.isEmpty(sequenceNo)) {
            list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD));
            list.add(CommonUtils.formatNumber(billShopNums)); // 下单门店数
            list.add(CommonUtils.formatNumber(customerLoseNum)); // 流失门店数
            list.add(CommonUtils.formatNumber(addCustomerLoseNum)); //新增流失门店数
            list.add(customerLoseRate); // 流失率
        } else {
            list.add(sequenceNo);//序号
            list.add(purchaserName);// 采购商集团
            list.add(shopName); // 采购商门店
            list.add(TextUtils.isEmpty(linkMan) ? "- -" : linkMan); // 联系人
            String phone = PhoneUtil.formatPhoneNum(linkPhone);
            list.add(TextUtils.isEmpty(phone) ? "- -" : phone); // 联系方式
            list.add(TextUtils.isEmpty(salesManName) ? "- -" : salesManName); // 销售代表
            list.add(DateUtil.getReadableTime(lastBillTime, Constants.SLASH_YYYY_MM_DD)); // 最后下单日期
            list.add(CommonUtils.formatNumber(shopBillNums)); // 门店下单量
            list.add(CommonUtils.formatMoney(salesAmount)); // 销售总额
            list.add(CommonUtils.formatMoney(averageAmount)); // 单均
        }
        return list;
    }
}
