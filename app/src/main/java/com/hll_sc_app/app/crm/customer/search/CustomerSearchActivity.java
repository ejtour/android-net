package com.hll_sc_app.app.crm.customer.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchTitleBar;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

@Route(path = RouterConfig.CRM_CUSTOMER_SEARCH)
public class CustomerSearchActivity extends BaseLoadActivity implements ICustomerSearchContract.ICustomerSearchView {
    private static final int REQ_CODE = 0x323;
    @BindView(R.id.ccs_title_bar)
    SearchTitleBar mTitleBar;
    @BindView(R.id.ccs_list_view)
    RecyclerView mListView;
    @BindView(R.id.ccs_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    int mType;
    @Autowired(name = "object2")
    String mSearchWords;
    private CustomerSearchAdapter mAdapter;
    private ICustomerSearchContract.ICustomerSearchPresenter mPresenter;
    private EmptyView mEmptyView;

    /**
     * @param id   已选id
     * @param type 0-意向客户 1-合作门店 2-合作集团 3-采购商集团
     */
    public static void start(Activity context, String id, int type) {
        Object[] args = {id, type};
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEARCH, context, REQ_CODE, args);
    }

    public static void start(Activity context, String searchWords) {
        Object[] args = {null, 3, searchWords};
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_customer_search);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CustomerSearchPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mTitleBar.setOnSearchListener(mPresenter::start);
    }

    private void initView() {
        mTitleBar.updateSearchWords(mSearchWords);
        mAdapter = new CustomerSearchAdapter(mID);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Parcelable item = mAdapter.getItem(position);
            if (item != null) {
                Intent intent = new Intent();
                intent.putExtra("parcelable", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void setData(List<? extends Parcelable> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("搜索不到相关客户");
            }
            mAdapter.setNewData(new ArrayList<>(list));
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == (mType == 3 ? 50 : 20));
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public String getSearchWords() {
        return mTitleBar.getSearchContent();
    }

    @Override
    public int getType() {
        return mType;
    }
}
