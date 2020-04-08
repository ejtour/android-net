package com.hll_sc_app.app.contractmanage.linkcontractlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.ContractManageActivity;
import com.hll_sc_app.app.contractmanage.detail.ContractManageDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    @Autowired(name = "contractID")
    String contractID;
    @Autowired(name = "extContractID")
    String extContractID;
    ContractManageActivity.ContractListAdapter mAdpter;

    public static void start(String contractID, String extContractID) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_LINK_CONTRACT_LIST)
                .withString("contractID", contractID)
                .withString("extContractID", extContractID)
                .navigation();
    }

    @Override
    public String getContractID() {
        return contractID;
    }

    @Override
    public String getExContractID() {
        return extContractID;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_link_list);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
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
                mAdpter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前没有关联合同").create());
            } else {
                mAdpter.setNewData(resp.getList());
            }
        }
        if (CommonUtils.isEmpty(resp.getList())) {
            mRefreshLayout.setEnableLoadMore(resp.getList().size() == mPresent.getPageSize());
        }
    }

}