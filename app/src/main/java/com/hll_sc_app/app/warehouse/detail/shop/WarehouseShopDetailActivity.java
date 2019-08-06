package com.hll_sc_app.app.warehouse.detail.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓货主门店详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
@Route(path = RouterConfig.WAREHOUSE_SHOP_DETAIL, extras = Constant.LOGIN_EXTRA)
public class WarehouseShopDetailActivity extends BaseLoadActivity implements WarehouseShopDetailContract.IWarehouseShopDetailView {
    @BindView(R.id.img_logoUrl)
    GlideImageView mImgLogoUrl;
    @BindView(R.id.txt_shopName)
    TextView mTxtShopName;
    @BindView(R.id.txt_linkman)
    TextView mTxtLinkman;
    @BindView(R.id.txt_mobile)
    TextView mTxtMobile;
    @BindView(R.id.txt_shopAddress)
    TextView mTxtShopAddress;
    @BindView(R.id.switch_pay)
    SwitchButton mSwitchPay;
    @Autowired(name = "parcelable", required = true)
    WarehouseShopBean mShopBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_shop_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        WarehouseShopDetailPresenter presenter = WarehouseShopDetailPresenter.newInstance();
        presenter.register(this);
        showView();
    }

    private void showView() {
        if (TextUtils.equals(mShopBean.getIsActive(), "0")) {
            mImgLogoUrl.setShopDisableImageUrl(mShopBean.getLogoUrl());
        } else {
            mImgLogoUrl.setImageURL(mShopBean.getLogoUrl());
        }
        mTxtShopName.setText(mShopBean.getShopName());
        mTxtLinkman.setText(mShopBean.getLinkman());
        mTxtMobile.setText(mShopBean.getMobile());
        mTxtShopAddress.setText(mShopBean.getShopAddress());
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showDetail(WarehouseDetailResp resp) {

    }
}
