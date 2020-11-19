package com.hll_sc_app.app.goods.add.selectproductowner;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectProductOwnerPresent implements ISelectProductOwnerContract.IPresent{

    private int page_num=1;
    private int page_num_temp=1;
    private static final int page_size= 20;

    private ISelectProductOwnerContract.IView mView;

    @Override
    public void start() {
        //no-op
    }

    public static SelectProductOwnerPresent newInstance(){
        return new SelectProductOwnerPresent();
    }

    @Override
    public void queryList(boolean isLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "1")
                .put("groupID", UserConfig.getGroupID())
                .put("status", "2")
                .put("isSizeLimit", "1")
                .put("isWarehouse", "1")
                .put("name", mView.getName())
                .create();
        CommonService.INSTANCE.searchShipperList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<WareHouseShipperBean>>() {
                    @Override
                    public void onSuccess(List<WareHouseShipperBean> wareHouseShipperBeans) {
                        mView.showList(wareHouseShipperBeans, false);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }

    @Override
    public void refresh() {
        page_num_temp  =1;
        queryList(false);
    }

    @Override
    public void quereMore() {
        page_num_temp++;
        queryList(false);
    }

    @Override
    public void register(ISelectProductOwnerContract.IView view) {
        mView = view;
    }
}
