package com.hll_sc_app.app.analysis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.order.OrderAnalysisFragment;
import com.hll_sc_app.app.analysis.purchaser.PurchaserAnalysisFragment;
import com.hll_sc_app.app.analysis.toptean.TopTenFragment;
import com.hll_sc_app.app.analysis.trade.TradeAmountFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.event.AnalysisEvent;
import com.hll_sc_app.bean.operationanalysis.AnalysisParam;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.LostResp;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.analysis.AnalysisMonthDialog;
import com.hll_sc_app.widget.analysis.AnalysisWeekDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/11
 */

@Route(path = RouterConfig.OPERATION_ANALYSIS)
public class AnalysisActivity extends BaseLoadActivity implements IAnalysisContract.IAnalysisView, DateWindow.OnDateSelectListener {
    @BindView(R.id.aa_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.aa_filter)
    TextView mFilter;
    @BindView(R.id.aa_filter_arrow)
    TriangleView mFilterArrow;
    @BindView(R.id.aa_date)
    TextView mDate;
    @BindView(R.id.aa_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.aa_view_pager)
    ViewPager mViewPager;
    private AnalysisEvent mAnalysisEvent = new AnalysisEvent();
    private AnalysisParam mParam = new AnalysisParam();
    private IAnalysisContract.IAnalysisPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;
    private AnalysisWeekDialog mWeekDialog;
    private AnalysisMonthDialog mMonthDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_analysis);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = AnalysisPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(),
                Arrays.asList(new TradeAmountFragment(), new OrderAnalysisFragment(),
                        new PurchaserAnalysisFragment(), new TopTenFragment()),
                Arrays.asList("交易记录", "订单分析", "采购商分析", "门店TOP10")));
        mTabLayout.setViewPager(mViewPager);
        updateDate();
    }

    private void updateDate() {
        mDate.setText(String.format("统计周期：%s - %s", CalendarUtils.format(mParam.getDate(), Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mParam.getTimeType() == 2 ? CalendarUtils.getWeekDate(mParam.getDate(), 0, 7)
                        : CalendarUtils.getLastDateInMonth(mParam.getDate()), Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick({R.id.aa_filter, R.id.aa_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aa_filter:
                showOptionWindow();
                break;
            case R.id.aa_date:
                if (mParam.getTimeType() == 2) {
                    showWeekDialog();
                } else {
                    showMonthDialog();
                }
                break;
        }
    }

    private void showWeekDialog() {
        if (mWeekDialog == null) {
            mWeekDialog = new AnalysisWeekDialog(this);
            mWeekDialog.setOnDateSelectListener(this);
        }
        mWeekDialog.show();
    }

    private void showMonthDialog() {
        if (mMonthDialog == null) {
            mMonthDialog = new AnalysisMonthDialog(this);
            mMonthDialog.setOnDateSelectListener(this);
        }
        mMonthDialog.show();
    }

    private void showOptionWindow() {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(OptionType.OPTION_WEEK));
            list.add(new OptionsBean(OptionType.OPTION_MONTH));
            mOptionsWindow = new ContextOptionsWindow(this)
                    .setListPadding(UIUtils.dip2px(40), 0, UIUtils.dip2px(40), 0)
                    .refreshList(list)
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        OptionsBean item = (OptionsBean) adapter.getItem(position);
                        if (item != null) {
                            mFilter.setText(item.getLabel());
                            int oldType = mParam.getTimeType();
                            if (OptionType.OPTION_MONTH.equals(item.getLabel()))
                                mParam.setTimeType(3);
                            if (OptionType.OPTION_WEEK.equals(item.getLabel()))
                                mParam.setTimeType(2);
                            if (oldType != mParam.getTimeType()) {
                                if (mParam.getTimeType() == 2)
                                    mParam.setDate(CalendarUtils.getWeekDate(-1, 1));
                                if (mParam.getTimeType() == 3)
                                    mParam.setDate(CalendarUtils.getFirstDateInMonth(CalendarUtils.getDateBeforeMonth(new Date(), 1)));
                                mWeekDialog = null;
                                mMonthDialog = null;
                                updateDate();
                                mPresenter.start();
                            }
                        }
                    });
        }
        mOptionsWindow.showAsDropDownFix(mFilter, Gravity.LEFT);
    }

    @Override
    public void setLostData(LostResp resp) {
        mAnalysisEvent.setLostResp(resp);
        afterSetData();
    }

    @Override
    public void setAnalysisData(AnalysisResp resp) {
        mAnalysisEvent.setAnalysisResp(resp);
        afterSetData();
    }

    @Override
    public void setTopTenData(TopTenResp resp) {
        mAnalysisEvent.setTopTenResp(resp);
        afterSetData();
    }

    @Override
    public void showError(UseCaseException e) {
        mAnalysisEvent.revert();
        super.showError(e);
    }

    private void afterSetData() {
        if (mAnalysisEvent.done()) {
            mAnalysisEvent.setTimeType(mParam.getTimeType());
            EventBus.getDefault().postSticky(mAnalysisEvent);
        }
    }

    @Override
    public void dateSelect(Date date) {
        mParam.setDate(date);
        updateDate();
        mPresenter.start();
    }
}
