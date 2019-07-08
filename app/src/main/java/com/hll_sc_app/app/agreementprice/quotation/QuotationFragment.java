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
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.BaseAgreementPriceFragment;
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
    private QuotationFragmentContract.IHomePresenter mPresenter;
    private PurchaserSelectWindow mPurchaserWindow;
    private DateRangeWindow mDateRangeWindow;
    private DateRangeWindow mDateEffectWindow;
    private QuotationListAdapter mAdapter;

    private EmptyView mEmptyView;
    private EmptyView mNetEmptyView;

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
        mPresenter.start();
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
        mAdapter = new QuotationListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            QuotationBean bean = (QuotationBean) adapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_DETAIL, bean);
            }
        });
        mEmptyView = EmptyView.newBuilder(getActivity()).setTipsTitle("喔唷，居然是「 空 」的").create();
        mNetEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
            setForceLoad(true);
            lazyLoad();
        }).create();
        mRecyclerView.setAdapter(mAdapter);
        if (isSearchActivity()) {
            mLlFilter.setVisibility(View.GONE);
        }
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
        if (list == null) {
            list = new ArrayList<>();
        }
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list.size() == QuotationFragmentPresenter.PAGE_SIZE);
    }

    @Override
    public void showPurchaserWindow(List<PurchaserBean> list) {
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new PurchaserSelectWindow(getActivity(), list);
            mPurchaserWindow.setListener(bean -> {
                if (TextUtils.equals(bean.getPurchaserName(), "全部")) {
                    mTxtPurchaser.setText("采购商");
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
    public void exportSuccess(String email) {

    }

    @Override
    public void exportFailure(String message) {

    }

    @Override
    public void unbindEmail() {

    }

    @Override
    public boolean isSearchActivity() {
        return false;
    }

    @Override
    public String getSearchParam() {
        return null;
    }


    public void toSearch() {
        mPresenter.queryQuotationList(true);
    }

    protected void toExport(String email) {
        mPresenter.exportQuotation(email);
    }

    @OnClick({R.id.ll_purchaser, R.id.ll_date, R.id.ll_effectDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                showDateWindow();
                break;
            case R.id.ll_effectDate:
                showDateEffectWindow();
                break;
            case R.id.ll_purchaser:
                topurchaser();
                break;
            default:
                break;
        }
    }

    private void topurchaser() {
        mTxtPurchaser.setSelected(true);
        mImgSupplier.setSelected(true);
        mImgSupplier.setRotation(-180F);
        mPresenter.queryCooperationPurchaserList();
    }

    @Override
    public void toExport() {

    }
}