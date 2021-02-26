package com.hll_sc_app.app.crm.customer.intent.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.app.crm.customer.PartnerHelper;
import com.hll_sc_app.app.crm.customer.intent.add.AddCustomerActivity;
import com.hll_sc_app.app.crm.customer.plan.add.AddVisitPlanActivity;
import com.hll_sc_app.app.crm.customer.record.VisitRecordAdapter;
import com.hll_sc_app.app.crm.customer.record.add.AddVisitRecordActivity;
import com.hll_sc_app.app.crm.customer.record.detail.VisitRecordDetailActivity;
import com.hll_sc_app.app.crm.customer.seas.allot.SalesmanAllotActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.customer.CustomerIntentDetailView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.ViewCollections;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

@Route(path = RouterConfig.CRM_CUSTOMER_INTENT_DETAIL)
public class CustomerDetailActivity extends BaseLoadActivity implements ICustomerDetailContract.ICustomerDetailView {
    private static final int REQ_CODE = 0x943;
    @BindView(R.id.cid_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.cid_level_flag)
    ImageView mLevelFlag;
    @BindView(R.id.cid_name)
    TextView mName;
    @BindView(R.id.cid_level)
    TextView mLevel;
    @BindView(R.id.cid_status)
    TextView mStatus;
    @BindView(R.id.cid_source)
    TextView mSource;
    @BindView(R.id.cid_manager)
    TextView mManager;
    @BindView(R.id.cid_last_visit)
    TextView mLastVisit;
    @BindView(R.id.cid_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindViews({R.id.cid_plan, R.id.cid_record, R.id.cid_seas, R.id.cid_partner})
    List<View> mBottomButtons;
    @BindViews({R.id.cid_allot, R.id.cid_receive})
    List<View> mButtons;
    @BindView(R.id.cid_view_pager)
    ViewPager mViewPager;
    @Autowired(name = "parcelable", required = true)
    CustomerBean mBean;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;
    private CustomerIntentDetailView mDetailView;
    private boolean mHasChanged;
    private ICustomerDetailContract.ICustomerDetailPresenter mPresenter;
    private VisitRecordAdapter mAdapter;
    private EmptyView mEmptyView;
    private VisitRecordBean mCurBean;
    private PartnerHelper mHelper;

    public static void start(Activity activity, CustomerBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_INTENT_DETAIL, activity, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_customer_intent_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> AddCustomerActivity.start(this, mBean));
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mRefreshLayout = (SmartRefreshLayout) View.inflate(this, R.layout.layout_simple_refresh_list, null);
        mListView = mRefreshLayout.findViewById(R.id.srl_list_view);
        mListView.addItemDecoration(
                new SimpleDecoration(ContextCompat.getColor(this, R.color.colorPrimary), ViewUtils.dip2px(this, 0.5f)));
        mDetailView = new CustomerIntentDetailView(this);
        mViewPager.setAdapter(new ViewPagerAdapter(mDetailView, mRefreshLayout));
        mTabLayout.setViewPager(mViewPager, new String[]{"客户信息", "拜访记录"});
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
        if (mBean.isSeas()) {
            ViewCollections.run(mButtons, (view, index) -> view.setVisibility(View.VISIBLE));
            mTitleBar.setRightBtnVisible(true);
        } else if (mBean.getEmployeeID().equals(GreenDaoUtils.getUser().getEmployeeID())) {
            ViewCollections.run(mBottomButtons, (view, index) -> view.setVisibility(View.VISIBLE));
            mTitleBar.setRightBtnVisible(true);
        }
        mAdapter = new VisitRecordAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.cvr_root) {
                mCurBean = mAdapter.getItem(position);
                if (mCurBean == null) return;
                VisitRecordDetailActivity.start(this, mCurBean);
            }
        });
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                VisitRecordBean bean = data.getParcelableExtra(CustomerHelper.VISIT_KEY);
                mAdapter.replaceData(mCurBean, bean);
                if (bean != null) return;
                CustomerBean customer = data.getParcelableExtra(CustomerHelper.INTENT_KEY);
                if (customer != null) {
                    mHasChanged = true;
                    updateData(customer);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) setResult(RESULT_OK);
        super.onBackPressed();
    }

    private void initData() {
        updateData();
        mPresenter = CustomerDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateData() {
        mName.setText(mBean.getCustomerName());
        mLevelFlag.setImageResource(CustomerHelper.getCustomerLevelFlag(mBean.getCustomerLevel()));
        mLevel.setText(String.format("%s类客户", mBean.getCustomerLevel()));
        mStatus.setText(CustomerHelper.getCustomerState(mBean.getCustomerStatus()));
        mSource.setText(CustomerHelper.getCustomerSource(mBean.getCustomerSource()));
        mManager.setText(mBean.getCustomerAdmin());
        mLastVisit.setText(CommonUtils.getLong(mBean.getVisitTime()) == 0 ? ""
                : DateUtil.getReadableTime(mBean.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
        mDetailView.setData(mBean);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick(R.id.cid_plan)
    public void plan() {
        AddVisitPlanActivity.start(this, null);
    }

    @OnClick(R.id.cid_record)
    public void record() {
        AddVisitRecordActivity.start(this, null);
    }

    @OnClick(R.id.cid_seas)
    public void seas() {
        SuccessDialog.newBuilder(this)
                .setMessageTitle("确认转到公海吗？")
                .setMessage("客户转到公海后可由销售人员领取，确认是否转到公海")
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1)
                        mPresenter.transfer();
                }, "取消", "确认")
                .create().show();
    }

    @OnClick(R.id.cid_partner)
    public void partner(View view) {
        if (mHelper == null) {
            mHelper = new PartnerHelper();
        }
        mHelper.showOption(this, view, Gravity.RIGHT);
    }

    @OnClick(R.id.cid_allot)
    public void allot() {
        SalesmanAllotActivity.start(mBean.getId(), mBean.getCustomerName(), null);
    }

    @OnClick(R.id.cid_receive)
    public void receive() {
        SuccessDialog.newBuilder(this)
                .setMessageTitle("确认领取客户么")
                .setMessage("确认将“" + mBean.getCustomerName() + "”领取为意向客户么")
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1)
                        mPresenter.receive();
                }, "取消", "确认")
                .create().show();
    }

    public void updateData(CustomerBean bean) {
        mBean = bean;
        updateData();
    }

    @Override
    public void setData(List<VisitRecordBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("您还没有拜访记录哦~");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void loadError(UseCaseException e) {
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
    public String getID() {
        return mBean.getId();
    }

    @Override
    public void handleSuccess() {
        mHasChanged = true;
        onBackPressed();
    }
}
