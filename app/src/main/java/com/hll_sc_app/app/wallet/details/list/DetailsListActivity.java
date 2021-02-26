package com.hll_sc_app.app.wallet.details.list;

import android.os.Bundle;
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
import com.hll_sc_app.app.wallet.details.show.DetailsShowActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.filter.WalletDetailsParam;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;
import com.hll_sc_app.bean.wallet.details.DetailsRecord;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.wallet.TradeTypeWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
@Route(path = RouterConfig.WALLET_DETAILS_LIST)
public class DetailsListActivity extends BaseLoadActivity implements IDetailsListContract.IDetailsListView, DatePickerDialog.SelectCallback {

    /**
     * @param settleUnitID 结算主体 id
     */
    public static void start(String settleUnitID) {
        RouterUtil.goToActivity(RouterConfig.WALLET_DETAILS_LIST, settleUnitID);
    }

    @Autowired(name = "object0", required = true)
    String settleUnitID;
    @BindView(R.id.wdl_list)
    RecyclerView mListView;
    @BindView(R.id.wdl_header)
    TitleBar mTitleBar;
    @BindView(R.id.wdl_date_filter)
    TextView mDate;
    @BindView(R.id.wdl_amount)
    TextView mAmount;
    @BindView(R.id.wdl_filter_container)
    View mFilterContainer;
    @BindView(R.id.wdl_refresh)
    SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyView;
    private DatePickerDialog mDatePickerDialog;
    private TradeTypeWindow mTradeTypeWindow;
    private DetailsListAdapter mAdapter;
    private IDetailsListContract.IDetailsListPresenter mPresenter;
    private final WalletDetailsParam mParam = new WalletDetailsParam();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_wallet_details_list);
        ButterKnife.bind(this);
        initView();
        initData();
        bindListener();
    }

    private void bindListener() {
        mTitleBar.setRightBtnClick(v -> {
            if (!RightConfig.checkRight(getString(R.string.right_rechargeList_exportDetail))) {
                showToast(getString(R.string.right_tips));
                return;
            }
            mPresenter.export(null);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DetailsRecord item = (DetailsRecord) adapter.getItem(position);
            if (item != null) {
                DetailsShowActivity.start(item);
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

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private void initData() {
        Date curTime = new Date();
        mParam.setEndDate(curTime);
        mParam.setStartDate(CalendarUtils.getDateBefore(curTime, 89));
        mParam.setSettleUnitID(settleUnitID);
        mParam.setRange(true);
        updateSelectDate();
        mPresenter = DetailsListPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectDate() {
        if (mParam.isRange()) {
            mDate.setText(String.format("%s - %s", mParam.getFormatStartDate(Constants.SIGNED_YYYY_MM_DD),
                    mParam.getFormatEndDate(Constants.SIGNED_YYYY_MM_DD)));
        } else {
            mDate.setText(mParam.getFormatStartDate(Constants.SIGNED_YYYY_MM));
        }
    }

    private void initView() {
        // 分割线
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0);
        mListView.addItemDecoration(decor);
        mAdapter = new DetailsListAdapter();
        mListView.setAdapter(mAdapter);
    }

    @OnClick(R.id.wdl_date_filter)
    public void filterDate() {
        if (mDatePickerDialog == null) {
            Calendar begin = Calendar.getInstance();
            begin.add(Calendar.YEAR, -3);
            mDatePickerDialog = DatePickerDialog.newBuilder(this)
                    .setBeginTime(begin.getTimeInMillis())
                    .setEndTime(System.currentTimeMillis())
                    .setToggleStatus(DatePickerDialog.ToggleStatus.MONTH)
                    .setShowDay(false)
                    .setShowEnd(false)
                    .setCallback(this)
                    .create();
        }
        mDatePickerDialog.show();
    }

    @OnClick(R.id.wdl_type_filter)
    public void filterType() {
        if (mTradeTypeWindow == null) {
            mTradeTypeWindow = new TradeTypeWindow(this, value -> {
                mParam.setTransType(value);
                mPresenter.start();
            });
        }
        mTradeTypeWindow.showAsDropDownFix(mFilterContainer);
    }

    @Override
    public void select(Date time) {
        mParam.setFilter(true);
        mParam.setRange(false);
        mParam.setStartDate(CalendarUtils.getFirstDateInMonth(time));
        mParam.setEndDate(CalendarUtils.getLastDateInMonth(time));
        updateSelectDate();
        mPresenter.start();
    }

    @Override
    public void select(Date beginTime, Date endTime) {
        mParam.setFilter(true);
        mParam.setRange(true);
        mParam.setStartDate(beginTime);
        mParam.setEndDate(endTime);
        updateSelectDate();
        mPresenter.start();
    }

    @Override
    public void setDetailsList(DetailsListResp resp, boolean append) {
        List<DetailsRecord> records = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(records)) {
                mAdapter.addData(records);
            }
        } else {
            if (CommonUtils.isEmpty(records)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("先去充个值试试?");
                mEmptyView.setTipsTitle("暂时还没有任何记录噢");
            }
            mAdapter.setNewData(records);
        }
        mRefreshLayout.setEnableLoadMore(records != null && records.size() == 20);
        if (resp.getTransAmountSum() != null && resp.getTransSalesCommissionSum() != null) {
            mAmount.setVisibility(View.VISIBLE);
            mAmount.setText(String.format("交易金额：¥%s   交易费用：¥%s",
                    CommonUtils.formatMoney(CommonUtils.getDouble(resp.getTransAmountSum())),
                    CommonUtils.formatMoney(CommonUtils.getDouble(resp.getTransSalesCommissionSum()))));
        } else {
            mAmount.setVisibility(View.GONE);
        }
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
    public void bindEmail() {
        Utils.bindEmail(this, email -> mPresenter.export(email));
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
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
