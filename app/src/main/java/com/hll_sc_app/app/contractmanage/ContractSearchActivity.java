package com.hll_sc_app.app.contractmanage;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;

@Route(path = RouterConfig.ACTIVITY_CONTRACT_SEARCH)
public class ContractSearchActivity extends SearchActivity {
    @Autowired(name = "index")
    int mIndex;
    @Autowired(name = "content")
    String mContent;
    private ContractSearchEmptyView searchEmptyView;

    public static void start(Activity activity, int requestCode, int index, String content) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CONTRACT_SEARCH)
                .withInt("index", index)
                .withString("content", content)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void beforeInitView() {
        mSearchWords = mContent;
        searchEmptyView = new ContractSearchEmptyView(this);
        searchEmptyView.setCurIndex(mIndex);

        searchEmptyView.setStringListener((result -> {
            mTitleBar.setHint(result);

        }));
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
