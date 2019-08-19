package com.hll_sc_app.app.helpcenter;

/**
 * 帮助中心webview请求js页面传的参数
 *
 * @author zc
 */
public class HelpCenterJsParams {

    /**
     * 来源， 不传，默认是h5；
     */
    private String source = "rn";
    /**
     * 请求源，参考rap设置
     */
    private String httpReq = "shopmall";
    /**
     * 平台 ios, android
     */
    private String cs = "android";
    /**
     * mall 为采购商端，sc为供应商端
     */
    private String appFrom = "sc";

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHttpReq() {
        return httpReq;
    }

    public void setHttpReq(String httpReq) {
        this.httpReq = httpReq;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getAppFrom() {
        return appFrom;
    }

    public void setAppFrom(String appFrom) {
        this.appFrom = appFrom;
    }
}
