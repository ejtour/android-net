package com.hll_sc_app.app.cooperation;

import android.text.TextUtils;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class CooperationPurchaserPresenter implements CooperationPurchaserContract.IGoodsRelevancePurchaserPresenter {
    private CooperationPurchaserContract.IGoodsRelevancePurchaserView mView;
    private int mPageNum;
    private int mTempPageNum;

    static CooperationPurchaserPresenter newInstance() {
        return new CooperationPurchaserPresenter();
    }

    @Override
    public void start() {
        queryPurchaserList(true);
    }

    @Override
    public void register(CooperationPurchaserContract.IGoodsRelevancePurchaserView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserList(showLoading);
    }

    @Override
    public void queryMorePurchaserList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserList(false);
    }

    @Override
    public void delCooperationPurchaser(String purchaserId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", purchaserId)
            .create();
        CooperationPurchaserService.INSTANCE.delCooperationPurchaser(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("解除关系成功");
                    queryPurchaserList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void exportPurchaser(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("cooperation");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.CooperationBean cooperationBean = new ExportReq.ParamsBean.CooperationBean();
        cooperationBean.setGroupID(userBean.getGroupID());
        paramsBean.setCooperation(cooperationBean);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(Utils.getExportObserver(mView, ""));
    }

    private void toQueryPurchaserList(boolean showLoading) {
        UserBean user = GreenDaoUtils.getUser();
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "formalCooperation")
                .put("groupID", user.getGroupID())
                .put("name", mView.getSearchParam())
                .put("pageNo", String.valueOf(mTempPageNum))
                .put("pageSize", "20")
                .put("originator", "1")
                .put("ignoreGroupActive", "1")
                .put("salesmanID", "1".equals(user.getCurRole()) ? user.getEmployeeID() : "")
                .put("cooperationActive", String.valueOf(mView.getCooperationActive()))
                .create();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CooperationPurchaserResp>() {
                    @Override
                    public void onSuccess(CooperationPurchaserResp resp) {
                        mPageNum = mTempPageNum;
                        mView.showPurchaserList(resp, mPageNum != 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
