package com.hll_sc_app.app.report.voucherconfirm.detail;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.customersettle.detail.CustomerSettleDetailAdapter;
import com.hll_sc_app.app.report.customreceivequery.detail.CustomReceiveDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.bean.report.voucherconfirm.VoucherGroupBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/21
 */
@Route(path = RouterConfig.REPORT_VOUCHER_CONFIRM_DETAIL)
public class VoucherConfirmDetailActivity extends BaseLoadActivity implements IVoucherConfirmDetailContract.IVoucherConfirmDetailView {
    private static final int REQ_CODE = 0x968;
    @BindView(R.id.rrc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rrc_date)
    TextView mDate;
    @BindView(R.id.rrc_select_num)
    TextView mSelectNum;
    @BindView(R.id.rrc_confirm)
    TextView mConfirm;
    @BindView(R.id.rrc_bottom_group)
    Group mBottomGroup;
    @BindView(R.id.rrc_list_view)
    RecyclerView mListView;
    @BindView(R.id.rrc_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    VoucherGroupBean mGroupBean;
    private EmptyView mEmptyView;
    private CustomerSettleDetailAdapter mAdapter;
    private IVoucherConfirmDetailContract.IVoucherConfirmDetailPresenter mPresenter;
    private final BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private DateSelectWindow mDateSelectWindow;
    private boolean mHasChanged;

    public static void start(Activity activity, VoucherGroupBean bean, String startDate, String endDate) {
        bean.setStartDate(startDate);
        bean.setEndDate(endDate);
        RouterUtil.goToActivity(RouterConfig.REPORT_VOUCHER_CONFIRM_DETAIL, activity, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_receipt_check);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initData() {
        mReq.put("extGroupID", mGroupBean.getExtGroupID());
        mReq.put("groupID", UserConfig.getGroupID());
        mDate.setTag(R.id.date_start, DateUtil.parse(mGroupBean.getStartDate()));
        mDate.setTag(R.id.date_end, DateUtil.parse(mGroupBean.getEndDate()));
        updateSelectDate();
        mPresenter = VoucherConfirmDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        getReq().put("startVoucherDate", CalendarUtils.toLocalDate(startDate));
        getReq().put("endVoucherDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mTitleBar.setHeaderTitle(mGroupBean.getPurchaserName());
        mBottomGroup.setVisibility(View.VISIBLE);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mAdapter = new CustomerSettleDetailAdapter(true);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CustomReceiveListResp.RecordsBean bean = mAdapter.getItem(position);
            if (bean != null) {
                bean.setSelect(!bean.isSelect());
                mAdapter.notifyItemChanged(position);
                updateSelectNum();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CustomReceiveListResp.RecordsBean bean = mAdapter.getItem(position);
            if (bean != null) {
                CustomReceiveDetailActivity.start(mGroupBean.getPurchaserName(), bean, 2);
            }
        });
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

    private void updateSelectNum() {
        int count = 0;
        for (CustomReceiveListResp.RecordsBean bean : mAdapter.getData()) {
            if (bean.isSelect()) {
                count++;
            }
        }
        mConfirm.setEnabled(count > 0);
        mSelectNum.setText(String.format("已选：%s", CommonUtils.formatNum(count)));
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @OnClick(R.id.rrc_date_btn)
    void selectDate() {
        if (mDateSelectWindow == null) {
            mDateSelectWindow = new DateSelectWindow(this);
            mDateSelectWindow.setForbiddenStartBeforeCurrent(false);
            mDateSelectWindow.setSelectRange(CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_start)),
                    CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_end)));
            mDateSelectWindow.setSelectListener((startDate, endDate) -> {
                Date start = DateUtil.parse(startDate);
                Date end = DateUtil.parse(endDate);
                if (CalendarUtils.getYear(start) != CalendarUtils.getYear(end)) {
                    showToast("日期查询不允许跨年");
                    return;
                }
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectDate();
                mPresenter.start();
            });
        }
        mDateSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.rrc_confirm)
    void confirm() {
        List<String> ids = new ArrayList<>();
        for (CustomReceiveListResp.RecordsBean bean : mAdapter.getData()) {
            if (bean.isSelect()) {
                ids.add(bean.getVoucherID());
            }
        }
        mPresenter.confirm(mGroupBean.getExtGroupID(), ids);
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

    @Override
    public void setData(List<CustomReceiveListResp.RecordsBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("暂无数据");
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
        updateSelectNum();
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Subscribe
    public void handleVoucherConfirmEvent(CustomReceiveListResp.RecordsBean bean) {
        mHasChanged = true;
        List<CustomReceiveListResp.RecordsBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            CustomReceiveListResp.RecordsBean recordsBean = data.get(i);
            if (TextUtils.equals(bean.getVoucherID(), recordsBean.getVoucherID())) {
                mAdapter.remove(i);
                return;
            }
        }
        mPresenter.start();
    }

    @Override
    public void success() {
        mHasChanged = true;
        List<CustomReceiveListResp.RecordsBean> list = new ArrayList<>();
        if (mAdapter.getData().size() != 1) {
            for (CustomReceiveListResp.RecordsBean bean : mAdapter.getData()) {
                if (bean.isSelect()) continue;
                list.add(bean);
            }
        }
        setData(list, false);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }
}
