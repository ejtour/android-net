package com.hll_sc_app.app.order.search;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.order.OrderSearchEmptyView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/25
 */

@Route(path = RouterConfig.ORDER_SEARCH)
public class OrderSearchActivity extends SearchActivity implements IOrderSearchContract.IOrderSearchView {
    private OrderSearchEmptyView mEmptyView;

    public static void start(Activity context, String searchWords, String index) {
        Object[] args = {searchWords, index};
        RouterUtil.goToActivity(RouterConfig.ORDER_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void beforeInitView() {
        mSearchWords = getIntent().getStringExtra("object0");
        mKey = getIntent().getStringExtra("object1");
        mEmptyView = new OrderSearchEmptyView(this);
        mEmptyView.setStringListener(result -> {
            mSearchEdit.setHint(result);
            if (getIndex() == 2) {
                String string = mSearchEdit.getText().toString();
                String text = string.replaceAll("\\D+", "");
                mSearchEdit.setText(text);
                mSearchEdit.setSelection(text.length());
                mSearchEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            } else mSearchEdit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        });
        mEmptyView.setCurIndex(Integer.parseInt(mKey));
        IOrderSearchContract.IOrderSearchPresenter presenter = new OrderSearchPresenter();
        presenter.register(this);
        mDisposable = getTextObservable().subscribe(presenter::requestSearch);
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
