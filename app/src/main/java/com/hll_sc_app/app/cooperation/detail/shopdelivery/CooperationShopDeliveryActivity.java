package com.hll_sc_app.app.cooperation.detail.shopdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.DeliveryBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情-选择配送方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DELIVERY, extras = Constant.LOGIN_EXTRA)
public class CooperationShopDeliveryActivity extends BaseLoadActivity implements CooperationShopDeliveryContract.ICooperationShopDeliveryView {
    @Autowired(name = "parcelable", required = true)
    ShopSettlementReq mReq;
    @BindView(R.id.rb_deliveryOneSelf)
    RadioButton mRbDeliveryOneSelf;
    @BindView(R.id.rb_deliveryPick)
    RadioButton mRbDeliveryPick;
    @BindView(R.id.rb_deliveryThird)
    RadioButton mRbDeliveryThird;
    private CooperationShopDeliveryPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_delivery);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = CooperationShopDeliveryPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_confirm:
                toConfirm();
                break;
            default:
                break;
        }
    }

    private void toConfirm() {
        List<String> list = new ArrayList<>();
        if (mRbDeliveryOneSelf.isChecked()) {
            list.add("1");
        } else if (mRbDeliveryPick.isChecked()) {
            list.add("2");
        } else if (mRbDeliveryThird.isChecked()) {
            list.add("3");
        }
        if (CommonUtils.isEmpty(list)) {
            showToast("请选择配送方式");
            return;
        }
        mReq.setDeliveryWay(TextUtils.join(",", list));
        if (changeGroupProperty()) {
            showChangeRangeWindow();
        } else {
            mPresenter.editShopDelivery(mReq);
        }
    }

    /**
     * 修改集团参数
     *
     * @return true-时
     */
    private boolean changeGroupProperty() {
        return TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_PROPERTY);
    }

    private void showChangeRangeWindow() {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("是否统一修改配送方式")
            .setMessage("是否要修改集团下所有门店的配送方\n式，确认修改后将统一所有门店配送方式")
            .setButton((dialog, item) -> {
                dialog.dismiss();
                if (item == 1) {
                    mReq.setChangeAllShops("0");
                } else {
                    mReq.setChangeAllShops("1");
                }
                mPresenter.editShopDelivery(mReq);
            }, "确认修改", "暂不修改").create().show();
    }

    @Override
    public void showDeliveryList(DeliveryBean bean) {
        String deliveryWay = bean.getDeliveryWay();
        if (!TextUtils.isEmpty(deliveryWay)) {
            String[] strings = deliveryWay.split(",");
            for (String s : strings) {
                switch (s) {
                    case "1":
                        mRbDeliveryOneSelf.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        mRbDeliveryPick.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        mRbDeliveryThird.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
        if (!TextUtils.isEmpty(mReq.getDeliveryWay())) {
            if (mRbDeliveryOneSelf.getVisibility() == View.VISIBLE) {
                mRbDeliveryOneSelf.setChecked(mReq.getDeliveryWay().contains("1"));
            }
            if (mRbDeliveryPick.getVisibility() == View.VISIBLE) {
                mRbDeliveryPick.setChecked(mReq.getDeliveryWay().contains("2"));
            }
            if (mRbDeliveryThird.getVisibility() == View.VISIBLE) {
                mRbDeliveryThird.setChecked(mReq.getDeliveryWay().contains("3"));
            }
        }
    }

    @Override
    public void editSuccess() {
        showToast("配送方式修改成功");
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_DETAIL)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
