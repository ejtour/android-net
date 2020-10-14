package com.hll_sc_app.app.agreementprice.quotation.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.QuotationListAdapter;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.bean.agreementprice.quotation.CategoryRatioListBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SingleSelectionDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议价管理-添加报价单
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD, extras = Constant.LOGIN_EXTRA)
public class QuotationAddActivity extends BaseLoadActivity implements QuotationAddContract.IPurchaseView {
    public static final String STRING_WARE_HOUSE = "代仓客户";
    public static final String STRING_SELF_SUPPORT = "自营客户";
    @BindView(R.id.txt_isWarehouse)
    TextView mTxtIsWarehouse;
    @BindView(R.id.txt_select_purchaser)
    TextView mTxtSelectPurchaser;
    @BindView(R.id.txt_priceDate)
    TextView mTxtPriceDate;
    @BindView(R.id.txt_templateID)
    TextView mTxtTemplateName;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.include_title)
    ConstraintLayout mListTitle;
    @BindView(R.id.rl_isWarehouse)
    RelativeLayout mRlIsWarehouse;
    @BindView(R.id.rl_select_purchaser)
    RelativeLayout mRlSelectPurchaser;
    @Autowired(name = "parcelable")
    QuotationReq mCopyReq;
    private QuotationAddPresenter mPresenter;
    private GoodsListAdapter mAdapter;
    private SingleSelectionDialog mWarehouseDialog;
    private QuotationBean mQuotationBean;
    private DateSelectWindow mDateSelectWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_add);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = QuotationAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mQuotationBean = new QuotationBean();
        initView();
        showView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsListAdapter(true);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_quotation_add_empty, mRecyclerView, false);
        emptyView.findViewById(R.id.txt_product).setOnClickListener(v -> addProduct());
        mAdapter.setEmptyView(emptyView);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            QuotationDetailBean bean = (QuotationDetailBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.txt_price) {
                showInputDialog(bean, adapter, position);
            } else if (id == R.id.img_delete) {
                adapter.remove(position);
                adapter.notifyDataSetChanged();
                if (mAdapter.getData().size() > 0) {
                    mListTitle.setVisibility(View.VISIBLE);
                    if (CommonUtils.getInt(mQuotationBean.getSource()) == 0)
                        mLlBottom.setVisibility(View.VISIBLE);
                } else {
                    mListTitle.setVisibility(View.GONE);
                    mLlBottom.setVisibility(View.GONE);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 复制跳转过来
     */
    private void showView() {
        if (mCopyReq == null) {
            return;
        }
        QuotationBean bean = mCopyReq.getQuotation();
        // 报价类别
        mQuotationBean.setIsWarehouse(bean.getIsWarehouse());
        mTxtIsWarehouse.setText(TextUtils.equals("1", bean.getIsWarehouse()) ? STRING_WARE_HOUSE : STRING_SELF_SUPPORT);
        // 报价对象
        mQuotationBean.setExtGroupID(bean.getExtGroupID());
        mQuotationBean.setPurchaserID(bean.getPurchaserID());
        mQuotationBean.setPurchaserName(bean.getPurchaserName());
        mQuotationBean.setIsAllShop(bean.getIsAllShop());
        mQuotationBean.setShopIDs(bean.getShopIDs());
        mQuotationBean.setShopIDNum(bean.getShopIDNum());
        mQuotationBean.setSource(bean.getSource());
        // 报价商品
        refreshList(mCopyReq.getList());
        if (CommonUtils.getInt(mQuotationBean.getSource()) == 0) {
            mTxtSelectPurchaser.setText(String.format("%s %s 家门店", bean.getPurchaserName(), bean.getShopIDNum()));
            // 生效期限
            mQuotationBean.setPriceStartDate(bean.getPriceStartDate());
            mQuotationBean.setPriceEndDate(bean.getPriceEndDate());
            mTxtPriceDate.setText(QuotationListAdapter.getPriceDate(bean.getPriceStartDate(), bean.getPriceEndDate()));
            // 比例模板
            mQuotationBean.setTemplateID(bean.getTemplateID());
            mQuotationBean.setTemplateName(bean.getTemplateName());
            mTxtTemplateName.setText(bean.getTemplateName());
        } else {
            mTxtSelectPurchaser.setText(String.format("%s - %s", bean.getPurchaserName(), bean.getShopName()));
            mRlIsWarehouse.setClickable(false);
            mRlSelectPurchaser.setClickable(false);
            mTxtIsWarehouse.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mTxtSelectPurchaser.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * 添加商品
     */
    private void addProduct() {
        if (isSelectWarehouse()) {
            showToast("请先选择报价类别");
            return;
        }
        if (TextUtils.equals(STRING_WARE_HOUSE, mTxtIsWarehouse.getText().toString())) {
            if (TextUtils.isEmpty(mTxtSelectPurchaser.getText().toString())) {
                showToast("请选择报价对象");
                return;
            }
            toAddGoods(true);
        } else {
            if (GlobalPreference.getParam(Constants.ONLY_RECEIVE, false) && TextUtils.isEmpty(mTxtSelectPurchaser.getText().toString())) {
                showToast("请选择报价对象");
                return;
            }
            toAddGoods(false);
        }
    }

    private void showInputDialog(QuotationDetailBean bean, BaseQuickAdapter adapter, int position) {
        String price = CommonUtils.formatNumber(bean.getPrice());
        InputDialog.newBuilder(this)
                .setCancelable(false)
                .setTextTitle("输入" + bean.getProductName() + "协议价")
                .setHint("输入协议价")
                .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .setText(TextUtils.equals(price, "0") ? "" : price)
                .setTextWatcher((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
                    if (!GoodsSpecsAddActivity.PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                        s.delete(s.length() - 1, s.length());
                        showToast("协议价值支持7位整数或小数点后两位");
                    }
                })
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        if (TextUtils.isEmpty(dialog.getInputString())) {
                            showToast("输入协议价不能为空");
                            return;
                        }
                        bean.setPrice(CommonUtils.formatNumber(CommonUtils.getDouble(dialog.getInputString())));
                        adapter.notifyItemChanged(position);
                    }
                    dialog.dismiss();
                }, "取消", "确定")
                .create().show();
    }

    /**
     * 是否选择了报价类别
     *
     * @return true-没有选择报价类表
     */
    private boolean isSelectWarehouse() {
        return TextUtils.isEmpty(mTxtIsWarehouse.getText().toString());
    }

    private void toAddGoods(boolean isWarehouse) {
        // 用于数据反显
        ArrayList<SkuGoodsBean> goodsList = new ArrayList<>();
        List<QuotationDetailBean> beans = mAdapter.getData();
        if (!CommonUtils.isEmpty(beans)) {
            for (QuotationDetailBean bean : beans) {
                SkuGoodsBean skuGoodsBean = new SkuGoodsBean();
                skuGoodsBean.setSpecContent(bean.getProductDesc());
                skuGoodsBean.setShopProductCategoryThreeID(bean.getShopProductCategoryThreeID());
                skuGoodsBean.setSpecID(bean.getProductSpecID());
                skuGoodsBean.setCostPrice(bean.getCostPrice());
                skuGoodsBean.setImgUrl(bean.getImgUrl());
                skuGoodsBean.setProductCode(bean.getProductCode());
                skuGoodsBean.setProductID(bean.getProductID());
                skuGoodsBean.setProductName(bean.getProductName());
                skuGoodsBean.setProductPrice(bean.getProductPrice());
                skuGoodsBean.setSaleUnitName(bean.getSaleUnitName());
                skuGoodsBean.setCategoryThreeID(bean.getCategoryID());
                skuGoodsBean.setCategorySubID(bean.getCategorySubID());
                goodsList.add(skuGoodsBean);
            }
        }
        if (!isWarehouse) {
            ARouter.getInstance().build(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_GOODS)
                    .withString("object1", mQuotationBean.getExtGroupID())
                    .withParcelableArrayList("parcelable", goodsList)
                    .setProvider(new LoginInterceptor()).navigation();
        } else {
            ARouter.getInstance().build(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_GOODS)
                    .withString("object0", mQuotationBean.getPurchaserID())
                    .withParcelableArrayList("parcelable", goodsList)
                    .setProvider(new LoginInterceptor()).navigation();
        }
    }

    @Subscribe
    public void onEvent(QuotationBean event) {
        // 选择报价对象
        mQuotationBean.setExtGroupID(event.getExtGroupID());
        mQuotationBean.setPurchaserID(event.getPurchaserID());
        mQuotationBean.setPurchaserName(event.getPurchaserName());
        mQuotationBean.setIsAllShop(event.getIsAllShop());
        mQuotationBean.setShopIDs(event.getShopIDs());
        mQuotationBean.setShopIDNum(event.getShopIDNum());
        mTxtSelectPurchaser.setText(String.format("%s %s 家门店", event.getPurchaserName(), event.getShopIDNum()));
    }

    @Subscribe
    public void onEvent(RatioTemplateBean event) {
        // 协议价比例模板
        mQuotationBean.setTemplateID(event.getTemplateID());
        mQuotationBean.setTemplateName(event.getTemplateName());
        mTxtTemplateName.setTag(event);
        mTxtTemplateName.setText(event.getTemplateName());
        List<QuotationDetailBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (QuotationDetailBean bean : list) {
                bean.setPrice(getRecommendPrice(bean.getProductPrice(), bean.getShopProductCategoryThreeID()));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private String getRecommendPrice(String productPrice, String shopProductCategoryThreeId) {
        String recommendPrice = productPrice;
        // 有比例模板
        if (mTxtTemplateName.getTag() != null) {
            RatioTemplateBean templateBean = (RatioTemplateBean) mTxtTemplateName.getTag();
            List<CategoryRatioListBean> listBeans = templateBean.getCategoryRatioList();
            if (!CommonUtils.isEmpty(listBeans)) {
                for (CategoryRatioListBean categoryRatioListBean : listBeans) {
                    if (TextUtils.equals(categoryRatioListBean.getShopProductCategoryThreeID(),
                            shopProductCategoryThreeId)) {
                        double ratio = CommonUtils.addDouble(CommonUtils.getDouble(categoryRatioListBean.getRatio()),
                                100).doubleValue();
                        double result =
                                CommonUtils.mulDouble(ratio, CommonUtils.getDouble(productPrice)).doubleValue();
                        recommendPrice = CommonUtils.formatNumber(CommonUtils.divDouble(result, 100).doubleValue());
                        break;
                    }
                }
            }
        }
        return recommendPrice;
    }

    private void refreshList(List<QuotationDetailBean> list) {
        mAdapter.setNewData(list);
        if (mAdapter.getData().size() > 0) {
            mListTitle.setVisibility(View.VISIBLE);
            if (CommonUtils.getInt(mQuotationBean.getSource()) == 0)
                mLlBottom.setVisibility(View.VISIBLE);
        } else {
            mListTitle.setVisibility(View.GONE);
            mLlBottom.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onEvent(List<SkuGoodsBean> event) {
        // 选择的商品数据
        if (CommonUtils.isEmpty(event)) {
            return;
        }
        List<QuotationDetailBean> list = new ArrayList<>();
        for (SkuGoodsBean bean : event) {
            QuotationDetailBean quotationDetailBean = new QuotationDetailBean();
            quotationDetailBean.setProductDesc(bean.getSpecContent());
            quotationDetailBean.setShopProductCategoryThreeID(bean.getShopProductCategoryThreeID());
            quotationDetailBean.setProductSpecID(bean.getSpecID());
            quotationDetailBean.setCostPrice(bean.getCostPrice());
            quotationDetailBean.setImgUrl(bean.getImgUrl());
            quotationDetailBean.setProductCode(bean.getProductCode());
            quotationDetailBean.setProductID(bean.getProductID());
            quotationDetailBean.setProductName(bean.getProductName());
            quotationDetailBean.setProductPrice(bean.getProductPrice());
            quotationDetailBean.setSaleUnitName(bean.getSaleUnitName());
            quotationDetailBean.setCategoryID(bean.getCategoryThreeID());
            quotationDetailBean.setCategorySubID(bean.getCategorySubID());
            quotationDetailBean.setPrice(getRecommendPrice(bean.getProductPrice(),
                    bean.getShopProductCategoryThreeID()));
            list.add(quotationDetailBean);
        }
        refreshList(list);
    }

    @OnClick({R.id.img_close, R.id.txt_add_product, R.id.rl_isWarehouse, R.id.rl_select_purchaser, R.id.rl_priceDate,
            R.id.rl_templateID, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add_product:
                addProduct();
                break;
            case R.id.rl_isWarehouse:
                showWarehouseSelect();
                break;
            case R.id.rl_select_purchaser:
                toSelectPurchaser();
                break;
            case R.id.rl_priceDate:
                showPriceDateWindow();
                break;
            case R.id.rl_templateID:
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_RATIO);
                break;
            case R.id.txt_submit:
                toSubmit();
                break;
            default:
                break;
        }
    }

    private void showWarehouseSelect() {
        if (mWarehouseDialog == null) {
            List<NameValue> values = new ArrayList<>();
            values.add(new NameValue(STRING_SELF_SUPPORT, "0"));
            if (!BuildConfig.isOdm && !GlobalPreference.getParam(Constants.ONLY_RECEIVE, false)) {
                values.add(new NameValue(STRING_WARE_HOUSE, "1"));
            }
            mWarehouseDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("报价类别")
                    .refreshList(values)
                    .setOnSelectListener(nameValue -> {
                        mQuotationBean.setIsWarehouse(nameValue.getValue());
                        mTxtIsWarehouse.setText(nameValue.getName());
                        // 重置商品和报价对象
                        refreshList(null);
                        mQuotationBean.setExtGroupID(null);
                        mQuotationBean.setPurchaserID(null);
                        mQuotationBean.setPurchaserName(null);
                        mQuotationBean.setIsAllShop(null);
                        mQuotationBean.setShopIDs(null);
                        mQuotationBean.setShopIDNum(null);
                        mTxtSelectPurchaser.setText(null);
                    }).create();
        }
        mWarehouseDialog.show();
    }

    private void toSelectPurchaser() {
        if (isSelectWarehouse()) {
            showToast("请先选择报价类别");
            return;
        }
        if (TextUtils.isEmpty(mQuotationBean.getShopIDs())) {
            boolean warehouse = TextUtils.equals(STRING_WARE_HOUSE, mTxtIsWarehouse.getText().toString());
            RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER, warehouse);
        } else {
            RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER_SHOP, mQuotationBean);
        }
    }

    private void showPriceDateWindow() {
        if (mDateSelectWindow == null) {
            mDateSelectWindow = new DateSelectWindow(this);
            mDateSelectWindow.setSelectListener((startDate, endDate) -> {
                mQuotationBean.setPriceStartDate(startDate);
                mQuotationBean.setPriceEndDate(endDate);
                mTxtPriceDate.setText(QuotationListAdapter.getPriceDate(startDate, endDate));
            });
        }
        mDateSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void toSubmit() {
        if (TextUtils.isEmpty(mQuotationBean.getIsWarehouse())) {
            showToast("请选择报价类别");
            return;
        }
        if (TextUtils.isEmpty(mQuotationBean.getPurchaserID())) {
            showToast("请选择报价对象");
            return;
        }
        if (TextUtils.isEmpty(mQuotationBean.getPriceStartDate())) {
            showToast("请选择生效期限");
            return;
        }
        if (CommonUtils.isEmpty(mAdapter.getData())) {
            showToast("请选择报价商品");
            return;
        }
        QuotationReq req = new QuotationReq();
        mQuotationBean.setBillStatus(1);
        mQuotationBean.setBillType(GlobalPreference.getParam(Constants.ONLY_RECEIVE, false) ? "2" : "1");
        mQuotationBean.setBillDate(CalendarUtils.format(new Date(), CalendarUtils.FORMAT_SERVER_DATE));
        mQuotationBean.setGroupID(UserConfig.getGroupID());
        req.setQuotation(mQuotationBean);
        req.setList(mAdapter.getData());
        mPresenter.addQuotation(req);
    }

    @Override
    public void addSuccess() {
        showToast("添加报价单成功");
        finish();
        RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE);
    }

    public static class GoodsListAdapter extends BaseQuickAdapter<QuotationDetailBean, BaseViewHolder> {
        private boolean mAdd;

        public GoodsListAdapter(boolean add) {
            super(R.layout.item_agreement_price_quotation_detail);
            this.mAdd = add;
        }

        @Override
        protected void convert(BaseViewHolder helper, QuotationDetailBean item) {
            String ratioString = getRation(item);
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
            helper
                    .addOnClickListener(R.id.txt_price)
                    .addOnClickListener(R.id.txt_price_ratio)
                    .addOnClickListener(R.id.img_delete)
                    .setText(R.id.txt_productName, item.getProductName())
                    .setText(R.id.txt_productDesc, item.getProductDesc())
                    .setText(R.id.txt_unit_name, item.getSaleUnitName())
                    .setGone(R.id.txt_unit_name, !TextUtils.isEmpty(item.getSaleUnitName()))
                    .setGone(R.id.view_divider_unit, !TextUtils.isEmpty(item.getSaleUnitName()))
                    .setText(R.id.txt_price, CommonUtils.formatNumber(item.getPrice()))
                    .setText(R.id.txt_price_ratio, ratioString)
                    .setGone(R.id.txt_price_ratio, !TextUtils.isEmpty(ratioString))
                    .setText(R.id.txt_costPrice, "成本价：" + CommonUtils.formatNumber(item.getCostPrice()))
                    .setText(R.id.txt_productPrice, "平台价：" + CommonUtils.formatNumber(item.getProductPrice()))
                    .setGone(R.id.group_delete, mAdd);
        }

        private String getRation(QuotationDetailBean item) {
            String result = null;
            double price = CommonUtils.getDouble(item.getPrice());
            double productPrice = CommonUtils.getDouble(item.getProductPrice());
            if (productPrice != 0) {
                double rate = CommonUtils.mulDouble(100, CommonUtils.divDouble(CommonUtils.subDouble(price,
                        productPrice).doubleValue(), productPrice).doubleValue()).doubleValue();
                if (rate > 0) {
                    result = "高于平台价" + rate + "%";
                } else if (rate < 0) {
                    result = "低于平台价" + Math.abs(rate) + "%";
                }
            }
            return result;
        }
    }
}
