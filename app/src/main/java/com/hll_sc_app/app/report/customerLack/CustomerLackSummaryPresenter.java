package com.hll_sc_app.app.report.customerLack;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.bean.UserBeanDao;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * @since 2019/7/22
 */

public class CustomerLackSummaryPresenter implements ICustomerLackSummaryContract.ICustomerLackPresenter {
    private ICustomerLackSummaryContract.ICustomerLackView mView;
    private int mPageNum;

    public static CustomerLackSummaryPresenter newInstance() {
        CustomerLackSummaryPresenter presenter = new CustomerLackSummaryPresenter();
        return presenter;
    }

    @Override
    public void getPurchaserList(String searchWords) {
        Common.queryPurchaserList("customerOrder",searchWords, new SimpleObserver<List<PurchaserBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserBean> purchaserBeans) {
                mView.refreshPurchaserList(purchaserBeans);
            }
        });
    }

    @Override
    public void getShopList(String purchaseID, String searchWords) {
        if (TextUtils.isEmpty(purchaseID)) return;
        Common.queryPurchaserShopList(purchaseID, "customerOrder", searchWords, new SimpleObserver<List<PurchaserShopBean>>(mView) {
            @Override
            public void onSuccess(List<PurchaserShopBean> purchaserShopBeans) {
                mView.refreshShopList(purchaserShopBeans);
            }
        });
    }

    @Override
    public void loadMore() {
        querySummaryList(false);
    }

    @Override
    public void querySummaryList(boolean showLoading) {
        CustomerLackReq requestParams = mView.getRequestParams();
        requestParams.setPageNum(mPageNum);
        requestParams.setGroupID(UserConfig.getGroupID());
        Report.queryCustomerLack(requestParams, new SimpleObserver<CustomerLackResp>(mView, showLoading) {
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
        querySummaryList(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void export(String params,String email) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(params, "111053", email,
                Utils.getExportObserver(mView));
    }

    private void bindEmail(String email) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null)
            return;
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("email", email)
                .put("employeeID", user.getEmployeeID())
                .create();
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                CustomerLackReq requestParams = mView.getRequestParams();
                Gson gson = new Gson();
                String reqParams = gson.toJson(requestParams);
                export(reqParams,null);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void start() {
        getPurchaserList("");
        reload();
    }

    @Override
    public void register(ICustomerLackSummaryContract.ICustomerLackView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
