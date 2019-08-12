package com.hll_sc_app.app.orientationsale.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.event.RefreshOrientationList;
import com.hll_sc_app.bean.orientation.OrientationListBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.ORIENTATION_LIST, extras = Constant.LOGIN_EXTRA)
public class OrientationListActivity extends BaseLoadActivity implements IOrientationListContract.IOrientationListView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private IOrientationListContract.IOrientationListPresenter mPresenter;
    private OrientationListAdapter mAdapter;

    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = OrientationListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getOrientation(null);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getOrientation(1);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_activity_bg)
            , UIUtils.dip2px(10)));
        mAdapter = new OrientationListAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.txt_del) {
                    mPresenter.delOrientation(mAdapter.getItem(position).getId());
                } else {
                    OrientationListBean bean = mAdapter.getData().get(position);
                    RouterUtil.goToActivity(RouterConfig.ORIENTATION_DETAIL, bean);
                }
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有定向售卖商品哦").create();
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void initData() {
        mPresenter.getOrientation(1);
    }

    @Override
    public void setView(List<OrientationListBean> list, Integer pageNum) {
        if (pageNum != 1) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void delSuccess() {
        showToast("删除成功");
        initData();
    }

    @Subscribe
    public void onEvent(RefreshOrientationList event) {
        mPresenter.getOrientation(1);
    }

    @OnClick({R.id.img_close, R.id.txt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add:
                RouterUtil.goToActivity(RouterConfig.ORIENTATION_COOPERATION_PURCHASER);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean reload = intent.getBooleanExtra("reload", false);
        if (reload) {
            initData();
        }
    }
}
