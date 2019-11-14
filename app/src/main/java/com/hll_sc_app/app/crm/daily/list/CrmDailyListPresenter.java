package com.hll_sc_app.app.crm.daily.list;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.filter.DateStringParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Daily;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyListPresenter implements ICrmDailyListContract.ICrmDailyListPresenter {
    private DateStringParam mParam;
    private boolean mSend;
    private int mPageNum;
    private ICrmDailyListContract.ICrmDailyListView mView;

    private CrmDailyListPresenter(DateStringParam param, boolean send) {
        mParam = param;
        mSend = send;
    }

    public static CrmDailyListPresenter newInstance(DateStringParam param, boolean send) {
        return new CrmDailyListPresenter(param, send);
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
        Daily.queryDaily(mSend, mPageNum, 20,
                mView.getSearchWords(), mParam.getExtra(),
                mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                new SimpleObserver<SingleListResp<DailyBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<DailyBean> dailyBeanSingleListResp) {
                        mView.setData(dailyBeanSingleListResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(dailyBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(ICrmDailyListContract.ICrmDailyListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
