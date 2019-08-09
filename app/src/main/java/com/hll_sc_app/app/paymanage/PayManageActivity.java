package com.hll_sc_app.app.paymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.CooperationShopSettlementActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.delivery.DeliveryCompanyBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
@Route(path = RouterConfig.PAY_MANAGE, extras = Constant.LOGIN_EXTRA)
public class PayManageActivity extends BaseLoadActivity implements PayManageContract.IDeliveryTypeSetView,
    CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.ll_1)
    LinearLayout mLlCash;
    @BindView(R.id.switch_1)
    SwitchButton mSwitchCash;
    @BindView(R.id.ll_2)
    LinearLayout mLlAccount;
    @BindView(R.id.switch_2)
    SwitchButton mSwitchAccount;
    @BindView(R.id.ll_3)
    LinearLayout mLlOnline;
    @BindView(R.id.switch_3)
    SwitchButton mSwitchOnline;
    @BindView(R.id.txt_payTermType)
    TextView mTxtPayTermType;
    @BindViews({R.id.ll_1, R.id.ll_2, R.id.ll_3})
    List<View> mViews;
    @BindView(R.id.txt_payOnline)
    TextView mTxtPayOnline;
    @BindView(R.id.txt_payCash)
    TextView mTxtPayCash;

    private SettlementBean mBean;
    private PayManagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = PayManagePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSwitchCash.setOnCheckedChangeListener(this);
        mSwitchAccount.setOnCheckedChangeListener(this);
        mSwitchOnline.setOnCheckedChangeListener(this);
    }

    @Subscribe
    public void onEvent(ArrayList<DeliveryCompanyBean> list) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showPayList(SettlementBean bean) {
        this.mBean = bean;
        mSwitchOnline.setCheckedNoEvent(false);
        mSwitchCash.setCheckedNoEvent(false);
        mSwitchAccount.setCheckedNoEvent(false);
        ButterKnife.apply(mViews, (view, index) -> view.setVisibility(View.GONE));
        mSwitchCash.setCheckedNoEvent(TextUtils.equals("1", bean.getCashPayment()));
        mSwitchAccount.setCheckedNoEvent(TextUtils.equals("1", bean.getAccountPayment()));
        mSwitchOnline.setCheckedNoEvent(TextUtils.equals("1", bean.getOnlinePayment()));
        showOnlinePayment(bean.getOnlinePayMethod());
        showCashPayment(bean.getCodPayMethod());
        showAccountPayment(bean);
    }

    private void showOnlinePayment(String onlinePayMethod) {
        mLlOnline.setVisibility(mSwitchOnline.isChecked() ? View.VISIBLE : View.GONE);
        mTxtPayOnline.setText(onlinePayMethod);
        if (!TextUtils.isEmpty(onlinePayMethod)) {
            SpannableString spannableString = getSpannableString(onlinePayMethod);
            mTxtPayOnline.setText(spannableString);
        } else {
            mTxtPayOnline.setText(onlinePayMethod);
        }
    }

    private void showCashPayment(String codPayMethod) {
        mLlCash.setVisibility(mSwitchCash.isChecked() ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(codPayMethod)) {
            SpannableString spannableString = getSpannableString(codPayMethod);
            mTxtPayCash.setText(spannableString);
        } else {
            mTxtPayCash.setText(codPayMethod);
        }
    }

    private void showAccountPayment(SettlementBean bean) {
        mLlAccount.setVisibility(mSwitchAccount.isChecked() ? View.VISIBLE : View.GONE);
        if (TextUtils.equals(bean.getPayTermType(), CooperationShopSettlementActivity.TERM_WEEK)) {
            mTxtPayTermType.setText(String.format("周结,%s",
                CooperationShopSettlementActivity.getPayTermStr(CommonUtils.getInt(bean.getPayTerm()))));
        } else if (TextUtils.equals(bean.getPayTermType(), CooperationShopSettlementActivity.TERM_MONTH)) {
            mTxtPayTermType.setText(String.format("月结，每月%s号", bean.getPayTerm()));
        } else {
            mTxtPayTermType.setText(null);
        }
    }

    private SpannableString getSpannableString(@NotNull String method) {
        SpannableString spannableString = new SpannableString(method.replaceAll(",", " "));
        String[] strings = method.split(",");
        int preLength = 0;
        for (String string : strings) {
            int resourceId = 0;
            switch (string) {
                case "1":
                    resourceId = R.drawable.ic_pay_type_1;
                    break;
                case "2":
                    resourceId = R.drawable.ic_pay_type_2;
                    break;
                case "3":
                    resourceId = R.drawable.ic_pay_type_3;
                    break;
                case "4":
                    resourceId = R.drawable.ic_pay_type_4;
                    break;
                case "9":
                    resourceId = R.drawable.ic_pay_type_9;
                    break;
                case "10":
                    resourceId = R.drawable.ic_pay_type_10;
                    break;
                case "13":
                    resourceId = R.drawable.ic_pay_type_13;
                    break;
                case "14":
                    resourceId = R.drawable.ic_pay_type_14;
                    break;
                default:
                    break;
            }
            if (resourceId != 0) {
                spannableString.setSpan(new ImageSpan(this, resourceId, DynamicDrawableSpan.ALIGN_BASELINE),
                    preLength, preLength + string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            preLength = preLength + string.length() + 1;
        }
        return spannableString;
    }

    @Override
    public void editSuccess() {
        showToast("支付方式修改成功");
        mPresenter.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!mSwitchCash.isChecked() && !mSwitchOnline.isChecked()) {
            ((SwitchButton) buttonView).setCheckedNoEvent(!isChecked);
            showToast("在线支付、货到付款至少保留一个支付方式");
            return;
        }
    }
}
