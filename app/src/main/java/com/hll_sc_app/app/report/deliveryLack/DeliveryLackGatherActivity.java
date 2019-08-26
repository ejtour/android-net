package com.hll_sc_app.app.report.deliveryLack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGather;
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGatherResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 缺货汇总
 *
 * @author 初坤
 * @date 20190723
 */
@Route(path = RouterConfig.REPORT_DELIVERY_LACK_GATHER)
public class DeliveryLackGatherActivity extends BaseLoadActivity implements DeliveryLackGatherContract.IDeliveryLackGatherView, BaseQuickAdapter.OnItemClickListener {
    String FORMAT_DATE = "yyyy/MM/dd";
    @BindView(R.id.txt_date_name)
    TextView mTxtDateName;
    @BindView(R.id.rl_select_date)
    RelativeLayout mRlSelectDate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.delivery_lack_gather_content)
    SyncHorizontalScrollView syncHorizontalScrollView;
    @BindView(R.id.delivery_lack_total)
    SyncHorizontalScrollView footSyncHorizontalScrollView;
    @BindView(R.id.txt_options)
    ImageView textOption;
    DeliveryLackGatherPresenter mPresenter;
    DeliveryLackGatherAdapter mAdapter;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.total_valid_order_num)
    TextView totalValidOrderNum;
    @BindView(R.id.total_trade_amount)
    TextView totalTradeAmount;
    @BindView(R.id.total_delivery_lack_product)
    TextView totalDeliveryLackProduct;
    @BindView(R.id.total_delivery_lack_num)
    TextView totalDeliveryLackNum;
    @BindView(R.id.total_delivery_lack_amount)
    TextView totalDeliveryLackAmount;
    @BindView(R.id.total_delivery_lack_rate)
    TextView totalDeliveryLackRate;
    @BindView(R.id.total_delivery_lack_shopnum)
    TextView totalDeliveryLackShopnum;
    String startDate = "";
    String endDate = "";
    private EmptyView mEmptyView;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_delivery_lack_gather);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = new DeliveryLackGatherPresenter();
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreDeliveryLackGatherList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryDeliveryLackGatherList(false);
            }
        });
        mAdapter = new DeliveryLackGatherAdapter();
        View headView = LayoutInflater.from(this).inflate(R.layout.item_report_delivery_lack_gather_title,
            recyclerView, false);
        mAdapter.addHeaderView(headView);
        recyclerView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("当前日期下没有数据").create();
        syncHorizontalScrollView.setLinkageViews(footSyncHorizontalScrollView);
        footSyncHorizontalScrollView.setLinkageViews(syncHorizontalScrollView);
        mPresenter.register(this);
        mPresenter.start();
    }


    /**
     * 初始化时间
     */
    private void initDefaultTime() {
        Date currentDate = new Date();
        Date preStartDate = CalendarUtils.getDateBeforeMonth(currentDate, 1);
        String displayStartDate = CalendarUtils.format(preStartDate, FORMAT_DATE);
        String displayEndDate = CalendarUtils.format(currentDate, FORMAT_DATE);
        startDate = CalendarUtils.format(preStartDate, CalendarUtils.FORMAT_SERVER_DATE);
        endDate = CalendarUtils.format(currentDate, CalendarUtils.FORMAT_SERVER_DATE);
        mTxtDateName.setText(String.format("%s-%s", displayStartDate, displayEndDate));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_DETAIL)) {
            export(null);
        }

    }


    @Override
    public void showDeliveryLackGatherList(List<DeliveryLackGather> records, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(records))
                mAdapter.addData(records);
        } else {
            mAdapter.setNewData(records);
        }
        mAdapter.setEmptyView(mEmptyView);
        if (null == records || records.size() == 0) {
            footSyncHorizontalScrollView.setVisibility(View.GONE);
        } else {
            footSyncHorizontalScrollView.setVisibility(View.VISIBLE);
        }
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() - 1 != total);

    }

    @Override
    public void showTotalDeliveryGatherDatas(DeliveryLackGatherResp deliveryLackGatherResp) {
        txtTotal.setText("合计");
        totalValidOrderNum.setText(String.valueOf(deliveryLackGatherResp.getTotalDeliveryOrderNum()));
        totalTradeAmount.setText(CommonUtils.formatMoney(deliveryLackGatherResp.getTotalDeliveryTradeAmount()));
        totalDeliveryLackProduct.setText("--");
        totalDeliveryLackNum.setText(String.valueOf(deliveryLackGatherResp.getTotalDeliveryLackNum()));
        totalDeliveryLackAmount.setText(CommonUtils.formatMoney(deliveryLackGatherResp.getTotalDeliveryLackAmount()));
        totalDeliveryLackRate.setText(new BigDecimal(deliveryLackGatherResp.getTotalDeliveryLackRate()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
        totalDeliveryLackShopnum.setText(String.valueOf(deliveryLackGatherResp.getTotalDeliveryLackShopNum()));
    }

    @Override
    public String getStartDate() {
        if (mTxtDateName.getTag(R.id.date_start) != null) {
            startDate = (String) mTxtDateName.getTag(R.id.date_start);
        }
        return startDate;
    }

    @Override
    public String getEndDate() {
        if (mTxtDateName.getTag(R.id.date_end) != null) {
            endDate = (String) mTxtDateName.getTag(R.id.date_end);
        }
        return endDate;
    }

    @Override
    public BaseReportReqParam getReqParams() {
        BaseReportReqParam params = new BaseReportReqParam();
        params.setStartDate(startDate);
        params.setEndDate(endDate);
        params.setGroupID(UserConfig.getGroupID());
        return params;
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
        Gson gson = new Gson();
        String reqParams = gson.toJson(getReqParams());
        Utils.bindEmail(this, (email) -> mPresenter.exportDeliveryLackGather(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getReqParams());
        mPresenter.exportDeliveryLackGather(email, reqParams);
    }

    @OnClick({R.id.txt_date_name, R.id.img_back, R.id.txt_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date_name:
                showDateRangeWindow();
                break;
            case R.id.txt_options:
                showExportOptionsWindow(textOption);
            default:
                break;
        }
    }

    private void showDateRangeWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    mTxtDateName.setText(null);
                    mTxtDateName.setTag(R.id.date_start, "");
                    mTxtDateName.setTag(R.id.date_end, "");
                    mPresenter.queryDeliveryLackGatherList(true);
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
                    startDate = CalendarUtils.getDateFormatString(startStr,FORMAT_DATE,CalendarUtils.FORMAT_SERVER_DATE);
                    endDate = CalendarUtils.getDateFormatString(endStr,FORMAT_DATE,CalendarUtils.FORMAT_SERVER_DATE);
                    mPresenter.queryDeliveryLackGatherList(true);
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                    OptionType.OPTION_EXPORT_DETAIL_INFO)))
                .setListener((adapter, view, position) -> {
                    mExportOptionsWindow.dismiss();
                    export(null);
                });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    class DeliveryLackGatherAdapter extends BaseQuickAdapter<DeliveryLackGather, BaseViewHolder> {

        DeliveryLackGatherAdapter() {
            super(R.layout.item_report_delivery_lack_gather);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryLackGather bean) {
            helper.setText(R.id.txt_delivery_date, String.valueOf(bean.getDate()))
                .setText(R.id.txt_delivery_valid_order_num, String.valueOf(bean.getDeliveryOrderNum()))
                .setText(R.id.txt_delivery_lack_salesamount, CommonUtils.formatMoney(bean.getDeliveryTradeAmount()))
                .setText(R.id.txt_delivery_lack_product, String.valueOf((bean.getDeliveryLackKindNum())))
                .setText(R.id.txt_delivery_lack_num, CommonUtils.formatMoney(bean.getDeliveryLackNum()))
                .setText(R.id.txt_delivery_lack_amount, CommonUtils.formatMoney(bean.getDeliveryLackAmount()))
                .setText(R.id.txt_delivery_rate,
                    new BigDecimal(bean.getDeliveryLackRate()).multiply(new BigDecimal(100)).setScale(2,
                        BigDecimal.ROUND_HALF_UP).toPlainString() + "%")
                .setText(R.id.txt_delivery_lack_shopnum, String.valueOf(bean.getDeliveryLackShopNum()))
                .itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                R.drawable.bg_price_log_content_white : R.drawable.bg_price_log_content_gray);
        }
    }
}
