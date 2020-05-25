package com.hll_sc_app.app.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.app.order.transfer.OrderTransferFragment;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.ExportEvent;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.impl.IReload;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页-订单管理
 *
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */
@Route(path = RouterConfig.ROOT_HOME_ORDER)
public class OrderHomeFragment extends BaseLoadFragment implements BaseQuickAdapter.OnItemClickListener, IReload {
    Unbinder unbinder;
    @BindView(R.id.fmo_options)
    ImageView mOptions;
    @BindView(R.id.fmo_clear_search)
    ImageView mClearSearch;
    @BindView(R.id.fmo_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.fmo_pager)
    ViewPager mPager;
    @BindView(R.id.fmo_search)
    TextView mSearch;
    private ContextOptionsWindow mOptionsWindow;
    private final OrderParam mOrderParam = new OrderParam();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_order, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showStatusBar();
        initView();
        EventBus.getDefault().register(this);
        return rootView;
    }

    private void initView() {
        mPager.setAdapter(new OrderListFragmentPager(getChildFragmentManager(), OrderType.values()));
        mPager.setOffscreenPageLimit(2);
        mTabLayout.setViewPager(mPager, OrderType.getTitles());
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mOptions.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Subscribe(priority = 1, threadMode = ThreadMode.MAIN, sticky = true)
    public void handleOrderEvent(OrderEvent event) {
        if (event.getMessage().equals(OrderEvent.SELECT_STATUS)) {
            if (mPager != null) {
                mPager.setCurrentItem(OrderType.getPosition((int)event.getData()));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            String value = data.getStringExtra("value");
            mOrderParam.setSearchType(data.getIntExtra("index", 0));
            mOrderParam.setExtraId(data.getStringExtra("extraId"));
            handleNameValue(name, value);
        }
    }

    public void handleNameValue(String name, String value) {
        mClearSearch.setVisibility(!TextUtils.isEmpty(name) ? View.VISIBLE : View.GONE);
        mSearch.setText(name);

        ShopSearchEvent event = new ShopSearchEvent();
        event.setName(name);
        event.setShopMallId(value);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fmo_search, R.id.fmo_clear_search, R.id.fmo_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmo_search:
                OrderSearchActivity.start(requireActivity(),
                        mOrderParam.getSearchWords(), String.valueOf(mOrderParam.getSearchType()));
                break;
            case R.id.fmo_clear_search:
                handleNameValue("", "");
                break;
            case R.id.fmo_options:
                showOptionsWindow(view);
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(requireActivity())
                    .setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        if (mTabLayout.getCurrentTab() == 0) {
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_CREATE));
        } else {
            if (mTabLayout.getCurrentTab() == 2) {
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ASSEMBLY));
            } else if (mTabLayout.getCurrentTab() == 3) {
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_OUT_DETAILS));
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_OUT_CATEGORY));
            } else if (mTabLayout.getCurrentTab() == 4) {
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER));
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER_DETAILS));
            } else if (mTabLayout.getCurrentTab() == 5) {
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER));
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER_DETAILS));
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_CHECK_DETAILS));
                list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_CHECK_CATEGORY));
            }
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_CREATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_EXECUTE));
            if (mTabLayout.getCurrentTab() == 4 || mTabLayout.getCurrentTab() == 5) {
                list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_SIGN));
            }
        }
        mOptionsWindow.refreshList(list)
                .showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter.getItem(position) instanceof OptionsBean) {
            mOptionsWindow.dismiss();
            OptionsBean item = (OptionsBean) adapter.getItem(position);
            if (item == null) return;
            String label = item.getLabel();
            switch (label) {
                case OptionType.OPTION_FILTER_CREATE:
                case OptionType.OPTION_FILTER_EXECUTE:
                case OptionType.OPTION_FILTER_SIGN:
                    OrderHelper.showDatePicker(label, mOrderParam, requireActivity(),
                            () -> EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST)));
                    break;
                default:
                    EventBus.getDefault().post(new ExportEvent(label));
                    break;
            }
        }
    }

    @Override
    public void reload() {
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    class OrderListFragmentPager extends FragmentPagerAdapter {

        private final OrderType[] mTypes;

        OrderListFragmentPager(FragmentManager fm, OrderType[] types) {
            super(fm);
            mTypes = types;
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? OrderTransferFragment.newInstance(mOrderParam)
                    : OrderManageFragment.newInstance(mTypes[position], mOrderParam);
        }

        @Override
        public int getCount() {
            return 7;
        }
    }
}