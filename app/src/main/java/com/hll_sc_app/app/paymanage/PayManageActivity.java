package com.hll_sc_app.app.paymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.CooperationShopSettlementActivity;
import com.hll_sc_app.app.paymanage.account.PayAccountManageActivity;
import com.hll_sc_app.app.paymanage.method.PayMethodManageActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.delivery.DeliveryCompanyBean;
import com.hll_sc_app.bean.paymanage.PayBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
@Route(path = RouterConfig.PAY_MANAGE, extras = Constant.LOGIN_EXTRA)
public class PayManageActivity extends BaseLoadActivity implements PayManageContract.IPayManageView {
    @BindView(R.id.txt_payOnline)
    TextView mTxtPayOnline;
    @BindView(R.id.txt_payCash)
    TextView mTxtPayCash;
    @BindView(R.id.txt_payTermType)
    TextView mTxtPayTermType;

    private SettlementBean mBean;
    private List<PayBean> mData;
    private PayManagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
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

    @Subscribe
    public void onEvent(ArrayList<DeliveryCompanyBean> list) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.querySettlementList();
    }

    @OnClick({R.id.img_close, R.id.rl_payterm, R.id.rl_cash, R.id.rl_online, R.id.txt_alert})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.img_close) {
            finish();
        } else if (id == R.id.txt_alert) {
            RouterUtil.goToActivity(RouterConfig.WALLET);
        } else if (id == R.id.rl_payterm) {
            // 账期支付
            PayAccountManageActivity.start(mBean.getPayTermType(), mBean.getPayTerm(), mBean.getSettleDate());
        } else if (id == R.id.rl_cash) {
            // 货到付款
            PayMethodManageActivity.start(getCodPayList(), TextUtils.equals("1", mBean.getCashPayment()));
        } else if (id == R.id.rl_online) {
            // 在线支付
            PayMethodManageActivity.start(getOnlinePayList(), TextUtils.equals("1", mBean.getOnlinePayment()));
        }
    }

    /**
     * 货到付款列表参数
     *
     * @return
     */
    private ArrayList<PayBean> getCodPayList() {
        ArrayList<PayBean> arrayList = new ArrayList<>();
        if (!CommonUtils.isEmpty(mData)) {
            for (PayBean bean : mData) {
                if (TextUtils.equals("2", bean.getPayType())) {
                    // 货到付款
                    if (!TextUtils.isEmpty(mBean.getCodPayMethod()) && mBean.getCodPayMethod().contains(bean.getId())) {
                        bean.setSelect(true);
                    } else {
                        bean.setSelect(false);
                    }

                    bean.setEnable(true);
                    if (TextUtils.equals("0", mBean.getOpenStatus())) {//铁金库没开通，则只能使用现金刷卡
                        if (!TextUtils.equals("9", bean.getId()) && !TextUtils.equals("10", bean.getId())) {
                            bean.setEnable(false);
                        }
                    }

                    if (TextUtils.equals("12", bean.getId())) {//微信直连关闭-》微信支付不能用
                        bean.setEnable(TextUtils.equals("1", mBean.getWechatStatus()));
                    }

                    if (TextUtils.equals("16", bean.getId())) {//卡支付关闭,储值卡不能用
                        bean.setEnable(TextUtils.equals("1", mBean.getCardStatus()));
                    }

                    arrayList.add(bean);
                }
            }
        }
        return arrayList;
    }

    private ArrayList<PayBean> getOnlinePayList() {
        ArrayList<PayBean> arrayList = new ArrayList<>();
        if (!CommonUtils.isEmpty(mData)) {
            for (PayBean bean : mData) {
                if (TextUtils.equals("1", bean.getPayType())) {

                    if (!TextUtils.isEmpty(mBean.getOnlinePayMethod()) && mBean.getOnlinePayMethod().contains(bean.getId())) {
                        bean.setSelect(true);
                    } else {
                        bean.setSelect(false);
                    }
                    bean.setEnable(true);
                    if (TextUtils.equals("0", mBean.getOpenStatus())) {//铁金库没开通，则只能使用现金刷卡
                        bean.setEnable(false);
                    }

                    if (TextUtils.equals("4", bean.getId())) {//微信直连关闭-》微信支付不能用
                        bean.setEnable(TextUtils.equals("1", mBean.getWechatStatus()));
                    }
                    if (TextUtils.equals("15", bean.getId())) {//卡支付关闭,储值卡不能用
                        bean.setEnable(TextUtils.equals("1", mBean.getCardStatus()));
                    }
                    arrayList.add(bean);
                }
            }
        }
        return arrayList;
    }


    @Override
    public void showPayList(SettlementBean bean) {
        this.mBean = bean;
        showButton(bean);
    }

    private void showButton(SettlementBean bean) {
        if (bean == null) {
            return;
        }
        showOnlinePayment(bean);
        showCashPayment(bean);
        showAccountPayment(bean);
    }

    private void showOnlinePayment(SettlementBean bean) {
        mTxtPayOnline.setVisibility(View.VISIBLE);
        if (TextUtils.equals("1", bean.getOnlinePayment())) {
            mTxtPayOnline.setText(String.format("已开启 %s 种支付方式", bean.getOnlinePayMethod().split(",").length));
        } else {
            mTxtPayOnline.setText("未开启");
        }
    }

    private void showCashPayment(SettlementBean bean) {
        if (TextUtils.equals("1", bean.getCashPayment())) {
            mTxtPayCash.setText(String.format("已开启 %s 种支付方式", bean.getCodPayMethod().split(",").length));
        } else {
            mTxtPayCash.setText("未开启");
        }
    }

    private void showAccountPayment(SettlementBean bean) {
        if (TextUtils.equals("1", bean.getAccountPayment())) {
            if (TextUtils.equals(bean.getPayTermType(), CooperationShopSettlementActivity.TERM_WEEK)) {
                mTxtPayTermType.setText(String.format("已开启 周结,%s",
                        CooperationShopSettlementActivity.getPayTermStr(CommonUtils.getInt(bean.getPayTerm()))));
            } else if (TextUtils.equals(bean.getPayTermType(), CooperationShopSettlementActivity.TERM_MONTH)) {
                mTxtPayTermType.setText(String.format("已开启 月结，每月%s号", bean.getPayTerm()));
            }
        } else {
            mTxtPayTermType.setText("未开启");
        }
    }

    @Override
    public void showPayList() {
        showButton(mBean);
    }

    @Override
    public void editSuccess() {
        showToast("支付方式修改成功");
        mPresenter.start();
    }

    @Override
    public void setDefaultPayMethod(List<PayBean> list) {
        mData = list;
    }

}
