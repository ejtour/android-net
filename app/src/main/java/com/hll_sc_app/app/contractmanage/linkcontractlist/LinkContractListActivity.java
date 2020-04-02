package com.hll_sc_app.app.contractmanage.linkcontractlist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.ContractManageActivity;
import com.hll_sc_app.app.contractmanage.detail.ContractManageDetailActivity;
import com.hll_sc_app.app.contractmanage.search.ContractSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.event.ContractManageEvent;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_LOCAL_DATE;
import static com.hll_sc_app.utils.Constants.SEARCH_RESULT_CODE;

/**
 * 关联合同列表
 */
@Route(path = RouterConfig.ACTIVITY_LINK_CONTRACT_LIST)
public class LinkContractListActivity extends BaseLoadActivity implements ILinkContractListContract.IView {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    private Unbinder unbinder;
    private ILinkContractListContract.IPresent mPresent;

    ContractManageActivity.ContractListAdapter mAdpter;

    //todo:列表接口:还有传参吧？

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_link_list);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = LinkContractListPresent.newInstance();
        mPresent.register(this);
        mPresent.queryList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mAdpter = new ContractManageActivity.ContractListAdapter(null);
        mAdpter.setOnItemClickListener((adapter, view, position) -> {
            ContractListResp.ContractBean contractBean = mAdpter.getItem(position);
            ContractManageDetailActivity.start(contractBean);
        });
        mListView.setAdapter(mAdpter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.queryMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        mAdpter.setEmptyView(EmptyView.newBuilder(this).setNetError(true)
                .setOnClickListener(new EmptyView.OnActionClickListener() {
                    @Override
                    public void retry() {
                        mPresent.refresh();
                    }
                }).create());

    }

    @Override
    public void querySuccess(ContractListResp resp, boolean isMore) {
        if (isMore) {
            mAdpter.addData(resp.getList());
        } else {
            if (CommonUtils.isEmpty(resp.getList())) {
                mAdpter.setNewData(null);
                mAdpter.setEmptyView( EmptyView.newBuilder(this).setTipsTitle("当前没有关联合同").create());
            } else {
                mAdpter.setNewData(resp.getList());
            }
        }
        if (CommonUtils.isEmpty(resp.getList())) {
            mRefreshLayout.setEnableLoadMore(resp.getList().size() == mPresent.getPageSize());
        }
    }

}