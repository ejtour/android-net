package com.hll_sc_app.app.goods.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.base.widget.StartTextView;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.zhihu.matisse.Matisse;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_ADD, extras = Constant.LOGIN_EXTRA)
public class GoodsAddActivity extends BaseLoadActivity implements GoodsAddContract.IGoodsAddView {
    @BindView(R.id.img_imgUrl)
    ImgUploadBlock mImgImgUrl;
    @BindView(R.id.ll_imgUrlSub)
    LinearLayout mLlImgUrlSub;
    @BindView(R.id.img_imgUrlSub)
    ImgUploadBlock mImgImgUrlSub;
    @BindView(R.id.et_productCode)
    EditText mEtProductCode;
    @BindView(R.id.et_productName)
    EditText mEtProductName;
    @BindView(R.id.txt_categoryName)
    TextView mTxtCategoryName;
    @BindView(R.id.txt_categoryName_copy)
    TextView mTxtCategoryNameCopy;
    @BindView(R.id.txt_shopProductCategorySubName)
    TextView mTxtShopProductCategorySubName;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.txt_productImg_title)
    StartTextView mTxtProductImgTitle;
    @BindView(R.id.txt_productCode_title)
    StartTextView mTxtProductCodeTitle;
    @BindView(R.id.txt_productName_title)
    StartTextView mTxtProductNameTitle;
    @BindView(R.id.txt_categoryName_title)
    StartTextView mTxtCategoryNameTitle;
    @BindView(R.id.rl_categoryName)
    RelativeLayout mRlCategoryName;
    @BindView(R.id.txt_shopProductCategorySubName_title)
    StartTextView mTxtShopProductCategorySubNameTitle;
    @BindView(R.id.rl_shopProductCategorySubName)
    RelativeLayout mRlShopProductCategorySubName;
    @BindView(R.id.txt_specs_add)
    TextView mTxtSpecsAdd;
    @BindView(R.id.recyclerView_specs)
    RecyclerView mRecyclerViewSpecs;
    @BindView(R.id.txt_specs_add_assistUnit)
    TextView mTxtSpecsAddAssistUnit;
    @BindView(R.id.txt_productBrief_title)
    TextView mTxtProductBriefTitle;
    @BindView(R.id.et_productBrief)
    EditText mEtProductBrief;
    @BindView(R.id.txt_nickNames1_title)
    TextView mTxtNickNames1Title;
    @BindView(R.id.et_nickNames1)
    EditText mEtNickNames1;
    @BindView(R.id.txt_nickNames2_title)
    TextView mTxtNickNames2Title;
    @BindView(R.id.et_nickNames2)
    EditText mEtNickNames2;
    @BindView(R.id.txt_nickNames3_title)
    TextView mTxtNickNames3Title;
    @BindView(R.id.et_nickNames3)
    EditText mEtNickNames3;
    @BindView(R.id.txt_label_add)
    TextView mTxtLabelAdd;
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.txt_depositProductType_title)
    TextView mTxtDepositProductTypeTitle;
    @BindView(R.id.switch_depositProductType)
    Switch mSwitchDepositProductType;
    @BindView(R.id.txt_stockCheckType_title)
    TextView mTxtStockCheckTypeTitle;
    @BindView(R.id.switch_stockCheckType)
    Switch mSwitchStockCheckType;
    private GoodsAddPresenter mPresenter;
    private CategorySelectWindow mCategorySelectWindow;
    private AssistUnitSelectWindow mAssistUnitSelectWindow;
    private SpecsAdapter mSpecsAdapter;
    private LabelSelectWindow mLabelSelectWindow;
    private FlowAdapter mFlowAdapter;
    /**
     * 选择的商品属性
     */
    private ArrayList<ProductAttrBean> mProductAttrs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
        mPresenter = GoodsAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        // 主图
        mImgImgUrl.setRequestCode(ImgUploadBlock.REQUEST_CODE_IMG_URL);
        // 辅图
        mImgImgUrlSub.setRequestCode(ImgUploadBlock.REQUEST_CODE_IMG_URL_SUB);

        // 商品规格
        mRecyclerViewSpecs.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSpecs.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mSpecsAdapter = new SpecsAdapter();
        mSpecsAdapter.setOnItemClickListener((adapter, view, position) -> {
            // 去规格详情中去修改
            SpecsBean specsBean = (SpecsBean) adapter.getItem(position);
            if (specsBean != null) {
                specsBean.setEdit(true);
                specsBean.setDepositProduct(mSwitchDepositProductType.isChecked());
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_SPECS, specsBean);
            }

        });
        mSpecsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.img_del) {
                SpecsBean specsBean = (SpecsBean) adapter.getItem(position);
                if (specsBean != null) {
                    if (TextUtils.equals(SpecsBean.STANDARD_UNIT, specsBean.getStandardUnitStatus())) {
                        showToast("标准规格不能删除");
                        return;
                    }
                    mSpecsAdapter.remove(position);
                }
                showSpecsAddAssistUnit();
            }
        });
        mRecyclerViewSpecs.setAdapter(mSpecsAdapter);

        mSwitchDepositProductType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            if (mSwitchStockCheckType.isChecked()) {
                mSwitchDepositProductType.setChecked(false);
                showToast("押金商品不需要库存校验");
                return;
            }
            if (isRelevanceDepositProduct()) {
                mSwitchDepositProductType.setChecked(false);
                showToast("关联押金商品后不能设置");
                return;
            }
            showDepositProductType();
        });

        mSwitchStockCheckType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            if (mSwitchDepositProductType.isChecked()) {
                mSwitchStockCheckType.setChecked(false);
                showToast("押金商品不需要库存校验");
                return;
            }
            showStockCheckType();
        });
    }

    private void showSpecsAddAssistUnit() {
        // 选择辅助规格
        mTxtSpecsAddAssistUnit.setVisibility((mSpecsAdapter != null && mSpecsAdapter.getItemCount() >= 2) ?
            View.VISIBLE : View.GONE);
    }

    /**
     * 规格里面是否关联了押金商品
     *
     * @return true-关联了押金商品
     */
    private boolean isRelevanceDepositProduct() {
        if (mSpecsAdapter != null && !CommonUtils.isEmpty(mSpecsAdapter.getData())) {
            for (SpecsBean bean : mSpecsAdapter.getData()) {
                if (!CommonUtils.isEmpty(bean.getDepositProducts())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showDepositProductType() {
        TipsDialog.newBuilder(this)
            .setTitle("设置为押金商品")
            .setMessage("启用后，该商品不能单独售卖，不能在商城端被搜索到，不能单独加入进货单，且不能下架。")
            .setButton((dialog, item) -> dialog.dismiss(), "我知道了")
            .create().show();
    }

    private void showStockCheckType() {
        TipsDialog.newBuilder(this)
            .setTitle("开启库存校验")
            .setMessage("开启库存校验后，当商品某规格库存不足时不允许采购商下单采购")
            .setButton((dialog, item) -> dialog.dismiss(), "我知道了")
            .create().show();
    }

    @Subscribe
    public void onEvent(CopyCategoryBean bean) {
        // 自定义分类新增
        showCustomCategory(bean);
    }

    @Subscribe
    public void onEvent(SpecsBean bean) {
        // 商品规格新增
        if (mSpecsAdapter == null) {
            return;
        }
        if (CommonUtils.isEmpty(mSpecsAdapter.getData())) {
            // 第一个默认为标注规格
            bean.setStandardUnitStatus("1");
        }
        if (bean.isEdit()) {
            int position = -1;
            if (!CommonUtils.isEmpty(mSpecsAdapter.getData())) {
                List<SpecsBean> specsBeanList = mSpecsAdapter.getData();
                int size = specsBeanList.size();
                for (int i = 0; i < size; i++) {
                    SpecsBean specsBean = specsBeanList.get(i);
                    if (specsBean.isEdit()) {
                        specsBean.setEdit(false);
                        specsBean.setSpecContent(bean.getSpecContent());
                        specsBean.setSaleUnitName(bean.getSaleUnitName());
                        specsBean.setSaleUnitID(bean.getSaleUnitID());
                        specsBean.setProductPrice(bean.getProductPrice());
                        specsBean.setDepositProducts(bean.getDepositProducts());
                        specsBean.setSkuCode(bean.getSkuCode());
                        specsBean.setRation(bean.getRation());
                        specsBean.setBuyMinNum(bean.getBuyMinNum());
                        specsBean.setMinOrder(bean.getMinOrder());
                        specsBean.setIsDecimalBuy(bean.getIsDecimalBuy());
                        position = i;
                        break;
                    }
                }
            }
            if (position != -1) {
                mSpecsAdapter.notifyItemChanged(position);
            }
        } else {
            mSpecsAdapter.addData(bean);
            mSpecsAdapter.notifyDataSetChanged();
        }
        showSpecsAddAssistUnit();
    }

    @Subscribe
    public void onEvent(ArrayList<ProductAttrBean> productAttrs) {
        // 商品规格新增
        this.mProductAttrs = productAttrs;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        List<String> list = Matisse.obtainPathResult(data);
        if (!CommonUtils.isEmpty(list)) {
            mPresenter.uploadImg(new File(list.get(0)), requestCode);
        }
    }

    @Override
    public void uploadSuccess(String url, int requestCode) {
        if (requestCode == ImgUploadBlock.REQUEST_CODE_IMG_URL) {
            mImgImgUrl.showImage(url, v -> mImgImgUrl.deleteImage());
        } else if (requestCode == ImgUploadBlock.REQUEST_CODE_IMG_URL_SUB) {
            mImgImgUrlSub.setVisibility(mLlImgUrlSub.getChildCount() == 4 ? View.GONE : View.VISIBLE);
            ImgShowDelBlock block = new ImgShowDelBlock(this);
            block.setImgUrl(url);
            block.setDeleteListener(v -> {
                mImgImgUrlSub.setVisibility(mLlImgUrlSub.getChildCount() == 4 ? View.GONE : View.VISIBLE);
                mLlImgUrlSub.removeView(block);
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(80), UIUtils.dip2px(80));
            params.rightMargin = UIUtils.dip2px(10);
            mLlImgUrlSub.addView(block, mLlImgUrlSub.getChildCount() - 1, params);
        }
    }

    @Override
    public void showCategorySelectWindow(CategoryResp resp) {
        if (mCategorySelectWindow == null) {
            mCategorySelectWindow = new CategorySelectWindow(this, resp);
            mCategorySelectWindow.setSelectListener((categoryItem1, categoryItem2, categoryItem3)
                -> {
                mTxtCategoryName.setTag(R.id.base_tag_1, categoryItem1);
                mTxtCategoryName.setTag(R.id.base_tag_2, categoryItem2);
                mTxtCategoryName.setTag(R.id.base_tag_3, categoryItem3);
                mTxtCategoryName.setText(String.format("%s - %s - %s", categoryItem1.getCategoryName(),
                    categoryItem2.getCategoryName(), categoryItem3.getCategoryName()));
            });
        }
        mCategorySelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showCustomCategory(CopyCategoryBean bean) {
        mTxtShopProductCategorySubName.setTag(bean.getShopProductCategorySubID());
        mTxtShopProductCategorySubName.setText(String.format("%s - %s", bean.getShopProductCategorySubName(),
            bean.getShopProductCategoryThreeName()));
    }

    @Override
    public void showLabelSelectWindow(List<LabelBean> list) {
        if (mLabelSelectWindow == null) {
            mLabelSelectWindow = new LabelSelectWindow(this);
            mLabelSelectWindow.setListener(() -> {
                mFlowAdapter = new FlowAdapter(GoodsAddActivity.this, mLabelSelectWindow.getSelectList());
                mFlowLayout.setAdapter(mFlowAdapter);
            });
        }
        mLabelSelectWindow.setList(list);
        mLabelSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @OnClick({R.id.img_close, R.id.rl_categoryName, R.id.rl_shopProductCategorySubName, R.id.txt_categoryName_copy,
        R.id.txt_specs_add, R.id.txt_specs_add_assistUnit, R.id.txt_label_add, R.id.txt_productAttrs_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.rl_categoryName:
                mPresenter.queryCategory();
                break;
            case R.id.rl_shopProductCategorySubName:
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_CUSTOM_CATEGORY,
                    mTxtShopProductCategorySubName.getTag() != null ?
                        (String) mTxtShopProductCategorySubName.getTag() : "");

                break;
            case R.id.txt_categoryName_copy:
                toCopy();
                break;
            case R.id.txt_specs_add:
                // 新增规格
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_SPECS, mSwitchDepositProductType.isChecked());
                break;
            case R.id.txt_specs_add_assistUnit:
                // 选择辅助规格
                showAssistUnitSelectWindow();
                break;
            case R.id.txt_label_add:
                // 选择商品标签
                mPresenter.queryLabelList();
                break;
            case R.id.txt_productAttrs_add:
                // 选择商品属性
                toProductAttrsActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void toProductAttrsActivity() {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR, mProductAttrs);
    }

    /**
     * 复制商城分类为自定义分类
     */
    private void toCopy() {
        CategoryItem categoryItem1 = null;
        if (mTxtCategoryName.getTag(R.id.base_tag_1) != null) {
            categoryItem1 = (CategoryItem) mTxtCategoryName.getTag(R.id.base_tag_1);
        }
        CategoryItem categoryItem2 = null;
        if (mTxtCategoryName.getTag(R.id.base_tag_2) != null) {
            categoryItem2 = (CategoryItem) mTxtCategoryName.getTag(R.id.base_tag_2);
        }
        CategoryItem categoryItem3 = null;
        if (mTxtCategoryName.getTag(R.id.base_tag_3) != null) {
            categoryItem3 = (CategoryItem) mTxtCategoryName.getTag(R.id.base_tag_3);
        }
        if (categoryItem1 == null || categoryItem2 == null || categoryItem3 == null) {
            return;
        }
        mPresenter.copyToCustomCategory(categoryItem1, categoryItem2, categoryItem3);
    }

    private void showAssistUnitSelectWindow() {
        if (mAssistUnitSelectWindow == null) {
            mAssistUnitSelectWindow = new AssistUnitSelectWindow(this);
            mAssistUnitSelectWindow.setListener(beans -> {
                if (CommonUtils.isEmpty(beans)) {
                    List<SpecsBean> specsBeanList = mSpecsAdapter.getData();
                    for (SpecsBean specsBean : specsBeanList) {
                        specsBean.setAssistUnitStatus("0");
                    }
                } else {
                    for (SpecsBean bean : beans) {
                        bean.setAssistUnitStatus("1");
                    }
                }
                mSpecsAdapter.notifyDataSetChanged();
            });
        }
        mAssistUnitSelectWindow.refreshList(mSpecsAdapter.getData());
        mAssistUnitSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private static class FlowAdapter extends TagAdapter<LabelBean> {
        private LayoutInflater mInflater;
        private List<LabelBean> mList;

        FlowAdapter(Context context, List<LabelBean> list) {
            super(list);
            mList = list;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, LabelBean s) {
            View view = mInflater.inflate(R.layout.view_item_goods_label, flowLayout, false);
            TextView textView = view.findViewById(R.id.txt_labelName);
            textView.setText(s.getLabelName());
            ImageView imageDel = view.findViewById(R.id.img_del);
            imageDel.setOnClickListener(v -> {
                mList.remove(position);
                notifyDataChanged();
            });
            return view;
        }
    }

    class SpecsAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {

        SpecsAdapter() {
            super(R.layout.item_goods_specs);
        }

        @Override
        protected void convert(BaseViewHolder helper, SpecsBean item) {
            helper.setText(R.id.txt_specContent, item.getSpecContent())
                .setVisible(R.id.txt_standardUnitStatus, TextUtils.equals(item.getStandardUnitStatus(),
                    SpecsBean.STANDARD_UNIT))
                .setVisible(R.id.txt_assistUnitStatus, TextUtils.equals(item.getAssistUnitStatus(), "1"))
                .addOnClickListener(R.id.img_del)
                .setImageResource(R.id.img_del, TextUtils.equals(item.getStandardUnitStatus(),
                    SpecsBean.STANDARD_UNIT) ? R.drawable.ic_spec_delete_disable : R.drawable.ic_spec_delete)
                .setText(R.id.txt_productPrice, "¥" + item.getProductPrice());
        }
    }
}
