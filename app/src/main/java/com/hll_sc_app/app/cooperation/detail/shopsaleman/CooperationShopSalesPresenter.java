package com.hll_sc_app.app.cooperation.detail.shopsaleman;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情- 批量指派销售
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
public class CooperationShopSalesPresenter implements CooperationShopSalesContract.ICooperationAddShopPresenter {
    private CooperationShopSalesContract.ICooperationAddShopView mView;
    private int mPageNum;
    private int mTempPageNum;

    static CooperationShopSalesPresenter newInstance() {
        return new CooperationShopSalesPresenter();
    }

    @Override
    public void start() {
        queryPurchaserShopList(true);
    }

    @Override
    public void register(CooperationShopSalesContract.ICooperationAddShopView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserShopList(boolean show) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserShopList(show);
    }

    private void toQueryPurchaserShopList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", mView.getPurchaserId())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("searchParam", mView.getSearchParam())
            .put("actionType", "addCooperation")
            .create();
        AgreementPriceService.INSTANCE.queryCooperationPurchaserShopList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserShopBean>>() {
                @Override
                public void onSuccess(List<PurchaserShopBean> result) {
                    mPageNum = mTempPageNum;
                    mView.showEmployeeList(result, mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void queryMorePurchaserShopList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserShopList(false);
    }
}
