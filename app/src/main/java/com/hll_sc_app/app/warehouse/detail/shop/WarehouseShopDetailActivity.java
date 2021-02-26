package com.hll_sc_app.app.warehouse.detail.shop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.warehouse.detail.showpaylist.ShowPayListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.RefreshWarehouseShopList;
import com.hll_sc_app.bean.warehouse.ShopParameterBean;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.bean.warehouse.WarehouseShopEditReq;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;

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
    @BindView(R.id.txt_payee)
    TextView mTxtPayee;
    @BindView(R.id.txt_payType)
    TextView mTxtPayType;
    @BindView(R.id.txt_pay_check)
    TextView mTxtPayCheck;
    @BindView(R.id.ll_payee)
    LinearLayout mLlPayee;
    @BindView(R.id.ll_payType)
    LinearLayout mLlPayType;
    @BindView(R.id.ll_pay_check)
    LinearLayout mLlPayCheck;
    @BindView(R.id.txt_remove)
    TextView mRemove;

    private WarehouseShopDetailPresenter mPresenter;
    private ShopParameterBean mShopParameterBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_shop_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = WarehouseShopDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryWarehouseShop();
        showView();
            mSwitchPay.setOnCheckedChangeListener((buttonView, isChecked)
                -> mPresenter.editWarehouseShop(isChecked ? "1" : "0"));
    }

    private void showView() {
        if (TextUtils.equals(mShopBean.getIsActive(), "0")) {
            mImgLogoUrl.setDisableImageUrl(mShopBean.getLogoUrl(), GlideImageView.DISABLE_SHOP);
        } else {
            mImgLogoUrl.setImageURL(mShopBean.getLogoUrl());
        }
        mTxtShopName.setText(mShopBean.getShopName());
        mTxtLinkman.setText(mShopBean.getLinkman());
        mTxtMobile.setText(mShopBean.getMobile());
        mTxtShopAddress.setText(mShopBean.getShopAddress());
        if ("2".equals(mShopBean.getIsWarehouse())) {
            mRemove.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_close, R.id.txt_pay_check, R.id.txt_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_pay_check:
                ShowPayListActivity.start(mShopParameterBean);
                break;
            case R.id.txt_remove:
                removeConfirm();
                break;
            default:
                break;
        }
    }

    private void removeConfirm() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确认要解除合作嘛")
                .setMessage("您确认要解除和该门店的\n代仓合作关系嘛")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        remove();
                    }
                    dialog.dismiss();
                }, "我再看看", "确认解除")
                .create()
                .show();
    }

    private void remove() {
        WarehouseShopEditReq req = new WarehouseShopEditReq();
        req.setPurchaserID(mShopBean.getPurchaserId());
        req.setDeleteShopIds(Collections.singletonList(mShopBean.getId()));
        mPresenter.delWarehouse(req);
    }

    @Override
    public void showDetail(ShopParameterBean bean) {
        mShopParameterBean = bean;
        mSwitchPay.setCheckedNoEvent(!TextUtils.equals(bean.getSupportPay(), "0"));

        if (TextUtils.equals(bean.getSupportPay(), "1")) {
            mLlPayee.setVisibility(View.VISIBLE);
            mLlPayType.setVisibility(View.VISIBLE);
            mLlPayCheck.setVisibility(View.VISIBLE);
            if (TextUtils.equals(bean.getPayee(), "1")) {
                // 代仓公司代收货款
                mTxtPayee.setText("代仓公司代收货款");
            } else if (TextUtils.equals(bean.getPayee(), "2")) {
                // 货主直接收取货款
                mTxtPayee.setText("货主直接收取货款");
            }
            StringBuilder payBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(bean.getPayType())) {
                if (bean.getPayType().contains("1")) {
                    payBuilder.append("在线支付");
                }
                if (bean.getPayType().contains("2")) {
                    payBuilder.append(payBuilder.length() == 0 ? "" : "/").append("账期支付");
                }
                if (bean.getPayType().contains("3")) {
                    payBuilder.append(payBuilder.length() == 0 ? "" : "/").append("货到付款");
                }
            }
            mTxtPayType.setText(payBuilder.toString());
        } else {
            mLlPayee.setVisibility(View.GONE);
            mLlPayType.setVisibility(View.GONE);
            mLlPayCheck.setVisibility(View.GONE);
        }
    }

    @Override
    public String getShopIds() {
        return mShopBean.getId();
    }

    @Override
    public String getPurchaserId() {
        return mShopBean.getPurchaserId();
    }

    @Override
    public void success() {
        showToast("解除代仓成功");
        EventBus.getDefault().post(new RefreshWarehouseShopList());
        finish();
    }
}
