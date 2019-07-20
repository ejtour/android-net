package com.hll_sc_app.bean.cooperation;

import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 查询合作餐企
 *
 * @author zhuyingsong
 * @date 2019-07-17
 */
public class CooperationPurchaserResp {
    private List<PurchaserBean> records;
    /**
     * 集团数量
     */
    private int groupTotal;
    /**
     * 新增店铺数量
     */
    private int newShopNum;
    /**
     * 店铺数量
     */
    private int shopTotal;

    public List<PurchaserBean> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaserBean> records) {
        this.records = records;
    }

    public int getGroupTotal() {
        return groupTotal;
    }

    public void setGroupTotal(int groupTotal) {
        this.groupTotal = groupTotal;
    }

    public int getNewShopNum() {
        return newShopNum;
    }

    public void setNewShopNum(int newShopNum) {
        this.newShopNum = newShopNum;
    }

    public int getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(int shopTotal) {
        this.shopTotal = shopTotal;
    }
}
