package com.hll_sc_app.app.report.ordergoods;

import android.text.TextUtils;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsBean;
import com.hll_sc_app.bean.filter.DateStringParam;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsPresenter implements IOrderGoodsContract.IOrderGoodsPresenter {
    private DateStringParam mParam;
    private IOrderGoodsContract.IOrderGoodsView mView;
    private int mPageNum;

    public static OrderGoodsPresenter newInstance(DateStringParam param) {
        OrderGoodsPresenter presenter = new OrderGoodsPresenter();
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void getPurchaserList(String searchWords) {
        Common.queryPurchaserList("customerOrder", searchWords, new SimpleObserver<List<PurchaserBean>>(mView) {
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
        getOrderGoods(false);
    }

    @Override
    public void getOrderGoods(boolean showLoading) {
        Report.queryOrderGoods(mParam.getExtra(), mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                mPageNum, new SimpleObserver<OrderGoodsResp<OrderGoodsBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(OrderGoodsResp orderGoodsResp) {
                        mView.showList(orderGoodsResp.getRecords(), mPageNum > 1);
                        if (!CommonUtils.isEmpty(orderGoodsResp.getRecords())) mPageNum++;
                    }
                });
    }

    @Override
    public void reload() {
        mPageNum = 1;
        getOrderGoods(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportOrderGoodsDetails(mParam.getExtra(), mParam.getFormatStartDate(), mParam.getFormatEndDate(),
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
                export(null);
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
    public void register(IOrderGoodsContract.IOrderGoodsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
