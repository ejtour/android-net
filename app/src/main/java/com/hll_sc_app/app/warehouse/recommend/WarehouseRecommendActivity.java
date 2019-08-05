package com.hll_sc_app.app.warehouse.recommend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓管理-我是货主默认介绍页面-查看推荐
 *
 * @author zhuyingsong
 * @date 2019/8/2
 */
@Route(path = RouterConfig.WAREHOUSE_RECOMMEND, extras = Constant.LOGIN_EXTRA)
public class WarehouseRecommendActivity extends BaseLoadActivity implements WarehouseRecommendContract.IWarehouseRecommendView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private EmptyView mEmptyView;
    private WarehouseGroupListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_recommend);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        WarehouseRecommendPresenter presenter = WarehouseRecommendPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mAdapter = new WarehouseGroupListAdapter(true);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("暂时还没有推荐的代仓公司").create();
        View headerView = LayoutInflater.from(this).inflate(R.layout.view_warehouse_recommend_header, mRecyclerView,
            false);
        mAdapter.addHeaderView(headerView);
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean != null) {
                // RouterUtil.goToActivity(RouterConfig.USER_WAREHOUSE_MANAGER_DETAIL, bean);
            }
        });
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showList(List<PurchaserBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }
}
