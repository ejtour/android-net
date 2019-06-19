package com.hll_sc_app.app.goods.add.specs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.saleunitname.SaleUnitNameActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.base.widget.StartTextView;
import com.hll_sc_app.bean.goods.SaleUnitNameBean;

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
    private GoodsSpecsAddPresenter mPresenter;

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            SaleUnitNameBean bean = data.getParcelableExtra(SaleUnitNameActivity.INTENT_TAG);
            if (bean != null) {
                mTxtSaleUnitName.setText(bean.getSaleUnitName());
                mTxtSaleUnitName.setTag(bean.getSaleUnitId());
            }
        }
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.rl_saleUnitName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                // 保存
                break;
            case R.id.rl_saleUnitName:
                // 选择售卖单位
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_SPECS_SALE_UNIT_NAME, this,
                    ImgUploadBlock.REQUEST_CODE_CHOOSE);
                break;
            default:
                break;
        }
    }

    public interface CheckTextWatcher extends TextWatcher {

        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }
}
