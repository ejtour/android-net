package com.hll_sc_app.bean.aftersales;

import com.hll_sc_app.bean.common.PurchaserBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserListResp {
    private List<PurchaserBean> list;

    public List<PurchaserBean> getList() {
        return list;
    }

    public void setList(List<PurchaserBean> list) {
        this.list = list;
    }
}
