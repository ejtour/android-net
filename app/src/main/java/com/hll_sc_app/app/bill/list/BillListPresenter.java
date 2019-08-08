package com.hll_sc_app.app.bill.list;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.bill.BillListResp;
import com.hll_sc_app.bean.bill.BillParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Bill;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillListPresenter implements IBillListContract.IBillListPresenter {
    private int mPageNum;
    private BillParam mParam;
    private IBillListContract.IBillListView mView;

    public static BillListPresenter newInstance(BillParam param) {
        BillListPresenter presenter = new BillListPresenter();
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void export(String email, int sign) {
        Bill.exportEmail(sign, email, mParam.getFormatStartTime(), mParam.getFormatEndTime(), UserConfig.getGroupID(),
                null, mParam.getShopIDs(), mParam.getSettlementStatus(), Utils.getExportObserver(mView));
    }

    @Override
    public void doAction(List<Integer> ids) {
        Bill.billAction(ids, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.actionSuccess();
            }
        });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void loadMore() {
        getBillList(false);
    }

    private void getBillList(boolean showLoading) {
        Bill.getBillList(mPageNum,
                mParam.getFormatStartTime(),
                mParam.getFormatEndTime(),
                null,
                UserConfig.getGroupID(),
                mParam.getShopIDs(),
                mParam.getSettlementStatus(),
                new SimpleObserver<BillListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(BillListResp billListResp) {
                        mView.setBillList(billListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(billListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void start() {
        mPageNum = 1;
        getBillList(true);
    }

    @Override
    public void register(IBillListContract.IBillListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
