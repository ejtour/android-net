package com.hll_sc_app.app.complainmanage.ordernumberlist;

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

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectOrderListPresent implements ISelectOrderListContract.IPresent {
    private final int PAGE_SIZE = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;
    private ISelectOrderListContract.IView mView;

    public static SelectOrderListPresent newInstance() {
        return new SelectOrderListPresent();
    }

    @Override
    public void register(ISelectOrderListContract.IView view) {
        mView = view;
    }


    @Override
    public void queryOrderList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        OrderService.INSTANCE
                .getOrderList(BaseMapReq
                        .newBuilder()
                        .put("billSource", "1")
                        .put("groupID", userBean.getGroupID())
                        .put("pageNum", String.valueOf(pageTempNum))
                        .put("pageSize", String.valueOf(PAGE_SIZE))
                        .put("purchaserIDs", mView.getPurchaserId())
                        .put("shopID", mView.getShopId())
                        .put("subBillCreateTimeStart", mView.getCreateTimeStart())
                        .put("subBillCreateTimeEnd", mView.getCreateTimeEnd())
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<OrderResp>>() {
                    @Override
                    public void onSuccess(List<OrderResp> listBaseResp) {
                        mView.queySuccess(listBaseResp, pageTempNum > 1);
                        pageNum = pageTempNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageTempNum = pageNum;
                    }
                });

    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryOrderList(false);
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryOrderList(false);

    }

    @Override
    public int getPageSize() {
        return PAGE_SIZE;
    }
}
