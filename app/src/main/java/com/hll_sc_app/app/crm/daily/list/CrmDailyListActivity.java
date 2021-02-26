package com.hll_sc_app.app.crm.daily.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.daily.CrmDailyAdapter;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.filter.DateStringParam;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
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
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

@Route(path = RouterConfig.CRM_DAILY_LIST)
public class CrmDailyListActivity extends BaseLoadActivity implements ICrmDailyListContract.ICrmDailyListView {
    private static final int REQ_CODE = 0x659;
    @BindView(R.id.cdl_search_view)
    SearchView mSearchView;
    @BindView(R.id.cdl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.cdl_type)
    TextView mType;
    @BindView(R.id.cdl_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.cdl_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.cdl_date)
    TextView mDate;
    @BindView(R.id.cdl_list_view)
    RecyclerView mListView;
    @BindView(R.id.cdl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    boolean mSend;
    private DateStringParam mDateParam = new DateStringParam();
    private CrmDailyAdapter mAdapter;
    private ICrmDailyListContract.ICrmDailyListPresenter mPresenter;
    private EmptyView mEmptyView;
    private DatePickerDialog mDateDialog;
    private SingleSelectionDialog mDialog;
    private boolean mHasChanged;

    /**
     * @param send 是否为发送日报
     */
    public static void start(Activity context, boolean send) {
        Object[] args = {send};
        RouterUtil.goToActivity(RouterConfig.CRM_DAILY_LIST, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_daily_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        if (resultCode == RESULT_OK) {
            mHasChanged = true;
            mPresenter.start();
        }
    }

    private void initView() {
        if (!mSend) {
            mTitleBar.setHeaderTitle("");
            mTitleBar.setRightText("统计");
            mTitleBar.setRightBtnClick(v -> RouterUtil.goToActivity(RouterConfig.REPORT_SALES_DAILY));
            mSearchView.setVisibility(View.VISIBLE);
            mSearchView.setTextColorWhite();
            mSearchView.setSearchTextLeft();
            mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
            mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
                @Override
                public void click(String searchContent) {
                    SearchActivity.start(CrmDailyListActivity.this, searchContent, CommonSearch.class.getSimpleName());
                }

                @Override
                public void toSearch(String searchContent) {
                    mPresenter.start();
                }
            });
        }
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mAdapter = new CrmDailyAdapter(mSend);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mListView.setAdapter(mAdapter);
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

    private void initData() {
        Date date = new Date();
        mDateParam.setStartDate(CalendarUtils.getFirstDateInMonth(date));
        mDateParam.setEndDate(date);
        updateDate();
        mPresenter = CrmDailyListPresenter.newInstance(mDateParam, mSend);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateDate() {
        mDate.setText(String.format("%s - %s",
                mDateParam.getFormatStartDate(Constants.SLASH_YYYY_MM_DD), mDateParam.getFormatEndDate(Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick(R.id.cdl_filter_btn)
    public void selectDate() {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateDialog == null) {
            mDateDialog = DatePickerDialog.newBuilder(this)
                    .setBeginTime(CalendarUtils.parse("20170101", Constants.UNSIGNED_YYYY_MM_DD).getTime())
                    .setEndTime(System.currentTimeMillis())
                    .setSelectBeginTime(mDateParam.getStartDate().getTime())
                    .setSelectEndTime(mDateParam.getEndDate().getTime())
                    .setCallback(new DatePickerDialog.SelectCallback() {
                        @Override
                        public void select(Date beginTime, Date endTime) {
                            mDateParam.setStartDate(beginTime);
                            mDateParam.setEndDate(endTime);
                            updateDate();
                            mPresenter.start();
                        }
                    })
                    .create();
            mDateDialog.setOnDismissListener(dialog -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mDateDialog.show();
    }

    @OnClick(R.id.cdl_type)
    public void selectType() {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            nameValues.add(new NameValue("全部日报", "0"));
            nameValues.add(new NameValue("未读", "1"));
            nameValues.add(new NameValue("已读", "2"));
            nameValues.add(new NameValue("未回复", "3"));
            nameValues.add(new NameValue("已回复", "4"));
            NameValue cur = nameValues.get(0);
            mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择类别")
                    .refreshList(nameValues)
                    .select(cur)
                    .setOnSelectListener(value -> {
                        mDateParam.setExtra(value.getValue());
                        mType.setText(value.getName());
                        mPresenter.start();
                    })
                    .create();
            mDialog.setOnDismissListener(dialog -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mDialog.show();
    }

    @Override
    public void setData(SingleListResp<DailyBean> resp, boolean append) {
        if (mSend) {
            mTitleBar.setHeaderTitle(String.format("我发送的(%s)", resp.getTotalSize()));
        }
        List<DailyBean> records = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(records)) mAdapter.addData(records);
        } else {
            if (CommonUtils.isEmpty(records)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据哦");
            }
            mAdapter.setNewData(records);
        }
        mRefreshLayout.setEnableLoadMore(records != null && records.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
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
    public void onBackPressed() {
        if (mHasChanged) setResult(RESULT_OK);
        super.onBackPressed();
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
