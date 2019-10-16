package com.hll_sc_app.app.analysis;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.operationanalysis.AnalysisParam;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.LostResp;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class AnalysisPresenter implements IAnalysisContract.IAnalysisPresenter {
    private IAnalysisContract.IAnalysisView mView;
    private AnalysisParam mAnalysisParam;

    private AnalysisPresenter(AnalysisParam param) {
        mAnalysisParam = param;
    }

    public static AnalysisPresenter newInstance(AnalysisParam param) {
        return new AnalysisPresenter(param);
    }

    @Override
    public void start() {
        Other.queryLostInfo(mAnalysisParam.getFormatDate(), mAnalysisParam.getTimeType(), new SimpleObserver<LostResp>(mView) {
            @Override
            public void onSuccess(LostResp lostResp) {
                mView.setLostData(lostResp);
            }
        });
        Other.queryAnalysisInfo(mAnalysisParam.getFormatDate(), mAnalysisParam.getTimeType(), new SimpleObserver<AnalysisResp>(mView) {
            @Override
            public void onSuccess(AnalysisResp analysisResp) {
                mView.setAnalysisData(analysisResp);
            }
        });
        Other.queryTopTenInfo(mAnalysisParam.getFormatDate(), mAnalysisParam.getTimeType(), new SimpleObserver<TopTenResp>(mView) {
            @Override
            public void onSuccess(TopTenResp topTenResp) {
                mView.setTopTenData(topTenResp);
            }
        });
    }

    @Override
    public void register(IAnalysisContract.IAnalysisView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
