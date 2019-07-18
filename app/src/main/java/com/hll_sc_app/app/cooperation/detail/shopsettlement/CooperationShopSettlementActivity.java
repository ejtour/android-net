package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
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
    public static final String TERM_WEEK = "1";
    public static final String TERM_MONTH = "2";
    public static final String[] STRINGS_WEEK = {"每周日", "每周一", "每周二", "每周三", "每周四", "每周五", "每周六"};
    @Autowired(name = "parcelable", required = true)
    ShopSettlementReq mReq;
    @BindView(R.id.txt_accountPeriod)
    TextView mTxtAccountPeriod;
    @BindView(R.id.txt_settleDate)
    TextView mTxtSettleDate;
    @BindView(R.id.ll_account_detail)
    LinearLayout mLlAccountDetail;
    @BindView(R.id.cb_onlinePayment)
    CheckBox mCbOnlinePayment;
    @BindView(R.id.cb_cashPayment)
    CheckBox mCbCashPayment;
    @BindView(R.id.cb_accountPayment)
    CheckBox mCbAccountPayment;
    private CooperationShopSettlementPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_settlement);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = CooperationShopSettlementPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mCbAccountPayment.setOnCheckedChangeListener((buttonView, isChecked) -> showAccountDetail());
    }

    /**
     * 是否显示账期信息
     */
    private void showAccountDetail() {
        mLlAccountDetail.setVisibility(mCbAccountPayment.isChecked() ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.rl_accountPeriod, R.id.rl_settleDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_confirm:
                toConfirm();
                break;
            case R.id.rl_accountPeriod:
                showAccountPeriodWindow();
                break;
            case R.id.rl_settleDate:
                showInputDialog();
                break;
            default:
                break;
        }
    }

    private void toConfirm() {
        List<String> list = new ArrayList<>();
        if (mCbOnlinePayment.isSelected()) {
            list.add(PAY_ONLINE);
        }
        if (mCbCashPayment.isSelected()) {
            list.add(PAY_CASH);
        }
        if (mCbAccountPayment.isChecked()) {
            list.add(PAY_ACCOUNT);
        }
        if (CommonUtils.isEmpty(list)) {
            showToast("请选择结算方式");
            return;
        }
        mReq.setSettlementWay(TextUtils.join(",", list));
        if (mLlAccountDetail.getVisibility() == View.VISIBLE) {
            // 账期支付的详细信息
            if (mTxtAccountPeriod.getTag(R.id.date_start) != null) {
                // 账期日类型,0-未设置,1-按周,2-按月
                mReq.setAccountPeriodType(String.valueOf(mTxtAccountPeriod.getTag(R.id.date_start)));
            } else {
                mReq.setAccountPeriodType("0");
            }
            if (mTxtAccountPeriod.getTag(R.id.date_end) != null) {
                mReq.setAccountPeriod(String.valueOf(mTxtAccountPeriod.getTag(R.id.date_end)));
            }
            if (mTxtSettleDate.getTag() != null) {
                mReq.setSettleDate(String.valueOf(mTxtSettleDate.getTag()));
            }
        }
        mPresenter.editShopSettlement(mReq);
    }

    private void showAccountPeriodWindow() {
        AccountPeriodSelectWindow window = new AccountPeriodSelectWindow(this);
        window.setSelectListener((payTermType, payTerm) -> {
            if (TextUtils.equals(payTermType, "周结")) {
                mTxtAccountPeriod.setTag(R.id.date_start, 1);
                mTxtAccountPeriod.setTag(R.id.date_end, payTerm);
                mTxtAccountPeriod.setText(String.format("周结,%s", getPayTermStr(payTerm)));
            } else {
                mTxtAccountPeriod.setTag(R.id.date_start, 2);
                mTxtAccountPeriod.setTag(R.id.date_end, payTerm);
                mTxtAccountPeriod.setText(String.format("月结，每月%s号", payTerm));
            }
        });
        window.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void showInputDialog() {
        InputDialog.newBuilder(this)
            .setCancelable(false)
            .setTextTitle("输入结算日")
            .setHint("输入在对账单产生后多少日")
            .setInputType(InputType.TYPE_CLASS_NUMBER)
            .setMaxLength(7)
            .setButton((dialog, item) -> {
                if (item == 1) {
                    if (TextUtils.isEmpty(dialog.getInputString())) {
                        showToast("输入不能为空");
                        return;
                    }
                    mTxtSettleDate.setText(String.format("对账单产生后%s日", dialog.getInputString()));
                    mTxtSettleDate.setTag(dialog.getInputString());
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
    }

    public static String getPayTermStr(int payTerm) {
        return payTerm > STRINGS_WEEK.length ? "" : STRINGS_WEEK[payTerm];
    }

    @Override
    public void showSettlementList(SettlementBean bean) {
        if (TextUtils.equals(bean.getPayTermType(), TERM_WEEK)) {
            mTxtAccountPeriod.setTag(R.id.date_start, 1);
            mTxtAccountPeriod.setTag(R.id.date_end, bean.getPayTerm());
            mTxtAccountPeriod.setText(String.format("周结,%s", getPayTermStr(CommonUtils.getInt(bean.getPayTerm()))));
        } else if (TextUtils.equals(bean.getPayTermType(), TERM_MONTH)) {
            mTxtAccountPeriod.setTag(R.id.date_start, 2);
            mTxtAccountPeriod.setTag(R.id.date_end, bean.getPayTerm());
            mTxtAccountPeriod.setText(String.format("月结，每月%s号", bean.getPayTerm()));
        } else {
            mTxtAccountPeriod.setText(null);
        }
        mTxtSettleDate.setText(String.format("对账单产生后%s日", bean.getSettleDate()));
        mTxtSettleDate.setTag(bean.getSettleDate());

        List<String> payTypeEnum = bean.getPayTypeEnum();
        if (!CommonUtils.isEmpty(payTypeEnum)) {
            for (String s : payTypeEnum) {
                switch (s) {
                    case PAY_CASH:
                        mCbCashPayment.setVisibility(View.VISIBLE);
                        break;
                    case PAY_ACCOUNT:
                        mCbAccountPayment.setVisibility(View.VISIBLE);
                        break;
                    case PAY_ONLINE:
                        mCbOnlinePayment.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void editSuccess() {
        showToast("结算方式修改成功");
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_DETAIL)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
