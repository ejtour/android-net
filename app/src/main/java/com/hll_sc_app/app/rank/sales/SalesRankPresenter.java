package com.hll_sc_app.app.rank.sales;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.rank.RankParam;
import com.hll_sc_app.bean.rank.SalesRankResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class SalesRankPresenter implements ISalesRankContract.ISalesRankPresenter {
    private int mPageNum;
    private ISalesRankContract.ISalesRankView mView;
    private RankParam mRankParam;

    private SalesRankPresenter(RankParam rankParam) {
        mRankParam = rankParam;
    }

    public static SalesRankPresenter newInstance(RankParam rankParam) {
        return new SalesRankPresenter(rankParam);
    }

    private void load(boolean showLoading) {
        Other.querySalesRank(mPageNum,
                mRankParam.getDateType(),
                mRankParam.getFormatDate(),
                new SimpleObserver<SalesRankResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(SalesRankResp salesRankResp) {
                        mView.setData(salesRankResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(salesRankResp.getRecords())) return;
                        mPageNum++;
                    }
                });
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

    @Override
    public void register(ISalesRankContract.ISalesRankView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
