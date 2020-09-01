package com.hll_sc_app.app.order.summary.search;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.order.OrderSummaryEmptyView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/25
 */

@Route(path = RouterConfig.ORDER_SUMMARY_SEARCH)
public class OrderSummarySearchActivity extends SearchActivity implements IOrderSummarySearchContract.IOrderSummarySearchView {
    private OrderSummaryEmptyView mEmptyView;

    public static void start(Activity context, String searchWords, String index) {
        Object[] args = {searchWords, index};
        RouterUtil.goToActivity(RouterConfig.ORDER_SUMMARY_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void beforeInitView() {
        mEmptyView = new OrderSummaryEmptyView(this);
        mEmptyView.setStringListener(result -> {
            mTitleBar.setHint(result);
        });
        mEmptyView.setCurIndex(Integer.parseInt(mKey));
    }

    @Override
    protected void afterInitView() {
        IOrderSummarySearchContract.IOrderSummarySearchPresenter presenter = new OrderSummarySearchPresenter();
        presenter.register(this);
        mTitleBar.subscribe(presenter::requestSearch);
    }

    @Override
    protected View getEmptyView() {
        return mEmptyView;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.item_search;
    }

    @Override
    protected void beforeFinish(Intent intent) {
        intent.putExtra("index", getIndex());
    }

    @Override
    public int getIndex() {
        return mEmptyView.getCurIndex();
    }
}
