package com.hll_sc_app.app.aptitude.goods;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

class AptitudeGoodsPresenter implements IAptitudeGoodsContract.IAptitudeGoodsPresenter {
    private IAptitudeGoodsContract.IAptitudeGoodsView mView;
    private int mPageNum;

    public static AptitudeGoodsPresenter newInstance() {
        return new AptitudeGoodsPresenter();
    }

    private AptitudeGoodsPresenter() {
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

    private void load(boolean showLoading) {
        GoodsListReq req = new GoodsListReq();
        req.setPageSize(20);
        req.setPageNum(mPageNum);
        req.setActionType(mView.getActionType());
        req.setGroupID(UserConfig.getGroupID());
        req.setIsWareHourse("0");
        req.setName(mView.getSearchWords());
        SimpleObserver<List<GoodsBean>> observer = new SimpleObserver<List<GoodsBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<GoodsBean> list) {
                mView.setData(list, mPageNum > 1);
                if (CommonUtils.isEmpty(list)) return;
                mPageNum++;
            }
        };
        GoodsService.INSTANCE
                .queryGoodsList(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
