package com.hll_sc_app.app.crm.daily.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyReplyBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Daily;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyDetailPresenter implements ICrmDailyDetailContract.ICrmDailyDetailPresenter {
    private int mPageNum;
    private ICrmDailyDetailContract.ICrmDailyDetailView mView;

    private CrmDailyDetailPresenter() {

    }

    public static CrmDailyDetailPresenter newInstance() {
        return new CrmDailyDetailPresenter();
    }

    @Override
    public void send(String msg) {
        Daily.replyDaily(msg, mView.getReplyID(), new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        });
    }

    @Override
    public void refresh() {
        loadDetail();
        mPageNum = 1;
        load(false);
    }

    private void loadDetail() {
        Daily.queryDailyDetail(mView.isSend(), mView.getID(), new SimpleObserver<DailyBean>(mView, false) {
            @Override
            public void onSuccess(DailyBean dailyBean) {
                mView.setData(dailyBean);
            }
        });
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void start() {
        loadDetail();
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Daily.queryDailyReply(mPageNum, mView.getReplyID(), new SimpleObserver<SingleListResp<DailyReplyBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<DailyReplyBean> dailyReplyBeanSingleListResp) {
                mView.setData(dailyReplyBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(dailyReplyBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ICrmDailyDetailContract.ICrmDailyDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
