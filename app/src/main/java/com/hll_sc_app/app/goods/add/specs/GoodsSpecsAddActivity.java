package com.hll_sc_app.app.goods.add.specs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.depositproducts.DepositProductsActivity;
import com.hll_sc_app.app.goods.add.specs.saleunitname.SaleUnitNameActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.base.widget.StartTextView;
import com.hll_sc_app.bean.goods.DepositProductBean;
import com.hll_sc_app.bean.goods.SaleUnitNameBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增商品规格
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_SPECS, extras = Constant.LOGIN_EXTRA)
public class GoodsSpecsAddActivity extends BaseLoadActivity implements GoodsSpecsAddContract.IGoodsAddView {
    public static final Pattern PRODUCT_PRICE = Pattern.compile("^[0-9]{1,7}([.]{1}[0-9]{0,2})?$");
    public static final Pattern RATION = Pattern.compile("^[0-9]{1,8}([.]{1}[0-9]{0,2})?$");
    public static final Pattern BUY_MIN_NUM = Pattern.compile("^[0-9]{1,4}$");
    public static final Pattern MIN_ORDER = Pattern.compile("^[0-9]{1,7}([.]{1}[5]{0,1})?$");
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.txt_save)
    TextView mTxtSave;
    @BindView(R.id.txt_specContent_title)
    StartTextView mTxtSpecContentTitle;
    @BindView(R.id.edt_specContent)
    EditText mEdtSpecContent;
    @BindView(R.id.txt_saleUnitName_title)
    StartTextView mTxtSaleUnitNameTitle;
    @BindView(R.id.txt_saleUnitName)
    TextView mTxtSaleUnitName;
    @BindView(R.id.rl_saleUnitName)
    RelativeLayout mRlSaleUnitName;
    @BindView(R.id.txt_productPrice_title)
    StartTextView mTxtProductPriceTitle;
    @BindView(R.id.edt_productPrice)
    EditText mEdtProductPrice;
    @BindView(R.id.txt_skuCode_title)
    StartTextView mTxtSkuCodeTitle;
    @BindView(R.id.edt_skuCode)
    EditText mEdtSkuCode;
    @BindView(R.id.txt_ration_title)
    StartTextView mTxtRationTitle;
    @BindView(R.id.edt_ration)
    EditText mEdtRation;
    @BindView(R.id.txt_buyMinNum_title)
    TextView mTxtBuyMinNumTitle;
    @BindView(R.id.edt_buyMinNum)
    EditText mEdtBuyMinNum;
    @BindView(R.id.txt_minOrder_title)
    TextView mTxtMinOrderTitle;
    @BindView(R.id.edt_minOrder)
    EditText mEdtMinOrder;
    @BindView(R.id.txt_isDecimalBuy_title)
    TextView mTxtIsDecimalBuyTitle;
    @BindView(R.id.txt_depositProducts_add)
    TextView mTxtDepositProductsAdd;
    @BindView(R.id.recyclerView_depositProduct)
    RecyclerView mRecyclerViewDepositProduct;
    private GoodsSpecsAddPresenter mPresenter;
    private DepositProductAdapter mDepositProductAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_specs);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsSpecsAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        // 输入限制
        mEdtProductPrice.addTextChangedListener((CheckTextWatcher) s -> {
            if (s.toString().startsWith(".")) {
                s.insert(0, "0");
            }
            if (!PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("平台价格支持7位整数或小数点后两位");
            }
        });
        mEdtRation.addTextChangedListener((CheckTextWatcher) s -> {
            if (s.toString().startsWith(".")) {
                s.insert(0, "0");
            }
            if (!RATION.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("转换率仅支持8位整数或小数点后两位");
            }
        });
        mEdtBuyMinNum.addTextChangedListener((CheckTextWatcher) s -> {
            if (!BUY_MIN_NUM.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("最低起购量仅支持1-4位整数");
            }
        });
        mEdtMinOrder.addTextChangedListener((CheckTextWatcher) s -> {
            if (s.toString().startsWith(".")) {
                s.insert(0, "0");
            }
            if (!MIN_ORDER.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("订单倍数仅支持1-7位整数或0.5的倍数");
            }
        });

        // 押金商品展示
        mRecyclerViewDepositProduct.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewDepositProduct.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mDepositProductAdapter = new DepositProductAdapter();
        mDepositProductAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DepositProductBean bean = (DepositProductBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (view.getId() == R.id.img_del) {
                // 移除
                mDepositProductAdapter.remove(position);
            } else if (view.getId() == R.id.txt_depositNum) {
                // 输入数量
                showInputDialog(bean, position);
            }
        });
        mRecyclerViewDepositProduct.setAdapter(mDepositProductAdapter);
    }

    /**
     * 押金商品数量输入
     *
     * @param bean     押金商品
     * @param position 位置
     */
    private void showInputDialog(DepositProductBean bean, int position) {
        InputDialog.newBuilder(this)
            .setCancelable(false)
            .setTextTitle("输入" + bean.getProductName() + "数量")
            .setHint("请输入数量")
            .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
            .setMaxLength(7)
            .setText(bean.getDepositNum())
            .setButton((dialog, item) -> {
                if (item == 1) {
                    // 输入的数量
                    if (TextUtils.isEmpty(dialog.getInputString())) {
                        showToast("数量不能为空");
                        return;
                    }
                    bean.setDepositNum(CommonUtils.formatRound(dialog.getInputString()));
                    mDepositProductAdapter.notifyItemChanged(position);
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
                // 售卖单位
                SaleUnitNameBean bean = data.getParcelableExtra(SaleUnitNameActivity.INTENT_TAG);
                if (bean != null) {
                    mTxtSaleUnitName.setText(bean.getSaleUnitName());
                    mTxtSaleUnitName.setTag(bean.getSaleUnitId());
                }
            } else if (requestCode == ImgUploadBlock.REQUEST_CODE_IMG_URL) {
                // 押金商品
                ArrayList<DepositProductBean> arrayList =
                    data.getParcelableArrayListExtra(DepositProductsActivity.INTENT_TAG);
                mDepositProductAdapter.setNewData(arrayList);
            }
        }
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.rl_saleUnitName, R.id.txt_depositProducts_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                // 保存
                toSave();
                break;
            case R.id.rl_saleUnitName:
                // 选择售卖单位
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_SPECS_SALE_UNIT_NAME, this,
                    ImgUploadBlock.REQUEST_CODE_CHOOSE);
                break;
            case R.id.txt_depositProducts_add:
                // 选择押金商品
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_SPECS_DEPOSIT_PRODUCT,
                    GoodsSpecsAddActivity.this, ImgUploadBlock.REQUEST_CODE_IMG_URL);
                break;
            default:
                break;
        }
    }

    private void toSave() {
        if (TextUtils.isEmpty(mEdtSpecContent.getText().toString().trim())) {
            showToast("规格内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(mTxtSaleUnitName.getText().toString().trim())) {
            showToast("售卖单位不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEdtProductPrice.getText().toString().trim())) {
            showToast("单价不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEdtSkuCode.getText().toString().trim())) {
            showToast("SKU条码不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEdtRation.getText().toString().trim())) {
            showToast("转换率不能为空");
            return;
        }
        boolean depositProductPass = true;
        if (mDepositProductAdapter != null && !CommonUtils.isEmpty(mDepositProductAdapter.getData())) {
            for (DepositProductBean bean : mDepositProductAdapter.getData()) {
                if (TextUtils.isEmpty(bean.getDepositNum())) {
                    depositProductPass = false;
                    break;
                }
            }
        }
        if (!depositProductPass) {
            showToast("请填写押金商品数量");
            return;
        }
        mPresenter.checkSkuCode(mEdtSkuCode.getText().toString().trim());
    }

    @Override
    public void checkSuccess() {
        // 返回上级页面
    }

    public interface CheckTextWatcher extends TextWatcher {

        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }

    class DepositProductAdapter extends BaseQuickAdapter<DepositProductBean, BaseViewHolder> {

        DepositProductAdapter() {
            super(R.layout.item_spec_deposit_product);
        }

        @Override
        protected void convert(BaseViewHolder helper, DepositProductBean item) {
            helper.setText(R.id.txt_productName, item.getProductName())
                .setText(R.id.txt_depositNum, item.getDepositNum())
                .addOnClickListener(R.id.img_del)
                .addOnClickListener(R.id.txt_depositNum);
        }
    }
}
