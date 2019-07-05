package com.hll_sc_app.app.goods.relevance.goods.fragment.relevance;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsRelevanceBean;
import com.hll_sc_app.bean.goods.GoodsRelevanceResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * 第三方商品关联-采购商列表-关联商品列表-已关联
 *
 * @author zhuyingsong
 * @date 2019/7/5
 */
public class GoodsRelevanceListFragmentPresenter implements GoodsRelevanceListFragmentContract.IGoodsRelevanceListPresenter {
    private GoodsRelevanceListFragmentContract.IGoodsRelevanceListView mView;
    private int mPageNum;
    private int mTempPageNum;

    static GoodsRelevanceListFragmentPresenter newInstance() {
        return new GoodsRelevanceListFragmentPresenter();
    }

    @Override
    public void start() {
        queryGoodsRelevanceList(true);
    }

    @Override
    public void register(GoodsRelevanceListFragmentContract.IGoodsRelevanceListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGoodsRelevanceList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryGoodsUnRelevanceList(showLoading);
    }

    @Override
    public void queryMoreGoodsRelevanceList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryGoodsUnRelevanceList(false);
    }

    @Override
    public void removeGoodsRelevance(GoodsRelevanceBean bean) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("goodsCode", bean.getGoodsCode())
            .put("id", bean.getId())
            .put("plateSupplierID", bean.getPlateSupplierID())
            .put("resourceType", bean.getResourceType())
            .put("thirdGroupID", bean.getThirdGroupID())
            .create();
        GoodsService.INSTANCE.removeGoodsRelevance(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object goodsInvResp) {
                    mView.removeRelevanceSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
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
        GoodsService.INSTANCE.queryGoodsRelevanceList(req)
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
