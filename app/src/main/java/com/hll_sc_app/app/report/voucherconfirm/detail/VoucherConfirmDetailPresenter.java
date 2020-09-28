package com.hll_sc_app.app.report.voucherconfirm.detail;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/21
 */
public class VoucherConfirmDetailPresenter implements IVoucherConfirmDetailContract.IVoucherConfirmDetailPresenter {
    private IVoucherConfirmDetailContract.IVoucherConfirmDetailView mView;
    private int mPageNum;

    public static VoucherConfirmDetailPresenter newInstance() {
        return new VoucherConfirmDetailPresenter();
    }

    private VoucherConfirmDetailPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IVoucherConfirmDetailContract.IVoucherConfirmDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    private void load(boolean showLoading) {
        Report.queryVouchers(mView.getReq()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<CustomReceiveListResp.RecordsBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<CustomReceiveListResp.RecordsBean> recordsBeanSingleListResp) {
                mView.setData(recordsBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(recordsBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
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
    public void confirm(String extGroupID, List<String> ids) {
        Report.confirmVouchers(extGroupID, ids, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.success();
            }
        });
    }
}
