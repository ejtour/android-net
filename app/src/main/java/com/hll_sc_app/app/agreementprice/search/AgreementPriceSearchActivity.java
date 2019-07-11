package com.hll_sc_app.app.agreementprice.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.AgreementPriceActivity;
import com.hll_sc_app.app.agreementprice.BaseAgreementPriceFragment;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议价管理-搜索页面
 *
 * @author zhuyingsong
 * @date 2019/2/12
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_SEARCH, extras = Constant.LOGIN_EXTRA)
public class AgreementPriceSearchActivity extends BaseLoadActivity {
    public static final int INT_GOODS = 1;
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
    private AgreementPriceActivity.PagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_price_search);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        mEdtSearch.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s
            -> mImgClear.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE));
        mEdtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return true;
        });
        ArrayList<BaseAgreementPriceFragment> list = new ArrayList<>(2);
        list.add((BaseAgreementPriceFragment) RouterUtil.getFragment(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION));
        list.add((BaseAgreementPriceFragment) RouterUtil.getFragment(RouterConfig.MINE_AGREEMENT_PRICE_GOODS));
        mAdapter = new AgreementPriceActivity.PagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        mTlTitle.setViewPager(mViewPager, new String[]{"报价单", "商品"});
        if (mTag == INT_GOODS) {
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
                    mEdtSearch.setHint("输入报价单号、采购商名称进行搜索");
                } else {
                    mEdtSearch.setHint("输入商品名称、采购商名称进行搜索");
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
        BaseAgreementPriceFragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
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
}
