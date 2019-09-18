package com.hll_sc_app.bean.stockmanage.purchaserorder;

/**
 * 分享的传参
 */
public class UrlObject {
    private String url;
    private UrlData body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UrlData getBody() {
        return body;
    }

    public void setBody(UrlData body) {
        this.body = body;
    }

    public static class UrlData {
        private String  purchaserBillID;
        private String  groupID;


        public String getPurchaserBillID() {
            return purchaserBillID;
        }

        public void setPurchaserBillID(String purchaserBillID) {
            this.purchaserBillID = purchaserBillID;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }
    }
}
