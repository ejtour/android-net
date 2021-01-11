package com.hll_sc_app.app.order.place.confirm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.place.commit.PlaceOrderCommitActivity;
import com.hll_sc_app.app.order.place.confirm.details.PlaceOrderDetailsActivity;
import com.hll_sc_app.app.order.place.confirm.remark.OrderConfirmRemarkActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.place.DiscountPlanBean;
import com.hll_sc_app.bean.order.place.ExecuteDateBean;
import com.hll_sc_app.bean.order.place.OrderCommitReq;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;
import com.hll_sc_app.bean.order.place.SupplierGroupBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.ExecuteDateDialog;
import com.hll_sc_app.widget.order.PayMethodDialog;
import com.hll_sc_app.widget.order.PlaceOrderConfirmHeader;
import com.hll_sc_app.widget.order.ShopDiscountDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/16
 */
@Route(path = RouterConfig.ORDER_PLACE_CONFIRM)
public class PlaceOrderConfirmActivity extends BaseLoadActivity implements IPlaceOrderConfirmContract.IPlaceOrderConfirmView, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.opc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.opc_list_view)
    RecyclerView mListView;
    @BindView(R.id.opc_total)
    TextView mTotal;
    @Autowired(name = "parcelable", required = true)
    SettlementInfoResp mResp;
    private PlaceOrderConfirmAdapter mAdapter;
    private OrderCommitReq mConfirmReq = new OrderCommitReq();
    private SupplierGroupBean mSupplierBean;
    private ShopDiscountDialog mDiscountDialog;
    private PayMethodDialog mPayMethodDialog;
    private ExecuteDateDialog mDateDialog;
    private IPlaceOrderConfirmContract.IPlaceOrderConfirmPresenter mPresenter;

    public static void start(SettlementInfoResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_CONFIRM, resp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place_confirm);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrderConfirmRemarkActivity.REQ_CODE && resultCode == RESULT_OK && data != null) {
            updateRemark(data.getStringExtra("remark"));
        }
    }

    private void initData() {
        mConfirmReq.setShopCartKey(mResp.getShopCartKey());
        mConfirmReq.setIsFromShopcart(3);
        mConfirmReq.setPurchaserID(mResp.getPurchaserID());
        mConfirmReq.setPurchaserShopID(mResp.getPurchaserShopID());
        mPresenter = PlaceOrderConfirmPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mAdapter = new PlaceOrderConfirmAdapter(mResp.getSupplierGroupList(),
                (int) (((float) UIUtils.getScreenWidth(this) - UIUtils.dip2px(60)) / 5));
        PlaceOrderConfirmHeader header = new PlaceOrderConfirmHeader(this);
        header.setData(mResp);
        mAdapter.setHeaderView(header);
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mTitleBar.setRightBtnClick(v -> RouterUtil.goToActivity(RouterConfig.ROOT_HOME));
        updateTotalAmount();
        mAdapter.setOnItemChildClickListener(this);
    }

    private void updatePayMethod(int payType) {
        if (mSupplierBean != null) {
            mSupplierBean.setPayType(payType);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mSupplierBean) + 1);
        }
    }

    private void updateDiscount(DiscountPlanBean.DiscountBean discountBean) {
        if (mSupplierBean != null) {
            mSupplierBean.setDiscountBean(discountBean);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mSupplierBean) + 1);
            updateTotalAmount();
        }
    }

    private void updateTotalAmount() {
        List<SupplierGroupBean> list = mAdapter.getData();
        double totalAmount = 0;
        double depositAmount = 0;
        if (!CommonUtils.isEmpty(list)) {
            for (SupplierGroupBean bean : list) {
                totalAmount = CommonUtils.addDouble(totalAmount, bean.getSubTotalAmount(), 0).doubleValue();
                depositAmount = CommonUtils.addDouble(depositAmount, bean.getDepositAmount(), 0).doubleValue();
            }
        }
        mTotal.setText(ConfirmHelper.getAmount(this, totalAmount, depositAmount, 2));
    }

    private void updateExecuteDate(String startDate, String endDate) {
        if (mSupplierBean != null) {
            mSupplierBean.setStartDate(startDate);
            mSupplierBean.setEndDate(endDate);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mSupplierBean) + 1);
        }
    }

    private void updateRemark(String remark) {
        if (mSupplierBean != null) {
            mSupplierBean.setRemark(remark);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mSupplierBean) + 1);
        }
    }

    @OnClick(R.id.opc_commit)
    public void commit() {
        List<OrderCommitReq.RemarkBean> remarkList = new ArrayList<>();
        List<OrderCommitReq.ExecuteDateBean> dateList = new ArrayList<>();
        List<OrderCommitReq.DiscountBean> discountList = new ArrayList<>();
        List<OrderCommitReq.PayBean> payList = new ArrayList<>();
        for (SupplierGroupBean bean : mAdapter.getData()) {
            if (bean.enablePay() && bean.getPayType() == 0) {
                showToast("请选择付款方式");
                return;
            }
            if (TextUtils.isEmpty(bean.getStartDate()) || TextUtils.isEmpty(bean.getEndDate())) {
                showToast(bean.getDeliverType() == 2 ? "请选择要求提货日期" : "请选择要求到货日期");
                return;
            }
            if (!TextUtils.isEmpty(bean.getRemark())) {
                OrderCommitReq.RemarkBean remark = new OrderCommitReq.RemarkBean();
                remark.setIsWareHouse(bean.getWareHourseStatus());
                remark.setSupplyShopID(bean.getSupplierShopID());
                remark.setPurchaserShopID(mResp.getPurchaserShopID());
                remark.setRemark(bean.getRemark());
                remarkList.add(remark);
            }

            OrderCommitReq.ExecuteDateBean date = new OrderCommitReq.ExecuteDateBean();
            date.setSubBillExecuteDate(bean.getStartDate());
            date.setSubBillExecuteEndDate(bean.getEndDate());
            date.setIsWareHouse(bean.getWareHourseStatus());
            date.setSupplierID(bean.getSupplierID());
            dateList.add(date);

            DiscountPlanBean.DiscountBean discountBean = bean.getDiscountBean();
            if (discountBean != null && discountBean.getRuleType() != 0) {
                OrderCommitReq.DiscountBean discount = new OrderCommitReq.DiscountBean();
                discount.setGroupID(bean.getSupplierID());
                discount.setDiscountAmount(discountBean.getDiscountValue());
                discount.setDiscountID(discountBean.getDiscountID());
                discount.setRuleID(discountBean.getRuleID());
                discount.setSpecList(discountBean.getSpecList());
                discountList.add(discount);
            }

            OrderCommitReq.PayBean pay = new OrderCommitReq.PayBean();
            pay.setSupplierID(bean.getSupplierID());
            pay.setWareHourseStatus(bean.getWareHourseStatus());
            pay.setPayType(bean.getPayType());
            payList.add(pay);
        }
        mConfirmReq.setRemarkDtoList(remarkList.size() > 0 ? remarkList : null);
        mConfirmReq.setExecuteDateDtoList(dateList);
        mConfirmReq.setDiscountList(discountList.size() > 0 ? discountList : null);
        mConfirmReq.setPayList(payList);
        mPresenter.commitOrder(mConfirmReq);
    }

    @OnClick(R.id.opc_edit)
    public void edit(View view) {
        finish();
    }

    public void produceList() {
        if (mSupplierBean != null)
            PlaceOrderDetailsActivity.start(mSupplierBean);
    }

    public void showDiscountDialog(View view) {
        if (mDiscountDialog == null) {
            mDiscountDialog = new ShopDiscountDialog(this)
                    .refreshList(mSupplierBean.getDiscountPlan().getShopDiscounts())
                    .select((DiscountPlanBean.DiscountBean) view.getTag())
                    .setOnItemClickListener((adapter, view1, position) -> {
                        mDiscountDialog.dismiss();
                        DiscountPlanBean.DiscountBean item = (DiscountPlanBean.DiscountBean) adapter.getItem(position);
                        if (item == null) return;
                        mDiscountDialog.select(item);
                        updateDiscount(item);
                    });
        }
        mDiscountDialog.show();
    }

    public void selectPayMethod() {
        SupplierGroupBean.PaymentBean payment = mSupplierBean.getPayment();
        if (payment == null) {
            showToast("没有可用的支付方式");
            return;
        }
        if (mPayMethodDialog == null) {
            List<PayMethodDialog.PayMethod> list = new ArrayList<>();
            PayMethodDialog.PayMethod select = null;
            if (payment.getOnlinePayment() == 1)
                list.add(new PayMethodDialog.PayMethod("在线支付", "3", R.drawable.ic_pay_online));
            if (payment.getCashPayment() == 1)
                list.add(new PayMethodDialog.PayMethod("货到付款", "1", R.drawable.ic_pay_cash));
            if (payment.getAccountPayment() == 1)
                list.add(new PayMethodDialog.PayMethod("账期支付", "2", R.drawable.ic_pay_account));
            if (list.size() == 0) {
                showToast("没有可用的支付方式");
                return;
            }
            for (PayMethodDialog.PayMethod value : list) {
                if (value.getValue().equals(String.valueOf(mSupplierBean.getPayType()))) {
                    select = value;
                    break;
                }
            }
            mPayMethodDialog = new PayMethodDialog(this)
                    .refreshList(list)
                    .select(select)
                    .setListener(nameValue -> updatePayMethod(Integer.parseInt(nameValue.getValue())));
        }
        mPayMethodDialog.show();
    }

    public void showDateDialog() {
        if (mDateDialog == null) {
            Map<String, List<String>> map = mSupplierBean.getMap();
            if (map.isEmpty()) {
                return;
            }
            mDateDialog = new ExecuteDateDialog(this, mSupplierBean.getDeliverType() == 2 ? "请选择要求提货日期" : "请选择要求到货日期", map);
            mDateDialog.setDayTimeCallback((day, time) -> {
                String dayStr = mSupplierBean.getDayList().get(day);
                ExecuteDateBean.FirstDay firstDay = mSupplierBean.getExecuteDateList().getFirstDay();
                ExecuteDateBean.TimeBean timeBean;
                if (firstDay != null && dayStr.equals(firstDay.getDate())) {
                    timeBean = firstDay.getFirstTimeList().get(time);
                } else {
                    timeBean = mSupplierBean.getExecuteDateList().getTimeList().get(time);
                }
                updateExecuteDate(dayStr + timeBean.getArrivalStartTime().replace(":", ""),
                        dayStr + timeBean.getArrivalEndTime().replace(":", ""));
            });
        }
        mDateDialog.show();
    }

    public void editRemark() {
        OrderConfirmRemarkActivity.start(this, mSupplierBean.getSupplierShopName(), mSupplierBean.getRemark());
    }

    @Override
    public void commitSuccess(String masterBillIDs) {
        PlaceOrderCommitActivity.start(masterBillIDs);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        SupplierGroupBean bean = mAdapter.getItem(position);
        if (mSupplierBean != bean) {
            mDateDialog = null;
            mPayMethodDialog = null;
            mDiscountDialog = null;
        }
        mSupplierBean = bean;
        if (mSupplierBean == null) return;
        switch (view.getId()) {
            case R.id.opc_goods_num:
                produceList();
                break;
            case R.id.opc_discount:
                showDiscountDialog(view);
                break;
            case R.id.opc_pay_method:
                selectPayMethod();
                break;
            case R.id.opc_request_date:
                showDateDialog();
                break;
            case R.id.opc_remark:
                editRemark();
                break;
        }
    }
}
