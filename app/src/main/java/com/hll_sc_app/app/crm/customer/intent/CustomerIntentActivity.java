package com.hll_sc_app.app.crm.customer.intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.crm.customer.intent.add.AddCustomerActivity;
import com.hll_sc_app.app.search.stratery.IntentCustomerSearch;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;

import java.util.Arrays;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

@Route(path = RouterConfig.CRM_CUSTOMER_INTENT)
public class CustomerIntentActivity extends BaseCustomerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mSearchView.setHint("搜索客户");
        mTitleBar.setRightBtnClick(v -> AddCustomerActivity.start(this, null));
        mFragments = Arrays.asList(CustomerIntentFragment.newInstance(0), CustomerIntentFragment.newInstance(1));
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(),
                mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"我的意向客户", "全部意向客户"});
    }

    @Override
    public void reload(boolean includeCurrent) {
        for (Fragment fragment : mFragments) {
            ((CustomerIntentFragment) fragment).reload();
        }
    }

    @Override
    protected String getSearchKey() {
        return IntentCustomerSearch.class.getSimpleName();
    }
}
