package com.hll_sc_app.app.stockmanage.stockchecksetting;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.stockmanage.RemoveStockCheckSettingReq;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class StockCheckSettingPresent implements IStockCheckSettingContract.IPresent {

    private IStockCheckSettingContract.IView mView;

    private int pageSize = 20;
    private int pageNum = 1;
    private int pageNumTemp = 1;

    public static StockCheckSettingPresent newInstance() {
        return new StockCheckSettingPresent();
    }

    @Override
    public void start() {
        queryGoodsResp(true);
    }

    @Override
    public void register(IStockCheckSettingContract.IView view) {
        mView = view;
    }


    @Override
    public void queryGoodsResp(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }

        GoodsListReq goodsListReq = new GoodsListReq();
        goodsListReq.setActionType("stockCheck");
        goodsListReq.setPageNum(pageNumTemp);
        goodsListReq.setPageSize(pageSize);
        goodsListReq.setGroupID(userBean.getGroupID());
        goodsListReq.setName(mView.getSearchContent());
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
                        mView.queryGoodsSuccess(goodsBeans, pageNumTemp > 1);
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
    public void getMore() {
        pageNumTemp++;
        queryGoodsResp(false);
    }

    @Override
    public void refresh() {
        pageNumTemp = 1;
        queryGoodsResp(false);
    }

    @Override
    public void changeStockCheckSetting(String actionType, List<String> ids) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        RemoveStockCheckSettingReq removeStockCheckSettingReq = new RemoveStockCheckSettingReq();
        removeStockCheckSettingReq.setActionType(actionType);
        removeStockCheckSettingReq.setGroupID(userBean.getGroupID());
        removeStockCheckSettingReq.setProductIDList(ids);

        BaseReq<RemoveStockCheckSettingReq> baseReq = new BaseReq<>();
        baseReq.setData(removeStockCheckSettingReq);
        StockManageService.INSTANCE
                .changeStockCheckSetting(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<BaseResp<Object>>() {
                    @Override
                    public void onSuccess(BaseResp<Object> objectBaseResp) {
                        if (TextUtils.equals(actionType, "ADD")) {
                            mView.addSuccess();

                        } else if (TextUtils.equals(actionType, "DELETE")) {
                            mView.removeSuccess();
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void remove() {
        changeStockCheckSetting("DELETE", mView.getProductIds());
    }

    @Override
    public void add(List<String> ids) {
        changeStockCheckSetting("ADD", ids);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
