package com.hll_sc_app.app.complainmanage.ordernumberlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.adapter.SelectItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 投诉详情-选择订单
 */
@Route(path = RouterConfig.ACTIVITY_ORDER_LIST_NUMBER, extras = Constant.LOGIN_EXTRA)
public class SelectOrderListActivity extends BaseLoadActivity implements ISelectOrderListContract.IView {

    @Autowired(name = "subBillNo")
    String mSubBillNo;
    @Autowired(name = "purchaserId")
    String mPurchaserId;
    @Autowired(name = "shopId")
    String mShopId;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mProductListView;
    @BindView(R.id.txt_filter_time)
    TextView mTxtFilterTime;


    private Unbinder unbinder;
    private ISelectOrderListContract.IPresent mPresent;

    private SelectItemAdapter<OrderResp> mAdpter;

    private DateRangeWindow mDateRangeWindow;

    public static void start(Activity activity, int requestCode, String subBillNo, String purchaserId, String shopId) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_ORDER_LIST_NUMBER)
                .withString("subBillNo", subBillNo)
                .withString("purchaserId", purchaserId)
                .withString("shopId", shopId)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_number_list);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = SelectOrderListPresent.newInstance();
        mPresent.register(this);
        mPresent.queryOrderList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        initList();
        initTime();
    }


    private void initList() {
        mRefreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mAdpter = new SelectItemAdapter<>(null, new SelectItemAdapter.Config<OrderResp>() {
            @Override
            public String getName(OrderResp orderResp) {
                return "订单号: " + orderResp.getSubBillNo();
            }

            @Override
            public boolean isSelected(OrderResp orderResp) {
                return TextUtils.equals(orderResp.getSubBillNo(), mSubBillNo);
            }
        });
        mAdpter.setOnItemClickListener((adapter, view, position) -> {
            OrderResp orderResp = mAdpter.getItem(position);
            mSubBillNo = orderResp.getSubBillNo();
            mAdpter.notifyDataSetChanged();
            Intent intent = new Intent();
            intent.putExtra("order", orderResp);
            setResult(RESULT_OK, intent);
            finish();
        });
        mProductListView.setAdapter(mAdpter);
    }

    private void initTime() {
        Date cur = new Date();
        Date pre = CalendarUtils.getDateBefore(cur, 30);
        String preT = CalendarUtils.format(pre, "yyyy/MM/dd");
        String curT = CalendarUtils.format(new Date(), "yyyy/MM/dd");
        mTxtFilterTime.setText(preT + " - " + curT);
        mTxtFilterTime.setTag(R.id.date_start, CalendarUtils.format(pre, "yyyyMMdd"));
        mTxtFilterTime.setTag(R.id.date_end, CalendarUtils.format(cur, "yyyyMMdd"));
    }

    @OnClick({R.id.txt_filter_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_filter_time:
                showDateRangeWindow();
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
                    mTxtFilterTime.setText(null);
                    mTxtFilterTime.setTag(R.id.date_start, "");
                    mTxtFilterTime.setTag(R.id.date_end, "");
                    mPresent.queryOrderList(true);
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), "yyyy/MM/dd");
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), "yyyy/MM/dd");
                    mTxtFilterTime.setText(String.format("%s-%s", startStr, endStr));
                    mTxtFilterTime.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mTxtFilterTime.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mPresent.queryOrderList(true);
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mTxtFilterTime);
    }

    @Override
    public void queySuccess(List<OrderResp> orderResps, boolean isMore) {
        if (isMore && orderResps.size() > 0) {
            mAdpter.addData(orderResps);
        } else if (!isMore) {
            mAdpter.setNewData(orderResps);
            mAdpter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("咿，该日期范围内没有可选的订单噢~").create());
        }
        mRefreshLayout.setEnableAutoLoadMore(orderResps.size() == mPresent.getPageSize());
    }

    @Override
    public String getPurchaserId() {
        return mPurchaserId;
    }

    @Override
    public String getShopId() {
        return mShopId;
    }

    @Override
    public String getCreateTimeStart() {
        return mTxtFilterTime.getTag(R.id.date_start).toString();
    }

    @Override
    public String getCreateTimeEnd() {
        return mTxtFilterTime.getTag(R.id.date_end).toString();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }
}
