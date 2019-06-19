package com.hll_sc_app.app.goods.add.specs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.txt_save)
    TextView mTxtSave;
    @BindView(R.id.txt_specContent_title)
    StartTextView mTxtSpecContentTitle;
    @BindView(R.id.et_specContent)
    EditText mEtSpecContent;
    @BindView(R.id.txt_saleUnitName_title)
    StartTextView mTxtSaleUnitNameTitle;
    @BindView(R.id.txt_saleUnitName)
    TextView mTxtSaleUnitName;
    @BindView(R.id.rl_saleUnitName)
    RelativeLayout mRlSaleUnitName;
    @BindView(R.id.txt_productPrice_title)
    StartTextView mTxtProductPriceTitle;
    @BindView(R.id.et_productPrice)
    EditText mEtProductPrice;
    @BindView(R.id.txt_skuCode_title)
    StartTextView mTxtSkuCodeTitle;
    @BindView(R.id.et_skuCode)
    EditText mEtSkuCode;
    @BindView(R.id.txt_ration_title)
    StartTextView mTxtRationTitle;
    @BindView(R.id.txt_ration)
    TextView mTxtRation;
    @BindView(R.id.txt_productStock_title)
    StartTextView mTxtProductStockTitle;
    @BindView(R.id.txt_productStock)
    TextView mTxtProductStock;
    @BindView(R.id.txt_buyMinNum_title)
    TextView mTxtBuyMinNumTitle;
    @BindView(R.id.txt_buyMinNum)
    TextView mTxtBuyMinNum;
    @BindView(R.id.txt_minOrder_title)
    TextView mTxtMinOrderTitle;
    @BindView(R.id.txt_minOrder)
    TextView mTxtMinOrder;
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
}
