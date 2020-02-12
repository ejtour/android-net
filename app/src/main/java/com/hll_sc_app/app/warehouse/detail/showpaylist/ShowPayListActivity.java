package com.hll_sc_app.app.warehouse.detail.showpaylist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.paymanage.PayBean;
import com.hll_sc_app.bean.paymanage.PayResp;
import com.hll_sc_app.bean.warehouse.ShopParameterBean;
import com.hll_sc_app.bean.warehouse.WarehouseSettlementBean;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 里面过滤2次
 * 支付方式展示
 */
@Route(path = RouterConfig.ACTIVITY_WAREHOUSE_PAY_LIST)
public class ShowPayListActivity extends BaseLoadActivity implements IShowPayListContract.IView {
    public static final String[] STRINGS_WEEK = {"每周日", "每周一", "每周二", "每周三", "每周四", "每周五", "每周六"};
    @Autowired(name = "bean")
    ShopParameterBean mBean;
    @Autowired(name = "wareBean")
    WarehouseSettlementBean mWareBean;
    @Autowired(name = "groupId")
    String mGroupId;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.header_bar)
    TitleBar mHeader;
    private Unbinder unbinder;
    private IShowPayListContract.IPresent mPresent;

    //货主收款进入
    public static void start(String groupId, ShopParameterBean status) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_WAREHOUSE_PAY_LIST)
                .withString("groupId", groupId)
                .withParcelable("bean", status)
                .navigation();
    }

    //代仓收款进入
    public static void start(String groupId, WarehouseSettlementBean warehouseSettlementBean) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_WAREHOUSE_PAY_LIST)
                .withString("groupId", groupId)
                .withParcelable("wareBean", warehouseSettlementBean)
                .navigation();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_pay_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mPresent = ShowPayListPresenter.newInstance();
        mPresent.register(this);
        mPresent.getAllPayList();
    }

    private class PayListAdpter extends BaseQuickAdapter<PayBean, BaseViewHolder> {
        public PayListAdpter(@Nullable List<PayBean> data) {
            super(R.layout.list_item_warehouse_pay, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayBean item) {
            boolean isTitle = TextUtils.isEmpty(item.getId());
            helper.getView(R.id.cst_container).setBackgroundColor(Color.parseColor(isTitle ? "#F3F3F3" : "#ffffff"));
            helper.setVisible(R.id.txt_name, !isTitle)
                    .setVisible(R.id.view_divider, !isTitle)
                    .setVisible(R.id.txt_title, isTitle)
                    .setGone(R.id.img_name, !TextUtils.isEmpty(item.getImgPath()))
                    .setText(R.id.txt_title, item.getPayMethodName())
                    .setText(R.id.txt_name, item.getPayMethodName());

            ((GlideImageView) helper.getView(R.id.img_name)).setImageURL(item.getImgPath());
        }
    }

    @Override
    public boolean isOwner() {
        return mBean != null;
    }

    @Override
    public void showPayList(PayResp payResp, SettlementBean settlementBean) {
        PayListAdpter adpter = new PayListAdpter(parsePayList(payResp, settlementBean));
        mListView.setAdapter(adpter);
    }

    /**
     * @return
     */
    private List<PayBean> parsePayList(PayResp allPayList, SettlementBean settlementBean) {
        List<String> onlinePays = new ArrayList<>(Arrays.asList(settlementBean.getOnlinePayMethod().split(",")));
        List<String> cashPays = new ArrayList<>(Arrays.asList(settlementBean.getCodPayMethod().split(",")));
        List<PayBean> usedOnlinePayList = new ArrayList<>();
        List<PayBean> usedCashPayList = new ArrayList<>();
        List<PayBean> usedAccountPayList = new ArrayList<>();
        for (PayBean bean : allPayList.getRecords()) {
            if (isHasOnline() && TextUtils.equals(bean.getPayType(), "1") && onlinePays.contains(bean.getId())) {//在线
                if (usedOnlinePayList.size() == 0) {
                    PayBean title = new PayBean();
                    title.setPayMethodName("在线支付");
                    usedOnlinePayList.add(title);
                }
                usedOnlinePayList.add(bean);
            } else if (isHasCash() && TextUtils.equals(bean.getPayType(), "2") && cashPays.contains(bean.getId())) {//货到
                if (usedCashPayList.size() == 0) {
                    PayBean title = new PayBean();
                    title.setPayMethodName("货到付款");
                    usedCashPayList.add(title);
                }
                usedCashPayList.add(bean);
            }
        }
        //代仓支付,最后增加账期支付
        if (isHasAccount()) {
            PayBean title = new PayBean();
            PayBean content = new PayBean();
            content.setId("account");
            title.setPayMethodName("账期支付");
            if (isOwner()) {
                content.setPayMethodName(String.format("账期日：%s；对账单产生后 %s 日后为结算日",
                        getPayTermString(mBean.getPayTermType(), mBean.getPayTerm()),
                        mBean.getSettleDate()
                ));
            } else {
                content.setPayMethodName(String.format("账期日：%s；对账单产生后 %s 日后为结算日",
                        getPayTermString(mWareBean.getAccountPeriodType(), mWareBean.getAccountPeriod()),
                        mWareBean.getSettleDate()
                ));
            }
            usedAccountPayList.add(title);
            usedAccountPayList.add(content);
        }

        usedOnlinePayList.addAll(usedCashPayList);
        usedOnlinePayList.addAll(usedAccountPayList);
        return usedOnlinePayList;
    }

    /**
     * 是否有线上支付
     */
    private boolean isHasOnline() {
        if (isOwner()) {
            return mBean.getPayType().contains("1");
        } else {
            return mWareBean.getSettlementWay().contains("3");
        }
    }

    /**
     * 是否含有货到付款
     *
     * @return
     */
    private boolean isHasCash() {
        if (isOwner()) {
            return mBean.getPayType().contains("3");
        } else {
            return mWareBean.getSettlementWay().contains("1");//含有货到付款
        }
    }

    private String getPayTermString(String payTermType, String payTerm) {
        if (TextUtils.equals(payTermType, "1")) {
            return String.format("周结,%s", getPayTermStr(Integer.parseInt(payTerm)));
        } else if (TextUtils.equals(payTermType, "2")) {
            return String.format("月结，每月%s号", getPayTermStr(Integer.parseInt(payTerm)));
        }
        return "";
    }


    public static String getPayTermStr(int payTerm) {
        return payTerm > STRINGS_WEEK.length ? "" : STRINGS_WEEK[payTerm];
    }

    @Override
    public String getGroupId() {
        return mGroupId;
    }

    private boolean isHasAccount() {
        if (isOwner()) {
            return mBean.getPayType().contains("2");
        } else {
            return mWareBean.getSettlementWay().contains("2");//含有账期支付
        }
    }


}
