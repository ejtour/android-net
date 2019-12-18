package com.hll_sc_app.app.stockmanage.purchaserorder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.stockmanage.purchaserorder.detail.PurchaserOrderDetailActivity;
import com.hll_sc_app.app.stockmanage.purchaserorder.search.PurchaserOrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
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
 * 采购单查询
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.STOCK_PURCHASER_ORDER)
public class PurchaserOrderActivity extends BaseLoadActivity implements IPurchaserOrderContract.IPurchaserOrderView {
    @BindView(R.id.spo_search_view)
    SearchView mSearchView;
    @BindView(R.id.spo_type)
    TextView mType;
    @BindView(R.id.spo_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.spo_date)
    TextView mDate;
    @BindView(R.id.spo_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.spo_list_view)
    RecyclerView mListView;
    @BindView(R.id.spo_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private PurchaserOrderListAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private PurchaserOrderPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_stock_purchaser_order);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mAdapter = new PurchaserOrderListAdapter();
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserOrderBean item = (PurchaserOrderBean) adapter.getItem(position);
            if (item == null) return;
            String billID = item.getBillID();
            PurchaserOrderDetailActivity.start(billID);
        });
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                PurchaserOrderSearchActivity.start(PurchaserOrderActivity.this);
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mReq.put("supplierIDs", "");
                }
                mPresenter.start();
            }
        });
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("flag", "1");
        Date date = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getDateBefore(date, 29));
        mDate.setTag(R.id.date_end, date);
        updateSelectDate();
        mPresenter = PurchaserOrderPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void setData(List<PurchaserOrderBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("噫，没有数据");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.SEARCH_RESULT_CODE) {
            String supplyiers = data.getStringExtra("result");
            mReq.put("supplierIDs", supplyiers);
            mPresenter.start();
        }
    }

    @OnClick(R.id.spo_type_btn)
    public void selectType(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_date_white, OptionType.OPTION_PURCHASER_ORDER_CREATE_DATE));
            list.add(new OptionsBean(R.drawable.ic_date_white, OptionType.OPTION_PURCHASER_ORDER_ARRIVAL_DATE));
            mOptionsWindow.refreshList(list);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                OptionsBean item = (OptionsBean) adapter.getItem(position);
                if (item == null) return;
                mOptionsWindow.dismiss();
                mReq.put("flag", TextUtils.equals(item.getLabel(), OptionType.OPTION_PURCHASER_ORDER_CREATE_DATE) ? "1" : "2");
                mPresenter.start();
                mType.setText(item.getLabel());
            });
            mOptionsWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.START);
    }

    @OnClick(R.id.spo_date_btn)
    public void selectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_end, end);
                mDate.setTag(R.id.date_start, start);
                updateSelectDate();
                mPresenter.start();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }


    private static class PurchaserOrderListAdapter extends BaseQuickAdapter<PurchaserOrderBean, BaseViewHolder> {

        PurchaserOrderListAdapter() {
            super(R.layout.item_stock_purchaser_order);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserOrderBean bean) {
            helper.setText(R.id.spo_name, bean.getSupplierName())
                    .setText(R.id.spo_status, bean.getStatusDesc())
                    .setText(R.id.spo_no, bean.getBillNo())
                    .setText(R.id.spo_amount, String.format("¥%s", CommonUtils.formatMoney(bean.getTotalPrice())))
                    .setText(R.id.spo_purchase_date, String.format("采购日期：%s", DateUtil.getReadableTime(bean.getBillCreateTime(), Constants.SLASH_YYYY_MM_DD)))
                    .setText(R.id.spo_arrive_date, String.format("到货日期：%s", DateUtil.getReadableTime(bean.getBillExecuteDate(), Constants.SLASH_YYYY_MM_DD)));
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
