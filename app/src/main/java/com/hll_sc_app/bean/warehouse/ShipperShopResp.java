package com.hll_sc_app.bean.warehouse;

import com.hll_sc_app.bean.priceratio.PageInfoBean;

import java.util.List;

/**
 * 代仓门店管理
 *
 * @author zhuyingsong
 * @date 2019-08-07
 */
public class ShipperShopResp {
    private PageInfoBean pageInfo;
    private List<ShopBean> purchaserList;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<ShopBean> getPurchaserList() {
        return purchaserList;
    }

    public void setPurchaserList(List<ShopBean> purchaserList) {
        this.purchaserList = purchaserList;
    }

    public static class ShopBean {
        private String purchaserID;
        private String shopNum;
        private String purchaserLogo;
        private String purchaserName;

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getShopNum() {
            return shopNum;
        }

        public void setShopNum(String shopNum) {
            this.shopNum = shopNum;
        }

        public String getPurchaserLogo() {
            return purchaserLogo;
        }

        public void setPurchaserLogo(String purchaserLogo) {
            this.purchaserLogo = purchaserLogo;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }
    }
}
