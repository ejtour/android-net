package com.hll_sc_app.app.rank.org;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.rank.OrgRankBean;
import com.hll_sc_app.bean.rank.RankParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/10
 */

public class OrgRankPresenter implements IOrgRankContract.IOrgRankPresenter {
    private boolean mShop;
    private IOrgRankContract.IOrgRankView mView;
    private int mPageNum;
    private RankParam mRankParam;

    private OrgRankPresenter(boolean shop, RankParam rankParam) {
        mShop = shop;
        mRankParam = rankParam;
    }

    public static OrgRankPresenter newInstance(boolean shop, RankParam rankParam) {
        return new OrgRankPresenter(shop, rankParam);
    }

    private void load(boolean showLoading) {
        Other.queryOrgRank(mShop, mPageNum, mRankParam.getDateType(), mRankParam.getFormatDate(),
                new SimpleObserver<SingleListResp<OrgRankBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<OrgRankBean> orgRankBeanSingleListResp) {
                        mView.setData(orgRankBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(orgRankBeanSingleListResp.getRecords())) return;
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
    public void register(IOrgRankContract.IOrgRankView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
