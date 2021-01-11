package com.hll_sc_app.app.report.daily;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWeekWindow;
import com.hll_sc_app.bean.report.daily.SalesDailyBean;
import com.hll_sc_app.bean.report.daily.SalesDailyDetailBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 销售日报
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_SALES_DAILY)
public class SalesDailyActivity extends BaseLoadActivity implements SalesDailyContract.ISalesDailyView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rsd_list_view)
    RecyclerView mListView;
    @BindView(R.id.rsd_date)
    TextView mDate;
    @BindView(R.id.rsd_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rsd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rsd_search_view)
    SearchView mSearchView;
    private DateWeekWindow mWeekWindow;
    private SalesReportListAdapter mAdapter;
    private ContextOptionsWindow mExportOptionsWindow;
    private SalesDailyPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_daily);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("timeType", "2");
        mReq.put("groupID", UserConfig.getGroupID());
        updateSelectDate(CalendarUtils.getWeekDate(0, 1));
        mPresenter = SalesDailyPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mAdapter = new SalesReportListAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SalesDailyActivity.this, searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mReq.put("keyword", searchContent);
                mPresenter.start();
            }
        });
    }

    @OnClick({R.id.rsd_pre, R.id.rsd_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rsd_pre:
                updateSelectDate(CalendarUtils.getWeekDate((Date) mDate.getTag(), -1, 1));
                mPresenter.start();
                break;
            case R.id.rsd_next:
                updateSelectDate(CalendarUtils.getWeekDate((Date) mDate.getTag(), 1, 1));
                mPresenter.start();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick(R.id.rsd_date_btn)
    public void showCustomerDate() {
        if (mWeekWindow == null) {
            mWeekWindow = new DateWeekWindow(this);
            mWeekWindow.setSelectListener(date -> {
                updateSelectDate(date);
                mPresenter.start();
            });
        }
        mWeekWindow.setCalendar((Date) mDate.getTag());
        mWeekWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void showExportOptionsWindow(View view) {
        if (mExportOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO));
            mExportOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mExportOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        mExportOptionsWindow.dismiss();
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_EXPORT_DETAIL_INFO)) {
            mPresenter.export(null);
        }
    }

    private void updateSelectDate(Date date) {
        mDate.setTag(date);
        mReq.put("date", CalendarUtils.toLocalDate(date));
        Date end = CalendarUtils.getWeekDate(date, 0, 7);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(end, Constants.SLASH_YYYY_MM_DD)));
    }

    @Override
    public void setData(List<SalesDailyBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    static class SalesReportListAdapter extends BaseQuickAdapter<SalesDailyBean, BaseViewHolder> {

        SalesReportListAdapter() {
            super(R.layout.item_report_sales_daily);
        }

        @Override
        protected void convert(BaseViewHolder helper, SalesDailyBean bean) {
            List<SalesDailyDetailBean> reportDetail = bean.getReportDetail();
            helper.setText(R.id.rsd_name, bean.getSalesmanName())
                    .setText(R.id.rsd_num, String.format("共 %s 条", bean.getReportNum()))
                    .setText(R.id.rsd_mon, String.valueOf(reportDetail.get(0).getReportNum()))
                    .setText(R.id.rsd_tue, String.valueOf(reportDetail.get(1).getReportNum()))
                    .setText(R.id.rsd_wed, String.valueOf(reportDetail.get(2).getReportNum()))
                    .setText(R.id.rsd_thu, String.valueOf(reportDetail.get(3).getReportNum()))
                    .setText(R.id.rsd_fri, String.valueOf(reportDetail.get(4).getReportNum()))
                    .setText(R.id.rsd_sat, String.valueOf(reportDetail.get(5).getReportNum()))
                    .setText(R.id.rsd_sun, String.valueOf(reportDetail.get(6).getReportNum()));
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
