package com.hll_sc_app.app.goodsdemand.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

@Route(path = RouterConfig.GOODS_DEMAND_PURCHASER_SEARCH)
public class PurchaserSearchActivity extends BaseLoadActivity {
    public static final int REQ_CODE = 0x670;
    @BindView(R.id.dps_search_view)
    SearchView mSearchView;
    @BindView(R.id.dps_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.dps_view_pager)
    ViewPager mViewPager;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    int mIndex;
    private List<Fragment> mFragments = new ArrayList<>();

    /**
     * @param id 已选择的采购商id
     */
    public static void start(Activity context, String id, int index) {
        Object[] args = {id, index};
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_PURCHASER_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_demand_purchaser_search);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(PurchaserSearchActivity.this,
                        searchContent, PurchaserSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                for (Fragment fragment : mFragments) {
                    ((PurchaserSearchFragment) fragment).reload();
                }
            }
        });
        mFragments.add(PurchaserSearchFragment.newInstance(0, mID));
        mFragments.add(PurchaserSearchFragment.newInstance(1, mID));
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"合作客户", "意向客户"});
        mViewPager.setCurrentItem(mIndex);
    }

    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }
}
