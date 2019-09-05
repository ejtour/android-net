package com.hll_sc_app.bean.filter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/4
 */

public class CrmOrderParam extends OrderParam {
    /**
     * 1-今日订单 2-全部订单
     */
    private String actionType;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
