package com.hll_sc_app.app.report.orderGoods;

import android.text.TextUtils;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsParam;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsPresenter implements IOrderGoodsContract.IOrderGoodsPresenter {
    private OrderGoodsParam mParam;
    private IOrderGoodsContract.IOrderGoodsView mView;
    private int mPageNum;

    public static OrderGoodsPresenter newInstance(OrderGoodsParam param) {
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
        Report.queryOrderGoods(mParam.getShopIDs(), mParam.getFormatStartDate(), mParam.getFormatEndDate(),
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
        Report.exportOrderGoodsDetails(mParam.getShopIDs(), mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                new SimpleObserver<ExportResp>(mView) {
                    @Override
                    public void onSuccess(ExportResp exportResp) {
                        if (!TextUtils.isEmpty(exportResp.getEmail()))
                            mView.exportSuccess(exportResp.getEmail());
                        else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if ("00120112037".equals(e.getCode())) mView.bindEmail();
                        else if ("00120112038".equals(e.getCode()))
                            mView.exportFailure("当前没有可导出的数据");
                        else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                    }
                });
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
