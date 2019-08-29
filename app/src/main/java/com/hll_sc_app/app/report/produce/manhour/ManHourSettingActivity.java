package com.hll_sc_app.app.report.produce.manhour;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ManHourFooter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

@Route(path = RouterConfig.REPORT_PRODUCE_MAN_HOUR)
public class ManHourSettingActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemChildClickListener {
    public static void start() {
        RouterUtil.goToActivity(RouterConfig.REPORT_PRODUCE_MAN_HOUR);
    }

    @BindView(R.id.acc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.acc_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.acc_view_pager)
    ViewPager mViewPager;
    private BaseManHourAdapter mCostAdapter;
    private ManHourFooter mCostFooter;
    private BaseManHourAdapter mShiftAdapter;
    private ManHourFooter mShiftFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_commodity_check);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mTitleBar.setHeaderTitle("设置工时费");
        mTitleBar.setRightText("保存");

        RecyclerView costList = createListView();
        mCostAdapter = new ManHourCostAdapter();
        costList.setAdapter(mCostAdapter);
        mCostAdapter.setOnItemChildClickListener(this);
        mCostFooter = new ManHourFooter(this);
        mCostFooter.setOnClickListener(this::addCost);
        mCostFooter.setText("工时模块可以向左滑动删除", "+ 添加工时费");
        mCostAdapter.setFooterView(mCostFooter);

        RecyclerView shiftList = createListView();
        mShiftAdapter = new ManHourShiftAdapter();
        shiftList.setAdapter(mShiftAdapter);
        mShiftAdapter.setOnItemChildClickListener(this);
        mShiftFooter = new ManHourFooter(this);
        mShiftFooter.setOnClickListener(this::addShift);
        mShiftFooter.setText("班次可以向左滑动删除", "+ 添加班次");
        mShiftAdapter.setFooterView(mShiftFooter);

        mViewPager.setAdapter(new ViewPagerAdapter(costList, shiftList));
        String[] titles = {"工时费", "班次"};
        mTabLayout.setViewPager(mViewPager, titles);
    }

    private void addCost(View view) {
        mCostAdapter.addData(new ManHourBean());
        updateCostAddable();
    }

    private void updateCostAddable() {
        mCostFooter.setAddable(mCostAdapter.getData().size() < 5);
    }

    private void addShift(View view) {
        mShiftAdapter.addData(new ManHourBean());
        updateShiftAddable();
    }

    private void updateShiftAddable() {
        mShiftFooter.setAddable(mShiftAdapter.getData().size() < 5);
    }

    private RecyclerView createListView() {
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, UIUtils.dip2px(10), 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        return recyclerView;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof ManHourCostAdapter) {
            mCostAdapter.remove(position);
            updateCostAddable();
        } else if (adapter instanceof ManHourShiftAdapter) {
            mShiftAdapter.remove(position);
            updateShiftAddable();
        }
    }
}
