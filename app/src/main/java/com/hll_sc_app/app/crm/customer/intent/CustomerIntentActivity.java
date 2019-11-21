package com.hll_sc_app.app.crm.customer.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.IntentCustomerSearch;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.SearchView;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

@Route(path = RouterConfig.CRM_CUSTOMER_INTENT)
public class CustomerIntentActivity extends BaseCustomerActivity {

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) reload();
    }

    private void initView() {
        mSearchView.setHint("搜索客户");
        mTitleBar.setRightBtnClick(v -> RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_INTENT_ADD));
        mFragments = Arrays.asList(CustomerIntentFragment.newInstance(false), CustomerIntentFragment.newInstance(true));
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(),
                mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"我的意向客户", "全部意向客户"});
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CustomerIntentActivity.this, searchContent, IntentCustomerSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                reload();
            }
        });
    }

    private void reload() {
        for (Fragment fragment : mFragments) {
            ((CustomerIntentFragment) fragment).reload();
        }
    }

    String getSearchWords() {
        return mSearchView.getSearchContent();
    }
}
