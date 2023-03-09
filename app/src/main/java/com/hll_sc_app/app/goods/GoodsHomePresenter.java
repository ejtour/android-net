package com.hll_sc_app.app.goods;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 首页商品管理Fragment
 *
 * @author 朱英松
 * @date 2019/7/3
 */
public class GoodsHomePresenter implements GoodsHomeContract.IGoodsHomePresenter {
    private GoodsHomeContract.IGoodsHomeView mView;

    private GoodsHomePresenter() {
    }

    public static GoodsHomePresenter newInstance() {
        return new GoodsHomePresenter();
    }

    @Override
    public void register(GoodsHomeContract.IGoodsHomeView view) {
        this.mView = view;
    }

    @Override
    public void exportGoodsList(String actionType, String productStatus) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("actionType", actionType)
                .put("productStatus", productStatus)
                .create();
        GoodsService.INSTANCE.exportGoodsList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void toBindEmail(String email) {
        User.bindEmail(email, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.bindSuccess();
            }
        });
    }

    @Override
    public void exportRecord(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("skuShelfFlow");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.SkuShelfFlowBean skuShelfFlowBean = new ExportReq.ParamsBean.SkuShelfFlowBean();
        skuShelfFlowBean.setGroupID(userBean.getGroupID());
        paramsBean.setSkuShelfFlow(skuShelfFlowBean);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(Utils.getExportObserver(mView, "shopmall-supplier"));
    }
}
