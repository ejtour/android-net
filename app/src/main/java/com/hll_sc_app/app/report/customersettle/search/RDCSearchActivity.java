package com.hll_sc_app.app.report.customersettle.search;

import android.app.Activity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.widget.EmptyView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */
@Route(path = RouterConfig.REPORT_CUSTOMER_SETTLE_SEARCH)
public class RDCSearchActivity extends SearchActivity implements IRDCSearchContract.IRDCSearchView {
    private IRDCSearchContract.IRDCSearchPresenter mPresenter;
    public static void start(Activity context, String searchWords, String purchaserID) {
        Object[] args = {searchWords, purchaserID};
        RouterUtil.goToActivity(RouterConfig.REPORT_CUSTOMER_SETTLE_SEARCH, context, REQ_CODE, args);
    }

    @Override
    public String getExtGroupID() {
        return mKey;
    }

    @Override
    protected void beforeInitView() {
        mTitleBar.setHint("搜索配送中心、门店");
    }

    @Override
    protected void afterInitView() {
        mPresenter = new RDCSearchPresenter();
        mPresenter.register(this);
        mTitleBar.subscribe(mPresenter::requestSearch);
        mTitleBar.setOnSearchListener(() -> mPresenter.requestSearch(mTitleBar.getSearchContent()));
    }

    @Override
    protected View getEmptyView() {
        return EmptyView.newBuilder(this)
                .setTips("搜索配送中心、门店")
                .setImage(R.drawable.ic_empty_shop_view)
                .create();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.item_search;
    }
}
