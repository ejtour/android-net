package com.hll_sc_app.app.goodsdemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;

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
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("新品反馈");
        for (int i = 0; i < 4; i++) {
            mFragments.add(GoodsDemandFragment.newInstance(i + 1));
        }
        mTabLayout.setViewPager(mViewPager, new String[]{"未处理", "已处理", "已上架", "已取消"}, this, mFragments);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        for (Fragment fragment : mFragments) {
            ((GoodsDemandFragment) fragment).reload();
        }
    }
}
