package com.hll_sc_app.bean.report.customreceivequery;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应链单据详情查询
 */
public class CustomReceiveDetailBean implements IStringArrayGenerator {

    private int index;
    private String detailID;
    private String voucherID;
    private String groupID;
    private String voucherNo;
    private String voucherDate;
    private int voucherType;
    private int voucherStatus;
    private String auditBy;
    private String auditTime;
    private String voucherRemark;
    private String houseID;
    private String houseName;
    private String supplierID;
    private String supplierName;
    private String createBy;
    private String createTime;
    private String actionBy;
    private String goodsCategoryID;
    private String goodsCategoryName;
    private String goodsCategoryCode;
    private String goodsID;
    private String goodsName;
    private String goodsCode;
    private String standardUnit;
    private String assistUnit;
    private String goodsDesc;
    private double goodsNum;
    private double taxPrice;
    private double taxAmount;
    private double pretaxPrice;
    private double pretaxAmount;
    private String auxiliaryNum;
    private String batchNumber;
    private String productionDate;
    private String detailRemark;
    private String demandID;
    private double rateValue;
    private String goodsMnemonicCode;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(index)); // 序号
        list.add("- -"); // 质检结果
        list.add(getString(goodsCode)); // 品项编码
        list.add(getString(goodsName)); // 品项名称
        list.add(getString(goodsDesc)); // 规格
        list.add(getString(standardUnit)); // 单位
        list.add(CommonUtils.formatNumber(goodsNum)); // 数量
        list.add(CommonUtils.formatMoney(taxPrice)); // 单价
        list.add(CommonUtils.formatMoney(taxAmount)); // 金额
        list.add(-2 == rateValue ? "- -" : Utils.numToPercent(rateValue)); // 税率
        list.add(CommonUtils.formatMoney(pretaxPrice)); // 不含税单价
        list.add(CommonUtils.formatMoney(pretaxAmount)); // 不含税金额
        list.add(getString(assistUnit)); // 辅助单位
        list.add(CommonUtils.formatNumber(auxiliaryNum)); // 辅助数量
        list.add(getString(DateUtil.getReadableTime(productionDate, Constants.SLASH_YYYY_MM_DD))); // 辅助数量
        list.add(getString(batchNumber)); // 批次号
        list.add(getString(detailRemark)); // 备注
        return list;
    }

    private String getString(String label) {
        return TextUtils.isEmpty(label) ? "- -" : label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public int getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(int voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getVoucherRemark() {
        return voucherRemark;
    }

    public void setVoucherRemark(String voucherRemark) {
        this.voucherRemark = voucherRemark;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getGoodsCategoryID() {
        return goodsCategoryID;
    }

    public void setGoodsCategoryID(String goodsCategoryID) {
        this.goodsCategoryID = goodsCategoryID;
    }

    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public String getGoodsCategoryCode() {
        return goodsCategoryCode;
    }

    public void setGoodsCategoryCode(String goodsCategoryCode) {
        this.goodsCategoryCode = goodsCategoryCode;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(String standardUnit) {
        this.standardUnit = standardUnit;
    }

    public String getAssistUnit() {
        return assistUnit;
    }

    public void setAssistUnit(String assistUnit) {
        this.assistUnit = assistUnit;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getPretaxPrice() {
        return pretaxPrice;
    }

    public void setPretaxPrice(double pretaxPrice) {
        this.pretaxPrice = pretaxPrice;
    }

    public double getPretaxAmount() {
        return pretaxAmount;
    }

    public void setPretaxAmount(double pretaxAmount) {
        this.pretaxAmount = pretaxAmount;
    }

    public String getAuxiliaryNum() {
        return auxiliaryNum;
    }

    public void setAuxiliaryNum(String auxiliaryNum) {
        this.auxiliaryNum = auxiliaryNum;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getDemandID() {
        return demandID;
    }

    public void setDemandID(String demandID) {
        this.demandID = demandID;
    }

    public double getRateValue() {
        return rateValue;
    }

    public void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }

    public String getGoodsMnemonicCode() {
        return goodsMnemonicCode;
    }

    public void setGoodsMnemonicCode(String goodsMnemonicCode) {
        this.goodsMnemonicCode = goodsMnemonicCode;
    }
}
