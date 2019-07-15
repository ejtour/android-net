package com.hll_sc_app.app.pricemanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.goods.invwarn.TopSingleSelectWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.RatioTemplateBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 售价设置
 *
 * @author zhuyingsong
 * @date 2019/7/12
 */
@Route(path = RouterConfig.PRICE_MANAGE, extras = Constant.LOGIN_EXTRA)
public class PriceManageActivity extends BaseLoadActivity implements PriceManageContract.IPriceManageView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_ration_name)
    TextView mTxtRationName;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.rl_select_ratio)
    RelativeLayout mRlSelectRatio;
    private EmptyView mEmptyView;
    private PriceManageListAdapter mAdapter;
    private PriceManagePresenter mPresenter;
    private TopSingleSelectWindow<RatioTemplateBean> mRatioTemplateWindow;
    private PriceManageFilterWindow mFilterWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = PriceManagePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mEdtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return true;
        });
        mEdtSearch.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s
            -> mImgClear.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePriceManageList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPriceManageList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new PriceManageListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SkuGoodsBean bean = (SkuGoodsBean) adapter.getItem(position);
            if (bean != null) {
                showInputDialog(bean, view.getId() == R.id.txt_edt_productPrice);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有售价设置数据").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void toSearch() {
        mEdtSearch.clearFocus();
        ViewUtils.clearEditFocus(mEdtSearch);
        mPresenter.queryPriceManageList(true);
    }

    private void showInputDialog(SkuGoodsBean bean, boolean isProductPrice) {
        PriceInputDialog.newBuilder(this)
            .setTextTitle(isProductPrice ? "修改售价" : "修改成本")
            .setProductName(bean.getProductName())
            .setSpecContent(bean.getSpecContent())
            .setPrice(CommonUtils.formatNumber(isProductPrice ? bean.getProductPrice() : bean.getCostPrice()))
            .setButton((dialog, item) -> {
                if (item == 1) {
                    String editPrice = dialog.getInputString();
                    if (TextUtils.isEmpty(editPrice)) {
                        showToast("请输入修改的价格");
                        return;
                    }
                    if (isProductPrice) {
                        mPresenter.updateProductPrice(bean, editPrice);
                    } else {
                        mPresenter.updateCostPrice(bean, editPrice);
                    }
                }
                dialog.dismiss();
            }, "容我想想", "确认修改")
            .create().show();
    }

    @OnClick({R.id.img_back, R.id.img_clear, R.id.txt_log, R.id.txt_filter, R.id.rl_select_ratio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_clear:
                mEdtSearch.setText(null);
                toSearch();
                break;
            case R.id.txt_log:
                break;
            case R.id.txt_filter:
                mPresenter.queryCustomCategory();
                break;
            case R.id.rl_select_ratio:
                mPresenter.queryRatioTemplateList();
                break;
            default:
                break;
        }
    }

    @Override
    public void showPriceManageList(List<SkuGoodsBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @Override
    public String getSearchParam() {
        return mEdtSearch.getText().toString().trim();
    }

    @Override
    public void showRatioTemplateWindow(List<RatioTemplateBean> values) {
        if (mRatioTemplateWindow == null) {
            mRatioTemplateWindow = new TopSingleSelectWindow<>(this, RatioTemplateBean::getTemplateName, true);
            mRatioTemplateWindow.setRecyclerViewHeight(UIUtils.dip2px(260));
            mRatioTemplateWindow.refreshList(values);
            mRatioTemplateWindow.setListener(ratioTemplateBean -> {
                if (ratioTemplateBean != null) {
                    mTxtRationName.setText(ratioTemplateBean.getTemplateName());
                    mTxtRationName.setTag(ratioTemplateBean.getTemplateID());
                } else {
                    mTxtRationName.setText(null);
                    mTxtRationName.setTag(null);
                }
            });
        }
        mRatioTemplateWindow.showAsDropDownFix(mRlSelectRatio, Gravity.NO_GRAVITY);
    }

    @Override
    public void showCustomCategoryWindow(CustomCategoryResp resp) {
        if (mFilterWindow == null) {
            mFilterWindow = new PriceManageFilterWindow(this, resp);
            mFilterWindow.setConfirmListener(() -> mPresenter.queryPriceManageList(true));
        }
        mFilterWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @Override
    public String getProductStatus() {
        String productStatus = null;
        if (mFilterWindow != null) {
            productStatus = mFilterWindow.getProductStatus();
        }
        return productStatus;
    }

    @Override
    public String getProductCategoryIds() {
        String categoryIds = null;
        if (mFilterWindow != null) {
            categoryIds = mFilterWindow.getCategoryIds();
        }
        return categoryIds;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class PriceManageListAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {

        PriceManageListAdapter() {
            super(R.layout.item_price_manage);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_edt_productPrice)
                .addOnClickListener(R.id.txt_edt_costPrice);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, SkuGoodsBean bean) {
            helper.setText(R.id.txt_productName, bean.getProductName())
                .setText(R.id.txt_specContent, bean.getSpecContent())
                .setText(R.id.txt_costPrice, getPrice("成本：", CommonUtils.formatNumber(bean.getCostPrice()),
                    bean.getSaleUnitName()))
                .setText(R.id.txt_productPrice, getPrice("售价：", CommonUtils.formatNumber(bean.getProductPrice()),
                    bean.getSaleUnitName()))
                .setGone(R.id.txt_edt_costPrice, TextUtils.equals(bean.getCostPriceModifyFlag(), "0"));
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(bean.getImgUrl());
        }

        private SpannableString getPrice(String title, String price, String unit) {
            SpannableString spannableString = new SpannableString(title + "¥" + price + "/" + unit);
            spannableString.setSpan(new ForegroundColorSpan(0xFFED5655), title.length(), spannableString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.2f), spannableString.toString().indexOf("¥") + 1,
                spannableString.toString().indexOf("/"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }
}
