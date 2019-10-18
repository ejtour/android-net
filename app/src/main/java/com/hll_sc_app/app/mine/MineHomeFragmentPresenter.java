package com.hll_sc_app.app.mine;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

import java.util.Date;
import java.util.List;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
public class MineHomeFragmentPresenter implements MineHomeFragmentContract.IHomePresenter {
    private MineHomeFragmentContract.IHomeView mView;

    private MineHomeFragmentPresenter() {
    }

    public static MineHomeFragmentPresenter newInstance() {
        return new MineHomeFragmentPresenter();
    }

    @Override
    public void start() {
        load(true);
    }

    private void load(boolean showLoading) {
        Date date = CalendarUtils.getWeekDate(-1, 1);
        Other.queryAnalysisInfo(CalendarUtils.toLocalDate(date), 2, new SimpleObserver<AnalysisResp>(mView, showLoading) {
            @Override
            public void onSuccess(AnalysisResp analysisResp) {
                List<AnalysisBean> records = analysisResp.getRecords();
                if (records != null && records.size() > 1) {
                    AnalysisBean cur = records.get(records.size() - 1);
                    AnalysisBean last = records.get(records.size() - 2);
                    cur.setRelativeRatio(last.getValidTradeAmount() == 0 ? cur.getValidTradeAmount() == 0 ? 0 : 1 :
                            (cur.getValidTradeAmount() - last.getValidTradeAmount()) / last.getValidTradeAmount());
                    mView.setData(cur);
                }
            }
        });
    }

    @Override
    public void register(MineHomeFragmentContract.IHomeView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void refresh() {
        load(false);
    }
}
