package com.hll_sc_app.app.report.orderGoods;

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
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.orderGoods.detail.OrderGoodsDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.aftersales.PurchaserShopSelectWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

@Route(path = RouterConfig.REPORT_ORDER_GOODS)
public class OrderGoodsActivity extends BaseLoadActivity implements IOrderGoodsContract.IOrderGoodsView {
    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rog_purchaser)
    TextView mPurchaser;
    @BindView(R.id.rog_purchaser_arrow)
    TriangleView mPurchaserArrow;
    @BindView(R.id.rog_date)
    TextView mDate;
    @BindView(R.id.rog_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.rog_list_view)
    RecyclerView mListView;
    @BindView(R.id.rog_refresh_view)
    SmartRefreshLayout mRefreshView;
    private ContextOptionsWindow mOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private PurchaserShopSelectWindow mSelectionWindow;
    private List<PurchaserBean> mPurchaserBeans;
    private IOrderGoodsContract.IOrderGoodsPresenter mPresenter;
    private final OrderGoodsParam mParam = new OrderGoodsParam();
    private OrderGoodsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_order_goods);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date endDate = new Date();
        mParam.setEndDate(endDate);
        mParam.setStartDate(CalendarUtils.getDateBefore(endDate, 29));
        updateSelectedDate();
        mPresenter = OrderGoodsPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        mDate.setText(String.format("%s - %s",
                CalendarUtils.format(mParam.getStartDate(), Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mParam.getEndDate(), Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mAdapter = new OrderGoodsAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderGoodsBean item = mAdapter.getItem(position);
            if (item == null) return;
            OrderGoodsDetailActivity.start(item, mParam.getStartDate(), mParam.getEndDate());
        });
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mListView.setAdapter(mAdapter);
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private void export(String email) {
        mPresenter.export(email);
    }

    @OnClick({R.id.rog_purchaser_btn, R.id.rog_date_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rog_purchaser_btn:
                if (mPurchaserBeans == null) {
                    mPresenter.getPurchaserList("");
                    return;
                }
                showPurchaserWindow(view);
                break;
            case R.id.rog_date_btn:
                showDateRangeWindow(view);
                break;
        }
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showList(List<OrderGoodsBean> list, boolean append) {
        if (append) mAdapter.addData(list);
        else mAdapter.setNewData(list);
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, this::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    @Override
    public void refreshPurchaserList(List<PurchaserBean> list) {
        mPurchaserBeans = list;
        if (mSelectionWindow != null && mSelectionWindow.isShowing()) {
            mSelectionWindow.setLeftList(list);
        }
    }

    @Override
    public void refreshShopList(List<PurchaserShopBean> list) {
        mSelectionWindow.setRightList(list);
    }

    private void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                String oldBegin = mParam.getFormatStartDate();
                String oldEnd = mParam.getFormatEndDate();
                if (start == null && end == null) {
                    mParam.setStartDate(null);
                    mParam.setEndDate(null);
                    mDate.setText("按日期筛选");
                    if (oldBegin != null && oldEnd != null) {
                        mPresenter.reload();
                    }
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    mParam.setStartDate(calendarStart.getTime());
                    mParam.setEndDate(calendarEnd.getTime());
                    updateSelectedDate();
                    if ((oldBegin == null && oldEnd == null) ||
                            !mParam.getFormatStartDate().equals(oldBegin) ||
                            !mParam.getFormatEndDate().equals(oldEnd)) {
                        mPresenter.reload();
                    }
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
            start.setTime(mParam.getStartDate());
            end.setTime(mParam.getEndDate());
            mDateRangeWindow.setSelectCalendarRange(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DATE),
                    end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private void showPurchaserWindow(View view) {
        mPurchaserArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mSelectionWindow == null) {
            mSelectionWindow = PurchaserShopSelectWindow.create(this, new PurchaserShopSelectWindow.PurchaserShopSelectCallback() {
                @Override
                public void onSelect(String purchaserID, String shopID, List<String> shopNameList) {
                    mSelectionWindow.dismiss();
                    mParam.setShopIDs(shopID);
                    mPresenter.reload();
                    if (!CommonUtils.isEmpty(shopNameList)) {
                        mPurchaser.setText(TextUtils.join(",", shopNameList));
                    } else mPurchaser.setText("全部采购商");
                }

                @Override
                public boolean search(String searchWords, int flag, String purchaserID) {
                    if (flag == 0) mPresenter.getPurchaserList(searchWords);
                    else mPresenter.getShopList(purchaserID, searchWords);
                    return true;
                }

                @Override
                public void loadPurchaserShop(String purchaserID, String searchWords) {
                    mPresenter.getShopList(purchaserID, searchWords);
                }
            }).setMulti(true).setLeftList(mPurchaserBeans).setRightList(null);
            mSelectionWindow.setOnDismissListener(() -> {
                mPurchaserArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mSelectionWindow.showAsDropDownFix(view);
    }
}
