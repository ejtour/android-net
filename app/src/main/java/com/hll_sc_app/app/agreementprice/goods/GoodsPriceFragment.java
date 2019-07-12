package com.hll_sc_app.app.agreementprice.goods;

import android.os.Bundle;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.BaseAgreementPriceFragment;
import com.hll_sc_app.app.agreementprice.search.AgreementPriceSearchActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 协议价管理-商品
 *
 * @author 朱英松
 * @date 2019/7/11
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_GOODS)
public class GoodsPriceFragment extends BaseAgreementPriceFragment implements GoodsPriceContract.IGoodsPriceView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_filter)
    LinearLayout mLlFilter;
    Unbinder unbinder;
    @BindView(R.id.txt_purchaser)
    TextView mTxtPurchaser;
    @BindView(R.id.img_purchaser)
    ImageView mImgSupplier;
    @BindView(R.id.txt_category)
    TextView mTxtCategory;
    @BindView(R.id.img_category)
    ImageView mImgCategory;
    @BindView(R.id.txt_effectDate)
    TextView mTxtEffectDate;
    @BindView(R.id.img_effectDate)
    ImageView mImgEffectDate;
    @BindView(R.id.ll_effectDate)
    RelativeLayout mLlEffectDate;
    private GoodsPriceContract.IGoodsPricePresenter mPresenter;
    private GoodsPriceShopSelectWindow mPurchaserWindow;
    private GoodsPriceCategoryWindow mCategoryWindow;
    private DateRangeWindow mDateEffectWindow;
    private GoodsPriceListAdapter mAdapter;
    private EmptyView mEmptyView;
    private EmptyView mNetEmptyView;

    public GoodsPriceContract.IGoodsPricePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = GoodsPricePresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_agreement_price_goods, container, false);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(),
            R.color.base_color_divider), UIUtils.dip2px(5)));
        mAdapter = new GoodsPriceListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            QuotationDetailBean bean = (QuotationDetailBean) adapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_GOODS_DETAIL, bean);
            }
        });
        mEmptyView = EmptyView.newBuilder(getActivity()).setTipsTitle("喔唷，居然是「 空 」的").create();
        mNetEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
            setForceLoad(true);
            lazyLoad();
        }).create();
        mRecyclerView.setAdapter(mAdapter);
        if (isSearchActivity()) {
            mEmptyView.setTips("输入商品名称、采购商名称进行搜索");
            mAdapter.setEmptyView(mEmptyView);
            mLlFilter.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(UseCaseException exception) {
        super.showError(exception);
        if (exception.getLevel() == UseCaseException.Level.NET) {
            mAdapter.setEmptyView(mNetEmptyView);
        }
    }

    @Override
    public void showGoodsPriceList(List<QuotationDetailBean> list) {
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setNewData(list);
    }

    @Override
    public void showPurchaserWindow(List<PurchaserBean> list) {
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new GoodsPriceShopSelectWindow(getActivity(), list);
            mPurchaserWindow.setListener(new GoodsPriceShopSelectWindow.ConfirmListener() {
                @Override
                public void confirm(String shopIds) {
                    mTxtPurchaser.setTag(shopIds);
                    mPresenter.queryGoodsPriceList();
                }

                @Override
                public void queryShopList(String purchaserId) {
                    mPresenter.queryPurchaserShopList(purchaserId);
                }
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
    public void showCategoryWindow(CategoryResp resp) {
        mTxtCategory.setSelected(true);
        mImgCategory.setSelected(true);
        mImgCategory.setRotation(-180F);
        if (mCategoryWindow == null) {
            mCategoryWindow = new GoodsPriceCategoryWindow(getActivity(), resp);
            mCategoryWindow.setListener(categoryIds -> {
                mTxtCategory.setTag(categoryIds);
                mPresenter.queryGoodsPriceList();
            });
            mCategoryWindow.setOnDismissListener(() -> {
                mTxtCategory.setSelected(false);
                mImgCategory.setSelected(false);
                mImgCategory.setRotation(0F);
            });
        }
        mCategoryWindow.showAsDropDownFix(mLlFilter);
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
                    mPresenter.queryGoodsPriceList();
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
                    mPresenter.queryGoodsPriceList();
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
    public String getCategoryId() {
        String categoryId = null;
        if (mTxtCategory.getTag() != null) {
            categoryId = (String) mTxtCategory.getTag();
        }
        return categoryId;
    }

    @Override
    public String getShopIds() {
        String shopIds = null;
        if (mTxtPurchaser.getTag() != null) {
            shopIds = (String) mTxtPurchaser.getTag();
        }
        return shopIds;
    }

    @Override
    public boolean isSearchActivity() {
        return getActivity() instanceof AgreementPriceSearchActivity;
    }

    @Override
    public void showPurchaserShopList(List<PurchaserShopBean> list) {
        if (mPurchaserWindow != null) {
            mPurchaserWindow.showShopList(list);
        }
    }

    @OnClick({R.id.ll_purchaser, R.id.ll_category, R.id.ll_effectDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_category:
                mPresenter.queryCategory();
                break;
            case R.id.ll_effectDate:
                showDateEffectWindow();
                break;
            case R.id.ll_purchaser:
                toPurchaser();
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

    @Override
    public void toExport() {
        if (!isFragmentVisible()) {
            return;
        }
        mPresenter.exportQuotation(null);
    }

    @Override
    public void toSearch() {
        if (!isFragmentVisible()) {
            return;
        }
        mPresenter.queryGoodsPriceList();
    }

    public class GoodsPriceListAdapter extends BaseQuickAdapter<QuotationDetailBean, BaseViewHolder> {

        GoodsPriceListAdapter() {
            super(R.layout.list_item_agreement_price_goods);
        }

        @Override
        protected void convert(BaseViewHolder helper, QuotationDetailBean item) {
            helper.setText(R.id.txt_productName, getString(item.getProductName()))
                .setText(R.id.txt_productDesc, "规格：" + getString(item.getProductDesc()));
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}