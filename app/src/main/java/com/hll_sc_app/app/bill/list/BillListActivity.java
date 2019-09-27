package com.hll_sc_app.app.bill.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.bill.detail.BillDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillListResp;
import com.hll_sc_app.bean.bill.BillStatus;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.filter.BillParam;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.aftersales.PurchaserShopSelectWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */
@Route(path = RouterConfig.BILL_LIST)
public class BillListActivity extends BaseLoadActivity implements IBillListContract.IBillListView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.abl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.abl_purchaser)
    TextView mPurchaser;
    @BindView(R.id.abl_purchaser_arrow)
    TriangleView mPurchaserArrow;
    @BindView(R.id.abl_date)
    TextView mDate;
    @BindView(R.id.abl_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.abl_type)
    TextView mType;
    @BindView(R.id.abl_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.abl_select_all)
    CheckBox mSelectAll;
    @BindView(R.id.abl_sum_label)
    TextView mSumLabel;
    @BindView(R.id.abl_amount)
    TextView mAmount;
    @BindView(R.id.abl_list_view)
    RecyclerView mListView;
    @BindView(R.id.abl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.abl_commit)
    TextView mCommit;
    private EmptyView mEmptyView;
    private ContextOptionsWindow mOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private PurchaserShopSelectWindow mSelectionWindow;
    private SingleSelectionWindow<NameValue> mTypeWindow;
    private final BillParam mParam = new BillParam();
    private BillListAdapter mAdapter;
    private IBillListContract.IBillListPresenter mPresenter;
    private boolean mIsDetailExport;
    private List<PurchaserBean> mPurchaserBeans;
    private BillListResp mResp;
    private BillBean mCurBean;
    private boolean mNotifyList = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        Date endDate = new Date();
        mParam.setEndDate(endDate);
        mParam.setStartDate(CalendarUtils.getDateBefore(endDate, 30));
        mParam.setSettlementStatus(BillStatus.NOT_SETTLE);
        updateSelectedDate();
        mPresenter = BillListPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
        mPresenter.getPurchaserList("");
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            if (!toggleBatch(View.GONE)) showOptionsWindow(v);
        });
        mAdapter = new BillListAdapter((buttonView, isChecked) -> {
            ((BillBean) buttonView.getTag()).setSelected(isChecked);
            updateBottomBar();
        });
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            switch (view.getId()) {
                case R.id.ibl_confirm:
                    mPresenter.doAction(Collections.singletonList(mCurBean.getId()));
                    break;
                case R.id.ibl_view_detail:
                    BillDetailActivity.start(this, mCurBean);
                    break;
                default:
                    break;
            }
        });
    }

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_BILL));
        list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_BILL_DETAIL));
        if (canBatch())
            list.add(new OptionsBean(R.drawable.ic_batch_option, OptionType.OPTION_BATCH_SETTLEMENT));
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private boolean canBatch() {
        return mParam.getSettlementStatus() != BillStatus.SETTLED && !CommonUtils.isEmpty(mAdapter.getData()) && !UserConfig.crm();
    }

    private void updateBottomBar() {
        if (canBatch() && mCommit.getVisibility() == View.VISIBLE) {
            int count = 0;
            for (BillBean bean : mAdapter.getData()) {
                if (bean.isSelected()) count++;
            }
            toggleCheckedWithoutNotify(count == mAdapter.getData().size());
            mCommit.setEnabled(count > 0);
            mCommit.setText(String.format("确认结算(%s)", count));
        } else toggleBatch(View.GONE);
        mSumLabel.setText(String.format("%s总计：", mParam.getSettlementStatus() == BillStatus.NOT_SETTLE ? "未结算" :
                mParam.getSettlementStatus() == BillStatus.SETTLED ? "已结算" : "剩余未结算"));
        mAmount.setText(String.format("¥%s",
                CommonUtils.formatMoney(mParam.getSettlementStatus() == BillStatus.SETTLED ? mResp.getTotalSettlementAmount() :
                        mResp.getTotalNoSettlementAmount())));
    }

    private boolean toggleBatch(int visibility) {
        if (mSelectAll.getVisibility() == visibility) return false;
        if (visibility == View.VISIBLE) {
            mTitleBar.setRightText("完成");
            mTitleBar.setHeaderTitle("批量结算对账单");
            mSelectAll.setVisibility(View.VISIBLE);
            mCommit.setVisibility(View.VISIBLE);
            mSumLabel.setVisibility(View.GONE);
            mAmount.setVisibility(View.GONE);
        } else if (mAmount.getVisibility() != View.VISIBLE) {
            toggleCheckedWithoutNotify(false);
            for (BillBean bean : mAdapter.getData()) {
                bean.setSelected(false);
            }
            mCommit.setEnabled(false);
            mCommit.setText("确认结算(0)");
            mTitleBar.setRightButtonImg(R.drawable.ic_options);
            mTitleBar.setHeaderTitle("对账单");
            mSelectAll.setVisibility(View.GONE);
            mCommit.setVisibility(View.GONE);
            mSumLabel.setVisibility(View.VISIBLE);
            mAmount.setVisibility(View.VISIBLE);
        }
        mAdapter.setBatch(mSelectAll.getVisibility() == View.VISIBLE);
        return true;
    }

    private void toggleCheckedWithoutNotify(boolean isChecked) {
        mNotifyList = false;
        mSelectAll.setChecked(isChecked);
        mNotifyList = true;
    }

    @OnCheckedChanged(R.id.abl_select_all)
    public void onCheckChanged(boolean isChecked) {
        if (!mNotifyList) return;
        for (BillBean bean : mAdapter.getData()) {
            bean.setSelected(isChecked);
        }
        mAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    @OnClick(R.id.abl_purchaser_btn)
    public void showPurchaseWindow(View view) {
        if (mPurchaserBeans == null) {
            mPresenter.getPurchaserList("");
            return;
        }
        mPurchaserArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mSelectionWindow == null) {
            mSelectionWindow = PurchaserShopSelectWindow.create(this, new PurchaserShopSelectWindow.PurchaserShopSelectCallback() {
                @Override
                public void onSelect(String purchaserID, String shopID, List<String> shopNameList) {
                    mSelectionWindow.dismiss();
                    mParam.setExtra(shopID);
                    mPresenter.start();
                    if (!CommonUtils.isEmpty(shopNameList)) {
                        mPurchaser.setText(TextUtils.join(",", shopNameList));
                    } else mPurchaser.setText("采购商");
                }

                @Override
                public boolean search(String searchWords, int flag, String purchaserID) {
                    if (flag == 0) mPresenter.getPurchaserList(searchWords);
                    else mPresenter.getShopList(purchaserID, searchWords);
                    return true;
                }

                @Override
                public void loadPurchaserShop(String purchaserID, String searchWords) {
                    mPresenter.getShopList(purchaserID, searchWords);
                }
            }).setMulti(true).setLeftList(mPurchaserBeans).setRightList(null);
            mSelectionWindow.setOnDismissListener(() -> {
                mPurchaserArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mSelectionWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.abl_date_btn)
    public void showDateWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null || end == null) return;
                String oldStart = mParam.getFormatStartDate();
                String oldEnd = mParam.getFormatEndDate();

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(start.getTimeInMillis());
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(end.getTimeInMillis());
                mParam.setStartDate(calendarStart.getTime());
                mParam.setEndDate(calendarEnd.getTime());

                if (!mParam.getFormatStartDate().equals(oldStart) || !mParam.getFormatEndDate().equals(oldEnd)) {
                    updateSelectedDate();
                    mPresenter.start();
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
            start.setTime(mParam.getStartDate());
            end.setTime(mParam.getEndDate());
            mDateRangeWindow.setSelectCalendarRange(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DATE),
                    end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private void updateSelectedDate() {
        mDate.setText(String.format("%s - %s", mParam.getFormatStartDate(Constants.SLASH_YYYY_MM_DD),
                mParam.getFormatEndDate(Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick(R.id.abl_type_btn)
    public void showTypeWindow(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mTypeWindow == null) {
            List<NameValue> list = new ArrayList<>();
            NameValue curValue = new NameValue("未结算", String.valueOf(BillStatus.NOT_SETTLE));
            list.add(curValue);
            list.add(new NameValue("已结算", String.valueOf(BillStatus.SETTLED)));
            list.add(new NameValue("部分结算", String.valueOf(BillStatus.PART_SETTLED)));
            mTypeWindow = new SingleSelectionWindow<>(this, NameValue::getName);
            mTypeWindow.refreshList(list);
            mTypeWindow.setSelect(curValue);
            mTypeWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mTypeWindow.setSelectListener(nameValue -> {
                mParam.setSettlementStatus(Integer.valueOf(nameValue.getValue()));
                mType.setText(nameValue.getName());
                mPresenter.start();
            });
        }
        mTypeWindow.showAsDropDownFix(view);
    }

    @Override
    public void updateBillListResp(BillListResp resp, boolean isMore) {
        mResp = resp;
        setBillList(resp.getRecords(), isMore);
    }

    private void setBillList(List<BillBean> list, boolean isMore) {
        if (isMore) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("当前没有对账单哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
        updateBottomBar();
    }

    @Override
    public void refreshPurchaserList(List<PurchaserBean> list) {
        mPurchaserBeans = list;
        if (mSelectionWindow != null && mSelectionWindow.isShowing()) {
            mSelectionWindow.setLeftList(list);
        }
    }

    @Override
    public void refreshShopList(List<PurchaserShopBean> list) {
        mSelectionWindow.setRightList(list);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, this::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BillDetailActivity.REQ_CODE) {
            mPresenter.start();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this).setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mOptionsWindow.dismiss();
        OptionsBean bean = (OptionsBean) adapter.getItem(position);
        if (bean == null) return;
        if (OptionType.OPTION_BATCH_SETTLEMENT.equals(bean.getLabel())) {
            toggleBatch(View.VISIBLE);
            return;
        }
        mIsDetailExport = OptionType.OPTION_EXPORT_BILL_DETAIL.equals(bean.getLabel());
        export(null);
    }

    private void export(String email) {
        mPresenter.export(email, mIsDetailExport ? 2 : 1);
    }

    @OnClick(R.id.abl_commit)
    public void commit() {
        mCurBean = null;
        List<String> ids = new ArrayList<>();
        for (BillBean bean : mAdapter.getData()) {
            if (bean.isSelected())
                ids.add(bean.getId());
        }
        mPresenter.doAction(ids);
    }
}
