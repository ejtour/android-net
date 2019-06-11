package com.hll_sc_app.app.order.deliver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */
@Route(path = RouterConfig.ROOT_HOME_ORDER_DELIVER)
public class DeliverInfoActivity extends BaseLoadActivity implements IDeliverInfoContract.IDeliverInfoView {

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_ORDER_DELIVER);
    }

    @BindView(R.id.odi_list_view)
    RecyclerView mListView;
    private DeliverInfoAdapter mAdapter;
    private int mCurPos;
    private IDeliverInfoContract.IDeliverInfoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_deliver_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = DeliverInfoPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new DeliverInfoAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurPos = position;
            DeliverInfoResp resp = mAdapter.getItem(position);
            if (resp == null) {
                return;
            }
            if (CommonUtils.isEmpty(resp.getList())) {
                mPresenter.requestShopList(resp.getProductSpecID());
            } else {
                resp.setExpanded(!resp.isExpanded());
                mAdapter.notifyItemChanged(position);
            }
        });
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(80), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void updateShopList(List<DeliverShopResp> list) {
        DeliverInfoResp resp = mAdapter.getItem(mCurPos);
        if (resp == null) {
            return;
        }
        resp.setList(list);
        resp.setExpanded(true);
        mAdapter.notifyItemChanged(mCurPos);
    }

    @Override
    public void updateInfoList(List<DeliverInfoResp> list) {
        mAdapter.setNewData(list);
    }
}
