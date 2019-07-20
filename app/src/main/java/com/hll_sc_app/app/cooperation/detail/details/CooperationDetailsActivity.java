package com.hll_sc_app.app.cooperation.detail.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.details.basic.CooperationDetailsBasicFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情-详细资料
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, extras = Constant.LOGIN_EXTRA)
public class CooperationDetailsActivity extends BaseLoadActivity {
    static final String[] STR_TITLE = {"基本信息", "认证信息"};
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Autowired(name = "parcelable", required = true)
    CooperationPurchaserDetail mDetail;
    private List<BaseCooperationDetailsFragment> mListFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_details);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mListFragment = new ArrayList<>();
        mListFragment.add(CooperationDetailsBasicFragment.newInstance(mDetail));
        mListFragment.add(CooperationDetailsBasicFragment.newInstance(mDetail));
        mViewPager.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), mListFragment));
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }

    class FragmentListAdapter extends FragmentPagerAdapter {
        private List<BaseCooperationDetailsFragment> mListFragment;

        FragmentListAdapter(FragmentManager fm, List<BaseCooperationDetailsFragment> list) {
            super(fm);
            this.mListFragment = list;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public BaseCooperationDetailsFragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}
