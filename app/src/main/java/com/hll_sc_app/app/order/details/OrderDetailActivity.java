package com.hll_sc_app.app.order.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.OrderDetailFooter;
import com.hll_sc_app.widget.order.OrderDetailHeader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */
@Route(path = RouterConfig.ROOT_ORDER_DETAIL)
public class OrderDetailActivity extends BaseLoadActivity implements IOrderDetailContract.IOrderDetailView {

    public static void start(String billID) {
        RouterUtil.goToActivity(RouterConfig.ROOT_ORDER_DETAIL, billID);
    }

    @BindView(R.id.aod_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aod_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0", required = true)
    String mBillID;
    private IOrderDetailContract.IOrderDetailPresenter mPresenter;
    private OrderDetailHeader mDetailHeader;
    private OrderDetailFooter mDetailFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = OrderDetailPresenter.newInstance(mBillID);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        OrderDetailAdapter adapter = new OrderDetailAdapter();
        mDetailHeader = new OrderDetailHeader(this);
        adapter.addHeaderView(mDetailHeader);
        mDetailFooter = new OrderDetailFooter(this);
        adapter.addFooterView(mDetailFooter);
        mListView.setAdapter(adapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    @Override
    public void updateOrderData(OrderResp resp) {
        mDetailHeader.setData(resp);
        mDetailFooter.setData(resp);
        ((OrderDetailAdapter) mListView.getAdapter()).setNewData(resp.getBillDetailList(),
                resp.getSubbillCategory() == 2);
    }
}
