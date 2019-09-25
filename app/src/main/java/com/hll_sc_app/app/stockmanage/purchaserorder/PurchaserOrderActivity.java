package com.hll_sc_app.app.stockmanage.purchaserorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.stockmanage.purchaserorder.detail.PurchaserOrderDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderRecord;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderReq;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 采购单查询
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.STOCK_PURCHASER_ORDER)
public class PurchaserOrderActivity extends BaseLoadActivity implements PurchaserOrderContract.IPurchaserOrderView,BaseQuickAdapter.OnItemClickListener {
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    private static final int PURCHASER_ORDER_SEARCH_CODE=13001;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.purchaser_order_date)
    TextView mTxtDateName;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.purchaser_filter_drop)
    ImageView imageView;
    @BindView(R.id.purchaser_order_filter_text)
    TextView filterTextView;
    @BindView(R.id.search_view)
    SearchView mSearchView;

    PurchaserOrderReq requestParams = new PurchaserOrderReq();
    private PurchaserOrderListAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private PurchaserOrderPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_purchaser_order);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = PurchaserOrderPresenter.newInstance();
        mAdapter = new PurchaserOrderListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserOrderList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserOrderList(false);
            }
        });
        mAdapter.setOnItemClickListener((adapter,view,position)->{
            PurchaserOrderRecord item = (PurchaserOrderRecord) adapter.getItem(position);
            String billID = item.getBillID();
            PurchaserOrderDetailActivity.start(billID);
        });
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                RouterUtil.goToActivity(RouterConfig.STOCK_PURCHASER_ORDER_SEARCH, PurchaserOrderActivity.this, PURCHASER_ORDER_SEARCH_CODE);
            }

            @Override
            public void toSearch(String searchContent) {
            }
        });
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initDefaultTime() {
        Date currentDate = new Date();
        String dateStr = CalendarUtils.format(currentDate, FORMAT_DATE);
        mTxtDateName.setText(String.format("%s - %s", dateStr, dateStr));
        mTxtDateName.setTag(R.id.date_start, CalendarUtils.format(currentDate, CalendarUtils.FORMAT_SERVER_DATE));
        mTxtDateName.setTag(R.id.date_end, CalendarUtils.format(currentDate, CalendarUtils.FORMAT_SERVER_DATE));
    }

    @OnClick({R.id.img_back, R.id.purchaser_order_filter_text, R.id.purchaser_order_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.purchaser_order_date:
                showDateRangeWindow();
                break;
            case R.id.purchaser_order_filter_text:
                showPurchaserOrderWindow(filterTextView);
                break;
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
                    mPresenter.queryPurchaserOrderList(true);
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mTxtDateName);
    }

    @Override
    public void showPurchaserOrderList(PurchaserOrderResp purchaserOrderResp, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(purchaserOrderResp.getRecords()))
                mAdapter.addData(purchaserOrderResp.getRecords());
        } else {
            mAdapter.setNewData(purchaserOrderResp.getRecords());
        }
    }

    @Override
    public PurchaserOrderReq getRequestParams() {
        if (mTxtDateName.getTag(R.id.date_start) != null) {
            String startTime = (String) mTxtDateName.getTag(R.id.date_start);
            requestParams.setStartDate(startTime);
        }
        if (mTxtDateName.getTag(R.id.date_end) != null) {
            String endTime = (String) mTxtDateName.getTag(R.id.date_end);
            requestParams.setEndDate(endTime);
        }
        return requestParams;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    /**
     * 显示过滤窗口
     * @param view
     */
    private void showPurchaserOrderWindow(View view){
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        list.add(new OptionsBean(R.drawable.ic_order_goods_statistic, OptionType.OPTION_PURCHASER_ORDER_CREATE_DATE));
        list.add(new OptionsBean(R.drawable.ic_order_goods_statistic, OptionType.OPTION_PURCHASER_ORDER_ARRIVAL_DATE));
        mOptionsWindow.setOnDismissListener(()->{
            imageView.setRotation(0);

        });
        imageView.setRotation(180);
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        String text = "";
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_PURCHASER_ORDER_CREATE_DATE)) {
            requestParams.setFlag(1);
            text = OptionType.OPTION_PURCHASER_ORDER_CREATE_DATE;
        }else if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_PURCHASER_ORDER_ARRIVAL_DATE)){
            requestParams.setFlag(2);
            text = OptionType.OPTION_PURCHASER_ORDER_ARRIVAL_DATE;
        }else{

        }
        filterTextView.setText(text);
        if (mOptionsWindow != null) {
            imageView.setRotation(0);
            mOptionsWindow.dismiss();
        }
        mPresenter.queryPurchaserOrderList(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PURCHASER_ORDER_SEARCH_CODE && resultCode == RESULT_OK) {
            String supplyiers = data.getStringExtra("result");
            requestParams.setSupplierIDs(supplyiers);
            mPresenter.queryPurchaserOrderList(true);
        }
    }

    class PurchaserOrderListAdapter extends BaseQuickAdapter<PurchaserOrderRecord, BaseViewHolder> {

        PurchaserOrderListAdapter() {
            super(R.layout.item_stock_purchaser_order);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserOrderRecord bean) {
            helper.setText(R.id.purchaser_order_comp_name, bean.getSupplierName())
                    .setText(R.id.purchaser_order_status, bean.getStatusDesc())
                    .setText(R.id.purchaser_order_no, bean.getBillNo())
                    .setText(R.id.purchaser_order_amount, String.format("%s%s","¥",CommonUtils.formatMoney(new BigDecimal(bean.getTotalPrice()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue())))
                    .setText(R.id.purchaser_order_date, String.format("%s:%s","采购日期",CalendarUtils.getDateFormatString(bean.getBillCreateTime(),CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD)))
                    .setText(R.id.purchaser_order_arrival_date, String.format("%s:%s","到货日期",CalendarUtils.getDateFormatString(bean.getBillExecuteDate(),CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD)));
        }
    }

}
