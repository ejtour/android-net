package com.hll_sc_app.app.stockmanage.stocklogquery;

import android.app.Activity;
import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.stockmanage.BusinessTypeBean;
import com.hll_sc_app.bean.stockmanage.StockLogResp;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class StockLogQueryPresent implements IStockLogQueryContract.IPresent {
    private IStockLogQueryContract.IView mView;

    private int pageNum = 1;
    private int pageNumTemp = 1;
    private int pageSize = 20;

    public static StockLogQueryPresent newInstance() {
        return new StockLogQueryPresent();
    }

    @Override
    public void start() {
        queryHouseList();
        queryBusinessType();
        fetchStockLogs(true);
    }

    @Override
    public void register(IStockLogQueryContract.IView view) {
        mView = view;
    }

    @Override
    public void queryHouseList() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder().put("groupID", userBean.getGroupID()).create();
        GoodsService.INSTANCE
                .queryHouseList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<HouseBean>>() {
                    @Override
                    public void onSuccess(List<HouseBean> houseBeans) {
                        mView.queryHouseListSuccess(houseBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryBusinessType() {
        BaseReq<Object> baseReq = new BaseReq<>();
        baseReq.setData(new Object());
        StockManageService.INSTANCE
                .queryBusinessType(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<BusinessTypeBean>>() {
                    @Override
                    public void onSuccess(List<BusinessTypeBean> businessTypeBeans) {
                        mView.queryBusinessTypeSuccess(businessTypeBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void fetchStockLogs(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .put("pageNum", pageNumTemp + "")
                .put("pageSize", pageSize + "")
                .put("houseID", mView.getHouseID())
                .put("businessType", mView.getBusinessType())
                .put("searchKey", mView.getSearchKey())
                .put("createTimeStart", mView.getCreateTimeStart())
                .put("createTimeEnd", mView.getCreateTimeEnd()).create();

        StockManageService.INSTANCE
                .fetchStockLogs(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<StockLogResp>() {
                    @Override
                    public void onSuccess(StockLogResp resp) {
                        mView.fetchStockLogsSuccess(resp, pageNumTemp > 1);
                        pageNum = pageNumTemp;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageNumTemp = pageNum;
                    }
                });
    }

    @Override
    public void fetchMore() {
        pageNumTemp++;
        fetchStockLogs(false);
    }

    @Override
    public void refresh() {
        pageNumTemp = 1;
        fetchStockLogs(false);
    }

    @Override
    public void export(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setActionType("2");
        req.setTypeCode("stock_flow");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        paramsBean.setPageNum(pageNumTemp + "");
        paramsBean.setPageSize(pageSize + "");
        paramsBean.setBusinessType(mView.getBusinessType());
        paramsBean.setCreateTimeStart(mView.getCreateTimeStart());
        paramsBean.setCreateTimeEnd(mView.getCreateTimeEnd());
        paramsBean.setGroupID(userBean.getGroupID());
        paramsBean.setHouseID(mView.getHouseID());
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
                        if (!TextUtils.isEmpty(resp.getEmail())) {
                            Utils.exportSuccess((Activity) mView, resp.getEmail());
                        } else {
                            mView.showToast("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (TextUtils.equals("00120112037", e.getCode())) {
                            Utils.bindEmail((Activity) mView, email -> {
                                export(email);
                            });
                        } else if (TextUtils.equals("00120112038", e.getCode())) {
                            mView.showToast("当前没有可导出的数据");
                        } else {
                            mView.showToast("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                        }
                    }
                });
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
