package com.hll_sc_app.app.goods.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.list.SpecStatusWindow;
import com.hll_sc_app.base.BaseLoadActivity;
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
import com.hll_sc_app.widget.SimpleDecoration;
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
@Route(path = RouterConfig.ROOT_HOME_GOODS_DETAIL)
public class GoodsDetailActivity extends BaseLoadActivity implements GoodsDetailContract.IGoodsDetailView {
    @BindView(R.id.banner)
    Banner mBanner;
    @Autowired(name = "object0")
    String mProductID;
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
    private ProductImgAdapter mAdapterImg;
    private BundlingGoodsAdapter mAdapterBundlingGoods;
    private ProductAttrAdapter mAdapterAttr;
    private GoodsDetailPresenter mPresenter;
    private SpecStatusWindow.SpecAdapter mAdapterSpec;

    public static void start(String productID) {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_DETAIL, productID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
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
                bean.setProductID(mProductID);
                mPresenter.updateSpecStatus(Collections.singletonList(bean));
            }
        });

        mRecyclerViewProductAttr.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProductAttr.addItemDecoration(new SimpleDecoration(0xFFEEEEEE, UIUtils.dip2px(1)));
        mRecyclerViewProductAttr.setNestedScrollingEnabled(false);
        mAdapterAttr = new ProductAttrAdapter(null);
        mRecyclerViewProductAttr.setAdapter(mAdapterAttr);

        mRecyclerViewProductImg.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProductImg.setNestedScrollingEnabled(false);
        mAdapterImg = new ProductImgAdapter(null);
        mRecyclerViewProductImg.setAdapter(mAdapterImg);

        mRecyclerViewBundlingGoods.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mRecyclerViewBundlingGoods.addItemDecoration(new BundlingGoodsDecoration(this));
        mRecyclerViewBundlingGoods.setNestedScrollingEnabled(false);
        mAdapterBundlingGoods = new BundlingGoodsAdapter(null);
        mAdapterBundlingGoods.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean goodsBean = (GoodsBean) adapter.getItem(position);
            if (goodsBean != null) {
                GoodsDetailActivity.start(goodsBean.getProductID());
            }
        });
        mRecyclerViewBundlingGoods.setAdapter(mAdapterBundlingGoods);
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

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showDetail(GoodsBean bean) {
        showBanner(bean);
        showProductName(bean);
        showSpecList(bean);
        showBundlingGoodsList(bean);
        showProductCategory(bean);
        showProductAttr(bean);
        showProductBrief(bean);

    }

    @Override
    public String getProductID() {
        return mProductID;
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
        mAdapterSpec.setNewData(bean);
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

    private static class FlowAdapter extends TagAdapter<NicknamesBean> {
        private LayoutInflater mInflater;

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

        ProductAttrAdapter(@Nullable List<ProductAttrBean> data) {
            super(R.layout.item_product_detail_attr, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductAttrBean item) {
            helper.setText(R.id.txt_keyNote, item.getKeyNote()).setText(R.id.txt_attrValue, item.getAttrValue());
        }
    }

    private static class ProductImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ProductImgAdapter(@Nullable List<String> data) {
            super(R.layout.item_product_detail_img, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ((GlideImageView) helper.getView(R.id.glideImageView)).setScaleByWidth(true).setImageURL(item);
        }
    }

    private static class BundlingGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        BundlingGoodsAdapter(@Nullable List<GoodsBean> data) {
            super(R.layout.item_product_detail_bundling, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            ((GlideImageView) helper.setText(R.id.txt_productName, item.getProductName())
                .getView(R.id.glideImageView)).setImageURL(item.getImgUrl());
        }
    }
}
