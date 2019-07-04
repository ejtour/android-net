package com.hll_sc_app.app.goods.relevance;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.goods.RelevancePurchaserResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
public class GoodsRelevancePurchaserPresenter implements GoodsRelevancePurchaserContract.IGoodsRelevancePurchaserPresenter {
    private GoodsRelevancePurchaserContract.IGoodsRelevancePurchaserView mView;
    private List<HouseBean> mListHouseBean;
    private int mPageNum;
    private int mTempPageNum;

    static GoodsRelevancePurchaserPresenter newInstance() {
        return new GoodsRelevancePurchaserPresenter();
    }

    @Override
    public void start() {
        queryPurchaserList(true);
    }

    @Override
    public void register(GoodsRelevancePurchaserContract.IGoodsRelevancePurchaserView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserList(showLoading);
    }

    @Override
    public void queryMorePurchaserList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserList(false);
    }

    private void toQueryPurchaserList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupName", mView.getGroupName())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("plateSupplierID", UserConfig.getGroupID())
            .put("resourceType", mView.getResourceType())
            .create();
        GoodsService.INSTANCE.queryGoodsRelevancePurchaserList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<RelevancePurchaserResp>() {
                @Override
                public void onSuccess(RelevancePurchaserResp goodsInvResp) {
                    mPageNum = mTempPageNum;
                    mView.showPurchaserList(goodsInvResp.getRecords(), mPageNum != 1, goodsInvResp.getTotal());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
