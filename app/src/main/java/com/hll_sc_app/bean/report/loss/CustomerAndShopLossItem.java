package com.hll_sc_app.bean.report.loss;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerAndShopLossItem implements IStringArrayGenerator {

    private int type;

    private int sequenceNo;
    /**
     * 新增流失门店数
     */
    private long   addCustomerLoseNum;
    /**
     * 单均
     */
    private String averageAmount;
    /**
     * 下单门店数
     */
    private long   billShopNums;
    /**
     * 流失门店数
     */
    private long   customerLoseNum;
    /**
     * 客户流失率
     */
    private String customerLoseRate;
    /**
     * 日期
     */
    private String   date;
    /**
     * 最后下单时间
     */
    private long   lastBillTime;
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
    private String salesAmount;
    /**
     * 销售代表
     */
    private String salesManName;
    /**
     * 门店下单数
     */
    private long   shopBillNums;
    /**
     * 门店名称
     */
    private String shopName;

    public long getAddCustomerLoseNum() {
        return addCustomerLoseNum;
    }

    public void setAddCustomerLoseNum(long addCustomerLoseNum) {
        this.addCustomerLoseNum = addCustomerLoseNum;
    }

    public String getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(String averageAmount) {
        this.averageAmount = averageAmount;
    }

    public long getBillShopNums() {
        return billShopNums;
    }

    public void setBillShopNums(long billShopNums) {
        this.billShopNums = billShopNums;
    }

    public long getCustomerLoseNum() {
        return customerLoseNum;
    }

    public void setCustomerLoseNum(long customerLoseNum) {
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

    public long getLastBillTime() {
        return lastBillTime;
    }

    public void setLastBillTime(long lastBillTime) {
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

    public String getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getSalesManName() {
        return salesManName;
    }

    public void setSalesManName(String salesManName) {
        this.salesManName = salesManName;
    }

    public long getShopBillNums() {
        return shopBillNums;
    }

    public void setShopBillNums(long shopBillNums) {
        this.shopBillNums = shopBillNums;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        if(getType()==0) {
            if (StringUtil.isEmpty(getDate())) {
                list.add("");
            } else {
                list.add(CalendarUtils.getDateFormatString(
                        getDate(), CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD));// 日期
            }
            list.add(getBillShopNums() + ""); // 下单门店数
            list.add(getCustomerLoseNum() + ""); // 流失门店数
            list.add(getAddCustomerLoseNum() + ""); //新增流失门店数
            list.add(getCustomerLoseRate()); // 流失率
        }else{
            list.add(getSequenceNo()+"");//序号
            list.add(getPurchaserName());// 采购商集团
            list.add(getShopName()); // 采购商门店
            list.add(getLinkMan()); // 联系人
            list.add(getLinkPhone()); //联系方式
            list.add(getSalesManName()); // 销售代表
            list.add(CalendarUtils.getDateFormatString(getLastBillTime()+"","yyyyMMddHHmmss",Constants.SLASH_YYYY_MM_DD)); // 最后下单日期
            list.add(getShopBillNums()+"");//门店下单量
            list.add(CommonUtils.formatMoney(Double.parseDouble(getSalesAmount())));//销售总额
            list.add(CommonUtils.formatMoney(Double.parseDouble(getAverageAmount())));//单均
        }
        return list;
    }
}
