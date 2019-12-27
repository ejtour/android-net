package com.hll_sc_app.app.contractmanage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
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

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_LOCAL_DATE;

/**
 * 合同管理
 */
@Route(path = RouterConfig.ACTIVITY_CONTRACT_MANAGE_LIST)
public class ContractManageActivity extends BaseLoadActivity implements IContractManageContract.IView {
    @BindView(R.id.tri_time)
    TriangleView mTriTime;
    @BindView(R.id.tri_status)
    TriangleView mTriStatus;
    @BindView(R.id.tri_days)
    TriangleView mTriDays;
    @BindView(R.id.txt_time)
    TextView mTxtTime;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_days)
    TextView mTxtDays;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.csl_filter)
    ConstraintLayout mCslFilter;
    private Unbinder unbinder;

    private DateRangeWindow mDateRangeWindow;
    private SingleSelectionWindow<NameValue> mSelectStatus;
    private SingleSelectionWindow<NameValue> mSelectDays;

    private IContractManageContract.IPresent mPresent;

    private ContractListAdapter mAdpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_list);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = ContractManagePresent.newInstance();
        mPresent.register(this);
        mPresent.queryList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mAdpter = new ContractListAdapter(null);
        mListView.setAdapter(mAdpter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.queryMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });

        mTitleBar.setRightBtnClick(v -> {
            RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD);
        });
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ContractManageActivity.this,
                        searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {

            }
        });

    }

    @OnClick({R.id.ll_time, R.id.ll_status, R.id.ll_days})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                if (mDateRangeWindow == null) {
                    mDateRangeWindow = new DateRangeWindow(this);
                    mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                        if (start == null && end == null) {
                            mTxtTime.setText("报价日期");
                            mTxtTime.setTag(R.id.date_start, "");
                            mTxtTime.setTag(R.id.date_end, "");
//                            mPresenter.queryQuotationList(true);
                            return;
                        }
                        if (start != null && end != null) {
                            Calendar calendarStart = Calendar.getInstance();
                            calendarStart.setTimeInMillis(start.getTimeInMillis());
                            Calendar calendarEnd = Calendar.getInstance();
                            calendarEnd.setTimeInMillis(end.getTimeInMillis());
                           /* //跨度大于30天
                            if (CalendarUtils.getDateAfter(calendarStart.getTime(), 30).after(calendarEnd.getTime())) {
                                showToast("只能选择不大于30天的时间");
                                return;
                            }*/
                            String startStr = CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_DATE_TIME);

                            String endStr = CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                            mTxtTime.setText(String.format("%s\n%s", startStr, endStr));
                            mTxtTime.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                                    CalendarUtils.FORMAT_SERVER_DATE));
                            mTxtTime.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                                    CalendarUtils.FORMAT_SERVER_DATE));
