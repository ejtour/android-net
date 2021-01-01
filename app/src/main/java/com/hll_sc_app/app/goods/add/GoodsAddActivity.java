package com.hll_sc_app.app.goods.add;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.selectproductowner.SelectProductOwnerActivity;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.goods.add.specs.depositproducts.DepositProductsActivity;
import com.hll_sc_app.app.marketingsetting.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.AreaProductSelectWindow;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.base.widget.StartTextView;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.event.BrandBackEvent;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.goods.NicknamesBean;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.zhihu.matisse.Matisse;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_ADD, extras = Constant.LOGIN_EXTRA)
public class GoodsAddActivity extends BaseLoadActivity implements GoodsAddContract.IGoodsAddView {
    public final static int REQUEST_SELECT_PRODUCT_OWNER_CODE = 100;
    public static final int REQ_CODE = 0x566;
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
    @BindView(R.id.et_productBrief)
    EditText mEtProductBrief;
    @BindView(R.id.et_nickNames1)
    EditText mEtNickNames1;
    @BindView(R.id.et_nickNames2)
    EditText mEtNickNames2;
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
    @BindView(R.id.txt_productAttrs_add)
    TextView mTxtProductAttrsAdd;
    @BindView(R.id.recyclerView_productAttrs)
    RecyclerView mRecyclerViewProductAttrs;
    @BindView(R.id.img_imgUrlDetail)
    ImgUploadBlock mImgImgUrlDetail;
    @BindView(R.id.ll_imgUrlDetail)
    LinearLayout mLlImgUrlDetail;
    @Autowired(name = "parcelable")
    GoodsBean mGoodsBean;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_save)
    TextView mTxtSave;
    @BindView(R.id.txt_saveAndUp)
    TextView mTxtSaveAndUp;
    @BindView(R.id.fl_bottom)
    RelativeLayout mFlBottom;
    @BindView(R.id.txt_title_save)
    TextView mTxtTitleSave;
    @BindView(R.id.txt_product_type)
    TextView mTxtProductType;
    @BindView(R.id.txt_product_owner)
    TextView mTxtProductOwner;
    @BindView(R.id.rl_product_owner)
    RelativeLayout mRlProdutOwner;
    @BindView(R.id.rl_productDetails)
    RelativeLayout mRlProductDetails;
    @BindView(R.id.cl_productDetails)
    ConstraintLayout mClProductDetails;
    @BindView(R.id.recyclerView_productDetails)
    RecyclerView mRecyclerViewProductDetails;
    @BindView(R.id.rl_depositProductType)
    RelativeLayout mRlDepositProductType;
    private ImgUploadBlock mCurUpload;

    private GoodsAddPresenter mPresenter;
    private CategorySelectWindow mCategorySelectWindow;
    private AssistUnitSelectWindow mAssistUnitSelectWindow;
    private SpecsAdapter mSpecsAdapter;
    private LabelSelectWindow mLabelSelectWindow;
    private FlowAdapter mFlowAdapter;
    private ProductAttrAdapter mProductAttrAdapter;
    private AreaProductSelectWindow mAreaProductSelectWindow;

    private SingleSelectionDialog mSingleSelectionDialog;
    private boolean edit = false;//新增编辑模式

    private int modifySpecIndex = -1;
    private ProductDetailsAdapter mBundleAdapter;

    /**
     * @param bean 商品
     */
    public static void start(Activity context, GoodsBean bean) {
        if (!RightConfig.checkRight(context.getString(bean == null ? R.string.right_productManagement_create : R.string.right_productManagement_update))) {
            ToastUtils.showShort(context.getString(R.string.right_tips));
            return;
        }
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_ADD, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        showView();
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
        // 商品规格
        mRecyclerViewSpecs.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSpecs.setNestedScrollingEnabled(false);
        mRecyclerViewSpecs.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
                R.color.base_color_divider), UIUtils.dip2px(1)));
        mSpecsAdapter = new SpecsAdapter();
        mSpecsAdapter.setOnItemClickListener((adapter, view, position) -> {
            // 去规格详情中去修改
            SpecsBean specsBean = (SpecsBean) adapter.getItem(position);
            if (specsBean != null) {
                specsBean.setDepositProduct(mSwitchDepositProductType.isChecked());
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_SPECS, specsBean);
                modifySpecIndex = position;
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
        // 商品属性
        mRecyclerViewProductAttrs.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProductAttrs.setNestedScrollingEnabled(false);
        mRecyclerViewProductAttrs.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
                R.color.base_color_divider), UIUtils.dip2px(1)));
        mProductAttrAdapter = new ProductAttrAdapter();
        mProductAttrAdapter.setOnItemClickListener((adapter, view, position) -> {
            ViewUtils.clearEditFocus(view);
            ProductAttrBean attrBean = (ProductAttrBean) adapter.getItem(position);
            if (attrBean != null) {
                switch (attrBean.getWidget()) {
                    case ProductAttrBean.WIDGET_BRAND:
                        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR_BRAND, attrBean.getId());
                        break;
                    case ProductAttrBean.WIDGET_INPUT:
                        showInputDialog(attrBean, adapter, position);
                        break;
                    case ProductAttrBean.WIDGET_DATE:
                        showDateDialog(attrBean, adapter, position);
                        break;
                    case ProductAttrBean.WIDGET_COMBOBOX:
                        showComboBoxWindow(attrBean, adapter, position);
                        break;
                    case ProductAttrBean.WIDGET_AREA:
                        showAreaWindow(attrBean, adapter, position);
                        break;
                    default:
                        break;
                }
            }

        });
        mRecyclerViewProductAttrs.setAdapter(mProductAttrAdapter);
        // 设置为押金商品
        mSwitchDepositProductType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked || !buttonView.isPressed()) {
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
        // 开启库存校验
        mSwitchStockCheckType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked || !buttonView.isPressed()) {
                return;
            }
            if (mSwitchDepositProductType.isChecked()) {
                mSwitchStockCheckType.setChecked(false);
                showToast("押金商品不需要库存校验");
                return;
            }
            showStockCheckType();
        });
        mEtNickNames1.addTextChangedListener(new NameCheckTextWatcher());
        mEtNickNames2.addTextChangedListener(new NameCheckTextWatcher());
        mEtNickNames3.addTextChangedListener(new NameCheckTextWatcher());

        //商品类型-默认自营
        selectProductTypeLogic(new NameValue(mGoodsBean == null ? "自营" : mGoodsBean.getTransWareHourse(), mGoodsBean == null ? "0" : mGoodsBean.getIsWareHourse()));

        if (mGoodsBean != null && TextUtils.equals(mGoodsBean.getBundlingGoodsType(), GoodsBean.BUNDLING_GOODS_TYPE)) {
            mTxtTitle.setText("新增组合商品");
            mRlDepositProductType.setVisibility(View.GONE);
            mRlProductDetails.setVisibility(View.VISIBLE);
            mRecyclerViewProductDetails.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mRecyclerViewProductDetails.setNestedScrollingEnabled(false);
            mBundleAdapter = new ProductDetailsAdapter();
            mRecyclerViewProductDetails.setAdapter(mBundleAdapter);
            mBundleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.gab_delete) {
                        adapter.remove(position);
                        if (adapter.getItemCount() == 0) {
                            mClProductDetails.setVisibility(View.GONE);
                            mRecyclerViewProductDetails.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    private void showView() {
        if (mGoodsBean == null || TextUtils.isEmpty(mGoodsBean.getProductID())) {
            return;
        }
        edit = true;
        if (TextUtils.equals(mGoodsBean.getEditFrom(), GoodsBean.EDIT_FROM_TEMPLATE)) {
            mFlBottom.setVisibility(View.GONE);
            mTxtTitleSave.setVisibility(View.VISIBLE);
        } else {
            mFlBottom.setVisibility(View.VISIBLE);
            mTxtTitleSave.setVisibility(View.GONE);
        }
        boolean isBundleType = TextUtils.equals(mGoodsBean.getBundlingGoodsType(), GoodsBean.BUNDLING_GOODS_TYPE);
        mTxtTitle.setText(isBundleType ? "编辑组合商品" : "编辑商品");
        mTxtSpecsAdd.setVisibility(isBundleType ? View.GONE : View.VISIBLE);
        // 主图
        mImgImgUrl.showImage(mGoodsBean.getImgUrl());
        // 辅图
        if (!TextUtils.isEmpty(mGoodsBean.getImgUrlSub())) {
            String[] strings = mGoodsBean.getImgUrlSub().split(",");
            for (String s : strings) {
                addImgUrlSub(s);
            }
        }
        // 商品详情图
        if (!TextUtils.isEmpty(mGoodsBean.getImgUrlDetail())) {
            String[] strings = mGoodsBean.getImgUrlDetail().split(",");
            for (String s : strings) {
                addImgUrlDetail(s);
            }
        }
        // 商品编码
        mEtProductCode.setText(mGoodsBean.getProductCode());
        // 商品名称
        mEtProductName.setText(mGoodsBean.getProductName());
        // 商城分类
        CategoryItem categoryItem1 = new CategoryItem();
        categoryItem1.setCategoryName(mGoodsBean.getCategoryName());
        categoryItem1.setCategoryID(mGoodsBean.getCategoryID());
        CategoryItem categoryItem2 = new CategoryItem();
        categoryItem2.setCategoryName(mGoodsBean.getCategorySubName());
        categoryItem2.setCategoryID(mGoodsBean.getCategorySubID());
        CategoryItem categoryItem3 = new CategoryItem();
        categoryItem3.setCategoryName(mGoodsBean.getCategoryThreeName());
        categoryItem3.setCategoryID(mGoodsBean.getCategoryThreeID());
        mTxtCategoryName.setTag(R.id.base_tag_1, categoryItem1);
        mTxtCategoryName.setTag(R.id.base_tag_2, categoryItem2);
        mTxtCategoryName.setTag(R.id.base_tag_3, categoryItem3);
        mTxtCategoryName.setText(String.format("%s - %s - %s", categoryItem1.getCategoryName(),
                categoryItem2.getCategoryName(), categoryItem3.getCategoryName()));
        // 自定义分类
        if (!TextUtils.isEmpty(mGoodsBean.getShopProductCategorySubID()) && !TextUtils.isEmpty(mGoodsBean.getShopProductCategoryThreeID())) {
            CopyCategoryBean copyCategoryBean = new CopyCategoryBean();
            copyCategoryBean.setShopProductCategorySubID(mGoodsBean.getShopProductCategorySubID());
            copyCategoryBean.setShopProductCategorySubName(mGoodsBean.getShopProductCategorySubName());
            copyCategoryBean.setShopProductCategoryThreeID(mGoodsBean.getShopProductCategoryThreeID());
            copyCategoryBean.setShopProductCategoryThreeName(mGoodsBean.getShopProductCategoryThreeName());
            showCustomCategory(copyCategoryBean);
        }
        // 商品规格
        mSpecsAdapter.setNewData(mGoodsBean.getSpecs());
        showSpecsAddAssistUnit();
        // 商品简介
        mEtProductBrief.setText(mGoodsBean.getProductBrief());
        // 别称
        List<NicknamesBean> nicknamesBeanList = mGoodsBean.getNicknames();
        if (!CommonUtils.isEmpty(nicknamesBeanList)) {
            for (NicknamesBean bean : nicknamesBeanList) {
                if (TextUtils.equals(bean.getNicknameType(), "2")) {
                    if (TextUtils.isEmpty(mEtNickNames1.getText().toString().trim())) {
                        mEtNickNames1.setText(bean.getNickname());
                    } else if (TextUtils.isEmpty(mEtNickNames2.getText().toString().trim())) {
                        mEtNickNames2.setText(bean.getNickname());
                    } else if (TextUtils.isEmpty(mEtNickNames3.getText().toString().trim())) {
                        mEtNickNames3.setText(bean.getNickname());
                    }
                }
            }
        }
        // 商品属性
        List<ProductAttrBean> productAttrBeanList = mGoodsBean.getProductAttrs();
        if (!CommonUtils.isEmpty(productAttrBeanList)) {
            for (ProductAttrBean bean : productAttrBeanList) {
                bean.setCurrAttrValue(bean.getAttrValue());
            }
        }
        mProductAttrAdapter.setNewData(productAttrBeanList);

        List<GoodsBean> goodsDetails = mGoodsBean.getBundlingGoodsDetails();
        if (!CommonUtils.isEmpty(goodsDetails)) {
            mClProductDetails.setVisibility(View.VISIBLE);
            mRecyclerViewProductDetails.setVisibility(View.VISIBLE);
            mBundleAdapter.setNewData(goodsDetails);
        }
        // 商品标签
        mFlowAdapter = new FlowAdapter(GoodsAddActivity.this, mGoodsBean.getLabelList());
        mFlowLayout.setAdapter(mFlowAdapter);
        // 设置为押金商品
        mSwitchDepositProductType.setChecked(TextUtils.equals(GoodsBean.DEPOSIT_GOODS_TYPE,
                mGoodsBean.getDepositProductType()));
        // 开启库存校验
        mSwitchStockCheckType.setChecked(TextUtils.equals(mGoodsBean.getStockCheckType(), "1"));


        //商品货主
        WareHouseShipperBean shipperBean = new WareHouseShipperBean();
        shipperBean.setPurchaserName(mGoodsBean.getCargoOwnerName());
        shipperBean.setPurchaserID(mGoodsBean.getCargoOwnerID());
        selectProductOwnerLogic(shipperBean);
    }


    private void selectProductTypeLogic(NameValue nameValue) {
        mTxtProductType.setText(nameValue.getName());
        mTxtProductType.setTag(nameValue);
        mRlProdutOwner.setVisibility(TextUtils.equals(nameValue.getValue(), "0") ? View.GONE : View.VISIBLE);
    }

    private void selectProductOwnerLogic(WareHouseShipperBean shipperBean) {
        mTxtProductOwner.setText(shipperBean.getPurchaserName());
        mTxtProductOwner.setTag(shipperBean);
    }


    /**
     * 当商品规格数量大于2时显示选择辅助规格按钮
     */
    private void showSpecsAddAssistUnit() {
        // 选择辅助规格
        mTxtSpecsAddAssistUnit.setVisibility((mSpecsAdapter != null && mSpecsAdapter.getItemCount() >= 2) ?
                View.VISIBLE : View.GONE);
    }

    /**
     * 商品属性的文本输入框
     *
     * @param attrBean 属性
     * @param adapter  adapter
     * @param position position
     */
    private void showInputDialog(ProductAttrBean attrBean, BaseQuickAdapter adapter, int position) {
        List<ProductAttrBean.RegexBean> regexBeans = attrBean.getRegexs();
        InputDialog.newBuilder(this)
                .setCancelable(false)
                .setTextTitle(attrBean.getKeyNote())
                .setHint(attrBean.getTip())
                .setText(attrBean.getCurrAttrValue())
                .setTextWatcher((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
                    if (!CommonUtils.isEmpty(regexBeans)) {
                        ProductAttrBean.RegexBean regexBean = regexBeans.get(0);
                        Pattern pattern = Pattern.compile(regexBean.getRegex());
                        if (!pattern.matcher(s.toString()).find() && s.length() > 1) {
                            s.delete(s.length() - 1, s.length());
                            showToast(regexBean.getTip());
                        }
                    }
                })
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        // 输入的名称
                        if (TextUtils.isEmpty(dialog.getInputString())) {
                            showToast("输入内容不能为空");
                            return;
                        }
                        attrBean.setCurrAttrValue(dialog.getInputString());
                        adapter.notifyItemChanged(position);
                    }
                    dialog.dismiss();
                }, "取消", "确定")
                .create().show();
    }

    /**
     * 商品属性的日期输入框
     *
     * @param attrBean 属性
     * @param adapter  adapter
     * @param position position
     */
    private void showDateDialog(ProductAttrBean attrBean, BaseQuickAdapter adapter, int position) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.BaseDialog, (view, year1, month1, dayOfMonth1) -> {
            attrBean.setCurrAttrValue(year1 + "-" + (month1 + 1) + "-" + dayOfMonth1);
            adapter.notifyItemChanged(position);
        }, year, monthOfYear, dayOfMonth);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.base_white)));
        }
        dialog.show();
    }

    /**
     * 商品属性的下拉选择框
     *
     * @param attrBean 属性
     * @param adapter  adapter
     * @param position position
     */
    private void showComboBoxWindow(ProductAttrBean attrBean, BaseQuickAdapter adapter, int position) {
        ComboBoxWindow comboBoxWindow = new ComboBoxWindow(this, attrBean);
        comboBoxWindow.setListener(selectString -> {
            attrBean.setCurrAttrValue(selectString);
            adapter.notifyItemChanged(position);
        });
        comboBoxWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 商品属性的地区选择控件-弹窗
     *
     * @param attrBean 属性
     * @param adapter  adapter
     * @param position position
     */
    private void showAreaWindow(ProductAttrBean attrBean, BaseQuickAdapter adapter, int position) {
        if (mAreaProductSelectWindow == null) {
            mAreaProductSelectWindow = new AreaProductSelectWindow(this);
        }
        mAreaProductSelectWindow.setResultSelectListener(t -> {
            attrBean.setCurrAttrValue(t.getShopProvince() + "," + t.getShopProvinceCode() + "," + t.getShopCity() +
                    "," + t.getShopCityCode());
            adapter.notifyItemChanged(position);
        });
        mAreaProductSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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

    /**
     * 设置为押金商品提示
     */
    private void showDepositProductType() {
        TipsDialog.newBuilder(this)
                .setTitle("设置为押金商品")
                .setMessage("启用后，该商品不能单独售卖，不能在商城端被搜索到，不能单独加入进货单，且不能下架。")
                .setButton((dialog, item) -> dialog.dismiss(), "我知道了")
                .create().show();
    }

    /**
     * 开启库存校验提示
     */
    private void showStockCheckType() {
        TipsDialog.newBuilder(this)
                .setTitle("开启库存校验")
                .setMessage("开启库存校验后，当商品某规格库存不足时不允许采购商下单采购")
                .setButton((dialog, item) -> dialog.dismiss(), "我知道了")
                .create().show();
    }

    private void addImgUrlSub(String url) {
        mImgImgUrlSub.setVisibility(mLlImgUrlSub.getChildCount() == 4 ? View.GONE : View.VISIBLE);
        ImgShowDelBlock block = new ImgShowDelBlock(this);
        block.setImgUrl(url);
        block.setDeleteListener(v -> {
            mLlImgUrlSub.removeView(block);
            mImgImgUrlSub.setVisibility(mLlImgUrlSub.getChildCount() == 4 ? View.GONE : View.VISIBLE);
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(80), UIUtils.dip2px(80));
        params.rightMargin = UIUtils.dip2px(10);
        mLlImgUrlSub.addView(block, mLlImgUrlSub.getChildCount() - 1, params);
    }

    private void addImgUrlDetail(String url) {
        mImgImgUrlDetail.setVisibility(mLlImgUrlDetail.getChildCount() == 5 ? View.GONE : View.VISIBLE);
        ImgShowDelBlock block = new ImgShowDelBlock(this);
        block.setImgUrl(url);
        block.setDeleteListener(v -> {
            mLlImgUrlDetail.removeView(block);
            mImgImgUrlDetail.setVisibility(mLlImgUrlDetail.getChildCount() == 5 ? View.GONE : View.VISIBLE);
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UIUtils.dip2px(80));
        params.bottomMargin = UIUtils.dip2px(10);
        mLlImgUrlDetail.addView(block, mLlImgUrlDetail.getChildCount() - 1, params);
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
            bean.setId(String.valueOf(mSpecsAdapter.getItemCount()));
            mSpecsAdapter.addData(bean);
            if (TextUtils.equals(GoodsBean.BUNDLING_GOODS_TYPE, mGoodsBean.getBundlingGoodsType())) {
                mTxtSpecsAdd.setVisibility(View.GONE);
            }
        } else {
            List<SpecsBean> specsBeanList = mSpecsAdapter.getData();
            if(modifySpecIndex>-1){
                SpecsBean specsBean = specsBeanList.get(modifySpecIndex);
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
                specsBean.setVolume(bean.getVolume());
                specsBean.setWeight(bean.getWeight());
            }else {
                bean.setId(String.valueOf(mSpecsAdapter.getItemCount()));
                mSpecsAdapter.addData(bean);
            }
        }
        mSpecsAdapter.notifyDataSetChanged();
        showSpecsAddAssistUnit();
    }

    @Subscribe
    public void onEvent(ArrayList<ProductAttrBean> productAttrs) {
        if (!CommonUtils.isEmpty(productAttrs) && !CommonUtils.isEmpty(mProductAttrAdapter.getData())) {
            for (int i = 0; i < productAttrs.size(); i++) {
                ProductAttrBean attrBean = productAttrs.get(i);
                for (ProductAttrBean bean : mProductAttrAdapter.getData()) {
                    if (TextUtils.equals(attrBean.getId(), bean.getId())) {
                        productAttrs.set(i, bean);
                        break;
                    }
                }
            }
        }
        // 商品属性列表展示
        mProductAttrAdapter.setNewData(productAttrs);
    }

    @Subscribe
    public void onEvent(BrandBackEvent event) {
        // 商品品牌
        String id = event.getId();
        List<ProductAttrBean> list = mProductAttrAdapter.getData();
        if (TextUtils.isEmpty(id) || CommonUtils.isEmpty(list)) {
            return;
        }
        for (int position = 0; position < list.size(); position++) {
            if (TextUtils.equals(list.get(position).getId(), id)) {
                list.get(position).setCurrAttrValue(event.getName());
                mProductAttrAdapter.notifyItemChanged(position);
                break;
            }
        }
    }

    @Subscribe
    public void onEvent(SingleListEvent<SkuGoodsBean> event) {
        if (event.getClazz() != SkuGoodsBean.class) return;
        List<SkuGoodsBean> goodsBeans = event.getList();
        if (CommonUtils.isEmpty(goodsBeans)) {
            return;
        }
        List<GoodsBean> list = mBundleAdapter.getData();
        List<GoodsBean> newList = new ArrayList<>();
        LABEL:
        for (SkuGoodsBean skuGoodsBean : goodsBeans) {
            GoodsBean goodsBean = new GoodsBean();
            newList.add(goodsBean);
            goodsBean.setBgdProductID(skuGoodsBean.getProductID());
            goodsBean.setBgdSpecID(skuGoodsBean.getSpecID());
            goodsBean.setProductName(skuGoodsBean.getProductName());
            goodsBean.setImgUrl(skuGoodsBean.getImgUrl());
            goodsBean.setSpecPrice(skuGoodsBean.getProductPrice());
            goodsBean.setSpecContent(skuGoodsBean.getSpecContent());
            if (!CommonUtils.isEmpty(list)) {
                for (GoodsBean bean : list) {
                    if (TextUtils.equals(bean.getBgdProductID(), skuGoodsBean.getProductID())) {
                        goodsBean.setSpecNum(bean.getSpecNum());
                        continue LABEL;
                    }
                }
            }
            goodsBean.setSpecNum("1");
        }
        if (!CommonUtils.isEmpty(newList)) {
            mRecyclerViewProductDetails.setVisibility(View.VISIBLE);
            mClProductDetails.setVisibility(View.VISIBLE);
            mBundleAdapter.setNewData(newList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) {
                mPresenter.uploadImg(list.get(0));
            }
        }
        if (requestCode == REQUEST_SELECT_PRODUCT_OWNER_CODE) {
            WareHouseShipperBean shipperBean = data.getParcelableExtra("bean");
            selectProductOwnerLogic(shipperBean);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.img_close, R.id.rl_categoryName, R.id.rl_shopProductCategorySubName, R.id.txt_categoryName_copy,
            R.id.txt_specs_add, R.id.txt_specs_add_assistUnit, R.id.txt_label_add, R.id.txt_productAttrs_add,
            R.id.txt_save, R.id.txt_saveAndUp, R.id.txt_title_save, R.id.txt_product_type, R.id.txt_product_owner, R.id.txt_productDetails_add})
    public void onViewClicked(View view) {
        ViewUtils.clearEditFocus(view);
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.rl_categoryName:
                mPresenter.queryCategory();
                break;
            case R.id.rl_shopProductCategorySubName:
                if (mTxtShopProductCategorySubName.getTag() != null) {
                    CopyCategoryBean bean = (CopyCategoryBean) mTxtShopProductCategorySubName.getTag();
                    if (bean != null) {
                        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_CUSTOM_CATEGORY,
                                bean.getShopProductCategorySubID(), bean.getShopProductCategoryThreeID());
                    }
                } else {
                    RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_CUSTOM_CATEGORY);
                }
                break;
            case R.id.txt_categoryName_copy:
                toCopy();
                break;
            case R.id.txt_specs_add:
                modifySpecIndex = -1;
                // 新增规格
                GoodsSpecsAddActivity.start(mSpecsAdapter.getData().size()==0?mEtProductCode.getText().toString():"",mSwitchDepositProductType.isChecked());
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
            case R.id.txt_save:
                // 仅保存
                toSave("1");
                break;
            case R.id.txt_saveAndUp:
                // 立即上架
                toSave("2");
                break;
            case R.id.txt_title_save:
                toSave(null);
                break;
            case R.id.txt_product_type:
                if (mSingleSelectionDialog == null) {
                    List<NameValue> types = new ArrayList<>();
                    types.add(new NameValue("自营", "0"));
                    types.add(new NameValue("代仓", "1"));
                    types.add(new NameValue("代配", "2"));
                    mSingleSelectionDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .refreshList(types)
                            .setTitleText("选择商品类型")
                            .select(types.get(Integer.parseInt(((NameValue)mTxtProductType.getTag()).getValue())))
                            .setOnSelectListener(nameValue -> {
                                selectProductTypeLogic(nameValue);
                            })
                            .create();
                }
                mSingleSelectionDialog.show();
                break;
            case R.id.txt_product_owner:
                WareHouseShipperBean wareHouseShipperBean = new WareHouseShipperBean();
                if (mGoodsBean != null) {
                    wareHouseShipperBean.setPurchaserID(mGoodsBean.getCargoOwnerID());
                    wareHouseShipperBean.setPurchaserName(mGoodsBean.getCargoOwnerName());
                }
                SelectProductOwnerActivity.start(this, REQUEST_SELECT_PRODUCT_OWNER_CODE, wareHouseShipperBean);
                break;
            case R.id.txt_productDetails_add:
                toSelectProduct();
                break;
            default:
                break;
        }
    }

    private void toSelectProduct() {
        ArrayList<SkuGoodsBean> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(mBundleAdapter.getData())) {
            for (GoodsBean detail : mBundleAdapter.getData()) {
                SkuGoodsBean bean = new SkuGoodsBean();
                list.add(bean);
                bean.setSpecID(detail.getBgdSpecID());
                bean.setProductID(detail.getBgdProductID());
                bean.setProductName(detail.getProductName());
                bean.setImgUrl(detail.getImgUrl());
                bean.setProductPrice(detail.getSpecPrice());
                bean.setSpecContent(detail.getSpecContent());
            }
        }
        ProductSelectActivity.start(getClass().getSimpleName(), "选择关联商品", list);
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

    /**
     * 商品规格-选择辅助规格
     */
    private void showAssistUnitSelectWindow() {
        if (mAssistUnitSelectWindow == null) {
            mAssistUnitSelectWindow = new AssistUnitSelectWindow(this);
            mAssistUnitSelectWindow.setListener(beans -> {
                // 重置
                List<SpecsBean> specsBeanList = mSpecsAdapter.getData();
                if (!CommonUtils.isEmpty(specsBeanList)) {
                    for (SpecsBean specsBean : specsBeanList) {
                        specsBean.setAssistUnitStatus("0");
                    }
                }
                // 设置选中
                if (!CommonUtils.isEmpty(beans)) {
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

    /**
     * 保存
     *
     * @param buttonType 按钮类型 1-仅保存，2-申请上架
     */
    private void toSave(String buttonType) {
//        boolean edit = true;
        if (mGoodsBean == null) {
//            edit = false;
            mGoodsBean = new GoodsBean();
            // 是否组合商品(0-不是，1-是)
            mGoodsBean.setBundlingGoodsType("0");
        }
        // 一级类目ID
        if (mTxtCategoryName.getTag(R.id.base_tag_1) != null) {
            CategoryItem categoryItem1 = (CategoryItem) mTxtCategoryName.getTag(R.id.base_tag_1);
            mGoodsBean.setCategoryID(categoryItem1.getCategoryID());
        } else {
            showToast("一级类目ID不能为空");
            return;
        }
        // 二级类目ID
        if (mTxtCategoryName.getTag(R.id.base_tag_2) != null) {
            CategoryItem categoryItem2 = (CategoryItem) mTxtCategoryName.getTag(R.id.base_tag_2);
            mGoodsBean.setCategorySubID(categoryItem2.getCategoryID());
        } else {
            showToast("二级类目ID不能为空");
            return;
        }
        // 三级类目ID
        if (mTxtCategoryName.getTag(R.id.base_tag_3) != null) {
            CategoryItem categoryItem3 = (CategoryItem) mTxtCategoryName.getTag(R.id.base_tag_3);
            mGoodsBean.setCategoryThreeID(categoryItem3.getCategoryID());
        } else {
            showToast("三级类目ID不能为空");
            return;
        }
        // 是否押金商品(0-不是，1-是)
        mGoodsBean.setDepositProductType(mSwitchDepositProductType.isChecked() ? "1" : "0");
        // 集团编号
        mGoodsBean.setGroupID(UserConfig.getGroupID());
        // 商品主图URL地址
        if (!TextUtils.isEmpty(mImgImgUrl.getImgUrl())) {
            mGoodsBean.setImgUrl(mImgImgUrl.getImgUrl());
        } else {
            showToast("商品主图不能为空");
            return;
        }
        // 商品详情图
        if (mLlImgUrlDetail != null) {
            List<String> list = new ArrayList<>();
            for (int index = 0; index < mLlImgUrlDetail.getChildCount(); index++) {
                View view = mLlImgUrlDetail.getChildAt(index);
                if (view instanceof ImgShowDelBlock) {
                    list.add(((ImgShowDelBlock) view).getImageUrl());
                }
            }
            mGoodsBean.setImgUrlDetail(TextUtils.join(",", list));
        }
        // 商品辅图URL地址，多个图片地址以逗号“,”分割
        if (mLlImgUrlSub != null) {
            List<String> list = new ArrayList<>();
            for (int index = 0; index < mLlImgUrlSub.getChildCount(); index++) {
                View view = mLlImgUrlSub.getChildAt(index);
                if (view instanceof ImgShowDelBlock) {
                    list.add(((ImgShowDelBlock) view).getImageUrl());
                }
            }
            mGoodsBean.setImgUrlSub(TextUtils.join(",", list));
        }
        // 是否代仓（0-自营，1-代仓
        mGoodsBean.setIsWareHourse(((NameValue)mTxtProductType.getTag()).getValue());
        if(!TextUtils.equals(mGoodsBean.getIsWareHourse(),"0") &&
          TextUtils.isEmpty(((WareHouseShipperBean)mTxtProductOwner.getTag()).getPurchaserID())){
            showToast("请选择货主");
            return;
        }

        if(mTxtProductOwner.getTag()!=null){
            WareHouseShipperBean shipperBean = (WareHouseShipperBean)mTxtProductOwner.getTag();
            mGoodsBean.setCargoOwnerID(shipperBean.getPurchaserID());
            mGoodsBean.setCargoOwnerName(shipperBean.getPurchaserName());
        }

        // 行业标签
        if (mFlowAdapter != null && !CommonUtils.isEmpty(mFlowAdapter.mList)) {
            List<LabelBean> labelList = new ArrayList<>();
            for (LabelBean labelBean : mFlowAdapter.mList) {
                LabelBean bean = new LabelBean();
                bean.setLabelID(labelBean.getLabelID());
                labelList.add(bean);
            }
            mGoodsBean.setLabelList(labelList);
        }

        // 商品属性
        if (!CommonUtils.isEmpty(mProductAttrAdapter.getData())) {
            List<ProductAttrBean> productAttrs = new ArrayList<>();
            for (ProductAttrBean bean : mProductAttrAdapter.getData()) {
                ProductAttrBean productAttrBean = new ProductAttrBean();
                productAttrBean.setAttrKey(bean.getAttrKey());
                productAttrBean.setAttrValue(bean.getCurrAttrValue());
                productAttrBean.setKeyNote(bean.getKeyNote());
                productAttrs.add(productAttrBean);
            }
            mGoodsBean.setProductAttrs(productAttrs);
        }

        if (mBundleAdapter != null && !CommonUtils.isEmpty(mBundleAdapter.getData())) {
            mGoodsBean.setBundlingGoodsDetails(mBundleAdapter.getData());
        }
        // 商品简介
        mGoodsBean.setProductBrief(mEtProductBrief.getText().toString().trim());
        // 商品编码
        if (!TextUtils.isEmpty(mEtProductCode.getText().toString().trim())) {
            mGoodsBean.setProductCode(mEtProductCode.getText().toString().trim());
        } else {
            showToast("商品编码不能为空");
            return;
        }
        // 商品名称
        if (!TextUtils.isEmpty(mEtProductName.getText().toString().trim())) {
            mGoodsBean.setProductName(mEtProductName.getText().toString().trim());
        } else {
            showToast("商品名称不能为空");
            return;
        }
        // 商品别称列表(避免商家输入特殊字符)
        List<NicknamesBean> listNickName = new ArrayList<>();
        if (!TextUtils.isEmpty(mEtNickNames1.getText().toString())) {
            NicknamesBean nicknamesBean = new NicknamesBean();
            nicknamesBean.setNickname(mEtNickNames1.getText().toString());
            nicknamesBean.setNicknameType("2");
            listNickName.add(nicknamesBean);
        }
        if (!TextUtils.isEmpty(mEtNickNames2.getText().toString())) {
            NicknamesBean nicknamesBean = new NicknamesBean();
            nicknamesBean.setNickname(mEtNickNames2.getText().toString());
            nicknamesBean.setNicknameType("2");
            listNickName.add(nicknamesBean);
        }
        if (!TextUtils.isEmpty(mEtNickNames3.getText().toString())) {
            NicknamesBean nicknamesBean = new NicknamesBean();
            nicknamesBean.setNickname(mEtNickNames3.getText().toString());
            nicknamesBean.setNicknameType("2");
            listNickName.add(nicknamesBean);
        }
        NicknamesBean nicknamesBean = new NicknamesBean();
        nicknamesBean.setNicknameType("1");
        nicknamesBean.setNickname(mGoodsBean.getProductName());
        listNickName.add(nicknamesBean);
        mGoodsBean.setNicknames(listNickName);
        // 数据来源类型（商城-shopmall，供应链-supplyChain）
        mGoodsBean.setResourceType("shopmall");
        // 自定义一级分类ID
        if (mTxtShopProductCategorySubName.getTag() != null) {
            CopyCategoryBean categoryBean = (CopyCategoryBean) mTxtShopProductCategorySubName.getTag();
            mGoodsBean.setShopProductCategorySubID(categoryBean.getShopProductCategorySubID());
            mGoodsBean.setShopProductCategorySubName(categoryBean.getShopProductCategorySubName());
            mGoodsBean.setShopProductCategoryThreeID(categoryBean.getShopProductCategoryThreeID());
            mGoodsBean.setShopProductCategoryThreeName(categoryBean.getShopProductCategoryThreeName());
        } else {
            showToast("自定义分类ID不能为空");
            return;
        }
        // 规格列表
        if (!CommonUtils.isEmpty(mSpecsAdapter.getData())) {
            mGoodsBean.setSpecs(mSpecsAdapter.getData());
        } else {
            showToast("规格列表不能为空");
            return;
        }
        // 是否开启库存校验(0-不是，1-是)
        mGoodsBean.setStockCheckType(mSwitchStockCheckType.isChecked() ? "1" : "0");
        if (edit) {
            if (TextUtils.equals(GoodsBean.EDIT_FROM_TEMPLATE, mGoodsBean.getEditFrom())) {
                Intent intent = new Intent();
                intent.putExtra(DepositProductsActivity.INTENT_TAG, mGoodsBean);
                setResult(RESULT_OK, intent);
                onBackPressed();
            } else {
                mGoodsBean.setButtonType(buttonType);
                mPresenter.editProduct(mGoodsBean);
            }
        } else {
            mGoodsBean.setButtonType(buttonType);
            mPresenter.addProduct(mGoodsBean);
        }
    }

    @Override
    public void uploadSuccess(String url) {
        if (mCurUpload == mImgImgUrl) {
            // 主图
            mImgImgUrl.showImage(url);
        } else if (mCurUpload == mImgImgUrlSub) {
            // 辅图
            addImgUrlSub(url);
        } else if (mCurUpload == mImgImgUrlDetail) {
            // 商品详情图
            addImgUrlDetail(url);
        }
    }

    @OnTouch({R.id.img_imgUrl, R.id.img_imgUrlSub, R.id.img_imgUrlDetail})
    boolean onTouch(View view) {
        mCurUpload = (ImgUploadBlock) view;
        return false;
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
        mTxtShopProductCategorySubName.setTag(bean);
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

    @Override
    public void toProductAttrsActivity() {
        if (!CommonUtils.isEmpty(mProductAttrAdapter.getData())) {
            RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR,
                    new ArrayList<>(mProductAttrAdapter.getData()));
        } else {
            RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR);
        }
    }

    @Override
    public void addSuccess(boolean edit) {
        showToast(edit ? "修改成功" : "保存成功");
        setResult(RESULT_OK);
        finish();
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

    private class NameCheckTextWatcher implements GoodsSpecsAddActivity.CheckTextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().startsWith(".")) {
                s.insert(0, "0");
            }
            if (!GoodsSpecsAddActivity.NICK_NAME.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("请勿包含空格");
            }
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
                    .setText(R.id.txt_productPrice, "¥" + CommonUtils.formatNumber(item.getProductPrice()));
        }
    }

    class ProductAttrAdapter extends BaseQuickAdapter<ProductAttrBean, BaseViewHolder> {

        ProductAttrAdapter() {
            super(R.layout.item_goods_attrs);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductAttrBean item) {
            helper.setText(R.id.txt_keyNote, item.getKeyNote())
                    .setGone(R.id.img_arrow, !TextUtils.equals(item.getWidget(), ProductAttrBean.WIDGET_INPUT));
            TextView txtArrValue = helper.getView(R.id.txt_attrValue);
            txtArrValue.setText(item.getCurrAttrValue());
            txtArrValue.setHint(item.getTip());
            if (TextUtils.equals(item.getWidget(), ProductAttrBean.WIDGET_AREA) && !TextUtils.isEmpty(item.getCurrAttrValue())) {
                String[] strings = item.getCurrAttrValue().split(",");
                txtArrValue.setText(strings.length > 2 ? String.format("%s-%s", strings[0], strings[2]) : strings[0]);
            }
        }
    }

    class ProductDetailsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        public ProductDetailsAdapter() {
            super(R.layout.item_goods_add_bundle);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            ((TextView) helper.getView(R.id.gab_number)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Utils.processMoney(s, false);
                    GoodsBean item = getItem(helper.getAdapterPosition());
                    if (item != null)
                        item.setSpecNum(s.toString());
                }
            });
            ((TextView) helper.getView(R.id.gab_price)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Utils.processMoney(s, false);
                    GoodsBean item = getItem(helper.getAdapterPosition());
                    if (item != null)
                        item.setSpecPrice(s.toString());
                }
            });
            helper.addOnClickListener(R.id.gab_delete);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            helper.setText(R.id.gab_name, item.getProductName())
                    .setText(R.id.gab_spec, item.getSpecContent())
                    .setText(R.id.gab_number, item.getSpecNum())
                    .setText(R.id.gab_price, item.getSpecPrice());
            ((GlideImageView) helper.getView(R.id.gab_image)).setImageURL(item.getImgUrl());
        }
    }
}
