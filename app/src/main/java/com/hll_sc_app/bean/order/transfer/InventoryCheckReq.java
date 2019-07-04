package com.hll_sc_app.bean.order.transfer;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class InventoryCheckReq {
    private List<InventoryCheckBean> detailList;

    public InventoryCheckReq(List<InventoryCheckBean> detailList) {
        this.detailList = detailList;
    }

    public List<InventoryCheckBean> getDetailList() {
        return detailList;
    }

    public static class InventoryCheckBean{
        private int flag;
        private double goodsNum;
        private String id;

        public InventoryCheckBean(int flag, double goodsNum, String id) {
            this.flag = flag;
            this.goodsNum = goodsNum;
            this.id = id;
        }

        public int getFlag() {
            return flag;
        }

        public double getGoodsNum() {
            return goodsNum;
        }

        public String getId() {
            return id;
        }
    }
}
