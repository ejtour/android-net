package com.hll_sc_app.app.aptitude.goods.add;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/1/20.
 */
class AptitudeGoodsAddPresenter implements IAptitudeGoodsAddContract.IAptitudeGoodsAddPresenter {
    private IAptitudeGoodsAddContract.IAptitudeGoodsAddView mView;
    private int mPageNum;
    private List<GoodsBean> mCacheList;

    public static AptitudeGoodsAddPresenter newInstance() {
        return new AptitudeGoodsAddPresenter();
    }

    private AptitudeGoodsAddPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IAptitudeGoodsAddContract.IAptitudeGoodsAddView view) {
        mView = CommonUtils.requireNonNull(view);
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

    private void pageCacheData() {
        int beginIndex = (mPageNum - 1) * 20;
        int size = mCacheList.size();
        int endIndex = Math.min(size, mPageNum * 20);
        setData(beginIndex >= size ? new ArrayList<>() : new ArrayList<>(mCacheList.subList(beginIndex, endIndex)));
    }

    private void setData(List<GoodsBean> list) {
        mView.setData(list, mPageNum > 1);
        if (CommonUtils.isEmpty(list)) return;
        mPageNum++;
    }

    private void load(boolean showLoading) {
        SimpleObserver<List<GoodsBean>> observer = new SimpleObserver<List<GoodsBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<GoodsBean> list) {
                if (UserConfig.isOnlyReceive() && list != null && list.size() >= 20) {
                    mCacheList = list;
                    pageCacheData();
                    return;
                }
                setData(list);
            }
        };
        if (UserConfig.isOnlyReceive()) {
            if (mPageNum <= 1) {
                Aptitude.queryGoodsList(mView.getSearchWords(), observer);
            } else {
                pageCacheData();
                mView.hideLoading();
            }
            return;
        }
        GoodsListReq req = new GoodsListReq();
        req.setPageSize(20);
        req.setPageNum(mPageNum);
        req.setGroupID(UserConfig.getGroupID());
        req.setIsWareHourse("0");
        req.setName(mView.getSearchWords());
        GoodsService.INSTANCE
                .queryGoodsList(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
