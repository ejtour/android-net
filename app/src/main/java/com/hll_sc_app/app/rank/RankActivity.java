package com.hll_sc_app.app.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.rank.org.OrgRankFragment;
import com.hll_sc_app.app.rank.sales.SalesRankFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWeekWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.DateYearMonthWindow;
import com.hll_sc_app.bean.event.RankEvent;
import com.hll_sc_app.bean.rank.RankParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

@Route(path = RouterConfig.CRM_RANK)
public class RankActivity extends BaseLoadActivity {
    private final RankParam mRankParam = new RankParam();
    @BindView(R.id.ar_filter_select)
    TextView mFilterSelect;
    @BindView(R.id.ar_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.ar_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.ar_date_filter)
    ImageView mDateFilter;
    private ContextOptionsWindow mOptionsWindow;
    private DateWindow mDateWindow;
    private DateWeekWindow mDateWeekWindow;
    private DateYearMonthWindow mYearMonthWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_rank);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mViewPager.setAdapter(new RankAdapter());
        mTabLayout.setViewPager(mViewPager, new String[]{"销售排名", "集团排名", "商户排名"});
    }

    @OnClick(R.id.ar_date_filter)
    public void dateFilter() {
        switch (mRankParam.getDateType()) {
            case 1:
                showDateWindow();
                break;
            case 2:
                showWeekWindow();
                break;
            case 3:
                showMonthWindow();
                break;
        }
    }

    private void showDateWindow() {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setCalendar(mRankParam.getStartDate());
            mDateWindow.setSelectListener(date -> {
                mRankParam.setStartDate(date);
                EventBus.getDefault().post(new RankEvent());
            });
        }
        mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    private void showWeekWindow() {
        if (mDateWeekWindow == null) {
            mDateWeekWindow = new DateWeekWindow(this);
            mDateWeekWindow.setCalendar(mRankParam.getStartDate());
            mDateWeekWindow.setSelectListener(date -> {
                mRankParam.setStartDate(CalendarUtils.getWeekDate(date, 0, 1));
                EventBus.getDefault().post(new RankEvent());
            });
        }
        mDateWeekWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    private void showMonthWindow() {
        if (mYearMonthWindow == null) {
            mYearMonthWindow = new DateYearMonthWindow(this);
            mYearMonthWindow.setCalendar(mRankParam.getStartDate());
            mYearMonthWindow.setSelectListener(date -> {
                mRankParam.setStartDate(CalendarUtils.getFirstDateInMonth(date));
                EventBus.getDefault().post(new RankEvent());
            });
        }
        mYearMonthWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @OnClick(R.id.ar_filter_select)
    public void selectFilter() {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_day_option, OptionType.OPTION_STATISTIC_DATE));
            list.add(new OptionsBean(R.drawable.ic_week_option, OptionType.OPTION_STATISTIC_WEEK));
            list.add(new OptionsBean(R.drawable.ic_month_option, OptionType.OPTION_STATISTIC_MONTH));
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(list)
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        OptionsBean item = (OptionsBean) adapter.getItem(position);
                        if (item == null || mRankParam.getDateType() == position + 1) return;
                        mFilterSelect.setText(item.getLabel());
                        mDateFilter.setImageResource(item.getIconRes());
                        mRankParam.setDateType(position + 1);
                        switch (item.getLabel()) {
                            case OptionType.OPTION_STATISTIC_DATE:
                                mRankParam.setStartDate(new Date());
                                break;
                            case OptionType.OPTION_STATISTIC_WEEK:
                                mRankParam.setStartDate(CalendarUtils.getWeekDate(0, 1));
                                break;
                            case OptionType.OPTION_STATISTIC_MONTH:
                                mRankParam.setStartDate(CalendarUtils.getFirstDateInMonth(new Date()));
                                break;
                        }
                        mYearMonthWindow = null;
                        mDateWindow = null;
                        mDateWeekWindow = null;
                        EventBus.getDefault().post(new RankEvent());
                    });
        }
        mOptionsWindow.showAsDropDownFix(mFilterSelect, -UIUtils.dip2px(20), 0, Gravity.CENTER_HORIZONTAL);
    }

    private class RankAdapter extends FragmentPagerAdapter {

        RankAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? SalesRankFragment.newInstance(mRankParam)
                    : OrgRankFragment.newInstance(position, mRankParam);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
