package com.hll_sc_app.app.goodsdemand.special;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandEntryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class SpecialDemandEntryPresenter implements ISpecialDemandEntryContract.ISpecialDemandEntryPresenter {
    private int mPageNum;
    private ISpecialDemandEntryContract.ISpecialDemandEntryView mView;

    public static SpecialDemandEntryPresenter newInstance() {
        return new SpecialDemandEntryPresenter();
    }

    private SpecialDemandEntryPresenter() {
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
        Other.querySpecialDemandCustomer(mPageNum, mView.getSearchWords(), new SimpleObserver<SingleListResp<SpecialDemandEntryBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<SpecialDemandEntryBean> specialDemandEntryBeanSingleListResp) {
                mView.setData(specialDemandEntryBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(specialDemandEntryBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ISpecialDemandEntryContract.ISpecialDemandEntryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
