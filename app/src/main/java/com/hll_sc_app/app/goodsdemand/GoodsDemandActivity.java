package com.hll_sc_app.app.goodsdemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.detail.GoodsDemandDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

@Route(path = RouterConfig.GOODS_DEMAND)
public class GoodsDemandActivity extends BaseLoadActivity {
    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("新品反馈");
        mFragments.add(GoodsDemandFragment.newInstance(1));
        mFragments.add(GoodsDemandFragment.newInstance(2));
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"未处理", "已处理"});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GoodsDemandDetailActivity.REQ_CODE) {
            for (Fragment fragment : mFragments) {
                ((GoodsDemandFragment) fragment).reload();
            }
        }
    }
}
