package com.hll_sc_app.app.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.WarehouseSearch;
import com.hll_sc_app.app.warehouse.recommend.WarehouseGroupListAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.event.GoodsRelevanceSearchEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓公司列表
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
@Route(path = RouterConfig.WAREHOUSE_LIST, extras = Constant.LOGIN_EXTRA)
public class WarehouseListActivity extends BaseLoadActivity implements WarehouseListContract.IWarehouseListView,
    BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private EmptyView mEmptyView;
    private EmptyView mSearchEmptyView;
    private WarehouseListPresenter mPresenter;
    private WarehouseGroupListAdapter mAdapter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = WarehouseListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSearchEmptyView = EmptyView.newBuilder(this).setTips("搜索不到代仓公司数据").create();
        mEmptyView = EmptyView.newBuilder(this)
            .setTipsTitle("恭喜您已成功开启代仓业务！")
            .setTips("当前还没有代仓业务客户")
            .setTipsButton("立即添加客户")
            .setOnClickListener(new EmptyView.OnActionClickListener() {
                @Override
                public void retry() {
                    // no-op
                }

                @Override
                public void action() {
                    toAdd();
                }
            })
            .create();

        mSearchView.setBackgroundResource(R.drawable.base_bg_shadow_top_white_bar);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, WarehouseSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryWarehouseList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreWarehouseList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryWarehouseList(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new WarehouseGroupListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if (view.getId() == R.id.txt_del) {
                    showDelTipsDialog(bean);
                } else if (view.getId() == R.id.content) {
                    RouterUtil.goToActivity(RouterConfig.WAREHOUSE_DETAIL, bean.getGroupID());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    /**
     * 新增代仓客户
     */
    private void toAdd() {
        RouterUtil.goToActivity(RouterConfig.WAREHOUSE_ADD);
    }

    /**
     * 删除关系提示框
     *
     * @param bean 代仓集团
     */
    private void showDelTipsDialog(PurchaserBean bean) {
        TipsDialog.newBuilder(this)
            .setTitle("删除合作")
            .setMessage("确定要删除和【" + bean.getGroupName() + "】的代仓关系嘛？")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.delWarehouseList(bean.getGroupID());
                } else {
                    SwipeItemLayout.closeAllItems(mRecyclerView);
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
    }

    @Subscribe
    public void onEvent(GoodsRelevanceSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showWarehouseList(List<PurchaserBean> list, boolean append, int totalNum) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mSearchView.isSearchStatus() ? mSearchEmptyView : mEmptyView);
        mRefreshLayout.setEnableLoadMore(totalNum != mAdapter.getItemCount());
    }

    @OnClick({R.id.img_close, R.id.txt_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_options:
                showOptionsWindow(view);
                break;
            default:
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_warehouse_add, OptionType.OPTION_WAREHOUSE_ADD));
            list.add(new OptionsBean(R.drawable.ic_cooperation_receive, OptionType.OPTION_COOPERATION_RECEIVE));
            list.add(new OptionsBean(R.drawable.ic_cooperation_send, OptionType.OPTION_COOPERATION_SEND));
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_WAREHOUSE_ADD)) {
            toAdd();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_RECEIVE)) {
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_SEND)) {
        }
        mOptionsWindow.dismiss();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }
}
