package com.hll_sc_app.app.report.profit.category;

import android.text.TextUtils;

import com.hll_sc_app.app.user.register.RegisterComplementPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.profit.ProfitResp;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public class CategoryProfitPresenter implements ICategoryProfitContract.ICategoryProfitPresenter {
    private ICategoryProfitContract.ICategoryProfitView mView;
    private int mPageNum;

    public static CategoryProfitPresenter newInstance() {
        return new CategoryProfitPresenter();
    }

    private CategoryProfitPresenter() {
    }

    @Override
    public void start() {
        loadList();
        queryCategory();
    }

    private void queryList(boolean showLoading) {
        Report.queryProfit(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<ProfitResp>(mView, showLoading) {
            @Override
            public void onSuccess(ProfitResp receiveDiffDetailsResp) {
                mView.setData(receiveDiffDetailsResp, mPageNum > 1);
                if (!CommonUtils.isEmpty(receiveDiffDetailsResp.getRecords())) {
                    mPageNum++;
                }
            }
        });
    }

    @Override
    public void queryCategory() {
        RegisterComplementPresenter.getCategoryObservable()
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CategoryResp>() {
                    @Override
                    public void onSuccess(CategoryResp resp) {
                        mView.setCategory(resp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void loadList() {
        mPageNum = 1;
        queryList(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            User.bindEmail(email, new SimpleObserver<Object>(mView) {
                @Override
                public void onSuccess(Object o) {
                    export(null);
                }
            });
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111026", email, Utils.getExportObserver(mView));
    }

    @Override
    public void register(ICategoryProfitContract.ICategoryProfitView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
