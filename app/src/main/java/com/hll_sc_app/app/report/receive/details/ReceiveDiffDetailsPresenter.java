package com.hll_sc_app.app.report.receive.details;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.receive.ReceiveDiffDetailsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author chukun
 * @since 2019/8/15
 */

public class ReceiveDiffDetailsPresenter implements IReceiveDiffDetailsContract.IReceiveDiffDetailsPresenter {
    private IReceiveDiffDetailsContract.IReceiveDiffDetailsView mView;
    private int mPageNum;

    public static ReceiveDiffDetailsPresenter newInstance() {
        return new ReceiveDiffDetailsPresenter();
    }

    private ReceiveDiffDetailsPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        Report.queryReceiveDiffDetails(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<ReceiveDiffDetailsResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(ReceiveDiffDetailsResp receiveDiffDetailsResp) {
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
                .create().getData(), "111013", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void register(IReceiveDiffDetailsContract.IReceiveDiffDetailsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
