package com.hll_sc_app.app.warehouse.application;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.WarehouseSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.GoodsRelevanceSearchEvent;
import com.hll_sc_app.bean.event.RefreshWarehouseList;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
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


/**
 * 代仓公司-我收到的申请
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
@Route(path = RouterConfig.WAREHOUSE_APPLICATION, extras = Constant.LOGIN_EXTRA)
public class WarehouseApplicationActivity extends BaseLoadActivity implements WarehouseApplicationContract.IWarehouseApplicationView {
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private EmptyView mEmptyView;
    private EmptyView mSearchEmptyView;
    private WarehouseListAdapter mAdapter;
    private WarehouseApplicationPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_application);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = WarehouseApplicationPresenter.newInstance();
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
        mSearchEmptyView = EmptyView.newBuilder(this).setTips("搜索不到申请数据").create();
        mEmptyView = EmptyView.newBuilder(this).setTips("还没有收到申请数据").create();
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
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mAdapter = new WarehouseListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean == null || bean.getWarehouseActive() == 1) {
                return;
            }
            int id = view.getId();
            if (id == R.id.txt_status && TextUtils.equals(bean.getStatus(), "0")) {
                // 待同意-点击未同意操作
                agree(bean);

            } else {
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_DETAILS, bean.getGroupID(), "signApplication");
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void agree(PurchaserBean bean) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        if (UserConfig.isSelfOperated()) {
            builder
                .put("groupID", UserConfig.getGroupID())
                .put("originator", "1")
                .put("purchaserID", bean.getGroupID());
        } else {
            builder
                .put("groupID", bean.getGroupID())
                .put("originator", "0")
                .put("purchaserID", UserConfig.getGroupID())
                .put("warehouseType", "1");
        }
        builder.put("actionType", "agree");
        mPresenter.agreeWarehouse(builder.create());

    }

    @Subscribe
    public void onEvent(GoodsRelevanceSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Subscribe
    public void onEvent(RefreshWarehouseList event) {
        mPresenter.queryWarehouseList(true);
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

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    private static class WarehouseListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        WarehouseListAdapter() {
            super(R.layout.item_warehouse_application);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder baseViewHolder = super.onCreateDefViewHolder(parent, viewType);
            baseViewHolder.addOnClickListener(R.id.txt_status).addOnClickListener(R.id.content);
            return baseViewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_groupName, item.getGroupName())
                .setText(R.id.txt_linkman, "联系人：" +
                    getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
                .setText(R.id.txt_groupArea, "所在地区：" + item.getGroupArea());
            ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getLogoUrl());
            setStatus(helper, item);
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private void setStatus(BaseViewHolder helper, PurchaserBean item) {
            TextView txtStatus = helper.getView(R.id.txt_status);
            switch (item.getStatus()) {
                case "0":
                    // 待同意
                    txtStatus.setBackgroundResource(R.drawable.bg_button_mid_solid_primary);
                    txtStatus.setTextColor(0xFFFFFFFF);
                    txtStatus.setText(R.string.agree);
                    break;
                case "1":
                    // 未同意
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setTextColor(0xFF999999);
                    txtStatus.setText("已拒绝");
                    break;
                case "2":
                    // 已同意
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setTextColor(0xFF999999);
                    txtStatus.setText("已同意");
                    break;
                default:
                    break;
            }

            if (item.getWarehouseActive() == 1) {
                txtStatus.setText(txtStatus.getText().toString() + "\n已停止");
            }
        }
    }
}
