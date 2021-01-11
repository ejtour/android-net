package com.hll_sc_app.app.order.place.commit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.order.list.CrmOrderListActivity;
import com.hll_sc_app.app.submit.BackType;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.place.OrderCommitBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */
@Route(path = RouterConfig.ORDER_PLACE_COMMIT)
public class PlaceOrderCommitActivity extends BaseLoadActivity implements IPlaceOrderCommitContract.IPlaceOrderCommitView {

    @BindView(R.id.opc_view_order)
    TextView mViewOrder;
    @BindView(R.id.opc_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mID;
    private BackType mBackType;
    private PlaceOrderCommitAdapter mAdapter;

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_COMMIT, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place_commit);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mBackType = ((MyApplication) getApplication()).getLastBackType();
        mAdapter = new PlaceOrderCommitAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        IPlaceOrderCommitContract.IPlaceOrderCommitPresenter presenter = PlaceOrderCommitPresenter.newInstance(mID);
        presenter.register(this);
        presenter.start();
    }

    @Override
    @OnClick(R.id.opc_return_list)
    public void onBackPressed() {
        ARouter.getInstance()
                .build(mBackType.getPath())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .setProvider(new LoginInterceptor())
                .navigation(this);
    }

    @OnClick(R.id.opc_view_order)
    public void viewOrder(View view) {
        if (view.getTag() instanceof OrderCommitBean) {
            OrderCommitBean bean = (OrderCommitBean) view.getTag();
            CrmOrderListActivity.start(bean.getShopID(), bean.getShopName());
        }
    }

    @Override
    public void handleCommitResp(OrderCommitBean bean) {
        mViewOrder.setTag(bean);
        mAdapter.setNewData(bean.getList());
    }
}
