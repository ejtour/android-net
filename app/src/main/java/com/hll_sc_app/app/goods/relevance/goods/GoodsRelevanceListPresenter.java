package com.hll_sc_app.app.goods.relevance.goods;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Inquiry;
import com.hll_sc_app.rest.Order;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/6
 */

public class GoodsRelevanceListPresenter implements IGoodsRelevanceListContract.IGoodsRelevanceListPresenter {
    private IGoodsRelevanceListContract.IGoodsRelevanceListView mView;

    private GoodsRelevanceListPresenter() {

    }

    public static GoodsRelevanceListPresenter newInstance() {
        return new GoodsRelevanceListPresenter();
    }

    @Override
    public void reqDetail() {
        if (mView.isTransfer()) {
            Order.getTransferDetail(mView.getID(), new SimpleObserver<TransferBean>(mView, false) {
                @Override
                public void onSuccess(TransferBean bean) {
                    mView.showTransferDetail(bean);
                }
            });
        } else {
            Inquiry.bindResult(mView.getID(), new SimpleObserver<InquiryBindResp>(mView, false) {
                @Override
                public void onSuccess(InquiryBindResp bindResp) {
                    mView.showBindResp(bindResp);
                }
            });
        }
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
