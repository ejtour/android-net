package com.hll_sc_app.app.deliverymanage.ageing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.deliverymanage.ageing.book.DeliveryAgeingBookFragment;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送时效管理-配送时效
 *
 * @author 朱英松
 * @date 2019/7/29
 */
@Route(path = RouterConfig.DELIVERY_AGEING_MANAGE, extras = Constant.LOGIN_EXTRA)
public class DeliveryAgeingActivity extends BaseLoadActivity {
    static final String[] STR_TITLE = {"配送时效", "预定设置"};
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.txt_add)
    TextView mTxtAdd;
    private List<BaseLazyFragment> mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_manage_ageing);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mListFragment = new ArrayList<>(STR_TITLE.length);
        mListFragment.add(DeliveryAgeingFragment.newInstance());
        mListFragment.add(DeliveryAgeingBookFragment.newInstance());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTxtAdd.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), mListFragment));
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!CommonUtils.isEmpty(mListFragment)) {
            DeliveryAgeingFragment lazyFragment = (DeliveryAgeingFragment) mListFragment.get(0);
            lazyFragment.refresh();
        }
    }

    @OnClick({R.id.img_close, R.id.txt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add:
                RouterUtil.goToActivity(RouterConfig.DELIVERY_AGEING_DETAIL, (getDeliveryAgeingCount() + 1));
                break;
            default:
                break;

        }
    }

    private int getDeliveryAgeingCount() {
        int position = 0;
        if (!CommonUtils.isEmpty(mListFragment)) {
            DeliveryAgeingFragment lazyFragment = (DeliveryAgeingFragment) mListFragment.get(0);
            position = lazyFragment.getItemCount();
        }
        return position;
    }

    class FragmentListAdapter extends FragmentPagerAdapter {
        private List<BaseLazyFragment> mListFragment;

        FragmentListAdapter(FragmentManager fm, List<BaseLazyFragment> list) {
            super(fm);
            this.mListFragment = list;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public BaseLazyFragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}