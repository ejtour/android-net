package com.hll_sc_app.app.price;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.price.domestic.PriceDomesticFragment;
import com.hll_sc_app.app.price.local.IPriceLocalContract;
import com.hll_sc_app.app.price.local.PriceLocalFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */
@Route(path = RouterConfig.PRICE)
public class PriceActivity extends BaseLoadActivity {

    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("市场价格");
        fragmentList.add(new PriceLocalFragment());
        fragmentList.add(new PriceDomesticFragment());
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), fragmentList));
        mTabLayout.setViewPager(mViewPager, new String[]{"当地价格", "国内价格"});
        ((ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams()).topMargin = UIUtils.dip2px(5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)){
                ((IPriceLocalContract.IPriceLocalView)fragmentList.get(0)).search(name);
            }

        }
    }
}
