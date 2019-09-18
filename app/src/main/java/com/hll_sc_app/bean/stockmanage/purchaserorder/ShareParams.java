package com.hll_sc_app.bean.stockmanage.purchaserorder;

/**
 * 分享的传参
 */
public class ShareParams {
    private String imgUrl;
    private String pageName;
    private String title;
    private String description;
    private UrlObject urlData;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UrlObject getUrlData() {
        return urlData;
    }

    public void setUrlData(UrlObject urlData) {
        this.urlData = urlData;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
