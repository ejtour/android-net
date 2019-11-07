package com.hll_sc_app.app.setting.tax.goodsselect;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public class GoodsSelectPresenter implements IGoodsSelectContract.IGoodsSelectPresenter {
    private IGoodsSelectContract.IGoodsSelectView mView;
    private int mPageNum;

    private GoodsSelectPresenter() {
    }

    public static GoodsSelectPresenter newInstance() {
        return new GoodsSelectPresenter();
    }

    private void load(boolean showLoading) {
        SimpleObserver<List<GoodsBean>> observer = new SimpleObserver<List<GoodsBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<GoodsBean> list) {
                mView.setData(list, mPageNum > 1);
                if (CommonUtils.isEmpty(list)) return;
                mPageNum++;
            }
        };
        GoodsListReq req = new GoodsListReq();
        req.setCategorySubID(mView.getID());
        req.setPageNum(mPageNum);
        req.setName(mView.getSearchWords());
        GoodsService.INSTANCE.queryGoodsList(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void start() {
        SimpleObserver<CategoryResp> observer = new SimpleObserver<CategoryResp>(mView) {
            @Override
            public void onSuccess(CategoryResp categoryResp) {
                mView.setCategory(categoryResp.getList2());
            }
        };
        UserService.INSTANCE.queryCategory(BaseMapReq.newBuilder().create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IGoodsSelectContract.IGoodsSelectView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void reload() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }
}
