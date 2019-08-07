package com.hll_sc_app.bean.warehouse;

import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 代仓列表
 *
 * @author zhuyingsong
 * @date 2019-08-05
 */
public class WarehouseListResp {
    private int totalNum;
    private List<PurchaserBean> groupInfos;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<PurchaserBean> getGroupInfos() {
        return groupInfos;
    }

    public void setGroupInfos(List<PurchaserBean> groupInfos) {
        this.groupInfos = groupInfos;
    }
}
