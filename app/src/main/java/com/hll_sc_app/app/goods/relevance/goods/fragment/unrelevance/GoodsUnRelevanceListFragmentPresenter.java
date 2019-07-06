package com.hll_sc_app.app.goods.relevance.goods.fragment.unrelevance;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsRelevanceResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * 第三方商品关联-采购商列表-关联商品列表-未关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public class GoodsUnRelevanceListFragmentPresenter implements GoodsUnRelevanceListFragmentContract.IGoodsRelevanceListPresenter {
    private GoodsUnRelevanceListFragmentContract.IGoodsRelevanceListView mView;
    private int mPageNum;
    private int mTempPageNum;

    static GoodsUnRelevanceListFragmentPresenter newInstance() {
        return new GoodsUnRelevanceListFragmentPresenter();
    }

    @Override
    public void start() {
        queryGoodsUnRelevanceList(true);
    }

    @Override
    public void register(GoodsUnRelevanceListFragmentContract.IGoodsRelevanceListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGoodsUnRelevanceList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryGoodsUnRelevanceList(showLoading);
    }

    @Override
    public void queryMoreGoodsUnRelevanceList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryGoodsUnRelevanceList(false);
    }

    @Override
    public void reqDoNotRelevance(String detailID) {
        Order.tagDoNotRelevance(detailID, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.reloadTransferDetail();
            }
        });
    }

    private void toQueryGoodsUnRelevanceList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("groupID", mView.getGroupId())
            .put("operateModel", mView.getOperateModel())
            .put("resourceType", mView.getResourceType())
            .put("plateSupplierID", UserConfig.getGroupID())
            .put("goodsName", mView.getGoodsName())
            .create();
        GoodsService.INSTANCE.queryGoodsUnRelevanceList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<GoodsRelevanceResp>() {
                @Override
                public void onSuccess(GoodsRelevanceResp goodsInvResp) {
                    mPageNum = mTempPageNum;
                    mView.showGoodsList(goodsInvResp.getRecords(), mPageNum != 1, goodsInvResp.getTotal());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
