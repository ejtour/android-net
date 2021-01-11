package com.hll_sc_app.app.cardmanage.transactiondetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.bean.cardmanage.CardTransactionListResp;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.MultiSelectionWindow;
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

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_TRANSACTION_LIST, extras = Constant.LOGIN_EXTRA)
public class TransactionListActivity extends BaseLoadActivity implements ITransactionListContract.IView {
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.ll_shop)
    LinearLayout mLlShop;
    @BindView(R.id.ll_type)
    LinearLayout mLlType;
    @BindView(R.id.txt_time)
    TextView mTxtTime;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mDealListView;
    @Autowired(name = "parcelable")
    CardManageBean mCardBean;
    private Unbinder unbinder;
    private MultiSelectionWindow<String> selecteTypeWindow;
    private MultiSelectionWindow<ReportFormSearchResp.ShopMallBean> selecteShopWindow;
    private DateRangeWindow selecteTimeWindow;
    private ITransactionListContract.IPresent mPresenter;

    private DealAdapter mAdapter;

    public static void start(CardManageBean bean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_TRANSACTION_LIST, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_transaction_list);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresenter = TransactionListPresent.newInstance();
        mPresenter.register(this);
        mPresenter.queryPurchaseList(mCardBean.getPurchaserID(), "");
        mPresenter.queryDetailList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });

        mAdapter = new DealAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            TransactionDetailActivity.start(mAdapter.getItem(position));
        });
        mDealListView.setAdapter(mAdapter);

        //默认时间范围当前月
        Calendar calendar = Calendar.getInstance();
        mLlTime.setTag(R.id.date_start, CalendarUtils.format(CalendarUtils.getFirstDateInMonth(calendar.getTime()),FORMAT_LOCAL_DATE));
        mLlTime.setTag(R.id.date_end, CalendarUtils.format(calendar.getTime(),FORMAT_LOCAL_DATE));
    }

    @OnClick({R.id.ll_type, R.id.ll_shop, R.id.ll_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_type:
                if (selecteTypeWindow == null) {
                    List<String> status = new ArrayList<>();
                    status.add("全部");
                    status.add("消费");
                    status.add("充值");
                    status.add("消费退款");
                    selecteTypeWindow = new MultiSelectionWindow<>(this, status, new MultiSelectionWindow.Config<String>() {
                        @Override
                        public boolean enableSelectAll() {
                            return false;
                        }

                        @Override
                        public boolean enableMultiple() {
                            return false;
                        }

                        @Override
                        public String getName(String item) {
                            return item;
                        }

                        @Override
                        public void onConfirm(List<Integer> selecteIndexs) {
                            int index = selecteIndexs.get(0);
                            mLlType.setTag(index == 1 ? "2" : index == 2 ? "1" : index == 3 ? "3" : "");
                            mPresenter.filter();
                        }

                        @Override
                        public void showBefore() {
                            dimissOtherWindow();
                        }

                        @Override
                        public void dismissBefore() {
                        }
                    });
                    selecteTypeWindow.setContentViewHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                    selecteTypeWindow.addSelectedIndex(0);
                }
                toggleWindow(selecteTypeWindow);
                break;
            case R.id.ll_shop:
                if (selecteShopWindow == null) {
                    return;
                }
                toggleWindow(selecteShopWindow);
                break;
            case R.id.ll_time:
                if (selecteTimeWindow == null) {
                    selecteTimeWindow = new DateRangeWindow(this);
                    selecteTimeWindow.setOnRangeSelectListener((start, end) -> {
                        if (start == null && end == null) {
                            mTxtTime.setText("报价日期");
                            mLlTime.setTag(R.id.date_start, null);
                            mLlTime.setTag(R.id.date_end, null);
                            mPresenter.filter();
                            return;
                        }
                        if (start != null && end != null) {
                            Calendar calendarStart = Calendar.getInstance();
                            calendarStart.setTimeInMillis(start.getTimeInMillis());
                            String startStr = CalendarUtils.format(calendarStart.getTime(), "yyyy/MM/dd");
                            Calendar calendarEnd = Calendar.getInstance();
                            calendarEnd.setTimeInMillis(end.getTimeInMillis());
                            String endStr = CalendarUtils.format(calendarEnd.getTime(), "yyyy/MM/dd");
                            mTxtTime.setText(String.format("%s - %s", startStr, endStr));
                            mLlTime.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_SERVER_DATE));
                            mLlTime.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_SERVER_DATE));
                            mPresenter.filter();
                        }
                    });
                    initSelectDate();
                }
                toggleWindow(selecteTimeWindow);
                break;
            default:
                break;
        }
    }

    /**
     * 设置初始时间的选择 默认选中当天的
     */
    private void initSelectDate() {
        Calendar current = Calendar.getInstance();
        selecteTimeWindow.setSelectCalendarRange(current.get(Calendar.YEAR), current.get(Calendar.MONTH) + 1, 1,
                current.get(Calendar.YEAR), current.get(Calendar.MONTH) + 1, current.get(Calendar.DATE));
    }

    private void dimissOtherWindow() {
        if (selecteShopWindow != null && selecteShopWindow.isShowing()) {
            selecteShopWindow.dismiss();
        }
        if (selecteTypeWindow != null && selecteTypeWindow.isShowing()) {
            selecteTypeWindow.dismiss();
        }
        if (selecteTimeWindow != null && selecteTimeWindow.isShowing()) {
            selecteTimeWindow.dismiss();
        }
    }

    /**
     * window的显示与隐藏
     *
     * @param window
     */
    private void toggleWindow(BasePopupWindow window) {
        if (window == null) {
            return;
        }
        if (window.isShowing()) {
            window.dismiss();
        } else {
            window.showAsDropDownFix(mLlTime);
        }
    }

    @Override
    public String getShopID() {
        if (mLlShop.getTag() == null) {
            return null;
        } else {
            return ((ReportFormSearchResp.ShopMallBean) mLlShop.getTag()).getShopmallID();
        }
    }

    @Override
    public String getTradeType() {
        if (mLlType.getTag() == null) {
            return null;
        } else {
            return mLlType.getTag().toString();
        }
    }

    @Override
    public String getStartDate() {
        if (mLlTime.getTag(R.id.date_start) == null) {
            return "";
        } else {
            return mLlTime.getTag(R.id.date_start).toString() + "000000";
        }
    }

    @Override
    public String getEndDate() {
        if (mLlTime.getTag(R.id.date_end) == null) {
            return "";
        } else {
            return mLlTime.getTag(R.id.date_end).toString() + "235959";
        }
    }

    @Override
    public String getCardNo() {
        return mCardBean.getCardNo();
    }

    @Override
    public void queryListSuccess(CardTransactionListResp resp, boolean isMore) {
        if (resp.getRecords() == null) {
            return;
        }
        if (isMore && resp.getRecords().size() > 0) {
            mAdapter.addData(resp.getRecords());
        } else if (!isMore) {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("目前没有交易记录噢").create());
            mAdapter.setNewData(resp.getRecords());
        }
        if (resp.getRecords() != null) {
            mRefreshLayout.setEnableLoadMore(resp.getRecords().size() == mPresenter.getPageSize());
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private class DealAdapter extends BaseQuickAdapter<CardTransactionListResp.CardDealDetail, BaseViewHolder> {
        public DealAdapter(@Nullable List<CardTransactionListResp.CardDealDetail> data) {
            super(R.layout.list_item_card_transaction, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CardTransactionListResp.CardDealDetail item) {
            ((ImageView) helper.setText(R.id.txt_shop_name, item.getTradeType() == 1 ? "集团充值" : item.getShopName())
                    .setText(R.id.txt_type, item.getStradeType())
                    .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getTradeTime(), "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm"))
                    .setText(R.id.txt_price, (item.getTradeType() == 2 ? "-" : "+") +
                            CommonUtils.formatMoney(item.getTradeAmount()))
                    .getView(R.id.img_url))
                    .setImageResource(getImgSource(item.getTradeType()));
        }

        private int getImgSource(int type) {
            if (type == 1) {
                return R.drawable.ic_money_radius_blue;
            } else if (type == 2) {
                return R.drawable.ic_car_radius_red;
            } else {
                return R.drawable.ic_refund_radius_yellow;
            }
        }

    }


    @Override
    public void queryPurchaseSuccess(ReportFormSearchResp reportFormSearchResp) {
        ReportFormSearchResp.ShopMallBean allBean = new ReportFormSearchResp.ShopMallBean();
        allBean.setName("全部门店");
        allBean.setShopmallID("0");
        reportFormSearchResp.getList().add(0, allBean);
        if (selecteShopWindow == null) {
            selecteShopWindow = new MultiSelectionWindow<>(this, reportFormSearchResp.getList(), new MultiSelectionWindow.Config<ReportFormSearchResp.ShopMallBean>() {
                @Override
                public boolean enableSelectAll() {
                    return false;
                }

                @Override
                public boolean enableMultiple() {
                    return false;
                }

                @Override
                public String getName(ReportFormSearchResp.ShopMallBean item) {
                    return item.getName();
                }

                @Override
                public void onConfirm(List<Integer> selectedIndexs) {
                    selecteShopWindow.dismiss();
                    mLlShop.setTag(selecteShopWindow.getAllData().get(selectedIndexs.get(0)));
                    mPresenter.queryDetailList(true);
                }

                @Override
                public void showBefore() {

                }

                @Override
                public void dismissBefore() {

                }

                @Override
                public void search(String content) {
                    mPresenter.queryPurchaseList(mCardBean.getPurchaserID(), content);
                }

                @Override
                public boolean isHasSearch() {
                    return true;
                }

                @Override
                public String searchHint() {
                    return "请输入关键字搜索";
                }
            });
            selecteShopWindow.setContentViewHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            selecteShopWindow.addSelectedIndex(0);
        } else {
            selecteShopWindow.setNewData(reportFormSearchResp.getList());
        }
    }
}
