package com.hll_sc_app.app.report.customreceivequery;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.customreceivequery.detail.CustomReceiveDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.MultiSelectionWindow;
import com.hll_sc_app.widget.SingleSelectionWindow;
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
    @Autowired(name = "parcelable", required = true)
    ReceiveCustomerBean mBean;
    @Autowired(name = "startDate", required = true)
    Date mStartDate;
    @Autowired(name = "endDate", required = true)
    Date mEndDate;

    private Unbinder unbinder;
    private ICustomReceiveQueryContract.IPresent mPresent;
    private ReceiveAdapter mAdapter;

    private SingleSelectionWindow<PurchaserShopBean> mSelectShopWindow;
    private MultiSelectionWindow<FilterParams.TypeBean> mSelectTypeWindow;
    private SingleSelectionWindow<FilterParams.StatusBean> mSelectStatusWindow;
    private DateRangeWindow mDateWindow;
    private ContextOptionsWindow mTitleMenuWindow;
    private List<PurchaserShopBean> mShopList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_custom_receive_query);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresent = CustomReceiveQueryPresent.newInstance();
        mPresent.register(this);
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTxtCustom.setText(mBean.getShopName());
        mAdapter = new ReceiveAdapter(null);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CustomReceiveDetailActivity.start(null, mAdapter.getItem(position), 0);
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

        /*时间选择初始化*/
        updateSelectDate();
        mDateWindow = new DateRangeWindow(this);
        mDateWindow.setReset(false);
        mDateWindow.setSelectCalendarRange(mStartDate, mEndDate);
        mDateWindow.setOnRangeChangedListener((start, end) -> {
            mStartDate = start;
            mEndDate = end;
            updateSelectDate();
            mPresent.refresh(true);
        });
        mDateWindow.setOnDismissListener(() -> {
            mImgDate.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mTxtDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
    }

    private void updateSelectDate() {
        mTxtDate.setText(String.format("%s-%s", CalendarUtils.format(mStartDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mEndDate, Constants.SLASH_YYYY_MM_DD)));
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void querySuccess(List<CustomReceiveListResp.RecordsBean> customReceiveBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(customReceiveBeans);
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this)
                    .setImage(R.drawable.ic_char_empty)
                    .setTips("当前条件下没有收货数据噢").create());
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
        return mBean.getGroupID();
    }

    @Override
    public String getDemandID() {
        return mBean.getDemandID();
    }

    @Override
    public String getPurchaserID() {
        return mBean.getPurchaserID();
    }

    @Override
    public String getStartDate() {
        return CalendarUtils.toLocalDate(mStartDate);
    }

    @Override
    public String getEndDate() {
        return CalendarUtils.toLocalDate(mEndDate);
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
    public void queryListFail() {
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setNetError(true).setOnClickListener(() -> {
            mPresent.refresh(true);
        }).create());
        mAdapter.setNewData(null);
    }

    @Override
    public void cacheShopList(List<PurchaserShopBean> list) {
        mShopList = list;
    }

    private void showShopWindow() {
        if (mShopList == null) {
            mPresent.queryCustomer();
            return;
        }
        mImgCustom.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mTxtCustom.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mSelectShopWindow == null) {
            mSelectShopWindow = new SingleSelectionWindow<>(this, PurchaserShopBean::getShopName);
            mSelectShopWindow.setListHeight(UIUtils.dip2px(400));
            mSelectShopWindow.refreshList(mShopList);
            mSelectShopWindow.setSelectListener(purchaserBean -> {
                mSelectShopWindow.dismiss();
                mTxtCustom.setText(purchaserBean.getShopName());
                mBean.setDemandID(purchaserBean.getExtShopID());
                mPresent.refresh(true);
            });
            mSelectShopWindow.setOnDismissListener(() -> {
                mImgCustom.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mTxtCustom.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mSelectShopWindow.showAsDropDown(mCslFilter);
    }

    @OnClick({R.id.view_filter_custome, R.id.view_filter_date, R.id.view_filter_type, R.id.view_filter_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_filter_custome:
                showShopWindow();
                break;
            case R.id.view_filter_date:
                mImgDate.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mTxtDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mDateWindow.showAsDropDown(mCslFilter);
                break;
            case R.id.view_filter_type:
                if (mSelectTypeWindow == null) {
                    List<FilterParams.TypeBean> data = CustomReceiveListResp.getTypeList();
                    mSelectTypeWindow = new MultiSelectionWindow<>(this, data, new MultiSelectionWindow.Config<FilterParams.TypeBean>() {
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
                            mTxtType.setText(nameBuilder.length() == 0 ? "单据类型" : selectedIndexs.size() == data.size() ? "全部" : nameBuilder.toString());
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

    private class ReceiveAdapter extends BaseQuickAdapter<CustomReceiveListResp.RecordsBean, BaseViewHolder> {

        public ReceiveAdapter(@Nullable List<CustomReceiveListResp.RecordsBean> data) {
            super(R.layout.list_item_query_custom_receive, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomReceiveListResp.RecordsBean item) {
            helper.setText(R.id.txt_no, item.getVoucherNo())
                    .setText(R.id.txt_status, item.getVoucherStatusName())
                    .setText(R.id.txt_type, "类型：" + item.getVoucherTypeName())
                    .setText(R.id.txt_money, "金额：¥" + CommonUtils.formatMoney(item.getTotalPrice()));
        }
    }
}
