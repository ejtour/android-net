package com.hll_sc_app.app.report.dailysale;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
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
 * 日销售汇总
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_DAILY_AGGREGATION)
public class DailyAggregationActivity extends BaseLoadActivity implements DailyAggregationContract.IDailyAggregationView,BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rds_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rds_date)
    TextView mDate;
    @BindView(R.id.rds_arrow)
    TriangleView mArrow;
    @BindView(R.id.rds_amount)
    TextView mAmount;
    @BindView(R.id.rds_num)
    TextView mNum;
    @BindView(R.id.rds_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rds_list_view)
    RecyclerView mListView;
    private DailyAggregationAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private DailyAggregationPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_daily_sale);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = DailyAggregationPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        initDefaultTime();
        setData(0, 0);
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mAdapter = new DailyAggregationAdapter();
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
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
    }

    private void initDefaultTime() {
        Date endDate = new Date();
        Date startDate = CalendarUtils.getDateBefore(endDate, 29);
        mDate.setTag(R.id.date_start, startDate);
        mDate.setTag(R.id.date_end, endDate);
        updateSelectedDate();
    }

    private void updateSelectedDate() {
        mDate.setText(String.format("%s - %s", CalendarUtils.format(((Date) mDate.getTag(R.id.date_start)), Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(((Date) mDate.getTag(R.id.date_end)), Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick(R.id.rds_select_date)
    public void showDateRangeWindow(View view) {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.start();
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.setOnDismissListener(()->{
            mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private void showExportOptionsWindow(View view) {
        if (mExportOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO));
            mExportOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mExportOptionsWindow.showAsDropDownFix(view, Gravity.RIGHT);
    }

    @Override
    public void showDailyAggregationList(DateSaleAmountResp dateSaleAmountResp, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(dateSaleAmountResp.getRecords()))
                mAdapter.addData(dateSaleAmountResp.getRecords());
        } else {
            mAdapter.setNewData(dateSaleAmountResp.getRecords());
        }
        mRefreshLayout.setEnableLoadMore(dateSaleAmountResp.getRecords() != null && dateSaleAmountResp.getRecords().size() == 20);
        setData(dateSaleAmountResp.getTotalSubtotalAmount(), dateSaleAmountResp.getTotalOrderNum());
    }

    private void setData(double amount, long num) {
        String source = "总交易额：¥" + CommonUtils.formatMoney(amount);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_222222)), source.indexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAmount.setText(ss);
        source = "总订单数：" + CommonUtils.formatNum(num);
        ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_222222)), source.indexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mNum.setText(ss);
    }

    @Override
    public String getStartDate() {
        return CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_start));
    }

    @Override
    public String getEndDate() {
        return CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_end));
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
        Utils.bindEmail(this, this::export);
    }

    @Override
    public void export(String email) {
        mPresenter.exportDailyReport(email);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mExportOptionsWindow.dismiss();
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_EXPORT_DETAIL_INFO)){
            export(null);
        }
    }

    private static class DailyAggregationAdapter extends BaseQuickAdapter<DateSaleAmount, BaseViewHolder> {
        private int mNumColor;

        DailyAggregationAdapter() {
            super(R.layout.item_report_purchase_summary);
            mNumColor = Color.parseColor(ColorStr.COLOR_222222);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.setGone(R.id.rps_modify_group, false);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, DateSaleAmount bean) {
            helper.setText(R.id.rps_amount, processText(1.6f, "\n交易金额(元)", CommonUtils.formatMoney(bean.getSubtotalAmount())))
                    .setText(R.id.rps_people_num, processText(1.3f, "\n有效订单(笔)", String.valueOf(bean.getOrderNum())))
                    .setText(R.id.rps_people_effect, processText(1.3f, "\n客单价(元)", CommonUtils.formatMoney(bean.getAverageShopAmount())))
                    .setText(R.id.rps_car_num, processText(1.3f, "\n单均(元)", CommonUtils.formatMoney(bean.getAverageAmount())))
                    .setText(R.id.rps_logistics_fee, processText(1.3f, "\n下单客户/门店", bean.getOrderCustomerNum() + "/" + bean.getOrderCustomerShopNum()))
                    .setText(R.id.rps_time, DateUtil.getReadableTime(String.valueOf(bean.getDate()), Constants.SLASH_YYYY_MM_DD));
        }

        private SpannableString processText(float proportion, String postfix, String num) {
            String source = num + postfix;
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new RelativeSizeSpan(proportion), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(mNumColor), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        }
    }
}
