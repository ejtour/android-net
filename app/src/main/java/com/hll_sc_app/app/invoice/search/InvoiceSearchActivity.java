package com.hll_sc_app.app.invoice.search;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.invoice.InvoiceShopEmptyView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/19
 */

@Route(path = RouterConfig.INVOICE_SEARCH)
public class InvoiceSearchActivity extends SearchActivity {
    private InvoiceShopEmptyView mEmptyView;

    public static void start(Activity context, String searchWords, String index) {
        Object[] args = {searchWords, index};
        RouterUtil.goToActivity(RouterConfig.INVOICE_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void beforeInitView() {
        mEmptyView = new InvoiceShopEmptyView(this);
        mEmptyView.setStringListener(result -> {
            mTitleBar.setHint(result);
            if (getIndex() == 2) {
                String string = mTitleBar.getSearchContent();
                String text = string.replaceAll("\\D+", "");
                mTitleBar.updateSearchWords(text);
                mTitleBar.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            } else mTitleBar.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        });
        mEmptyView.setCurIndex(Integer.parseInt(mKey));
    }

    protected int getIndex() {
        return mEmptyView.getCurIndex();
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
    protected View getEmptyView() {
        return mEmptyView;
    }
}
