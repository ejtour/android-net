package com.hll_sc_app.app.goods.relevance.goods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;
import com.hll_sc_app.bean.order.transfer.TransferBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/6
 */

public interface IGoodsRelevanceListContract {
    interface IGoodsRelevanceListView extends ILoadView {
        void showTransferDetail(TransferBean bean);

        String getID();

        boolean isTransfer();

        void showBindResp(InquiryBindResp resp);
    }

    interface IGoodsRelevanceListPresenter extends IPresenter<IGoodsRelevanceListView> {
        void reqDetail();
    }
}
