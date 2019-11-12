package com.hll_sc_app.app.goodsdemand.special.list;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class SpecialDemandListPresenter implements ISpecialDemandListContract.ISpecialDemandListPresenter {
    private ISpecialDemandListContract.ISpecialDemandListView mView;
    private int mPageNum;

    private SpecialDemandListPresenter() {
    }

    public static SpecialDemandListPresenter newInstance() {
        return new SpecialDemandListPresenter();
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

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Other.querySpecialDemand(mPageNum, mView.getSearchWords(), mView.getPurchaserID(), new SimpleObserver<SingleListResp<SpecialDemandBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<SpecialDemandBean> specialDemandBeanSingleListResp) {
                mView.setData(specialDemandBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(specialDemandBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ISpecialDemandListContract.ISpecialDemandListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
