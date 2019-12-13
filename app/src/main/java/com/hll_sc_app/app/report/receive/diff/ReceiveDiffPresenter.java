package com.hll_sc_app.app.report.receive.diff;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.receive.ReceiveDiffResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public class ReceiveDiffPresenter implements IReceiveDiffContract.IReceiveDiffPresenter {
    private IReceiveDiffContract.IReceiveDiffView mView;
    private int mPageNum;

    public static ReceiveDiffPresenter newInstance() {
        return new ReceiveDiffPresenter();
    }

    private ReceiveDiffPresenter() {
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
                .create().getData(), "111007", email, Utils.getExportObserver(mView));
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
        Report.queryReceiveDiff(mView.getReq()
                .put("pageNum",String.valueOf(mPageNum))
                .put("pageSize","20")
                .create(), new SimpleObserver<ReceiveDiffResp>(mView, showLoading) {
            @Override
            public void onSuccess(ReceiveDiffResp lackDiffResp) {
                mView.setData(lackDiffResp, mPageNum > 1);
                if (CommonUtils.isEmpty(lackDiffResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(IReceiveDiffContract.IReceiveDiffView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
