package com.hll_sc_app.app.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.app.order.deliver.DeliverInfoActivity;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.app.order.summary.OrderSummaryActivity;
import com.hll_sc_app.app.order.transfer.OrderTransferFragment;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.event.OrderExportEvent;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
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
import butterknife.OnPageChange;
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
    @BindView(R.id.fmo_filter_options)
    ImageView mFilterOptions;
    @BindView(R.id.fmo_clear_search)
    ImageView mClearSearch;
    @BindView(R.id.fmo_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.fmo_pager)
    ViewPager mPager;
    @BindView(R.id.fmo_search)
    TextView mSearch;
    private ContextOptionsWindow mOptionsWindow;
    private ContextOptionsWindow mFilterOptionsWindow;
    private final OrderParam mOrderParam = new OrderParam();
    private final OrderType[] TYPES = OrderType.values();
    private boolean mOnlyReceive;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_order, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.fitSystemWindowsWithMarginTop(mFilterOptions);
        initView();
        EventBus.getDefault().register(this);
        return rootView;
    }

    private void initView() {
        mOnlyReceive = UserConfig.isOnlyReceive();
        mPager.setAdapter(new OrderListFragmentPager(getChildFragmentManager()));
        mPager.setOffscreenPageLimit(2);
        mTabLayout.setViewPager(mPager);
        onPageSelected(0);
    }

    @Subscribe(priority = 1, threadMode = ThreadMode.MAIN, sticky = true)
    public void handleOrderEvent(OrderEvent event) {
        if (event.getMessage().equals(OrderEvent.SELECT_STATUS)) {
            EventBus.getDefault().removeStickyEvent(event);
            if (mPager != null) {
                int position = OrderType.getPosition((int) event.getData());
                mPager.setCurrentItem(mOnlyReceive ? position - 1 : position);
            }
        }
    }

    @OnPageChange(R.id.fmo_pager)
    void onPageSelected(int position) {
        OrderType type = getCurOrderType(position);
        mOptions.setVisibility(type == OrderType.CANCELED || type == OrderType.PENDING_TRANSFER ? View.GONE : View.VISIBLE);
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

    @OnClick({R.id.fmo_search, R.id.fmo_clear_search, R.id.fmo_options, R.id.fmo_filter_options})
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
            case R.id.fmo_filter_options:
                showFilterOptionsWindow(view);
                break;
        }
    }

    private void showFilterOptionsWindow(View view) {
        if (mFilterOptionsWindow == null) {
            mFilterOptionsWindow = new ContextOptionsWindow(requireActivity())
                    .setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        OrderType orderType = getCurOrderType(mTabLayout.getCurrentTab());
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_CREATE));
        if (orderType != OrderType.PENDING_TRANSFER) {
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_EXECUTE));
        }
        if (orderType == OrderType.PENDING_SETTLE || orderType == OrderType.RECEIVED) {
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_SIGN));
        }
        mFilterOptionsWindow.refreshList(list)
                .showAsDropDownFix(view, Gravity.END);
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(requireActivity())
                    .setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        OrderType orderType = getCurOrderType(mTabLayout.getCurrentTab());
        if (orderType != OrderType.PENDING_TRANSFER && orderType != OrderType.CANCELED) {
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER_DETAILS));
        }
        if (orderType == OrderType.PENDING_DELIVER) {
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ASSEMBLY));
        } else if (orderType == OrderType.DELIVERED) {
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ASSEMBLY));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_OUT_DETAILS));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_OUT_CATEGORY));
        } else if (orderType == OrderType.RECEIVED) {
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ASSEMBLY));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_CHECK_DETAILS));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_CHECK_CATEGORY));
        }
        if (orderType == OrderType.PENDING_RECEIVE) {
            list.add(new OptionsBean(R.drawable.ic_order_summary_option, OptionType.OPTION_RECEIVE_SUMMARY));
        }
        if (orderType == OrderType.PENDING_DELIVER) {
            list.add(new OptionsBean(R.drawable.ic_goods_total_option, OptionType.OPTION_DELIVER_TOTAL));
            list.add(new OptionsBean(R.drawable.ic_order_summary_option, OptionType.OPTION_DELIVER_SUMMARY));
        }
        if (orderType == OrderType.DELIVERED) {
            list.add(new OptionsBean(R.drawable.ic_goods_total_option, OptionType.OPTION_DELIVERED_TOTAL));
        }
        mOptionsWindow.refreshList(list)
                .showAsDropDownFix(view, Gravity.END);
    }

    private OrderType getCurOrderType(int position) {
        return TYPES[mOnlyReceive ? (position + 1) : position];
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter.getItem(position) instanceof OptionsBean) {
            OrderType orderType = getCurOrderType(mTabLayout.getCurrentTab());
            if (mOptionsWindow != null) {
                mOptionsWindow.dismiss();
            }
            if (mFilterOptionsWindow != null) {
                mFilterOptionsWindow.dismiss();
            }
            OptionsBean item = (OptionsBean) adapter.getItem(position);
            if (item == null) return;
            String label = item.getLabel();
            switch (label) {
                case OptionType.OPTION_FILTER_CREATE:
                case OptionType.OPTION_FILTER_EXECUTE:
                case OptionType.OPTION_FILTER_SIGN:
                    EventBus.getDefault().post(new OrderEvent(OrderEvent.TIME_FILTER, label));
                    break;
                case OptionType.OPTION_RECEIVE_SUMMARY:
                case OptionType.OPTION_DELIVER_SUMMARY:
                    OrderSummaryActivity.start(requireActivity(), orderType.getStatus());
                    break;
                case OptionType.OPTION_DELIVER_TOTAL:
                case OptionType.OPTION_DELIVERED_TOTAL:
                    DeliverInfoActivity.start(orderType.getStatus());
                    break;
                default:
                    EventBus.getDefault().post(new OrderExportEvent(label));
                    break;
            }
        }
    }

    @Override
    public void reload() {
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    class OrderListFragmentPager extends FragmentPagerAdapter {

        OrderListFragmentPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mOnlyReceive ? OrderManageFragment.newInstance(TYPES[position + 1], mOrderParam)
                    : position == 0 ? OrderTransferFragment.newInstance(mOrderParam)
                    : OrderManageFragment.newInstance(TYPES[position], mOrderParam);
        }

        @Override
        public int getCount() {
            return mOnlyReceive ? 6 : 7;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TYPES[mOnlyReceive ? (position + 1) : position].getLabel();
        }
    }
}