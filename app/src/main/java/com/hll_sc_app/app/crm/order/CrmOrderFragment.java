package com.hll_sc_app.app.crm.order;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.hll_sc_app.app.crm.order.page.CrmOrderPageFragment;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.filter.CrmOrderParam;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SingleSelectionWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

@Route(path = RouterConfig.CRM_ORDER)
public class CrmOrderFragment extends BaseLoadFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.fco_options)
    ImageView mOptions;
    @BindView(R.id.fco_filter)
    TextView mFilter;
    @BindView(R.id.fco_search_view)
    SearchView mSearchView;
    @BindView(R.id.fco_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.fco_pager)
    ViewPager mPager;
    private ContextOptionsWindow mOptionsWindow;
    private SingleSelectionWindow<NameValue> mSelectionWindow;
    private final CrmOrderParam mOrderParam = new CrmOrderParam();
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderParam.setActionType("1");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        showStatusBar();
        initView();
        return view;
    }

    private void initView() {
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mPager.setAdapter(new Pager());
        String[] titles = {"已下单", "未下单"};
        mTabLayout.setViewPager(mPager, titles);
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mFilter.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnPageChange(R.id.fco_pager)
    public void updateOptionsVisibility() {
        mOptions.setVisibility("2".equals(mOrderParam.getActionType()) && mPager.getCurrentItem() == 0 ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.fco_options)
    public void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_CREATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_EXECUTE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_SIGN));
            mOptionsWindow = new ContextOptionsWindow(requireActivity())
                    .refreshList(list)
                    .setListener(this);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @OnClick(R.id.fco_filter)
    public void filterDate(View view) {
        if (mSelectionWindow == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("今日订单", "1"));
            list.add(new NameValue("全部订单", "2"));
            mSelectionWindow = new SingleSelectionWindow<>(requireActivity(), NameValue::getName);
            mSelectionWindow.setTextGravity(Gravity.CENTER);
            mSelectionWindow.refreshList(list);
            mSelectionWindow.setSelect(list.get(0));
            mSelectionWindow.setSelectListener(nameValue -> {
                mOrderParam.setActionType(nameValue.getValue());
                updateOptionsVisibility();
                mFilter.setText(nameValue.getName().substring(0, 2));
                EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
            });
        }
        mSelectionWindow.showAsDropDownFix(view);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter.getItem(position) instanceof OptionsBean) {
            mOptionsWindow.dismiss();
            OptionsBean item = (OptionsBean) adapter.getItem(position);
            if (item == null) return;
            OrderHelper.showDatePicker(item.getLabel(), mOrderParam, requireActivity());
        }
    }

    private class Pager extends FragmentPagerAdapter {

        Pager() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return CrmOrderPageFragment.newInstance(mOrderParam, Math.abs(position - 1));
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
