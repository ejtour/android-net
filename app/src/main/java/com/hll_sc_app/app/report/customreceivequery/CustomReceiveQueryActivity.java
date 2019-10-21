package com.hll_sc_app.app.report.customreceivequery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.MutipleSelecteWindow;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.bean.window.OptionType.OPTION_REPORT_EXPORT_CUSTOMER_RECEIVE_LIST;

/***
 * 客户收货查询
 * */
@Route(path = RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE)
public class CustomReceiveQueryActivity extends BaseLoadActivity implements ICustomReceiveQueryContract.IView {
    @BindView(R.id.csl_filter)
    ConstraintLayout mCslFilter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.txt_custom)
    TextView mTxtCustom;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;

    @BindView(R.id.img_custom)
    TriangleView mImgCustom;
    @BindView(R.id.img_date)
    TriangleView mImgDate;
    @BindView(R.id.img_type)
    TriangleView mImgType;
    @BindView(R.id.img_status)
    TriangleView mImgStatus;

    @BindView(R.id.title_bar)
    TitleBar mTitle;


    private Unbinder unbinder;
    private ICustomReceiveQueryContract.IPresent mPresent;
    private ReceiveAdapter mAdapter;

    private SingleSelectionWindow<PurchaserBean> mSelectCustomWindow;
    private MutipleSelecteWindow<FilterParams.TypeBean> mSelectTypeWindow;
    private SingleSelectionWindow<FilterParams.StatusBean> mSelectStatusWindow;
    private DateRangeWindow mDateWindow;
    private ContextOptionsWindow mTitleMenuWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_custom_receive_query);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = CustomReceiveQueryPresent.newInstance();
        mPresent.register(this);
        mPresent.queryCustomer(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mAdapter = new ReceiveAdapter(null);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            //todo 跳入详情

        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh(false);
            }
        });

        mTitle.setRightBtnClick(v -> {
            if (mTitleMenuWindow == null) {
                mTitleMenuWindow = new ContextOptionsWindow(this);
                List<OptionsBean> menuList = new ArrayList<>();
                menuList.add(new OptionsBean(R.drawable.ic_export_option, OPTION_REPORT_EXPORT_CUSTOMER_RECEIVE_LIST));
                menuList.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_REPORT_EXPORT_CUSTOMER_RECEIVE_DETAIL));
                mTitleMenuWindow.refreshList(menuList);
                mTitleMenuWindow.setListener((adapter, view, position) -> {
                    mTitleMenuWindow.dismiss();
                    //todo 导出
                });
            }
            mTitleMenuWindow.showAsDropDownFix(mTitle, 0, 0, Gravity.RIGHT);
        });

    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
        mSelectCustomWindow.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);

    }

    @Override
    public void querySuccess(List<CustomReceiveListResp.CustomReceiveBean> customReceiveBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(customReceiveBeans);
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("喔唷，居然是「 空 」的").create());
            mAdapter.setNewData(customReceiveBeans);
        }
        if (!CommonUtils.isEmpty(customReceiveBeans)) {
            mRefreshLayout.setEnableLoadMore(customReceiveBeans.size() == mPresent.getPageSize());
        } else {
            mRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public String getOwnerId() {
        return null;
    }

    @Override
    public String getStartDate() {
        Object o = mTxtDate.getTag(R.id.date_start);
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public String getEndDate() {
        Object o = mTxtDate.getTag(R.id.date_end);
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public String getType() {
        Object o = mTxtType.getTag();
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public String getStatus() {
        Object o = mTxtStatus.getTag();
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public void queryCustomerListSuccess(List<PurchaserBean> purchaserBeans, boolean isMore) {
        if (mSelectCustomWindow == null) {
            mSelectCustomWindow = new SingleSelectionWindow<>(this, PurchaserBean::getPurchaserName);
            mSelectCustomWindow.setListHeight(UIUtils.dip2px(400));
            mSelectCustomWindow.setSelectListener(purchaserBean -> {
                mSelectCustomWindow.dismiss();
                mTxtCustom.setText(purchaserBean.getPurchaserName());
                mTxtCustom.setTag(purchaserBean.getPurchaserID());
                mPresent.refresh(true);
            });
            mSelectCustomWindow.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mPresent.getMoreCustomer();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                }
            });
            mSelectCustomWindow.setOnDismissListener(() -> {
                mImgCustom.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mTxtCustom.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        if (isMore) {
            mSelectCustomWindow.addList(purchaserBeans);
        } else {
            mSelectCustomWindow.refreshList(purchaserBeans);
            if (!CommonUtils.isEmpty(purchaserBeans)) {
                PurchaserBean first = purchaserBeans.get(0);
                mSelectCustomWindow.setSelect(first);
                mTxtCustom.setText(first.getPurchaserName());
                mTxtCustom.setTag(first.getPurchaserID());
                mPresent.refresh(true);
            }
        }
        if (!CommonUtils.isEmpty(purchaserBeans)) {
            mSelectCustomWindow.setEnableLoadMore(purchaserBeans.size() == mPresent.getPageSizeCustom());
        }
    }

    @Override
    public void queryListFail() {
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setNetError(true).setOnClickListener(() -> {
            mPresent.refresh(true);
        }).create());
        mAdapter.setNewData(null);
    }

    @OnClick({R.id.view_filter_custome, R.id.view_filter_date, R.id.view_filter_type, R.id.view_filter_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_filter_custome:
                if (mSelectCustomWindow == null) {
                    showToast("正在获取客户列表,请稍后");
                    return;
                }
                mImgCustom.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mTxtCustom.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mSelectCustomWindow.showAsDropDown(mCslFilter);
                break;
            case R.id.view_filter_date:
                if (mDateWindow == null) {
                    mDateWindow = new DateRangeWindow(this);
                    Calendar defaultCalendar = Calendar.getInstance();
                    mDateWindow.setSelectCalendarRange(defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH) + 1, 1,
                            defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH) + 1, defaultCalendar.get(Calendar.DATE));
                    mDateWindow.setOnRangeSelectListener((start, end) -> {
                        if (start == null || end == null) {
                            mTxtDate.setTag(R.id.date_start, null);
                            mTxtDate.setTag(R.id.date_end, null);
                            mTxtDate.setText("客户");
                        } else {
                            Calendar calendarStart = Calendar.getInstance();
                            calendarStart.setTimeInMillis(start.getTimeInMillis());
                            Calendar calendarEnd = Calendar.getInstance();
                            calendarEnd.setTimeInMillis(end.getTimeInMillis());
                            String showText = String.format("%s-%s", CalendarUtils.format(calendarStart.getTime(), "yyyy/MM/dd"),
                                    CalendarUtils.format(calendarEnd.getTime(), "yyyy/MM/dd"));
                            mTxtDate.setText(showText);
                            mTxtDate.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(), "yyyyMMdd"));
                            mTxtDate.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(), "yyyyMMdd"));
                        }
                        mPresent.refresh(false);
                    });
                    mDateWindow.setOnDismissListener(() -> {
                        mImgDate.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                        mTxtDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
                    });
                }
                mImgDate.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mTxtDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mDateWindow.showAsDropDown(mCslFilter);
                break;
            case R.id.view_filter_type:
                if (mSelectTypeWindow == null) {
                    List<FilterParams.TypeBean> data = FilterParams.getFilterTypeList();
                    mSelectTypeWindow = new MutipleSelecteWindow<>(this, data, new MutipleSelecteWindow.Config<FilterParams.TypeBean>() {
                        @Override
                        public boolean enableSelectAll() {
                            return true;
                        }

                        @Override
                        public boolean enableMultiple() {
                            return true;
                        }

                        @Override
                        public String getName(FilterParams.TypeBean item) {
                            return item.getTypeName();
                        }

                        @Override
                        public void onConfirm(List<Integer> selectedIndexs) {
                            StringBuilder nameBuilder = new StringBuilder();
                            StringBuilder idBuilder = new StringBuilder();
                            for (int index : selectedIndexs) {
                                nameBuilder.append(data.get(index).getTypeName()).append(",");
                                idBuilder.append(data.get(index).getType()).append(",");
                            }
                            mTxtType.setText(nameBuilder.length() == 0 ? "单据类型" : nameBuilder.toString());
                            mTxtType.setTag(idBuilder.toString());
                            mPresent.refresh(false);
                        }

                        @Override
                        public void showBefore() {
                            mImgType.update(TriangleView.TOP, ContextCompat.getColor(CustomReceiveQueryActivity.this, R.color.colorPrimary));
                            mTxtType.setTextColor(ContextCompat.getColor(CustomReceiveQueryActivity.this, R.color.colorPrimary));
                        }

                        @Override
                        public void dismissBefore() {
                            mImgType.update(TriangleView.BOTTOM, ContextCompat.getColor(CustomReceiveQueryActivity.this, R.color.color_dddddd));
                            mTxtType.setTextColor(ContextCompat.getColor(CustomReceiveQueryActivity.this, R.color.color_666666));
                        }
                    });
                }
                mSelectTypeWindow.showAsDropDown(mCslFilter);
                break;
            case R.id.view_filter_status:
                if (mSelectStatusWindow == null) {
                    mSelectStatusWindow = new SingleSelectionWindow<>(this, FilterParams.StatusBean::getStatusName);
                    List<FilterParams.StatusBean> data = new ArrayList<>();
                    data.add(new FilterParams.StatusBean("全部", 0));
                    data.add(new FilterParams.StatusBean("未审核", 1));
                    data.add(new FilterParams.StatusBean("已审核", 2));
                    mSelectStatusWindow.refreshList(data);
                    mSelectStatusWindow.setSelectListener(optionsBean -> {
                        mTxtStatus.setText(optionsBean.getStatusName());
                        mTxtStatus.setTag(optionsBean.getStatus());
                        mPresent.refresh(false);
                    });
                    mSelectStatusWindow.setSelect(data.get(0));
                    mSelectStatusWindow.setOnDismissListener(() -> {
                        mImgStatus.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                        mTxtStatus.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
                    });
                }
                mImgStatus.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mTxtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mSelectStatusWindow.showAsDropDown(mCslFilter);
                break;
            default:
                break;
        }
    }

    private class ReceiveAdapter extends BaseQuickAdapter<CustomReceiveListResp.CustomReceiveBean, BaseViewHolder> {

        public ReceiveAdapter(@Nullable List<CustomReceiveListResp.CustomReceiveBean> data) {
            super(R.layout.list_item_query_custom_receive, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomReceiveListResp.CustomReceiveBean item) {
            helper.setText(R.id.txt_no, item.getNo())
                    .setText(R.id.txt_status, item.getStatus())
                    .setText(R.id.txt_type, "类型：" + item.getTypeName())
                    .setText(R.id.txt_count, "数量：" + item.getCount())
                    .setText(R.id.txt_money, "金额：¥" + CommonUtils.formatMoney(item.getMoney()));
        }
    }

}
