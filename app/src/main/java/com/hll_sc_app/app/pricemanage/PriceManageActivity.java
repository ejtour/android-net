package com.hll_sc_app.app.pricemanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
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
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.goods.invwarn.TopSingleSelectWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.CategoryRatioListBean;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
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
    @BindView(R.id.txt_select_good_origin)
    TextView mTxtSelectOwner;
    private EmptyView mEmptyView;
    private PriceManagePresenter mPresenter;
    private PriceManageListAdapter mAdapter;
    private PriceManageFilterWindow mFilterWindow;
    private TopSingleSelectWindow<RatioTemplateBean> mRatioTemplateWindow;
    private TopSingleSelectWindow<WareHouseShipperBean> mOwnerWindow;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage);
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
                showInputDialog(bean, view.getId() == R.id.ll_product_price);
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
        if (!RightConfig.checkRight(getString(R.string.right_priceManagement_update))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        // 是否显示推荐价格 和 成本价格
        boolean isShow = false;
        // 推荐价格
        String recommendPrice = null;
        // 修改售价时 有比例模板
        if (isProductPrice && mTxtRationName.getTag() != null) {
            RatioTemplateBean templateBean = (RatioTemplateBean) mTxtRationName.getTag();
            List<CategoryRatioListBean> listBeans = templateBean.getCategoryRatioList();
            if (!CommonUtils.isEmpty(listBeans)) {
                for (CategoryRatioListBean categoryRatioListBean : listBeans) {
                    if (TextUtils.equals(categoryRatioListBean.getShopProductCategoryThreeID(),
                            bean.getShopProductCategoryThreeID())) {
                        isShow = true;
                        double ratio = CommonUtils.addDouble(CommonUtils.getDouble(categoryRatioListBean.getRatio()),
                                100).doubleValue();
                        double result =
                                CommonUtils.mulDouble(ratio, CommonUtils.getDouble(bean.getCostPrice())).doubleValue();
                        recommendPrice = CommonUtils.formatNumber(CommonUtils.divDouble(result, 100).doubleValue());
                        break;
                    }
                }
            }
        }

        PriceInputDialog.newBuilder(this)
                .showRecommend(isShow)
                .setCostPrice(CommonUtils.formatNumber(bean.getCostPrice()))
                .setRecommendPrice(recommendPrice)
                .setTitle(isProductPrice ? "修改售价" : "修改成本")
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

    @OnClick({R.id.img_menu, R.id.img_back, R.id.img_clear, R.id.txt_filter, R.id.rl_select_ratio, R.id.rl_select_good_origin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_clear:
                mEdtSearch.setText(null);
                toSearch();
                break;
            case R.id.txt_filter:
                mPresenter.queryCustomCategory();
                break;
            case R.id.rl_select_ratio:
                mPresenter.queryRatioTemplateList();
                break;
            case R.id.img_menu:
                showMenu(view);
                break;
            case R.id.rl_select_good_origin:
                mPresenter.queryOwners();
                break;
            default:
                break;
        }
    }

    private void showMenu(View target) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_PRICE_MANAGE_LOG));
            list.add(new OptionsBean(R.drawable.ic_goods_option_import, OptionType.OPTION_CHECK_PRICE_MANAGE_CHANGE_LOG));
            mOptionsWindow = new ContextOptionsWindow(this).setListener((adapter, view, position) -> {
                switch (position) {
                    case 0:
                        mPresenter.export(null);
                        break;
                    case 1:
                        RouterUtil.goToActivity(RouterConfig.PRICE_MANAGE_LOG);
                        break;
                    default:
                        break;
                }
                mOptionsWindow.dismiss();
            }).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(target, Gravity.END);
    }

    @Override
    public void showPriceManageList(List<SkuGoodsBean> list, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
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
        if (CommonUtils.isEmpty(values)) {
            showToast("您还没有可供选择的比例模板");
            return;
        }
        if (mRatioTemplateWindow == null) {
            mRatioTemplateWindow = new TopSingleSelectWindow<>(this, RatioTemplateBean::getTemplateName, true);
            mRatioTemplateWindow.setRecyclerViewHeight(UIUtils.dip2px(260));
            mRatioTemplateWindow.refreshList(values);
            mRatioTemplateWindow.setListener(ratioTemplateBean -> {
                if (ratioTemplateBean != null) {
                    mTxtRationName.setText(ratioTemplateBean.getTemplateName());
                    mTxtRationName.setTag(ratioTemplateBean);
                } else {
                    mTxtRationName.setText("比例模版");
                    mTxtRationName.setTag(null);
                }
            });
        }
        mRatioTemplateWindow.showAsDropDownFix(mRlSelectRatio);
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
    public int getIsWareHourse() {
        int isWareHourse = -1;
        if (mFilterWindow != null) {
            isWareHourse = mFilterWindow.getIsWareHourse();
        }
        return isWareHourse;
    }

    @Override
    public String getOwnerID() {
        if (mTxtSelectOwner.getTag() != null) {
            return ((WareHouseShipperBean) mTxtSelectOwner.getTag()).getPurchaserID();
        }
        return "";
    }

    @Override
    public void queryOwnersSuccess(List<WareHouseShipperBean> wareHouseShipperBeans) {
        if (CommonUtils.isEmpty(wareHouseShipperBeans)) {
            showToast("您还没有可供选择的货主");
            return;
        }
        if (mOwnerWindow == null) {
            mOwnerWindow = new TopSingleSelectWindow<>(this, WareHouseShipperBean::getPurchaserName, true);
            mOwnerWindow.removeAdapterDecoration();
            mOwnerWindow.setAdapterGravity(Gravity.LEFT);
            mOwnerWindow.setRecyclerViewHeight(UIUtils.dip2px(260));
            mOwnerWindow.refreshList(wareHouseShipperBeans);
            mOwnerWindow.setListener(wareHouseShipperBean -> {
                if (wareHouseShipperBean != null) {
                    mTxtSelectOwner.setText(wareHouseShipperBean.getPurchaserName());
                    mTxtSelectOwner.setTag(wareHouseShipperBean);
                } else {
                    mTxtSelectOwner.setText("选择货主");
                    mTxtSelectOwner.setTag(null);
                }
                mPresenter.queryPriceManageList(true);
            });
        }
        mOwnerWindow.showAsDropDownFix(mRlSelectRatio);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    class PriceManageListAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {

        PriceManageListAdapter() {
            super(R.layout.item_price_manage);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.ll_product_price)
                    .addOnClickListener(R.id.ll_cost_price);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, SkuGoodsBean bean) {
            boolean updatable = TextUtils.equals(bean.getCostPriceModifyFlag(), "0");
            helper.setText(R.id.txt_productName, bean.getProductName())
                    .setText(R.id.txt_specContent, bean.getSpecContent())
                    .setText(R.id.txt_costPrice, getPrice(getMoney(bean.getCostPrice()),
                            bean.getSaleUnitName()))
                    .setText(R.id.txt_productPrice, getPrice(getMoney(bean.getProductPrice()),
                            bean.getSaleUnitName()))
                    .setText(R.id.txt_group_info, bean.getCargoOwnerName())
                    .setText(R.id.txt_edt_costPrice, updatable ? "点击修改成本价" : "成本价")
                    .setGone(R.id.txt_divider, !TextUtils.isEmpty(bean.getCargoOwnerName()) && !TextUtils.isEmpty(bean.getSpecContent()));
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(bean.getImgUrl());
            helper.getView(R.id.ll_cost_price).setClickable(updatable);
        }

        private SpannableString getPrice(String price, String unit) {
            SpannableString spannableString = new SpannableString("¥ " + price + "/" + unit);
            spannableString.setSpan(new RelativeSizeSpan(1.2f), 1,
                    spannableString.toString().indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        private String getMoney(String price) {
            if (price == null) {
                price = "0";
            }
            return CommonUtils.formatMoney(Double.parseDouble(price));
        }
    }
}
