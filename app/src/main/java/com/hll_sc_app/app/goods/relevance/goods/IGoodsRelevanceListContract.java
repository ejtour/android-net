package com.hll_sc_app.app.goods.relevance.goods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/6
 */

public interface IGoodsRelevanceListContract {
    interface IGoodsRelevanceListView extends ILoadView {
        void showTransferDetail(TransferBean bean);
    }

    interface IGoodsRelevanceListPresenter extends IPresenter<IGoodsRelevanceListView> {
        void reqTransferDetail();
    }
}
