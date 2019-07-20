package com.hll_sc_app.app.goods;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.list.GoodsListFragment;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.GoodsSearchEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 商品管理Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS)
public class GoodsHomeFragment extends BaseLoadFragment implements BaseQuickAdapter.OnItemClickListener,
    GoodsHomeContract.IGoodsHomeView {
    static final String[] STR_TITLE = {"普通商品", "组合商品", "押金商品", "代仓商品"};
    static final String[] STR_ACTION_TYPE = {"normalProduct", "bundlingGoods", "depositProduct", "warehouse"};
    @BindView(R.id.space)
    View mSpace;
    Unbinder unbinder;
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
    private GoodsListFragmentPager mFragmentAdapter;
    private ContextOptionsWindow mOptionsWindow;
    private GoodsHomePresenter mPresenter;

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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEvent(GoodsSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_goods, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showStatusBar();
        initView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return rootView;
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSpace.getLayoutParams();
            params.height = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    private void initView() {
        mFragmentAdapter = new GoodsListFragmentPager(getChildFragmentManager(), STR_ACTION_TYPE, STR_TITLE);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTab.setViewPager(mViewPager, STR_TITLE);
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updateFragment());
        int padding = UIUtils.dip2px(10);
        mSearchView.setPadding(0, padding, 0, 0);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_GOODS);
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

    @OnClick({R.id.img_add})
    public void onViewClicked(View view) {
        showOptionsWindow(mImgAdd);
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_goods_option_add, OptionType.OPTION_GOODS_ADD));
            list.add(new OptionsBean(R.drawable.ic_goods_option_import, OptionType.OPTION_GOODS_IMPORT));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_GOODS_EXPORT));
            list.add(new OptionsBean(R.drawable.ic_goods_option_top, OptionType.OPTION_GOODS_TOP));
            list.add(new OptionsBean(R.drawable.ic_goods_option_relation, OptionType.OPTION_GOODS_RELATION));
            list.add(new OptionsBean(R.drawable.ic_goods_option_warn, OptionType.OPTION_GOODS_WARN));
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
            RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_ADD);
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
        mPresenter.exportRecord(email);
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
    public void bindEmail(@GoodsHomeContract.ExportType String type) {
        Utils.bindEmail(requireActivity(), email -> {
            if (TextUtils.equals(type, GoodsHomeContract.ExportType.EXPORT_GOODS)) {
                mPresenter.toBindEmail(email, type);
            } else {
                toExportRecord(email);
            }
        });
    }

    @Override
    public void bindSuccess(@GoodsHomeContract.ExportType String type) {
        if (TextUtils.equals(type, GoodsHomeContract.ExportType.EXPORT_GOODS)) {
            toExportGoodsList();
        }
    }

    class GoodsListFragmentPager extends FragmentPagerAdapter {
        private List<GoodsListFragment> mListFragment;

        GoodsListFragmentPager(FragmentManager fm, String[] types, String[] titles) {
            super(fm);
            mListFragment = new ArrayList<>(getCount());
            for (int i = 0; i < types.length; i++) {
                mListFragment.add(GoodsListFragment.newInstance(types[i], titles[i]));
            }
        }

        @Override
        public int getCount() {
            return 4;
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