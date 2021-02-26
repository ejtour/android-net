package com.hll_sc_app.app.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.MainActivity;
import com.hll_sc_app.app.goods.add.GoodsAddActivity;
import com.hll_sc_app.app.goods.list.GoodsListFragment;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.warehouse.shipper.ShipperWarehouseGoodsActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.goods.GoodsHomeContract.ExportType.EXPORT_GOODS;
import static com.hll_sc_app.app.goods.GoodsHomeContract.ExportType.EXPORT_RECORDS;

/**
 * 商品管理Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS)
public class GoodsHomeFragment extends BaseLoadFragment implements BaseQuickAdapter.OnItemClickListener, GoodsHomeContract.IGoodsHomeView {
    static final String[] STR_TITLE = {"普通商品", "组合商品", "押金商品", "代仓商品", "代配商品"};
    static final String[] STR_ACTION_TYPE = {"normalProduct", "bundlingGoods", "depositProduct", "warehouse", "substitution_select"};
    Unbinder unbinder;
    @BindView(R.id.rl_toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.img_add)
    ImageView mImgAdd;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.rb_productStatus4)
    RadioButton mRbProductStatus4;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    private GoodsListFragmentPager mFragmentAdapter;
    private ContextOptionsWindow mOptionsWindow;
    private GoodsHomePresenter mPresenter;
    private int mExportType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = GoodsHomePresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        if (resultCode == Activity.RESULT_OK && !isHidden()) {
            updateFragment();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_goods, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.fitSystemWindowsWithPaddingTop(mToolbar);
        initView();
        return rootView;
    }

    private void initView() {
        mImgClose.setVisibility((getActivity() instanceof MainActivity) ? View.INVISIBLE : View.VISIBLE);
        mFragmentAdapter = new GoodsListFragmentPager(getChildFragmentManager(), STR_ACTION_TYPE, STR_TITLE);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTab.setViewPager(mViewPager);
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updateFragment());
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                if (!RightConfig.checkRight(getString(R.string.right_productManagement_search))) {
                    showToast(getString(R.string.right_tips));
                    return;
                }
                SearchActivity.start(requireActivity(),
                        searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                updateFragment();
            }
        });
    }

    private void updateFragment() {
        if (mFragmentAdapter != null) {
            for (int i = 0; i < mFragmentAdapter.getCount(); i++) {
                mFragmentAdapter.getItem(i).refreshFragment(getProductStatus(), getSearchContent());
            }
        }
    }

    /**
     * 上下架
     *
     * @return 上-4 下-5
     */
    private String getProductStatus() {
        return mRbProductStatus4.isChecked() ? GoodsBean.PRODUCT_STATUS_UP : GoodsBean.PRODUCT_STATUS_DOWN;
    }

    /**
     * 获取搜索词
     *
     * @return 搜索词
     */
    private String getSearchContent() {
        return mSearchView.getSearchContent();
    }

    @OnClick({R.id.img_add, R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add:
                showOptionsWindow(mImgAdd);
                break;
            case R.id.img_close:
                if (getActivity() != null && getActivity() instanceof ShipperWarehouseGoodsActivity) {
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_goods_option_add, OptionType.OPTION_GOODS_ADD));
            list.add(new OptionsBean(R.drawable.ic_goods_option_bundle_add, OptionType.OPTION_GOODS_ADD_BUNDLE));
            list.add(new OptionsBean(R.drawable.ic_goods_option_import, OptionType.OPTION_GOODS_IMPORT));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_GOODS_EXPORT));
            list.add(new OptionsBean(R.drawable.ic_goods_option_top, OptionType.OPTION_GOODS_TOP));
            list.add(new OptionsBean(R.drawable.ic_goods_option_relation, OptionType.OPTION_GOODS_RELATION));
            if (!BuildConfig.isOdm) {
                list.add(new OptionsBean(R.drawable.ic_goods_option_warn, OptionType.OPTION_GOODS_WARN));
            }
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_RECORD));
            mOptionsWindow = new ContextOptionsWindow(requireActivity()).setListener(this).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_ADD)) {
            GoodsAddActivity.start(requireActivity(), null);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_ADD_BUNDLE)) {
            GoodsBean bean = new GoodsBean();
            bean.setBundlingGoodsType(GoodsBean.BUNDLING_GOODS_TYPE);
            GoodsAddActivity.start(requireActivity(), bean);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_IMPORT)) {
            RouterUtil.goToActivity(RouterConfig.GOODS_TEMPLATE_LIST);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_TOP)) {
            RouterUtil.goToActivity(RouterConfig.GOODS_STICK_MANAGE);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_WARN)) {
            RouterUtil.goToActivity(RouterConfig.GOODS_INVENTORY_WARNING);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_EXPORT)) {
            toExportGoodsList();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_EXPORT_RECORD)) {
            toExportRecord(null);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_GOODS_RELATION)) {
            RouterUtil.goToActivity(RouterConfig.GOODS_RELEVANCE_PURCHASER_LIST);
        }
        mOptionsWindow.dismiss();
    }

    /**
     * 导出商品列表
     */
    private void toExportGoodsList() {
        if (!RightConfig.checkRight(getString(R.string.right_productManagement_exportProductList))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        mExportType = EXPORT_GOODS;
        int currentItem = mViewPager.getCurrentItem();
        String actionType = STR_ACTION_TYPE[currentItem];
        if (TextUtils.equals(actionType, "warehouse") || TextUtils.equals(actionType, "normalProduct")) {
            mPresenter.exportGoodsList(actionType, getProductStatus());
        } else {
            showToast("不支持该类商品列表导出");
        }
    }

    /**
     * 上下架记录导出
     *
     * @param email 邮箱地址
     */
    private void toExportRecord(String email) {
        mExportType = EXPORT_RECORDS;
        mPresenter.exportRecord(email);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(requireActivity(), email -> {
            if (mExportType == EXPORT_GOODS) {
                mPresenter.toBindEmail(email);
            } else {
                toExportRecord(email);
            }
        });
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(requireActivity(), email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(requireActivity(), tip);
    }

    @Override
    public void bindSuccess() {
        toExportGoodsList();
    }

    class GoodsListFragmentPager extends FragmentPagerAdapter {
        private final String[] mTitles;
        private List<GoodsListFragment> mListFragment;

        GoodsListFragmentPager(FragmentManager fm, String[] types, String[] titles) {
            super(fm);
            mListFragment = new ArrayList<>(getCount());
            mTitles = titles;
            for (int i = 0; i < types.length; i++) {
                mListFragment.add(GoodsListFragment.newInstance(types[i], titles[i]));
            }
        }

        @Override
        public int getCount() {
            return BuildConfig.isOdm ? 3 : 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public GoodsListFragment getItem(int position) {
            GoodsListFragment goodsListFragment = mListFragment.get(position);
            goodsListFragment.refreshFragment(getProductStatus(), getSearchContent());
            return goodsListFragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}