package com.hll_sc_app.app.goods.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.GoodsAddActivity;
import com.hll_sc_app.app.goods.list.SpecStatusWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.BannerImageLoader;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.NicknamesBean;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 *
 * @author zhuyingsong
 * @date 2019/6/13
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_DETAIL, extras = Constant.LOGIN_EXTRA)
public class GoodsDetailActivity extends BaseLoadActivity implements GoodsDetailContract.IGoodsDetailView {
    private static final int REQ_CODE = 0x482;
    @BindView(R.id.banner)
    Banner mBanner;
    @Autowired(name = "object0")
    String mProductId;
    @Autowired(name = "object1")
    boolean mIsBundlingDetail;
    @Autowired(name = "object2")
    boolean mEditable;
    @BindView(R.id.rl_toolbar)
    TitleBar mTitleBar;
    @BindView(R.id.txt_productName)
    TextView mTxtProductName;
    @BindView(R.id.txt_productBrief)
    TextView mTxtProductBrief;
    @BindView(R.id.txt_nextDayDelivery)
    TextView mTxtNextDayDelivery;
    @BindView(R.id.txt_depositProduct)
    TextView mTxtDepositProduct;
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.recyclerView_spec)
    RecyclerView mRecyclerViewSpec;
    @BindView(R.id.txt_productCode)
    TextView mTxtProductCode;
    @BindView(R.id.txt_categoryName)
    TextView mTxtCategoryName;
    @BindView(R.id.txt_shopProductCategoryName)
    TextView mTxtShopProductCategoryName;
    @BindView(R.id.recyclerView_productAttr)
    RecyclerView mRecyclerViewProductAttr;
    @BindView(R.id.recyclerView_img)
    RecyclerView mRecyclerViewProductImg;
    @BindView(R.id.recyclerView_bundlingGoods)
    RecyclerView mRecyclerViewBundlingGoods;
    @BindView(R.id.txt_bundlingGoods)
    TextView mTxtBundlingGoods;
    @BindView(R.id.txt_productCode_title)
    TextView mTxtProductCodeTitle;
    @BindView(R.id.txt_categoryName_title)
    TextView mTxtCategoryNameTitle;
    @BindView(R.id.txt_shopProductCategoryName_title)
    TextView mTxtShopProductCategoryNameTitle;
    @BindView(R.id.txt_update_spec)
    TextView mTxtUpdateSpec;
    @BindView(R.id.txt_edit)
    TextView mTxtEdit;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    private ProductImgAdapter mAdapterImg;
    private BundlingGoodsAdapter mAdapterBundlingGoods;
    private ProductAttrAdapter mAdapterAttr;
    private GoodsDetailPresenter mPresenter;
    private SpecStatusWindow.SpecAdapter mAdapterSpec;
    private GoodsBean mGoodsBean;
    private boolean mHasChanged;

    /**
     * start activity
     *
     * @param productId        productId
     * @param isBundlingDetail 组合商品下的商品明细的商品详情
     */
    public static void start(String productId, boolean isBundlingDetail, boolean edit) {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_DETAIL, productId, isBundlingDetail, edit);
    }

    public static void start(Activity context, String productId, boolean isBundlingDetail, boolean edit) {
        Object[] args = {productId, isBundlingDetail, edit};
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_DETAIL, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsDetailPresenter.newInstance(mEditable);
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            statusChanged();
        }
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setBannerAnimation(Transformer.Default);
        mRecyclerViewSpec.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSpec.addItemDecoration(new SimpleDecoration(0xFFEEEEEE, UIUtils.dip2px(1)));
        mRecyclerViewSpec.setNestedScrollingEnabled(false);
        mAdapterSpec = new SpecStatusWindow.SpecAdapter(null);
        mRecyclerViewSpec.setAdapter(mAdapterSpec);
        mAdapterSpec.setOnItemChildClickListener((adapter, view, position) -> {
            SpecsBean bean = (SpecsBean) adapter.getItem(position);
            if (bean != null) {
                bean.setProductID(mProductId);
                mPresenter.updateSpecStatus(Collections.singletonList(bean));
            }
        });

        mRecyclerViewProductAttr.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProductAttr.addItemDecoration(new SimpleDecoration(0xFFEEEEEE, UIUtils.dip2px(1)));
        mRecyclerViewProductAttr.setNestedScrollingEnabled(false);
        mAdapterAttr = new ProductAttrAdapter();
        mRecyclerViewProductAttr.setAdapter(mAdapterAttr);

        mRecyclerViewProductImg.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProductImg.setNestedScrollingEnabled(false);
        mAdapterImg = new ProductImgAdapter();
        mRecyclerViewProductImg.setAdapter(mAdapterImg);

        mRecyclerViewBundlingGoods.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee),
                ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(70), 0, 0, 0);
        mRecyclerViewBundlingGoods.addItemDecoration(decor);
        mRecyclerViewBundlingGoods.setNestedScrollingEnabled(false);
        mAdapterBundlingGoods = new BundlingGoodsAdapter();
        mAdapterBundlingGoods.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean goodsBean = (GoodsBean) adapter.getItem(position);
            if (goodsBean != null) {
                GoodsDetailActivity.start(goodsBean.getProductID(), true, mEditable);
            }
        });
        mRecyclerViewBundlingGoods.setAdapter(mAdapterBundlingGoods);
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @OnClick({R.id.txt_update_spec, R.id.txt_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_update_spec:
                toUpdateSpec();
                break;
            case R.id.txt_edit:
                toEdit();
                break;
            default:
                break;
        }
    }

    /**
     * 上下架操作逻辑
     */
    private void toUpdateSpec() {
        if (CommonUtils.isEmpty(mAdapterSpec.getData())) {
            return;
        }
        // 找出未上架的规格
        List<SpecsBean> listDown = new ArrayList<>();
        for (SpecsBean bean : mAdapterSpec.getData()) {
            bean.setProductID(mProductId);
            if (TextUtils.equals(bean.getSpecStatus(), SpecsBean.SPEC_STATUS_DOWN)) {
                listDown.add(bean);
            }
        }
        if (CommonUtils.isEmpty(listDown)) {
            // 如果没有未上架的规格则为全部上架规格，此按钮的操作未全部下架
            mPresenter.updateSpecStatus(mAdapterSpec.getData());
        } else {
            // 如果有未上架的规格则此按钮的操作为全部上架
            mPresenter.updateSpecStatus(listDown);
        }
    }

    private void toEdit() {
        if (CommonUtils.isEmpty(mAdapterSpec.getData())) {
            return;
        }
        boolean isEdit = true;
        boolean isDeposit = mTxtDepositProduct.getVisibility() == View.VISIBLE;
        if (!isDeposit) {
            // 押金商品可以直接编辑
            for (SpecsBean bean : mAdapterSpec.getData()) {
                bean.setProductID(mProductId);
                if (TextUtils.equals(bean.getSpecStatus(), SpecsBean.SPEC_STATUS_UP)) {
                    isEdit = false;
                }
            }
        }
        if (!isEdit) {
            SuccessDialog.newBuilder(this)
                .setCancelable(false)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("当前不能编辑商品")
                .setMessage("当商品含有未下架的商品时\n不可以进行商品资料编辑噢")
                .setButton((dialog, item) -> dialog.dismiss(), "我知道了")
                .create().show();
            return;
        }
        if (mGoodsBean != null) {
            GoodsAddActivity.start(this, mGoodsBean);
        }
    }

    @Override
    public void showDetail(GoodsBean bean) {
        mGoodsBean = bean;
        showBottomButton(bean);
        showBanner(bean);
        showProductName(bean);
        showSpecList(bean);
        showBundlingGoodsList(bean);
        showProductCategory(bean);
        showProductAttr(bean);
        showProductBrief(bean);

    }

    private void showBottomButton(GoodsBean bean) {
        // 组合商品明细不显示底部按钮
        mRlBottom.setVisibility(!mIsBundlingDetail && mEditable ? View.VISIBLE : View.GONE);
        mTxtUpdateSpec.setVisibility(TextUtils.equals(bean.getDepositProductType(), GoodsBean.DEPOSIT_GOODS_TYPE) ?
            View.GONE : View.VISIBLE);
        boolean isDown = false;
        if (!CommonUtils.isEmpty(bean.getSpecs())) {
            for (SpecsBean specsBean : bean.getSpecs()) {
                if (TextUtils.equals(SpecsBean.SPEC_STATUS_DOWN, specsBean.getSpecStatus())) {
                    isDown = true;
                    break;
                }
            }
        }
        mTxtUpdateSpec.setText(isDown ? "全部上架" : "全部下架");
    }

    private void showBanner(GoodsBean bean) {
        List<String> list = new ArrayList<>();
        list.add(bean.getImgUrl());
        if (!TextUtils.isEmpty(bean.getImgUrlSub())) {
            list.addAll(Arrays.asList(bean.getImgUrlSub().split(",")));
        }
        mBanner.setImages(list).setImageLoader(new BannerImageLoader(list)).start();
    }

    private void showProductName(GoodsBean bean) {
        mTxtProductName.setText(bean.getProductName());
        mTxtNextDayDelivery.setVisibility(TextUtils.equals(bean.getNextDayDelivery(), "1") ? View.VISIBLE : View.GONE);
        mTxtDepositProduct.setVisibility(TextUtils.equals(bean.getDepositProductType(), GoodsBean.DEPOSIT_GOODS_TYPE) ? View.VISIBLE :
            View.GONE);
        List<NicknamesBean> nicknamesBeanList = bean.getNicknames();
        if (!CommonUtils.isEmpty(nicknamesBeanList)) {
            mFlowLayout.setVisibility(View.VISIBLE);
            List<NicknamesBean> list = new ArrayList<>();
            for (NicknamesBean nicknamesBean : nicknamesBeanList) {
                if (TextUtils.equals("2", nicknamesBean.getNicknameType())) {
                    list.add(nicknamesBean);
                }
            }
            mFlowLayout.setAdapter(new FlowAdapter(this, list));
        } else {
            mFlowLayout.setVisibility(View.GONE);
        }
    }

    private void showSpecList(GoodsBean bean) {
        mAdapterSpec.setNewData(bean, !mIsBundlingDetail && mEditable);
    }

    private void showBundlingGoodsList(GoodsBean bean) {
        if (!CommonUtils.isEmpty(bean.getBundlingGoodsDetails())) {
            mAdapterBundlingGoods.setNewData(bean.getBundlingGoodsDetails());
            mRecyclerViewBundlingGoods.setVisibility(View.VISIBLE);
            mTxtBundlingGoods.setVisibility(View.VISIBLE);
        } else {
            mRecyclerViewBundlingGoods.setVisibility(View.GONE);
            mTxtBundlingGoods.setVisibility(View.GONE);
        }
    }

    private void showProductCategory(GoodsBean bean) {
        mTxtProductCode.setText(bean.getProductCode());
        mTxtCategoryName.setText(String.format("%s-%s-%s", bean.getCategoryName(), bean.getCategorySubName(),
            bean.getCategoryThreeName()));
        mTxtShopProductCategoryName.setText(String.format("%s-%s", bean.getShopProductCategorySubName(),
            bean.getShopProductCategoryThreeName()));
    }

    private void showProductAttr(GoodsBean bean) {
        mAdapterAttr.setNewData(bean.getProductAttrs());
        mRecyclerViewProductAttr.setVisibility(CommonUtils.isEmpty(bean.getProductAttrs()) ? View.GONE : View.VISIBLE);
    }

    private void showProductBrief(GoodsBean bean) {
        // 商品详情图片
        List<String> listImg = new ArrayList<>();
        if (!TextUtils.isEmpty(bean.getImgUrlDetail())) {
            listImg.addAll(Arrays.asList(bean.getImgUrlDetail().split(",")));
        } else {
            if (!TextUtils.isEmpty(bean.getImgUrl())) {
                listImg.add(bean.getImgUrl());
            } else if (!TextUtils.isEmpty(bean.getImgUrlSub())) {
                listImg.addAll(Arrays.asList(bean.getImgUrlSub().split(",")));
            }
        }
        mAdapterImg.setNewData(listImg);
        // 商品简介
        if (TextUtils.isEmpty(bean.getProductBrief())) {
            mTxtProductBrief.setVisibility(View.GONE);
            return;
        }
        mTxtProductBrief.setVisibility(View.VISIBLE);
        String title = "商品简介：";
        SpannableString spannableString = new SpannableString(title + bean.getProductBrief());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_666666)), 0,
            title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtProductBrief.setText(spannableString);
    }

    @Override
    public String getProductId() {
        return mProductId;
    }

    @Override
    public void statusChanged() {
        mHasChanged = true;
        mPresenter.start();
    }

    private static class FlowAdapter extends TagAdapter<NicknamesBean> {
        private final LayoutInflater mInflater;

        FlowAdapter(Context context, List<NicknamesBean> list) {
            super(list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, NicknamesBean s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.view_item_goods_detail_name, flowLayout, false);
            tv.setText(s.getNickname());
            return tv;
        }
    }

    private static class ProductAttrAdapter extends BaseQuickAdapter<ProductAttrBean, BaseViewHolder> {

        ProductAttrAdapter() {
            super(R.layout.item_product_detail_attr);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductAttrBean item) {
            helper.setText(R.id.txt_keyNote, item.getKeyNote()).setText(R.id.txt_attrValue, item.getAttrValue());
            if (TextUtils.equals(item.getWidget(), ProductAttrBean.WIDGET_AREA) && !TextUtils.isEmpty(item.getAttrValue())) {
                String[] strings = item.getAttrValue().split(",");
                helper.setText(R.id.txt_attrValue, strings.length > 2 ? String.format("%s-%s", strings[0], strings[2]) : strings[0]);
            }
        }
    }

    private static class ProductImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ProductImgAdapter() {
            super(R.layout.item_product_detail_img);
        }


        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            ((GlideImageView) helper.getView(R.id.glideImageView)).setAdjustViewBounds(true);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ((GlideImageView) helper.getView(R.id.glideImageView)).setImageURL(item);
        }
    }

    private static class BundlingGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        BundlingGoodsAdapter() {
            super(R.layout.item_product_detail_bundling);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            String source = String.format("¥ %s / %s", CommonUtils.formatMoney(CommonUtils.getDouble(item.getSpecPrice())), item.getSaleUnitName());
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new RelativeSizeSpan(1.3f), 2, source.indexOf("/"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((GlideImageView) helper.setText(R.id.pdb_name, item.getProductName())
                    .setText(R.id.pdb_price, ss)
                    .setText(R.id.pdb_spec, String.format("规格：%s", item.getSpecContent()))
                    .setText(R.id.pdb_num, String.format("x %s", CommonUtils.formatNum(CommonUtils.getDouble(item.getSpecNum()))))
                    .getView(R.id.pdb_image)).setImageURL(item.getImgUrl());
        }
    }
}
