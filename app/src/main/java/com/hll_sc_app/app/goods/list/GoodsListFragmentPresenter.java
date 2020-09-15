package com.hll_sc_app.app.goods.list;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.goods.SpecsStatusReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 首页商品管理列表Fragment
 *
 * @author 朱英松
 * @date 2018/6/11
 */
public class GoodsListFragmentPresenter implements GoodsListFragmentContract.IGoodsListPresenter {
    private GoodsListFragmentContract.IGoodsListView mView;
    private int mPageNum;
    private int mTempPageNum;

    private GoodsListFragmentPresenter() {
    }

    public static GoodsListFragmentPresenter newInstance() {
        return new GoodsListFragmentPresenter();
    }

    @Override
    public void start() {
        queryGoodsList(true);
    }

    @Override
    public void register(GoodsListFragmentContract.IGoodsListView view) {
        this.mView = view;
    }

    @Override
    public void queryGoodsList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryGoodsList(showLoading);
    }

    @Override
    public void queryMoreGoodsList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryGoodsList(false);
    }

    @Override
    public void updateSpecStatus(List<SpecsBean> list) {
        if (CommonUtils.isEmpty(list)) {
            return;
        }
        getUpdateSpecStatusObservable(list)
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast(getTipPrefix(list) + "成功");
                    queryGoodsList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    public static String getTipPrefix(List<SpecsBean> list){
        String tipLabel = "下架";
        for (SpecsBean bean : list) {
            if (bean.getSpecStatus().equals(SpecsBean.SPEC_STATUS_DOWN)) {
                tipLabel = "上架";
                break;
            }
        }
        return tipLabel;
    }

    /**
     * 上下架状态修改
     *
     * @param list 规格列表
     * @return Observable
     */
    public static Observable<Object> getUpdateSpecStatusObservable(List<SpecsBean> list) {
        BaseReq<SpecsStatusReq> baseReq = new BaseReq<>();
        SpecsStatusReq req = new SpecsStatusReq();
        List<SpecsStatusReq.SpecsStatusItem> records = new ArrayList<>();
        for (SpecsBean specsBean : list) {
            SpecsStatusReq.SpecsStatusItem item = new SpecsStatusReq.SpecsStatusItem();
            item.setProductID(specsBean.getProductID());
            item.setSpecID(specsBean.getSpecID());
            item.setSpecStatus(TextUtils.equals(specsBean.getSpecStatus(), SpecsBean.SPEC_STATUS_UP) ?
                SpecsBean.SPEC_STATUS_DOWN : SpecsBean.SPEC_STATUS_UP);
            records.add(item);
        }
        req.setRecords(records);
        baseReq.setData(req);
        return GoodsService.INSTANCE.updateSpecStatus(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    private void toQueryGoodsList(boolean showLoading) {
        BaseReq<GoodsListReq> baseReq = new BaseReq<>();
        GoodsListReq req = new GoodsListReq();
        req.setPageNum(mTempPageNum);
        req.setName(mView.getName());
        req.setProductStatus(mView.getProductStatus());
        req.setActionType(mView.getActionType());
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
                public void onSuccess(List<GoodsBean> resp) {
                    mPageNum = mTempPageNum;
                    mView.showList(resp, mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
