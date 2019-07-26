package com.hll_sc_app.app.search.presenter;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public abstract class BaseSearchPresenter implements ISearchContract.ISearchPresenter {
    protected ISearchContract.ISearchView mView;

    public abstract void requestSearch(String searchWords);

    @Override
    public void register(ISearchContract.ISearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
