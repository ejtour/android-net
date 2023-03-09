package com.hll_sc_app.app.report.lack.diff;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.lack.LackDiffResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public class LackDiffPresenter implements ILackDiffContract.ILackDiffPresenter {
    private ILackDiffContract.ILackDiffView mView;
    private int mPageNum;

    public static LackDiffPresenter newInstance() {
        return new LackDiffPresenter();
    }

    private LackDiffPresenter() {
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
                .create().getData(), "111006", email, Utils.getExportObserver(mView, "shopmall-supplier"));
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
        Report.queryLackDiff(mView.getReq()
                .put("pageNum",String.valueOf(mPageNum))
                .put("pageSize","20")
                .create(), new SimpleObserver<LackDiffResp>(mView, showLoading) {
            @Override
            public void onSuccess(LackDiffResp lackDiffResp) {
                mView.setData(lackDiffResp, mPageNum > 1);
                if (CommonUtils.isEmpty(lackDiffResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ILackDiffContract.ILackDiffView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
