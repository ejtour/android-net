package com.hll_sc_app.app.crm.customer.seas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.crm.customer.intent.CustomerIntentFragment;
import com.hll_sc_app.app.search.stratery.IntentCustomerSearch;
import com.hll_sc_app.app.search.stratery.PartnerSearch;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;

import java.util.Arrays;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

@Route(path = RouterConfig.CRM_CUSTOMER_SEAS)
public class CustomerSeasActivity extends BaseCustomerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mSearchView.setHint("搜索客户");
        mTitleBar.setRightBtnVisible(false);
        mFragments = Arrays.asList(CustomerIntentFragment.newInstance(2), CustomerSeasFragment.newInstance());
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(),
                mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"意向客户", "合作客户"});
    }

    @Override
    public void reload(boolean includeCurrent) {
        ((CustomerIntentFragment) mFragments.get(0)).reload();
        ((CustomerSeasFragment) mFragments.get(1)).reload();
    }

    @Override
    protected String getSearchKey() {
        return mViewPager.getCurrentItem() == 0 ? IntentCustomerSearch.class.getSimpleName() : PartnerSearch.class.getSimpleName();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        reload(true);
    }
}
