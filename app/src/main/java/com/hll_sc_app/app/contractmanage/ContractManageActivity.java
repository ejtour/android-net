package com.hll_sc_app.app.contractmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_list);
        unbinder = ButterKnife.bind(this);
        mPresent = ContractManagePresent.newInstance();
        mPresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
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
                    status.add(new NameValue("已审核", "0"));
                    status.add(new NameValue("执行中", "0"));
                    status.add(new NameValue("已终止", "0"));
                    status.add(new NameValue("已过期", "0"));
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
                    status.add(new NameValue("小于30天的", "0"));
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

  /*  private class ContractListAdapter extends BaseQuickAdapter<>{

    }*/
}
