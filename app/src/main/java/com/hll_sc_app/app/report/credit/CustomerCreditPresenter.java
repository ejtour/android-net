package com.hll_sc_app.app.report.credit;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.credit.CreditBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CustomerCreditPresenter implements ICustomerCreditContract.ICustomerCreditPresenter {
    private ICustomerCreditContract.ICustomerCreditView mView;
    private int mPageNum;

    public static CustomerCreditPresenter newInstance() {
        return new CustomerCreditPresenter();
    }

    private CustomerCreditPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        Report.queryCustomerCredit(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<CreditBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<CreditBean> resp) {
                List<CreditBean> records = resp.getRecords();
                if (!CommonUtils.isEmpty(records)) {
                    for (CreditBean bean : records) {
                        bean.preProcess();
                    }
                }
                mView.setData(records, mPageNum > 1);
                if (!CommonUtils.isEmpty(records)) {
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
                .create().getData(), "111016", email, Utils.getExportObserver(mView));
    }

    @Override
    public void register(ICustomerCreditContract.ICustomerCreditView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
