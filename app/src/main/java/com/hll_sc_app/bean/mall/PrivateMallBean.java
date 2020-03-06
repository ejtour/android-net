package com.hll_sc_app.bean.mall;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */

public class PrivateMallBean {
    private String title;
    private String desc;
    private String imgUrl;
    private String link;
    private int icon;

    public static PrivateMallBean createToB(String imgUrl, String link) {
        PrivateMallBean bean = new PrivateMallBean();
        bean.title = "企业商城";
        bean.desc = "用户识别二维码后，需要填写企业信息包括营业执照、门店名称等，请提醒客户提前准备";
        bean.imgUrl = imgUrl;
        bean.link = link;
        bean.icon = R.drawable.ic_mall_enterprise;
        return bean;
    }

    public static PrivateMallBean createToC(String imgUrl, String link) {
        PrivateMallBean bean = new PrivateMallBean();
        bean.title = "个人商城";
        bean.desc = "用户识别二维码后，只需要通过手机号注册登录即可下单";
        bean.imgUrl = imgUrl;
        bean.link = link;
        bean.icon = R.drawable.ic_mall_personal;
        return bean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
