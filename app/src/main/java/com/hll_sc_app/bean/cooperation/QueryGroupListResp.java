package com.hll_sc_app.bean.cooperation;

import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 查询集团列表
 */
public class QueryGroupListResp {
    private List<PurchaserBean> groupList;
    private PageInfo pageInfo;
    private SummaryVo summaryVo;

    public List<PurchaserBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<PurchaserBean> groupList) {
        this.groupList = groupList;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public SummaryVo getSummaryVo() {
        return summaryVo;
    }

    public void setSummaryVo(SummaryVo summaryVo) {
        this.summaryVo = summaryVo;
    }

    public static class PageInfo {
        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class SummaryVo {
        private int applyShopTotal;
        private int groupTotal;
        private int newShopTotal;
        private int shopTotal;

        public int getApplyShopTotal() {
            return applyShopTotal;
        }

        public void setApplyShopTotal(int applyShopTotal) {
            this.applyShopTotal = applyShopTotal;
        }

        public int getGroupTotal() {
            return groupTotal;
        }

        public void setGroupTotal(int groupTotal) {
            this.groupTotal = groupTotal;
        }

        public int getNewShopTotal() {
            return newShopTotal;
        }

        public void setNewShopTotal(int newShopTotal) {
            this.newShopTotal = newShopTotal;
        }

        public int getShopTotal() {
            return shopTotal;
        }

        public void setShopTotal(int shopTotal) {
            this.shopTotal = shopTotal;
        }
    }

}
