package com.hll_sc_app.app.report.customersettle.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.customersettle.search.RDCSearchActivity;
import com.hll_sc_app.app.report.customreceivequery.detail.CustomReceiveDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleDetailResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */
@Route(path = RouterConfig.REPORT_CUSTOMER_SETTLE_DETAIL)
public class CustomerSettleDetailActivity extends BaseLoadActivity implements ICustomerSettleDetailContract.ICustomerSettleDetailView {

    @BindView(R.id.csd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.csd_search_view)
    SearchView mSearchView;
    @BindView(R.id.csd_settle)
    TextView mSettle;
    @BindView(R.id.csd_settle_arrow)
    TriangleView mSettleArrow;
    @BindView(R.id.csd_reconciliation)
    TextView mReconciliation;
    @BindView(R.id.csd_reconciliation_arrow)
    TriangleView mReconciliationArrow;
    @BindView(R.id.csd_date)
    TextView mDate;
    @BindView(R.id.csd_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.csd_amount)
    TextView mAmount;
    @BindView(R.id.csd_paid)
    TextView mPaid;
    @BindView(R.id.csd_no_pay)
    TextView mNoPay;
    @BindView(R.id.csd_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mPurchaserName;
    @Autowired(name = "object1")
    String mPurchaserID;
    @Autowired(name = "object2")
    String mExtGroupID;
    @Autowired(name = "object3")
    String mStartDate;
    @Autowired(name = "object4")
    String mEndDate;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private ICustomerSettleDetailContract.ICustomerSettleDetailPresenter mPresenter;
    private CustomerSettleDetailAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private SingleSelectionWindow<NameValue> mSettleWindow;
    private SingleSelectionWindow<NameValue> mReconciliationWindow;

    /**
     * @param purchaserName 采购商名
     * @param purchaserID   采购商集团id
     * @param extGroupID    采购商集团id
     * @param startDate     开始日期
     * @param endDate       结束日期
     */
    public static void start(String purchaserName, String purchaserID, String extGroupID, Date startDate, Date endDate) {
        RouterUtil.goToActivity(RouterConfig.REPORT_CUSTOMER_SETTLE_DETAIL, purchaserName, purchaserID, extGroupID, CalendarUtils.toLocalDate(startDate), CalendarUtils.toLocalDate(endDate));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_customer_settle_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupId", mExtGroupID);
        mReq.put("supplierId", UserConfig.getGroupID());
        mDate.setTag(R.id.date_start, DateUtil.parse(mStartDate));
        mDate.setTag(R.id.date_end, DateUtil.parse(mEndDate));
        updateSelectedDate();
        mPresenter = CustomerSettleDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        getReq().put("startDate", CalendarUtils.toLocalDate(startDate));
        getReq().put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mPurchaserName);
        mSearchView.setHint("搜索配送中心、门店");
        mAmount.setText(processText(0, "进货金额"));
        mPaid.setText(processText(0, "已付款"));
        mNoPay.setText(processText(0, "未付款"));
        mAdapter = new CustomerSettleDetailAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(8)));
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> CustomReceiveDetailActivity.start(mExtGroupID, mAdapter.getItem(position), true));
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                RDCSearchActivity.start(CustomerSettleDetailActivity.this, searchContent, mPurchaserID);
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mReq.put("demandId", null);
                }
                mPresenter.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            String value = data.getStringExtra("value");
            mReq.put("demandId", value);
            mSearchView.showSearchContent(!TextUtils.isEmpty(name), name);
        }
    }

    private SpannableString processText(double money, String label) {
        String value = String.format("¥%s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(value + "\n" + label);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_222222)), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(1.08f), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @OnClick(R.id.csd_settle_button)
    public void selectSettleStatus(View view) {
        mSettleArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mSettle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mSettleWindow == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("全部", null));
            list.add(new NameValue("未结算", "0"));
            list.add(new NameValue("部分结算", "1"));
            list.add(new NameValue("已结算", "2"));
            mSettleWindow = new SingleSelectionWindow<>(this, NameValue::getName);
            mSettleWindow.setSelect(list.get(0));
            mSettleWindow.refreshList(list);
            mSettleWindow.setSelectListener(nameValue -> {
                mSettleWindow.dismiss();
                mSettle.setText(nameValue.getName());
                mReq.put("settlementStatus", nameValue.getValue());
                mPresenter.start();
            });
            mSettleWindow.setOnDismissListener(() -> {
                mSettleArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mSettle.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mSettleWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.csd_reconciliation_button)
    public void selectReconciliationStatus(View view) {
        mReconciliationArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mReconciliation.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mReconciliationWindow == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("全部", null));
            list.add(new NameValue("未对账", "0"));
            list.add(new NameValue("已对账", "1"));
            mReconciliationWindow = new SingleSelectionWindow<>(this, NameValue::getName);
            mReconciliationWindow.setSelect(list.get(0));
            mReconciliationWindow.refreshList(list);
            mReconciliationWindow.setSelectListener(nameValue -> {
                mReconciliationWindow.dismiss();
                mReconciliation.setText(nameValue.getName());
                mReq.put("checkStatus", nameValue.getValue());
                mPresenter.start();
            });
            mReconciliationWindow.setOnDismissListener(() -> {
                mReconciliationArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mReconciliation.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mReconciliationWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.csd_date_button)
    public void selectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                if (CalendarUtils.getDateBefore(end, 30).getTime() > start.getTime()) {
                    showToast("开始日期至结束日期限制选择31天以内");
                    return;
                }
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.start();
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.setOnDismissListener(() -> {
            mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void setData(CustomerSettleDetailResp resp) {
        mAmount.setText(processText(resp.getTotalPrice(), "进货金额"));
        mPaid.setText(processText(resp.getPaymentAmt(), "已付款"));
        mNoPay.setText(processText(resp.getUnPaymentAmt(), "未付款"));
        mAdapter.setNewData(resp.getList());
    }
}
