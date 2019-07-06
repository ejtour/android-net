package com.hll_sc_app.bean.event;

import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.LogUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderEvent extends BaseEvent {
    private static final String TAG = "OrderEvent";
    public static final String REFRESH_LIST = "refresh_list";
    public static final String UPDATE_ITEM = "update_item";
    public static final String REMOVE_SELECTED = "remove_selected";
    public static final String SEARCH_WORDS = "search_words";
    public static final String RELOAD_ITEM = "reload_item";
    public static final String UPDATE_TRANSFER_ITEM = "update_transfer_item";

    public OrderEvent(String msg) {
        this(msg, null);
    }

    public OrderEvent(String msg, Object b) {
        super(msg, b);
        switch (msg) {
            case REMOVE_SELECTED:
            case REFRESH_LIST:
            case RELOAD_ITEM:
                break;
            case SEARCH_WORDS:
                if (b instanceof OrderSearchBean) break;
                LogUtil.e(TAG, "Wrong type");
                break;
            case UPDATE_ITEM:
                if (b instanceof OrderResp) break;
                LogUtil.e(TAG, "Wrong type");
                break;
            case UPDATE_TRANSFER_ITEM:
                if (b instanceof TransferBean) break;
                LogUtil.e(TAG, "Wrong type");
                break;
        }
    }
}
