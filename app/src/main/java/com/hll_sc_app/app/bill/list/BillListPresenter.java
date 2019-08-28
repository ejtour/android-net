package com.hll_sc_app.app.bill.list;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.bill.BillListResp;
import com.hll_sc_app.bean.filter.BillParam;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Bill;
import com.hll_sc_app.rest.Common;
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
        Bill.exportEmail(sign, email, mParam.getFormatStartDate(), mParam.getFormatEndDate(), UserConfig.getGroupID(),
                mParam.getShopIDs(), mParam.getSettlementStatus(), Utils.getExportObserver(mView));
    }

    @Override
    public void doAction(List<String> ids) {
        Bill.billAction(ids, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                start();
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

    @Override
    public void getPurchaserList(String searchWords) {
        Common.queryPurchaserList("statusment", searchWords, new SimpleObserver<List<PurchaserBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserBean> purchaserBeans) {
                mView.refreshPurchaserList(purchaserBeans);
            }
        });
    }

    @Override
    public void getShopList(String purchaseID, String searchWords) {
        if (TextUtils.isEmpty(purchaseID)) return;
        Common.queryPurchaserShopList(purchaseID, "statusment", searchWords, new SimpleObserver<List<PurchaserShopBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserShopBean> purchaserShopBeans) {
                mView.refreshShopList(purchaserShopBeans);
            }
        });
    }

    private void getBillList(boolean showLoading) {
        Bill.getBillList(mPageNum,
                mParam.getFormatStartDate(),
                mParam.getFormatEndDate(),
                UserConfig.getGroupID(),
                mParam.getShopIDs(),
                mParam.getSettlementStatus(),
                new SimpleObserver<BillListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(BillListResp billListResp) {
                        mView.updateBillListResp(billListResp, mPageNum > 1);
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
