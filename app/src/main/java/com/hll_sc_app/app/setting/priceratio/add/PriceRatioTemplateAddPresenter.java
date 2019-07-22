package com.hll_sc_app.app.setting.priceratio.add;

import com.hll_sc_app.api.PriceRatioTemplateService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
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
public class PriceRatioTemplateAddPresenter implements PriceRatioTemplateAddContract.IGoodsStickPresenter {
    private PriceRatioTemplateAddContract.IGoodsStickView mView;
    private int mPageNum;
    private int mTempPageNum;

    static PriceRatioTemplateAddPresenter newInstance() {
        return new PriceRatioTemplateAddPresenter();
    }

    @Override
    public void start() {
        queryCustomCategory();
    }

    @Override
    public void register(PriceRatioTemplateAddContract.IGoodsStickView view) {
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
            .subscribe(new BaseCallback<List<CopyCategoryBean>>() {
                @Override
                public void onSuccess(List<CopyCategoryBean> resp) {
                    mView.processData(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
