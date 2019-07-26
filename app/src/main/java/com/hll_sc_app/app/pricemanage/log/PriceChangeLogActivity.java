package com.hll_sc_app.app.pricemanage.log;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsRelevanceListSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.event.GoodsRelevanceListSearchEvent;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
@Route(path = RouterConfig.PRICE_MANAGE_LOG, extras = Constant.LOGIN_EXTRA)
public class PriceChangeLogActivity extends BaseLoadActivity implements PriceChangeLogContract.IPriceManageView {
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_options)
    ImageView mTxtOptions;
    @BindView(R.id.txt_date_name)
    TextView mTxtDateName;
    @BindView(R.id.rl_select_date)
    RelativeLayout mRlSelectDate;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    private EmptyView mEmptyView;
    private PriceLogListAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private PriceChangeLogPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage_log);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        initDefaultTime();
        mPresenter = PriceChangeLogPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSearchView.setPadding(0, 0, 0, 0);
        mSearchView.setBackgroundResource(R.color.base_colorPrimary);
        LinearLayout llContent = mSearchView.getContentView();
        if (llContent != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) llContent.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            llContent.setBackgroundResource(R.drawable.bg_search_text);
            llContent.setGravity(Gravity.CENTER_VERTICAL);
            mSearchView.setTextColorWhite();
        }
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, GoodsRelevanceListSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPriceLogList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePriceLogList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPriceLogList(false);
            }
        });
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PriceLogListAdapter();
        View headView = LayoutInflater.from(this).inflate(R.layout.item_price_manage_log_title, mRecyclerView, false);
        mAdapter.addHeaderView(headView);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("当前日期下没有变更日志数据").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDefaultTime() {
        Date endDate = new Date();
        Date startDate = CalendarUtils.getDateBeforeMonth(endDate, 1);
        String startStr = CalendarUtils.format(startDate, FORMAT_DATE);
        String endStr = CalendarUtils.format(endDate, FORMAT_DATE);
        mTxtDateName.setText(String.format("%s-%s", startStr, endStr));
        mTxtDateName.setTag(R.id.date_start, CalendarUtils.format(startDate, CalendarUtils.FORMAT_SERVER_DATE));
        mTxtDateName.setTag(R.id.date_end, CalendarUtils.format(endDate, CalendarUtils.FORMAT_SERVER_DATE));
    }

    @Subscribe
    public void onEvent(GoodsRelevanceListSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.img_back, R.id.txt_options, R.id.rl_select_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_options:
                showAddWindow();
                break;
            case R.id.rl_select_date:
                showDateRangeWindow();
                break;
            default:
                break;
        }
    }

    private void showAddWindow() {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_AGREEMENT_PRICE_LOG_EXPORT));
            mOptionsWindow = new ContextOptionsWindow(this).setListener((adapter, view, position)
                    -> {
                mPresenter.exportLog(null);
                mOptionsWindow.dismiss();
            }).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(mTxtOptions, Gravity.END);
    }

    private void showDateRangeWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    mTxtDateName.setText(null);
                    mTxtDateName.setTag(R.id.date_start, "");
                    mTxtDateName.setTag(R.id.date_end, "");
                    mPresenter.queryPriceLogList(true);
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), FORMAT_DATE);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), FORMAT_DATE);
                    mTxtDateName.setText(String.format("%s-%s", startStr, endStr));
                    mTxtDateName.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mTxtDateName.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mPresenter.queryPriceLogList(true);
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
    }

    @Override
    public void showPriceLogList(List<PriceLogBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() - 1 != total);
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getStartTime() {
        String startTime = null;
        if (mTxtDateName.getTag(R.id.date_start) != null) {
            startTime = (String) mTxtDateName.getTag(R.id.date_start);
        }
        return startTime;
    }

    @Override
    public String getEndTime() {
        String endTime = null;
        if (mTxtDateName.getTag(R.id.date_end) != null) {
            endTime = (String) mTxtDateName.getTag(R.id.date_end);
        }
        return endTime;
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
        Utils.bindEmail(this, email -> mPresenter.exportLog(email));
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class PriceLogListAdapter extends BaseQuickAdapter<PriceLogBean, BaseViewHolder> {

        PriceLogListAdapter() {
            super(R.layout.item_price_manage_log);
        }

        @Override
        protected void convert(BaseViewHolder helper, PriceLogBean bean) {
            helper.setText(R.id.txt_productCode, bean.getProductCode())
                    .setText(R.id.txt_skuCode, bean.getSkuCode())
                    .setText(R.id.txt_productName, bean.getProductName())
                    .setText(R.id.txt_specContent,
                            getString(bean.getSpecContent()) + "/" + getString(bean.getSaleUnitName()))
                    .setText(R.id.txt_type, TextUtils.equals(bean.getType(), "1") ? "售价" : "成本价")
                    .setText(R.id.txt_priceBefore, CommonUtils.formatNumber(bean.getPriceBefore()))
                    .setText(R.id.txt_priceAfter, CommonUtils.formatNumber(bean.getPriceAfter()))
                    .setText(R.id.txt_modifier, bean.getModifier())
                    .setText(R.id.txt_modifyTime, formatModifyTime(bean.getModifyTime()))
                    .itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                    R.drawable.bg_price_log_content_gray : R.drawable.bg_price_log_content_white);
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private String formatModifyTime(String modifyTime) {
            String formatStr = null;
            if (!TextUtils.isEmpty(modifyTime)) {
                Date date = CalendarUtils.parse(modifyTime, CalendarUtils.FORMAT_HH_MM_SS);
                if (date != null) {
                    formatStr = CalendarUtils.format(date, "yyyy/MM/dd HH:mm");
                }
            }
            return formatStr;
        }
    }
}
