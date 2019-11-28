package com.hll_sc_app.app.goodsdemand;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class GoodsDemandPresenter implements IGoodsDemandContract.IGoodsDemandPresenter {
    private int mPageNum;
    private IGoodsDemandContract.IGoodsDemandView mView;
    private int mStatus;

    private GoodsDemandPresenter(int status) {
        mStatus = status;
    }

    public static GoodsDemandPresenter newInstance() {
        return new GoodsDemandPresenter(0);
    }

    public static GoodsDemandPresenter newInstance(int status) {
        return new GoodsDemandPresenter(status);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    private void load(boolean showLoading) {
        Other.queryGoodsDemand(mPageNum, mStatus, null, new SimpleObserver<SingleListResp<GoodsDemandBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<GoodsDemandBean> goodsDemandBeanSingleListResp) {
                mView.handleData(goodsDemandBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(goodsDemandBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IGoodsDemandContract.IGoodsDemandView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
