package com.hll_sc_app.bean.report.salesman;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务员签约绩效
 */
public class SalesManSignAchievement implements IStringArrayGenerator {

    private String salesmanCode;
    private String salesmanName;
    private Long salesmanID;
    private int addIntentCustomerNum;
    private int addSignCustomerNum;
    private int addSignShopNum;
    private int intentCustomerNum;
    private int signCustomerNum;
    private int signShopNum;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(salesmanCode); // 编码
        list.add(salesmanName); // 姓名
        list.add(CommonUtils.formatNumber(intentCustomerNum)); // 意向客户
        list.add(CommonUtils.formatNumber(signCustomerNum)); // 签约客户
        list.add(CommonUtils.formatNumber(signShopNum)); // 签约门店
        list.add(CommonUtils.formatNumber(addIntentCustomerNum)); // 新增意向客户
        list.add(CommonUtils.formatNumber(addSignCustomerNum)); // 新增签约客户
        list.add(CommonUtils.formatNumber(addSignShopNum)); // 新增签约门店
        return list;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Long getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(Long salesmanID) {
        this.salesmanID = salesmanID;
    }

    public int getAddIntentCustomerNum() {
        return addIntentCustomerNum;
    }

    public void setAddIntentCustomerNum(int addIntentCustomerNum) {
        this.addIntentCustomerNum = addIntentCustomerNum;
    }

    public int getAddSignCustomerNum() {
        return addSignCustomerNum;
    }

    public void setAddSignCustomerNum(int addSignCustomerNum) {
        this.addSignCustomerNum = addSignCustomerNum;
    }

    public int getAddSignShopNum() {
        return addSignShopNum;
    }

    public void setAddSignShopNum(int addSignShopNum) {
        this.addSignShopNum = addSignShopNum;
    }

    public int getIntentCustomerNum() {
        return intentCustomerNum;
    }

    public void setIntentCustomerNum(int intentCustomerNum) {
        this.intentCustomerNum = intentCustomerNum;
    }

    public int getSignCustomerNum() {
        return signCustomerNum;
    }

    public void setSignCustomerNum(int signCustomerNum) {
        this.signCustomerNum = signCustomerNum;
    }

    public int getSignShopNum() {
        return signShopNum;
    }

    public void setSignShopNum(int signShopNum) {
        this.signShopNum = signShopNum;
    }
}
