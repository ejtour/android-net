package com.hll_sc_app.app.cooperation.detail.shopsaleman;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopadd.CooperationSelectShopActivity;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.EmployeeBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情- 批量指派销售
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SALES, extras = Constant.LOGIN_EXTRA)
public class CooperationShopSalesActivity extends BaseLoadActivity implements CooperationShopSalesContract.ICooperationAddShopView {
    @Autowired(name = "parcelable", required = true)
    ShopSettlementReq mReq;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    private EmptyView mEmptyView;
    private CooperationShopSalesPresenter mPresenter;
    private EmployeeListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_sales);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationShopSalesPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtTitle.setText(isSales() ? "选择销售" : "选择司机");
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserShopList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserShopList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
//        mAdapter = new CooperationDetailActivity.PurchaserShopListAdapter();

        mEmptyView = EmptyView.newBuilder(this).setTips(isSales() ? "您还没有销售人员数据" : "您还没有司机人员数据").create();
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_SEARCH);
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPurchaserShopList(true);
            }
        });
    }

    private boolean isSales() {
        return TextUtils.equals(mReq.getActionType(), CooperationSelectShopActivity.TYPE_SALESMAN);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showEmployeeList(List<EmployeeBean> list, boolean append) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getRoleType() {
        return isSales() ? "1" : "2";
    }

    public static class EmployeeListAdapter extends BaseQuickAdapter<EmployeeBean, BaseViewHolder> {

        EmployeeListAdapter() {
            super(R.layout.item_cooperation_purchaser);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, EmployeeBean item) {

        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
