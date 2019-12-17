package com.hll_sc_app.app.report.loss;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.loss.LossBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * 客户流失明细
 *
 * @author 初坤
 * @date 2019/7/20
 */
public class LossPresenter implements ILossContract.ILossPresenter {

    private ILossContract.ILossView mView;
    private int mPageNum;

    static LossPresenter newInstance() {
        return new LossPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(ILossContract.ILossView view) {
        this.mView = CommonUtils.checkNotNull(view);
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

    private void load(boolean showLoading) {
        Report.queryLossInfo(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<LossBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<LossBean> lossBeanSingleListResp) {
                mView.setData(lossBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(lossBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
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
                .create().getData(), "111032", email, Utils.getExportObserver(mView));
    }
}
