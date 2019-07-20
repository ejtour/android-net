package com.hll_sc_app.bean.pricemanage;

import java.util.List;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class PriceLogResp {
    private List<PriceLogBean> list;
    private int total;

    public List<PriceLogBean> getList() {
        return list;
    }

    public void setList(List<PriceLogBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
