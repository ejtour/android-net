package com.hll_sc_app.app.wallet.details.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.details.show.DetailsShowActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.details.DetailsRecordWrapper;
import com.hll_sc_app.bean.wallet.details.WalletDetailsParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.StickyItemDecoration;
import com.hll_sc_app.widget.TitleBar;
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

    @BindView(R.id.wdl_refresh)
    SmartRefreshLayout wdlRefresh;
    private DatePickerDialog mDatePickerDialog;
    private StickyItemDecoration mStickyItemDecoration;

    /**
     * @param settleUnitID 结算主体 id
     */
    public static void start(String settleUnitID) {
        RouterUtil.goToActivity(RouterConfig.WALLET_DETAILS_LIST, settleUnitID);
    }

    @Autowired(name = "object0", required = true)
    String settleUnitID;
    @BindView(R.id.wdl_list)
    RecyclerView wdlList;
    @BindView(R.id.wdl_header)
    TitleBar mTitleBar;
    @BindView(R.id.wdl_empty)
    EmptyView mEmptyView;
    /**
     * 过滤按钮
     */
    @BindView(R.id.wdl_filter)
    ImageView wdlFilter;
    private DetailsListAdapter mAdapter;
    private IDetailsListContract.IDetailsListPresenter mPresenter;
    private final WalletDetailsParam mParam = new WalletDetailsParam();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_wallet_details_list);
        ButterKnife.bind(this);
        initView();
        initData();
        bindListener();
    }

    private void bindListener() {
        mTitleBar.setRightBtnClick(v -> {
            mPresenter.export(null);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DetailsRecordWrapper item = (DetailsRecordWrapper) adapter.getItem(position);
            if (item != null && item.t != null) {
                DetailsShowActivity.start(item.t);
            }
        });
        wdlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        wdlRefresh.closeHeaderOrFooter();
    }

    private void initData() {
        Date curTime = new Date();
        mParam.setEndTime(curTime);
        mParam.setBeginTime(CalendarUtils.getDateBefore(curTime, 89));
        mParam.setSettleUnitID(settleUnitID);
        mPresenter = DetailsListPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        // 分割线
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0);
        wdlList.addItemDecoration(decor);
        // 粘性头部
        mStickyItemDecoration = new StickyItemDecoration();
        wdlList.addItemDecoration(mStickyItemDecoration);
        mAdapter = new DetailsListAdapter();
        wdlList.setAdapter(mAdapter);

        // 空布局
        mEmptyView.setTips("先去充个值试试?");
        mEmptyView.setTipsTitle("暂时还没有任何记录噢");
        mEmptyView.setTipsButton(null);
    }

    @OnClick(R.id.wdl_filter)
    public void filter() {
        if (mDatePickerDialog == null) {
            Calendar begin = Calendar.getInstance();
            begin.add(Calendar.YEAR, -3);
            mDatePickerDialog = DatePickerDialog.newBuilder(this)
                    .setBeginTime(begin.getTimeInMillis())
                    .setEndTime(System.currentTimeMillis())
                    .setToggleStatus(DatePickerDialog.ToggleStatus.DAY)
                    .setCallback(this)
                    .create();
        }
        mDatePickerDialog.show();
    }

    @Override
    public void select(Date time) {
        mParam.setFilter(true);
        mParam.setRange(false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DATE, 1);
        mParam.setBeginTime(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        mParam.setEndTime(calendar.getTime());
        mPresenter.start();
    }

    @Override
    public void select(Date beginTime, Date endTime) {
        mParam.setFilter(true);
        mParam.setRange(true);
        mParam.setBeginTime(beginTime);
        mParam.setEndTime(endTime);
        mPresenter.start();
    }

    @Override
    public void setDetailsList(List<DetailsRecordWrapper> wrappers) {
        mAdapter.setNewData(wrappers);
        mStickyItemDecoration.notifyChanged();
        if (wrappers.size() <= 1) {
            mEmptyView.setVisibility(View.VISIBLE);
            wdlFilter.setVisibility(wrappers.size() == 1 ? View.VISIBLE : View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            wdlFilter.setVisibility(View.VISIBLE);
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
}
