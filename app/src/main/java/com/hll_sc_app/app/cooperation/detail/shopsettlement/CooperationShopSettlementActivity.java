package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情-选择结算方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, extras = Constant.LOGIN_EXTRA)
public class CooperationShopSettlementActivity extends BaseLoadActivity implements CooperationShopSettlementContract.ICooperationShopSettlementView {
    public static final String PAY_CASH = "1";
    public static final String PAY_ACCOUNT = "2";
    public static final String PAY_ONLINE = "3";
    @Autowired(name = "parcelable", required = true)
    ShopSettlementReq mReq;
    @BindView(R.id.img_onlinePayment)
    ImageView mImgOnlinePayment;
    @BindView(R.id.txt_onlinePayment)
    TextView mTxtOnlinePayment;
    @BindView(R.id.ll_onlinePayment)
    LinearLayout mLlOnlinePayment;
    @BindView(R.id.img_cashPayment)
    ImageView mImgCashPayment;
    @BindView(R.id.txt_cashPayment)
    TextView mTxtCashPayment;
    @BindView(R.id.ll_cashPayment)
    LinearLayout mLlCashPayment;
    @BindView(R.id.img_accountPayment)
    ImageView mImgAccountPayment;
    @BindView(R.id.txt_accountPayment)
    TextView mTxtAccountPayment;
    @BindView(R.id.ll_accountPayment)
    LinearLayout mLlAccountPayment;
    private CooperationShopSettlementPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_settlement);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationShopSettlementPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {

    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.ll_onlinePayment, R.id.ll_cashPayment, R.id.ll_accountPayment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.ll_onlinePayment:
                mImgOnlinePayment.setSelected(!mImgOnlinePayment.isSelected());
                mTxtOnlinePayment.setSelected(!mTxtOnlinePayment.isSelected());
                break;
            case R.id.ll_cashPayment:
                mImgCashPayment.setSelected(!mImgCashPayment.isSelected());
                mTxtCashPayment.setSelected(!mTxtCashPayment.isSelected());
                break;
            case R.id.ll_accountPayment:
                mImgAccountPayment.setSelected(!mImgAccountPayment.isSelected());
                mTxtAccountPayment.setSelected(!mTxtAccountPayment.isSelected());
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
        if (mImgOnlinePayment.isSelected()) {
            list.add(PAY_ONLINE);
        }
        if (mImgCashPayment.isSelected()) {
            list.add(PAY_CASH);
        }
        if (mImgAccountPayment.isSelected()) {
            list.add(PAY_ACCOUNT);
        }
        if (CommonUtils.isEmpty(list)) {
            showToast("请选择结算方式");
            return;
        }
        mReq.setSettlementWay(TextUtils.join(",", list));
        mPresenter.editShopSettlement(mReq);
    }


    @Override
    public void showSettlementList(SettlementBean bean) {
        List<String> payTypeEnum = bean.getPayTypeEnum();
        if (!CommonUtils.isEmpty(payTypeEnum)) {
            for (String s : payTypeEnum) {
                switch (s) {
                    case PAY_CASH:
                        mLlCashPayment.setVisibility(View.VISIBLE);
                        break;
                    case PAY_ACCOUNT:
                        mLlAccountPayment.setVisibility(View.VISIBLE);
                        break;
                    case PAY_ONLINE:
                        mLlOnlinePayment.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void editSuccess() {
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_DETAIL)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
