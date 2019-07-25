package com.hll_sc_app.app.setting.priceratio.add;

import com.hll_sc_app.api.PriceRatioTemplateService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.CategoryRatioListBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 设置比例模版
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public class PriceRatioTemplateAddPresenter implements PriceRatioTemplateAddContract.IPriceRatioTemplateAddPresenter {
    private PriceRatioTemplateAddContract.IPriceRatioTemplateAddView mView;

    static PriceRatioTemplateAddPresenter newInstance() {
        return new PriceRatioTemplateAddPresenter();
    }

    @Override
    public void start() {
        queryCustomCategory();
    }

    @Override
    public void register(PriceRatioTemplateAddContract.IPriceRatioTemplateAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCustomCategory() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("searchType", mView.getSearchType())
            .put("templateID", mView.getTemplateId())
            .put("templateType", mView.getTemplateType())
            .create();
        PriceRatioTemplateService.INSTANCE
            .queryRatioTemplateDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<CategoryRatioListBean>>() {
                @Override
                public void onSuccess(List<CategoryRatioListBean> resp) {
                    mView.processData(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editRatioTemplate(RatioTemplateBean bean) {
        if (bean == null) {
            return;
        }
        BaseReq<RatioTemplateBean> baseReq = new BaseReq<>();
        baseReq.setData(bean);
        PriceRatioTemplateService.INSTANCE
            .editPriceRatioTemplate(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("修改成功");
                    mView.addSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void addRatioTemplate(RatioTemplateBean bean) {
        if (bean == null) {
            return;
        }
        BaseReq<RatioTemplateBean> baseReq = new BaseReq<>();
        baseReq.setData(bean);
        PriceRatioTemplateService.INSTANCE
            .addPriceRatioTemplate(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("新增成功");
                    mView.addSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