//                            mPresenter.queryQuotationList(true);
                        }
                    });
                    mDateRangeWindow.setOnDismissListener(() -> {
                        mTxtTime.setSelected(false);
                        mTriTime.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                    });
                }
                mTxtTime.setSelected(true);
                mTriTime.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mDateRangeWindow.showAsDropDownFix(mCslFilter);
                break;
            case R.id.ll_status:
                if (mSelectStatus == null) {
                    ArrayList<NameValue> status = new ArrayList<>();
                    status.add(new NameValue("全部", ""));
                    status.add(new NameValue("未审核", "0"));
                    status.add(new NameValue("已审核", "1"));
                    status.add(new NameValue("执行中", "2"));
                    status.add(new NameValue("已终止", "3"));
                    status.add(new NameValue("已过期", "4"));
                    mSelectStatus = new SingleSelectionWindow<>(this, NameValue::getName);
                    mSelectStatus.refreshList(status);
                    mSelectStatus.setSelectListener(nameValue -> {
                        if (TextUtils.isEmpty(nameValue.getValue())) {
                            mTxtStatus.setText("全部");
                        } else {
                            mTxtStatus.setText(nameValue.getName());
                        }
                        mTxtStatus.setTag(nameValue);
                    });
                    mSelectStatus.setOnDismissListener(() -> {
                        mTxtStatus.setSelected(false);
                        mTriStatus.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                    });
                }
                mTxtStatus.setSelected(true);
                mTriStatus.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mSelectStatus.showAsDropDownFix(mCslFilter);
                break;
            case R.id.ll_days:
                if (mSelectDays == null) {
                    ArrayList<NameValue> status = new ArrayList<>();
                    status.add(new NameValue("全部", ""));
                    status.add(new NameValue("小于30天的", "1"));
                    mSelectDays = new SingleSelectionWindow<>(this, NameValue::getName);
                    mSelectDays.refreshList(status);
                    mSelectDays.setSelectListener(nameValue -> {
                        if (TextUtils.isEmpty(nameValue.getValue())) {
                            mTxtDays.setText("全部");
                        } else {
                            mTxtDays.setText(nameValue.getName());
                        }
                        mTxtStatus.setTag(nameValue);
                    });
                    mSelectDays.setOnDismissListener(() -> {
                        mTxtDays.setSelected(false);
                        mTriDays.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                    });
                }
                mTxtDays.setSelected(true);
                mTriDays.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
                mSelectDays.showAsDropDownFix(mCslFilter);
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
            if (!TextUtils.isEmpty(name)) {
                mSearchView.showSearchContent(true, name);
            }
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        mAdpter.setEmptyView(EmptyView.newBuilder(this).setNetError(true)
                .setOnClickListener(new EmptyView.OnActionClickListener() {
                    @Override
                    public void retry() {
                        mPresent.refresh();
                    }
                }).create());

    }

    @Override
    public void querySuccess(ContractListResp resp, boolean isMore) {
        if (isMore) {
            mAdpter.addData(resp.getList());
        } else {
            if (CommonUtils.isEmpty(resp.getList())) {
                EmptyView emptyView = EmptyView.newBuilder(this)
                        .setTipsButton("新建一份合同")
                        .setTipsTitle("您还没有一份合同噢")
                        .setTips("点击下方按钮创建一份合同吧")
                        .setOnClickListener(new EmptyView.OnActionClickListener() {
                            @Override
                            public void retry() {

                            }

                            @Override
                            public void action() {
                                RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD);
                            }
                        })
                        .create();
                mAdpter.setNewData(null);
                mAdpter.setEmptyView(emptyView);
            } else {
                mAdpter.setNewData(resp.getList());
            }
        }
        if (CommonUtils.isEmpty(resp.getList())) {
            mRefreshLayout.setEnableLoadMore(resp.getList().size() == mPresent.getPageSize());
        }
    }

    @Override
    public String getSignTimeStart() {
        return null;
    }

    @Override
    public String getSignTimeEnd() {
        return null;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public String getDays() {
        return null;
    }

    @Override
    public String getContractCode() {
        return null;
    }

    @Override
    public String getContractName() {
        return null;
    }

    @Override
    public String getPurchaserName() {
        return null;
    }


    private class ContractListAdapter extends BaseQuickAdapter<ContractListResp.ContractBean, BaseViewHolder> {
        public ContractListAdapter(@Nullable List<ContractListResp.ContractBean> data) {
            super(R.layout.list_item_contract_manage, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ContractListResp.ContractBean item) {
            helper.setText(R.id.txt_contract_name, item.getContractName())
                    .setText(R.id.txt_group_name, item.getGroupName())
                    .setText(R.id.txt_group_name, "集团名称：" + item.getGroupName())
                    .setText(R.id.txt_contract_code, "合同编号：" + item.getContractCode())
                    .setText(R.id.txt_contract_person, "签约人：" + item.getSignEmployeeName())
                    .setText(R.id.txt_time_range, "起止时间：" + CalendarUtils.getDateFormatString(item.getStartDate(), FORMAT_LOCAL_DATE, "yyyy/MM/dd") + "-" +
                            CalendarUtils.getDateFormatString(item.getEndDate(), FORMAT_LOCAL_DATE, "yyyy/MM/dd"))
                    .setText(R.id.txt_time_sign, CalendarUtils.getDateFormatString(item.getSignDate(), FORMAT_LOCAL_DATE, "yyyy/MM/dd"))
                    .setText(R.id.txt_status, item.getTransformStatus())
                    .setTextColor(R.id.txt_status, getStatusColor(item.getStatus()))
                    .setText(R.id.txt_days, item.getDistanceExpirationDate() + "");
        }


        private @ColorInt
        int getStatusColor(int status) {
            String color = "#999999";
            switch (status) {
                case 0:
                    color = "#F5A623";
                    break;
                case 1:
                    color = "#5695D2";
                    break;
                case 2:
                    color = "#7ED321";
                    break;
                case 3:
                    color = "#ED5655";
                    break;
                case 4:
                    color = "#999999";
                    break;
                default:
                    color = "#999999";
                    break;
            }
            return Color.parseColor(color);
        }
    }
}