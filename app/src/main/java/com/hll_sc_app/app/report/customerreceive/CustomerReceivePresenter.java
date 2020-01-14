package com.hll_sc_app.app.report.customerreceive;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class CustomerReceivePresenter implements ICustomerReceiveContract.ICustomerReceivePresenter {
    private ICustomerReceiveContract.ICustomerReceiveView mView;
    private int mPageNum;

    private CustomerReceivePresenter() {
    }

    public static CustomerReceivePresenter newInstance() {
        return new CustomerReceivePresenter();
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
        Report.queryReceiptList(mView.getReq()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<ReceiveCustomerBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<ReceiveCustomerBean> receiveCustomerBeanSingleListResp) {
                mView.setData(receiveCustomerBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(receiveCustomerBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ICustomerReceiveContract.ICustomerReceiveView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
