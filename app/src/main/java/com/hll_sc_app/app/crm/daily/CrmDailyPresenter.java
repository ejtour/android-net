package com.hll_sc_app.app.crm.daily;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Daily;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyPresenter implements ICrmDailyContract.ICrmDailyPresenter {
    private ICrmDailyContract.ICrmDailyView mView;
    private int mPageNum;

    private CrmDailyPresenter() {
    }

    public static CrmDailyPresenter newInstance() {
        return new CrmDailyPresenter();
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
        String date = CalendarUtils.toLocalDate(new Date());
        Daily.queryDaily(true, mPageNum, 20,
                null, null, date, date,
                new SimpleObserver<SingleListResp<DailyBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<DailyBean> dailyBeanSingleListResp) {
                        mView.setData(dailyBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(dailyBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(ICrmDailyContract.ICrmDailyView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
