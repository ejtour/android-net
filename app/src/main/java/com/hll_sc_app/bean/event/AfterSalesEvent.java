package com.hll_sc_app.bean.event;

import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.LogUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class AfterSalesEvent extends BaseEvent {
    private static final String TAG = "AfterSalesEvent";
    public static final String REFRESH_LIST = "refresh_list";
    public static final String UPDATE_ITEM = "update_item";
    public static final String REMOVE_SELECTED = "remove_selected";
    public static final String RELOAD_ITEM = "reload_item";
    public static final String EXPORT_ORDER = "export_order";

    public AfterSalesEvent(String msg) {
        this(msg, null);
    }

    public AfterSalesEvent(String msg, Object b) {
        super(msg, b);
        switch (msg) {
            case REMOVE_SELECTED:
            case REFRESH_LIST:
            case RELOAD_ITEM:
            case EXPORT_ORDER:
                break;
            case UPDATE_ITEM:
                if (b instanceof AfterSalesBean) break;
                LogUtil.e(TAG, "Wrong type");
                break;
        }
    }
}
