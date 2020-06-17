package com.hll_sc_app.app.stockmanage.stockquery;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsInvWarnResp;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class StockQueryPresent implements IStockQueryContract.IPresent {
    private IStockQueryContract.IView mView;
    private int pageSize = 20;
    private int pageNum = 1;
    private int pageNumTemp = 1;

    public static StockQueryPresent newInstance() {
        return new StockQueryPresent();
    }

    @Override
    public void register(IStockQueryContract.IView view) {
        mView = view;
    }

    @Override
    public void queryHouseList() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .create();
        GoodsService.INSTANCE
                .queryHouseList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<HouseBean>>() {
                    @Override
                    public void onSuccess(List<HouseBean> houseBeans) {
                        mView.getHouseListSuccess(houseBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryGoodsInvList(boolean isLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("houseID", mView.getHouseId())
                .put("pageNum", pageNumTemp + "")
                .put("pageSize", pageSize + "")
                .create();

        GoodsService.INSTANCE
                .queryGoodsInvList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<GoodsInvWarnResp>() {
                    @Override
                    public void onSuccess(GoodsInvWarnResp goodsInvWarnResp) {
                        mView.getGoodsListSuccess(goodsInvWarnResp.getList(), pageNumTemp > 1);
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
    public void refreshGoodsList() {
        pageNumTemp = 1;
        if (TextUtils.isEmpty(mView.getProductName())) {
            queryGoodsInvList(false);
        } else {
            searchGoods(false);
        }
    }

    @Override
    public void getMoreGoodsList() {
        pageNumTemp++;
        if (TextUtils.isEmpty(mView.getProductName())) {
            queryGoodsInvList(false);
        } else {
            searchGoods(false);
        }
    }

    @Override
    public void search() {
        pageNumTemp = 1;
        searchGoods(true);
    }


    public void searchGoods(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        GoodsListReq goodsListReq = new GoodsListReq();
        goodsListReq.setActionType("stockSelect");
        goodsListReq.setGroupID(userBean.getGroupID());
        goodsListReq.setHouseID(mView.getHouseId());
        goodsListReq.setPageNum(pageNumTemp);
        goodsListReq.setPageSize(pageSize);
        goodsListReq.setName(mView.getProductName());

        BaseReq<GoodsListReq> baseReq = new BaseReq<>();
        baseReq.setData(goodsListReq);
        GoodsService.INSTANCE
                .queryGoodsList(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<GoodsBean>>() {
                    @Override
                    public void onSuccess(List<GoodsBean> goodsBeans) {
                        mView.getGoodsListSuccess(goodsBeans, pageNumTemp > 1);
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
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void export(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("supplyStock");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.SupplyStock supplyStock = new ExportReq.ParamsBean.SupplyStock();
        supplyStock.setGroupID(userBean.getGroupID());
        supplyStock.setHouseID(mView.getHouseId());
        supplyStock.setSearchKey(mView.getProductName());
        paramsBean.setSupplyStock(supplyStock);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(Utils.getExportObserver(mView));
    }
}
