package com.hll_sc_app.app.cooperation.detail.shopsaleman;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情- 批量指派销售/司机
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
        queryEmployeeList(true);
    }

    @Override
    public void register(CooperationShopSalesContract.ICooperationAddShopView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryEmployeeList(boolean show) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserShopList(show);
    }

    private void toQueryPurchaserShopList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("keyword", mView.getKeyWord())
            .put("roleType", mView.getRoleType())
            .create();
        CooperationPurchaserService.INSTANCE
            .queryEmployeeList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<EmployeeBean>>() {
                @Override
                public void onSuccess(List<EmployeeBean> result) {
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
    public void queryMoreEmployeeList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserShopList(false);
    }

    @Override
    public void editShopEmployee(ShopSettlementReq req) {
        if (req == null) {
            return;
        }
        BaseReq<ShopSettlementReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService
            .INSTANCE
            .editShopEmployee(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object object) {
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
