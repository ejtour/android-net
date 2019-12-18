package com.hll_sc_app.app.report.lack.customer;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.report.lack.CustomerLackResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author chukun
 * @since 2019/7/22
 */

public class CustomerLackPresenter implements ICustomerLackContract.ICustomerLackPresenter {
    private ICustomerLackContract.ICustomerLackView mView;
    private int mPageNum;

    public static CustomerLackPresenter newInstance() {
        return new CustomerLackPresenter();
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

    @Override
    public void loadMore() {
        load(false);
    }

    private void load(boolean showLoading) {
        Report.queryCustomerLack(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<CustomerLackResp>(mView, showLoading) {
            @Override
            public void onSuccess(CustomerLackResp orderGoodsResp) {
                mView.showSummaryList(orderGoodsResp.getSummary(), mPageNum > 1);
                if (!CommonUtils.isEmpty(orderGoodsResp.getSummary())) mPageNum++;
            }
        });
    }

    @Override
    public void reload() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        loadMore();
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
                .create().getData(), "111053", email, Utils.getExportObserver(mView));
    }

    @Override
    public void start() {
        getPurchaserList("");
        reload();
    }

    @Override
    public void register(ICustomerLackContract.ICustomerLackView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
