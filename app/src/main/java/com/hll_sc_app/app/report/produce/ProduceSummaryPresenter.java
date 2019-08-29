package com.hll_sc_app.app.report.produce;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.report.produce.ProduceSummaryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public class ProduceSummaryPresenter implements IProduceSummaryContract.IProduceSummaryPresenter {
    private int mPageNum;
    private IProduceSummaryContract.IProduceSummaryView mView;
    private DateParam mParam;

    public static ProduceSummaryPresenter newInstance(DateParam param) {
        return new ProduceSummaryPresenter(param);
    }

    private ProduceSummaryPresenter(DateParam param) {
        mParam = param;
    }

    private void load(boolean showLoading) {
        Report.queryProduceSummary(
                getReqParam()
                        .put("pageNum", String.valueOf(mPageNum))
                        .put("pageSize", "20")
                        .create(),
                new SimpleObserver<ProduceSummaryResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(ProduceSummaryResp produceSummaryResp) {
                        mView.setData(produceSummaryResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(produceSummaryResp.getList())) return;
                        mPageNum++;
                    }
                });
    }

    private BaseMapReq.Builder getReqParam() {
        return BaseMapReq.newBuilder()
                .put("startDate", mParam.getFormatStartDate())
                .put("endDate", mParam.getFormatEndDate())
                .put("groupID", UserConfig.getGroupID());
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
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
    public void export(boolean detail, String email) {
        Report.exportReport(getReqParam().create().getData(), detail ? "111073" : "111031", email, Utils.getExportObserver(mView));
    }

    @Override
    public void register(IProduceSummaryContract.IProduceSummaryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
