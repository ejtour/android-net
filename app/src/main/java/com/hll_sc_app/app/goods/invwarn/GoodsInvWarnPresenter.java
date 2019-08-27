package com.hll_sc_app.app.goods.invwarn;

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
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsInvWarnReq;
import com.hll_sc_app.bean.goods.GoodsInvWarnResp;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
public class GoodsInvWarnPresenter implements GoodsInvWarnContract.IGoodsInvWarnPresenter {
    private GoodsInvWarnContract.IGoodsInvWarnView mView;
    private List<HouseBean> mListHouseBean;
    private int mPageNum;
    private int mTempPageNum;

    static GoodsInvWarnPresenter newInstance() {
        return new GoodsInvWarnPresenter();
    }

    @Override
    public void start() {
        queryHouseList(false);
    }

    @Override
    public void register(GoodsInvWarnContract.IGoodsInvWarnView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryHouseList(boolean show) {
        if (show && !CommonUtils.isEmpty(mListHouseBean)) {
            mView.showHouseWindow(mListHouseBean);
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder().put("groupID", UserConfig.getGroupID()).create();
        GoodsService.INSTANCE.queryHouseList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<HouseBean>>() {
                    @Override
                    public void onSuccess(List<HouseBean> houseBeans) {
                        mListHouseBean = houseBeans;
                        if (show) {
                            mView.showHouseWindow(mListHouseBean);
                        } else {
                            if (CommonUtils.isEmpty(mListHouseBean)) {
                                mView.showToast("没有仓库数据");
                            } else {
                                mListHouseBean.get(0).setSelect(true);
                                mView.showSelectHouse(mListHouseBean.get(0));
                            }
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryGoodsInvList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryGoodsInvList(showLoading);
    }

    @Override
    public void queryMoreGoodsInvList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryGoodsInvList(false);
    }

    @Override
    public void searchGoodsInvList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toSearchGoodsInvList(showLoading);
    }

    @Override
    public void searchMoreGoodsInvList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toSearchGoodsInvList(false);
    }

    @Override
    public void setGoodsInvWarnValue(List<GoodsBean> list) {
        if (CommonUtils.isEmpty(list)) {
            return;
        }
        GoodsInvWarnReq req = new GoodsInvWarnReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setHouseID(mView.getHouseId());
        List<GoodsInvWarnReq.ListBean> listBeans = new ArrayList<>();
        for (GoodsBean goodsBean : list) {
            GoodsInvWarnReq.ListBean bean = new GoodsInvWarnReq.ListBean();
            bean.setProductID(goodsBean.getProductID());
            bean.setStockWarnNum(goodsBean.getStockWarnNum());
            listBeans.add(bean);
        }
        req.setList(listBeans);
        BaseReq<GoodsInvWarnReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.setGoodsInvWarnValue(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.saveSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void export(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setActionType("2");
        req.setEmail(email);
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setTypeCode("stock_warn_num");
        req.setUserID(userBean.getEmployeeID());
        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.StockWarnNum stockWarnNum = new ExportReq.ParamsBean.StockWarnNum();
        stockWarnNum.setGroupID(UserConfig.getGroupID());
        stockWarnNum.setHouseID(mView.getHouseId());
        paramsBean.setStockWarnNum(stockWarnNum);
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
                            mView.exportSuccess(resp.getEmail());
                        } else {
                            mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (TextUtils.equals("00120112037", e.getCode())) {
                            mView.bindEmail();
                        } else if (TextUtils.equals("00120112038", e.getCode())) {
                            mView.exportFailure("当前没有可导出的数据");
                        } else {
                            mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                        }
                    }
                });
    }

    private void toSearchGoodsInvList(boolean showLoading) {
        GoodsListReq req = new GoodsListReq();
        req.setPageNum(mTempPageNum);
        req.setActionType("warehoue_stockwarnnum");
        req.setHouseID(mView.getHouseId());
        req.setName(mView.getName());
        BaseReq<GoodsListReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.queryGoodsList(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<GoodsBean>>() {
                    @Override
                    public void onSuccess(List<GoodsBean> list) {
                        mPageNum = mTempPageNum;
                        mView.showGoodsInvList(list, mPageNum != 1, 0);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    private void toQueryGoodsInvList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "warehoue_stockwarnnum")
                .put("houseID", mView.getHouseId())
                .put("pageNum", String.valueOf(mTempPageNum))
                .put("pageSize", "20")
                .create();
        GoodsService.INSTANCE.queryGoodsInvList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<GoodsInvWarnResp>() {
                    @Override
                    public void onSuccess(GoodsInvWarnResp goodsInvResp) {
                        mPageNum = mTempPageNum;
                        mView.showGoodsInvList(goodsInvResp.getList(), mPageNum != 1, goodsInvResp.getTotalSize());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
