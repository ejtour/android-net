package com.hll_sc_app.app.crm.customer.search.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchTitleBar;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

@Route(path = RouterConfig.CRM_CUSTOMER_SEARCH_PLAN)
public class SearchPlanActivity extends BaseLoadActivity implements ISearchPlanContract.ISearchPlanView {
    private static final int REQ_CODE = 0x780;
    @BindView(R.id.cps_title_bar)
    SearchTitleBar mTitleBar;
    @BindView(R.id.cps_list_view)
    RecyclerView mListView;
    @BindView(R.id.cps_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.cps_date)
    TextView mDate;
    @BindView(R.id.cps_date_arrow)
    TriangleView mDateArrow;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    int mType;
    private ISearchPlanContract.ISearchPlanPresenter mPresenter;
    private SearchPlanAdapter mAdapter;
    private EmptyView mEmptyView;
    private Date mStartDate, mEndDate;
    private DateRangeWindow mDateRangeWindow;

    /**
     * @param id   已选的计划id
     * @param type 1-意向客户 2-合作客户
     */
    public static void start(Activity context, String id, int type) {
        Object[] args = {id, type};
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEARCH_PLAN, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_plan_search);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = SearchPlanPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mTitleBar.setOnSearchListener(mPresenter::start);
    }

    private void initView() {
        mStartDate = mEndDate = new Date();
        updateDate();
        mAdapter = new SearchPlanAdapter(mID);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1)));
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            VisitPlanBean item = mAdapter.getItem(position);
            if (item != null) {
                Intent intent = new Intent();
                intent.putExtra("parcelable", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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

    private void updateDate() {
        mDate.setText(String.format("%s - %s",
                CalendarUtils.format(mStartDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mEndDate, Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick(R.id.cps_date)
    public void selectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                if (start == null && end == null) {
                    mStartDate = mEndDate = new Date();
                } else {
                    mStartDate = start;
                    mEndDate = end;
                }
                updateDate();
                mPresenter.start();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @Override
    public void setData(List<VisitPlanBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("搜索不到相关计划");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
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

    @Override
    public String getSearchWords() {
        return mTitleBar.getSearchContent();
    }

    @Override
    public int getCustomerType() {
        return mType;
    }

    @Override
    public Date getStartDate() {
        return mStartDate;
    }

    @Override
    public Date getEndDate() {
        return mEndDate;
    }
}
