package com.hll_sc_app.app.agreementprice.quotation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.BaseAgreementPriceFragment;
import com.hll_sc_app.app.agreementprice.search.AgreementPriceSearchActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;
import com.hll_sc_app.bean.event.RefreshQuotationList;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 协议价管理-报价单
 *
 * @author 朱英松
 * @date 2019/7/8
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION)
public class QuotationFragment extends BaseAgreementPriceFragment implements QuotationFragmentContract.IHomeView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_filter)
    LinearLayout mLlFilter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.txt_purchaser)
    TextView mTxtPurchaser;
    @BindView(R.id.img_purchaser)
    ImageView mImgSupplier;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.img_date)
    ImageView mImgDate;
    @BindView(R.id.txt_effectDate)
    TextView mTxtEffectDate;
    @BindView(R.id.img_effectDate)
    ImageView mImgEffectDate;
    @BindView(R.id.ll_effectDate)
    RelativeLayout mLlEffectDate;
    @BindView(R.id.txt_confirm_export)
    TextView mTxtConfirmExport;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    @BindView(R.id.img_quotation_add)
    ImageView mImgQuotationAdd;
    @BindView(R.id.img_select_all)
    ImageView mImgSelectAll;
    private QuotationFragmentContract.IHomePresenter mPresenter;
    private PurchaserSelectWindow mPurchaserWindow;
    private DateRangeWindow mDateRangeWindow;
    private DateRangeWindow mDateEffectWindow;
    private QuotationListAdapter mAdapter;
    private EmptyView mEmptyView;
    private EmptyView mNetEmptyView;

    public QuotationFragmentContract.IHomePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = QuotationFragmentPresenter.newInstance();
        mPresenter.register(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_agreement_price_quotation, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    protected void initData() {
        if (!isSearchActivity()) {
            mPresenter.start();
        }
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreQuotationList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryQuotationList(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(),
            R.color.base_color_divider), UIUtils.dip2px(10)));
        mAdapter = new QuotationListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            QuotationBean bean = (QuotationBean) adapter.getItem(position);
            if (bean != null) {
                if (mAdapter.isExportStatus()) {
                    bean.setSelect(!bean.isSelect());
                    adapter.notifyDataSetChanged();
                    showExportCount();
                } else {
                    RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_DETAIL, bean);
                }
            }
        });
        mEmptyView = EmptyView.newBuilder(getActivity()).setTipsTitle("喔唷，居然是「 空 」的").create();
        mNetEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
            setForceLoad(true);
            lazyLoad();
        }).create();
        mRecyclerView.setAdapter(mAdapter);
        if (isSearchActivity()) {
            mEmptyView.setTips("输入报价单号、客户名称进行搜索");
            mAdapter.setEmptyView(mEmptyView);
            mLlFilter.setVisibility(View.GONE);
            mImgQuotationAdd.setVisibility(View.GONE);
        }
    }

    /**
     * 显示底部导出数量
     */
    private void showExportCount() {
        int count = 0;
        boolean selectAll = true;
        List<QuotationBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (QuotationBean bean : list) {
                if (bean.isSelect()) {
                    count++;
                } else {
                    selectAll = false;
                }
            }
        }
        mTxtConfirmExport.setEnabled(count != 0);
        mTxtConfirmExport.setText(String.format(Locale.getDefault(), "确定导出（%d）", count));
        mImgSelectAll.setSelected(selectAll);
    }

    @Subscribe
    public void onEvent(RefreshQuotationList event) {
        mPresenter.queryQuotationList(false);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException exception) {
        super.showError(exception);
        if (exception.getLevel() == UseCaseException.Level.NET) {
            mAdapter.setEmptyView(mNetEmptyView);
        }
    }

    @Override
    public void showQuotationList(QuotationResp resp, boolean append) {
        mAdapter.setEmptyView(mEmptyView);
        List<QuotationBean> list = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == QuotationFragmentPresenter.PAGE_SIZE);
        showExportCount();
    }

    @Override
    public void showPurchaserWindow(List<PurchaserBean> list) {
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new PurchaserSelectWindow(getActivity(), list);
            mPurchaserWindow.setListener(bean -> {
                if (TextUtils.equals(bean.getPurchaserName(), "全部")) {
                    mTxtPurchaser.setText("客户");
                } else {
                    mTxtPurchaser.setText(bean.getPurchaserName());
                }
                mTxtPurchaser.setTag(bean.getPurchaserID());
                mPresenter.queryQuotationList(true);
            });
            mPurchaserWindow.setOnDismissListener(() -> {
                mTxtPurchaser.setSelected(false);
                mImgSupplier.setSelected(false);
                mImgSupplier.setRotation(0F);
            });
        }
        mPurchaserWindow.showAsDropDownFix(mLlFilter);
    }

    @Override
    public void showDateWindow() {
        mTxtDate.setSelected(true);
        mImgDate.setSelected(true);
        mImgDate.setRotation(-180F);
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(getActivity());
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    mTxtDate.setText("报价日期");
                    mTxtDate.setTag(R.id.date_start, "");
                    mTxtDate.setTag(R.id.date_end, "");
                    mPresenter.queryQuotationList(true);
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    mTxtDate.setText(String.format("%s\n%s", startStr, endStr));
                    mTxtDate.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                        CalendarUtils.FORMAT_SERVER_DATE));
                    mTxtDate.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                        CalendarUtils.FORMAT_SERVER_DATE));
                    mPresenter.queryQuotationList(true);
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mTxtDate.setSelected(false);
                mImgDate.setSelected(false);
                mImgDate.setRotation(0F);
            });
        }
        mDateRangeWindow.showAsDropDownFix(mLlFilter);
    }

    @Override
    public void showDateEffectWindow() {
        mTxtEffectDate.setSelected(true);
        mImgEffectDate.setSelected(true);
        mImgEffectDate.setRotation(-180F);
        if (mDateEffectWindow == null) {
            mDateEffectWindow = new DateRangeWindow(getActivity());
            mDateEffectWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    mTxtEffectDate.setText("生效期限");
                    mTxtEffectDate.setTag(R.id.date_start, "");
                    mTxtEffectDate.setTag(R.id.date_end, "");
                    mPresenter.queryQuotationList(true);
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    mTxtEffectDate.setText(String.format("%s\n%s", startStr, endStr));
                    mTxtEffectDate.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                        CalendarUtils.FORMAT_SERVER_DATE));
                    mTxtEffectDate.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                        CalendarUtils.FORMAT_SERVER_DATE));
                    mPresenter.queryQuotationList(true);
                }
            });
            mDateEffectWindow.setOnDismissListener(() -> {
                mTxtEffectDate.setSelected(false);
                mImgEffectDate.setSelected(false);
                mImgEffectDate.setRotation(0F);
            });
        }
        mDateEffectWindow.showAsDropDownFix(mLlFilter);
    }

    @Override
    public String getPurchaserId() {
        String purchaserId = null;
        if (mTxtPurchaser != null && mTxtPurchaser.getTag() != null) {
            purchaserId = (String) mTxtPurchaser.getTag();
        }
        return purchaserId;
    }

    @Override
    public String getStartDate() {
        String startDate = null;
        if (mTxtDate != null && mTxtDate.getTag(R.id.date_start) != null) {
            startDate = (String) mTxtDate.getTag(R.id.date_start);
        }
        return startDate;
    }

    @Override
    public String getEndDate() {
        String endDate = null;
        if (mTxtDate != null && mTxtDate.getTag(R.id.date_end) != null) {
            endDate = (String) mTxtDate.getTag(R.id.date_end);
        }
        return endDate;
    }

    @Override
    public String getPriceStartDate() {
        String priceStartDate = null;
        if (mTxtEffectDate != null && mTxtEffectDate.getTag(R.id.date_start) != null) {
            priceStartDate = (String) mTxtEffectDate.getTag(R.id.date_start);
        }
        return priceStartDate;
    }

    @Override
    public String getPriceEndDate() {
        String priceEndDate = null;
        if (mTxtEffectDate != null && mTxtEffectDate.getTag(R.id.date_end) != null) {
            priceEndDate = (String) mTxtEffectDate.getTag(R.id.date_end);
        }
        return priceEndDate;
    }

    @Override
    public boolean isSearchActivity() {
        return getActivity() instanceof AgreementPriceSearchActivity;
    }

    @Override
    public List<String> getBillNos() {
        List<String> listBillNos = new ArrayList<>();
        List<QuotationBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (QuotationBean bean : list) {
                if (bean.isSelect()) {
                    listBillNos.add(bean.getBillNo());
                }
            }
        }
        return listBillNos;
    }

    @OnClick({R.id.ll_purchaser, R.id.ll_date, R.id.ll_effectDate, R.id.img_quotation_add, R.id.img_select_all,
        R.id.txt_confirm_export, R.id.txt_select_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                showDateWindow();
                break;
            case R.id.ll_effectDate:
                showDateEffectWindow();
                break;
            case R.id.ll_purchaser:
                toPurchaser();
                break;
            case R.id.img_quotation_add:
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD);
                break;
            case R.id.img_select_all:
            case R.id.txt_select_all:
                selectAll();
                break;
            case R.id.txt_confirm_export:
                // 确定导出
                mPresenter.exportQuotation(null);
                break;
            default:
                break;
        }
    }

    private void toPurchaser() {
        mTxtPurchaser.setSelected(true);
        mImgSupplier.setSelected(true);
        mImgSupplier.setRotation(-180F);
        mPresenter.queryCooperationPurchaserList();
    }

    /**
     * 全选
     */
    private void selectAll() {
        boolean select = !mImgSelectAll.isSelected();
        List<QuotationBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (QuotationBean bean : list) {
                bean.setSelect(select);
            }
        }
        mAdapter.notifyDataSetChanged();
        showExportCount();
    }

    @Override
    public void toExport() {
        if (!isFragmentVisible()) {
            return;
        }
        mAdapter.setExport(true);
        mAdapter.notifyDataSetChanged();
        mRlBottom.setVisibility(View.VISIBLE);
        mImgQuotationAdd.setVisibility(View.GONE);
    }

    @Override
    public void toSearch() {
        if (!isFragmentVisible()) {
            return;
        }
        mPresenter.queryQuotationList(true);
    }

    @Override
    public void exportSuccess(String email) {
        super.exportSuccess(email);
        mAdapter.setExport(false);
        mAdapter.notifyDataSetChanged();
        mRlBottom.setVisibility(View.GONE);
        mImgQuotationAdd.setVisibility(View.VISIBLE);
    }
}