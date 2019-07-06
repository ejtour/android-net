package com.hll_sc_app.app.goods.relevance.goods;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/6
 */

public class GoodsRelevanceListPresenter implements IGoodsRelevanceListContract.IGoodsRelevanceListPresenter {
    private String mID;
    private IGoodsRelevanceListContract.IGoodsRelevanceListView mView;

    private GoodsRelevanceListPresenter(String id) {
        mID = id;
    }

    public static GoodsRelevanceListPresenter newInstance(String id) {
        return new GoodsRelevanceListPresenter(id);
    }

    @Override
    public void reqTransferDetail() {
        Order.getTransferDetail(mID, new SimpleObserver<TransferBean>(mView) {
            @Override
            public void onSuccess(TransferBean bean) {
                mView.showTransferDetail(bean);
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(IGoodsRelevanceListContract.IGoodsRelevanceListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
