package com.hll_sc_app.app.setting.tax;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.user.TaxSaveReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/30
 */

public class TaxSettingPresenter implements ITaxSettingContract.ITaxSettingPresenter {
    private ITaxSettingContract.ITaxSettingView mView;

    @Override
    public void start() {
        SimpleObserver<CustomCategoryResp> observer = new SimpleObserver<CustomCategoryResp>(mView) {
            @Override
            public void onSuccess(CustomCategoryResp customCategoryResp) {
                customCategoryResp.processList();
                mView.setCategoryList(customCategoryResp.getList2());
            }
        };
        GoodsService.INSTANCE
                .queryCustomCategory(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);

    }

    @Override
    public void register(ITaxSettingContract.ITaxSettingView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void save(TaxSaveReq req) {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object object) {
                mView.saveSuccess();
            }
        };
        UserService.INSTANCE
                .saveTaxRate(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
