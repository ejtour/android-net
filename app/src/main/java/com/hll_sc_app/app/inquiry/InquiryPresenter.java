package com.hll_sc_app.app.inquiry;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Inquiry;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

class InquiryPresenter implements IInquiryContract.IInquiryPresenter {
    private IInquiryContract.IInquiryView mView;
    private int mPageNum;

    public static InquiryPresenter newInstance() {
        return new InquiryPresenter();
    }

    private InquiryPresenter() {
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
        Inquiry.queryList(mView.getStatus(), mPageNum, new SimpleObserver<SingleListResp<InquiryBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<InquiryBean> inquiryBeanSingleListResp) {
                mView.setData(inquiryBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(inquiryBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(IInquiryContract.IInquiryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
