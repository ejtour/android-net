package com.hll_sc_app.app.report.refund.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.ViewUtils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_REFUNDED_SEARCH)
public class RefundSearchActivity extends BaseLoadActivity {

    public static final int INT_PURCHASER = 1;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.tl_title)
    SlidingTabLayout mTlTitle;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @Autowired(name = "object0")
    int mTag;
    private RefundSearchActivity.PagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refund_search);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
    }


    public void initView(){
        mEdtSearch.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s
                -> mImgClear.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE));
        mEdtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return true;
        });
        ArrayList<BaseRefundSearchFragment> list = new ArrayList<>(2);
        //0-搜索集团 1-搜索门店
        list.add(RefundedSearchFragment.newInstance("0"));
        list.add(RefundedSearchFragment.newInstance("1"));
        mAdapter = new RefundSearchActivity.PagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        mTlTitle.setViewPager(mViewPager, new String[]{"采购商集团", "采购商门店"});
        if (mTag == INT_PURCHASER) {
            mViewPager.setCurrentItem(1);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // no-op
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mEdtSearch.setHint("搜索");
                } else {
                    mEdtSearch.setHint("搜索");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // no-op
            }
        });
    }

    private void toSearch() {
        ViewUtils.clearEditFocus(mEdtSearch);
        BaseRefundSearchFragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
        if (fragment != null) {
            fragment.toSearch();
        }
    }

    public String getSearchText() {
        return mEdtSearch.getText().toString().trim();
    }

    @OnClick({R.id.img_back, R.id.img_clear, R.id.txt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_clear:
                mEdtSearch.setText("");
                toSearch();
                break;
            case R.id.txt_search:
                toSearch();
                break;
            default:
                break;
        }
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private List<BaseRefundSearchFragment> mFragmentList;

        public PagerAdapter(FragmentManager fm, List<BaseRefundSearchFragment> fragmentList) {
            super(fm);
            this.mFragmentList = fragmentList;
        }

        @Override
        public BaseRefundSearchFragment getItem(int position) {
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

