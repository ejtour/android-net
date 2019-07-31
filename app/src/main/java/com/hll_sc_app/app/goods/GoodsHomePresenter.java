package com.hll_sc_app.app.goods;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
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
            .subscribe(new BaseCallback<ExportResp>() {
                @Override
                public void onSuccess(ExportResp resp) {
                    exportSuccess(resp.getEmail());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    exportFailure(e.getCode(), GoodsHomeContract.ExportType.EXPORT_GOODS);
                }
            });
    }

    private void exportSuccess(String email) {
        if (!TextUtils.isEmpty(email)) {
            mView.exportSuccess(email);
        } else {
            mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
        }
    }

    private void exportFailure(String code, @GoodsHomeContract.ExportType String type) {
        if (TextUtils.equals("00120112037", code)) {
            mView.bindEmail(type);
        } else if (TextUtils.equals("00120112038", code)) {
            mView.exportFailure("当前没有可导出的数据");
        } else {
            mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
        }
    }

    @Override
    public void toBindEmail(String email, @GoodsHomeContract.ExportType String type) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("email", email)
            .put("employeeID", userBean.getEmployeeID())
            .create();
        UserService.INSTANCE.bindEmail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.bindSuccess(type);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void exportRecord(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
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
            .subscribe(new BaseCallback<ExportResp>() {
                @Override
                public void onSuccess(ExportResp resp) {
                    exportSuccess(resp.getEmail());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    exportFailure(e.getCode(), GoodsHomeContract.ExportType.EXPORT_RECORDS);
                }
            });

    }
}
