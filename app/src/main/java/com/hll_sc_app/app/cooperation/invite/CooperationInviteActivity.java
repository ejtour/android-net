package com.hll_sc_app.app.cooperation.invite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CooperationSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.event.CooperationInviteSearchEvent;
import com.hll_sc_app.bean.goods.GoodsListReq;
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
 * 合作采购商-我发出的申请
 *
 * @author zhuyingsong
 * @date 2019/7/23
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_MY_APPLICATION, extras = Constant.LOGIN_EXTRA)
public class CooperationInviteActivity extends BaseLoadActivity implements CooperationInviteContract.ICooperationInviteView {
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private EmptyView mEmptyView;
    private PurchaserListAdapter mAdapter;
    private CooperationInvitePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_invite);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationInvitePresenter.newInstance();
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
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, CooperationSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPurchaserList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserList(false);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("还没有合作采购商数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new PurchaserListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, bean.getPurchaserID());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe
    public void onEvent(CooperationInviteSearchEvent event) {
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
    public void showPurchaserList(CooperationPurchaserResp resp, boolean append) {
        List<PurchaserBean> list = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到合作采购商数据");
        } else {
            mEmptyView.setTips("还没有合作采购商数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == GoodsListReq.PAGE_SIZE);
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    private static class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter() {
            super(R.layout.item_cooperation_purchaser_invite);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                    .setText(R.id.txt_linkMan,
                            getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getMobile())));
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
                    txtStatus.setTextColor(0xFFF6BB42);
                    txtStatus.setText("待同意");
                    break;
                case "1":
                    // 未同意
                    txtStatus.setTextColor(0xFFED5655);
                    txtStatus.setText("未同意");
                    break;
                case "2":
                    // 已同意
                    txtStatus.setTextColor(0xFFAEAEAE);
                    txtStatus.setText("已同意");
                    break;
                default:
                    break;
            }
        }
    }
}
