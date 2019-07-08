package com.hll_sc_app.app.agreementprice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-协议价管理
 *
 * @author zhuyingsong
 * @date 2019/7/8
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE, extras = Constant.LOGIN_EXTRA)
public class AgreementPriceActivity extends BaseLoadActivity {
    @BindView(R.id.tl_title)
    SlidingTabLayout mTlTitle;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.img_add)
    ImageView mImgAdd;
    private PagerAdapter mAdapter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manager);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        ArrayList<BaseAgreementPriceFragment> list = new ArrayList<>(2);
        list.add((BaseAgreementPriceFragment) RouterUtil.getFragment(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION));
        list.add((BaseAgreementPriceFragment) RouterUtil.getFragment(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION));
        mAdapter = new PagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        mTlTitle.setViewPager(mViewPager, new String[]{"报价单", "商品"});
    }

    @OnClick({R.id.img_back, R.id.img_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add:
                showAddWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 导出到邮箱
     */
    private void showAddWindow() {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_AGREEMENT_PRICE_EXPORT));
            mOptionsWindow = new ContextOptionsWindow(this).setListener((adapter, view, position) -> {
                BaseAgreementPriceFragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
                if (fragment != null) {
                    fragment.toExport();
                }
            }).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(mImgAdd, Gravity.END);
    }


    public static class PagerAdapter extends FragmentPagerAdapter {
        private List<BaseAgreementPriceFragment> mFragmentList;

        PagerAdapter(FragmentManager fm, List<BaseAgreementPriceFragment> fragmentList) {
            super(fm);
            this.mFragmentList = fragmentList;
        }

        @Override
        public BaseAgreementPriceFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }
    }
}
