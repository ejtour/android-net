package com.hll_sc_app.app.report.lack.details;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public class LackDetailsPresenter implements ILackDetailsContract.ILackDetailsPresenter {
    private ILackDetailsContract.ILackDetailsView mView;
    private int mPageNum;

    public static LackDetailsPresenter newInstance() {
        return new LackDetailsPresenter();
    }

    private LackDetailsPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        Report.queryLackDetails(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<LackDetailsResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(LackDetailsResp receiveDiffDetailsResp) {
                        mView.setData(receiveDiffDetailsResp, mPageNum > 1);
                        if (!CommonUtils.isEmpty(receiveDiffDetailsResp.getRecords())) {
                            mPageNum++;
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            User.bindEmail(email, new SimpleObserver<Object>(mView) {
                @Override
                public void onSuccess(Object o) {
                    export(null);
                }
            });
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111012", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void register(ILackDetailsContract.ILackDetailsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
