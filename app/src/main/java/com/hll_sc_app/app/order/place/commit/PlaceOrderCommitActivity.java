package com.hll_sc_app.app.order.place.commit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.place.OrderCommitBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */
@Route(path = RouterConfig.ORDER_PLACE_COMMIT)
public class PlaceOrderCommitActivity extends BaseLoadActivity implements IPlaceOrderCommitContract.IPlaceOrderCommitView {

    @BindView(R.id.opc_shop_name)
    TextView mShopName;
    @BindView(R.id.opc_pay_method)
    TextView mPayMethod;
    @BindView(R.id.opc_order_no)
    TextView mOrderNo;
    @BindView(R.id.opc_amount)
    TextView mAmount;
    @BindView(R.id.opc_view_order)
    TextView mViewOrder;
    @BindView(R.id.opc_self_lift_tag)
    TextView mSelfLiftTag;
    @Autowired(name = "object0")
    String mID;

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_COMMIT, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_place_commit);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        IPlaceOrderCommitContract.IPlaceOrderCommitPresenter presenter = PlaceOrderCommitPresenter.newInstance(mID);
        presenter.register(this);
        presenter.start();
    }

    @Override
    @OnClick(R.id.opc_return_list)
    public void onBackPressed() {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME, this);
    }

    @OnClick(R.id.opc_view_order)
    public void viewOrder(View view) {
        if (view.getTag() != null)
            OrderDetailActivity.start(view.getTag().toString());
    }

    @Override
    public void handleCommitResp(OrderCommitBean bean) {
        OrderCommitBean.SubBillBean subBillBean = bean.getList().get(0);
        mPayMethod.setText(OrderHelper.getPayType(subBillBean.getPayType()));
        mSelfLiftTag.setVisibility(subBillBean.getDeliverType() == 2 ? View.VISIBLE : View.GONE);
        mOrderNo.setText(subBillBean.getSubBillNo());
        mViewOrder.setTag(subBillBean.getSubBillID());
        mAmount.setText(String.format("¥%s", CommonUtils.formatMoney(subBillBean.getTotalAmount())));
        mShopName.setText(subBillBean.getSupplyShopName());
    }
}
