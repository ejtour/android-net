package com.hll_sc_app.app.deliverymanage.minimum.search;

import android.app.Activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.invoice.search.InvoiceSearchActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/24
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM_SEARCH)
public class DeliveryMinSearchActivity extends InvoiceSearchActivity implements IDeliveryMinSearchContract.IDeliveryMinSearchView {

    private IDeliveryMinSearchContract.IDeliveryMinSearchPresenter mPresenter;

    public static void start(Activity context, String searchWords, String index) {
        Object[] args = {searchWords, index};
        RouterUtil.goToActivity(RouterConfig.DELIVERY_MINIMUM_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void afterInitView() {
        mPresenter = DeliveryMinSearchPresenter.newInstance();
        mPresenter.register(this);
        mTitleBar.subscribe(mPresenter::requestSearch);
        mTitleBar.setOnSearchListener(() -> mPresenter.requestSearch(mTitleBar.getSearchContent()));
    }

    @Override
    public int getIndex() {
        return super.getIndex();
    }
}
