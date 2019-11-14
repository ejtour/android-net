package com.hll_sc_app.app.crm.daily.detail;

import android.os.Bundle;

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
    private String mReportID;
    private int mPageNum;
    private ICrmDailyDetailContract.ICrmDailyDetailView mView;

    public static CrmDailyDetailPresenter newInstance(String reportID) {
        return new CrmDailyDetailPresenter(reportID);
    }

    private CrmDailyDetailPresenter(String reportID) {
        mReportID = reportID;
    }

    @Override
    public void send(String msg) {
        Daily.replyDaily(msg, mReportID, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        });
    }

    @Override
    public void refresh() {
        Daily.queryDailyDetail(mView.isSend(), mReportID, new SimpleObserver<DailyBean>(mView, false) {
            @Override
            public void onSuccess(DailyBean dailyBean) {
                mView.setData(dailyBean);
            }
        });
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
        Daily.queryDailyReply(mPageNum, mReportID, new SimpleObserver<SingleListResp<DailyReplyBean>>(mView, showLoading) {
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
