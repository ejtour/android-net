package com.hll_sc_app.bean.mall;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */

public class PrivateMallResp {
    private String groupID;
    private String toBCode;
    private String toBLinkUrl;
    private String toCCode;
    private String toCLinkUrl;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getToBCode() {
        return toBCode;
    }

    public void setToBCode(String toBCode) {
        this.toBCode = toBCode;
    }

    public String getToBLinkUrl() {
        return toBLinkUrl;
    }

    public void setToBLinkUrl(String toBLinkUrl) {
        this.toBLinkUrl = toBLinkUrl;
    }

    public String getToCCode() {
        return toCCode;
    }

    public void setToCCode(String toCCode) {
        this.toCCode = toCCode;
    }

    public String getToCLinkUrl() {
        return toCLinkUrl;
    }

    public void setToCLinkUrl(String toCLinkUrl) {
        this.toCLinkUrl = toCLinkUrl;
    }
}
