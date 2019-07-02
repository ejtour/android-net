package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 库存预警值设置
 *
 * @author zhuyingsong
 * @date 2019-07-02
 */
public class GoodsInvWarnReq {
    private String groupID;
    private String houseID;
    private List<ListBean> list;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String productID;
        private double stockWarnNum;

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public double getStockWarnNum() {
            return stockWarnNum;
        }

        public void setStockWarnNum(double stockWarnNum) {
            this.stockWarnNum = stockWarnNum;
        }
    }
}
