package com.hll_sc_app.app.aptitude.goods;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;
import com.hll_sc_app.utils.Constants;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

class AptitudeGoodsPresenter implements IAptitudeGoodsContract.IAptitudeGoodsPresenter {
    private final Boolean mOnlyReceive;
    private IAptitudeGoodsContract.IAptitudeGoodsView mView;
    private int mPageNum;
    private List<GoodsBean> mCacheList;

    public static AptitudeGoodsPresenter newInstance() {
        return new AptitudeGoodsPresenter();
    }

    private AptitudeGoodsPresenter() {
        mOnlyReceive = GlobalPreference.getParam(Constants.ONLY_RECEIVE, false);
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IAptitudeGoodsContract.IAptitudeGoodsView view) {
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
                if (mOnlyReceive && list != null && list.size() >= 20) {
                    mCacheList = list;
                    pageCacheData();
                    return;
                }
                setData(list);
            }
        };
        if (mOnlyReceive) {
            if (mPageNum <= 1) {
                Aptitude.queryGoodsList(mView.getSearchWords(), mView.isChecked(), observer);
            } else {
                pageCacheData();
                mView.hideLoading();
            }
            return;
        }
        GoodsListReq req = new GoodsListReq();
        req.setPageSize(20);
        req.setPageNum(mPageNum);
        req.setActionType(mView.isChecked() ? "aptitude_have_set" : "aptitude_no_set");
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
