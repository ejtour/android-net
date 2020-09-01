package com.hll_sc_app.app.contractmanage.search;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

@Route(path = RouterConfig.ACTIVITY_CONTRACT_SEARCH)
public class ContractSearchActivity extends SearchActivity {
    private ContractSearchEmptyView searchEmptyView;

    public static void start(Activity activity, int requestCode, int index, String content) {
        Object[] args = {content, String.valueOf(index)};
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_SEARCH, activity, requestCode, args);
    }

    @Override
    protected void beforeInitView() {
        searchEmptyView = new ContractSearchEmptyView(this);
        searchEmptyView.setStringListener((result -> {
            mTitleBar.setHint(result);
        }));
        searchEmptyView.setCurIndex(Integer.parseInt(mKey));
    }

    @Override
    protected View getEmptyView() {
        return searchEmptyView;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.item_search;
    }

    @Override
    protected void beforeFinish(Intent intent) {
        intent.putExtra("index", searchEmptyView.getCurIndex());
    }
}
