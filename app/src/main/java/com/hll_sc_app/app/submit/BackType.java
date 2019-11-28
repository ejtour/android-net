package com.hll_sc_app.app.submit;

import com.hll_sc_app.base.utils.router.RouterConfig;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/14
 */

public enum BackType {
    /**
     * 回退到订单列表
     */
    ORDER_LIST("返回订单列表", RouterConfig.ROOT_HOME),
    PARTNER_DETAIL_LIST("返回列表", RouterConfig.CRM_CUSTOMER_PARTNER_DETAIL);
    String label;
    String path;

    public String getLabel() {
        return label;
    }

    public String getPath() {
        return path;
    }

    BackType(String label, String path) {
        this.label = label;
        this.path = path;
    }}
