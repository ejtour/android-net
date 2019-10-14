package com.hll_sc_app.app.complainmanage.add.productlist;

import com.hll_sc_app.api.OrderService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.order.OrderResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectProductListPresent implements ISelectProductListContract.IPresent {
    private ISelectProductListContract.IView mView;

    public static SelectProductListPresent newInstance() {
        return new SelectProductListPresent();
    }

    @Override
    public void register(ISelectProductListContract.IView view) {
        mView = view;
    }


    @Override
    public void queryList() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("filterProductName", mView.getSearchWords())
                .put("flag", "2")
                .put("groupID", userBean.getGroupID())
                .put("subBillNo", mView.getSubBillNo())
                .create();

        OrderService.INSTANCE
                .getOrderDetails(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                        mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<OrderResp>() {
                    @Override
                    public void onSuccess(OrderResp orderResp) {
                        mView.querySuccess(orderResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }

}
