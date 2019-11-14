package com.hll_sc_app.app.complainmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.adapter.ComplainListAdapter;
import com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.bean.event.ComplainManageEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ComplainListFragment extends BaseLazyFragment implements IComplainManageContract.IView {
    private final static String BUNDLE_STATUS_NAME = "status";
    @Autowired(name = BUNDLE_STATUS_NAME)
    String mStatus;

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private ComplainListAdapter mAdapter;
    private IComplainManageContract.IPresent mPresent;

    public static ComplainListFragment newInstance(String status) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_STATUS_NAME, status);
        ComplainListFragment fragment = new ComplainListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_complain_manage_list, null);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mPresent = ComplainManagePresent.newInstance();
        mPresent.register(this);
        return rootView;
    }

    @Override
    protected void initData() {
        mPresent.queryComplainList(true);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ComplainListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ComplainListResp.ComplainListBean complainListBean = mAdapter.getItem(position);
            if (complainListBean == null) {
                return;
            }
            if (mAdapter.isCheckModel()) {
              mAdapter.updateSelect(complainListBean);
            } else {
                ComplainMangeDetailActivity.start(complainListBean.getId(), ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void queryListSuccess(ComplainListResp resp, boolean isMore) {
        if (isMore && !CommonUtils.isEmpty(resp.getList())) {
            mAdapter.addData(resp.getList());
        } else if (!isMore) {
            mAdapter.setEmptyView(EmptyView.newBuilder(getActivity())
                    .setImage(R.drawable.ic_dialog_good)
                    .setTipsTitle("真棒！您没有收到任何投诉")
                    .setTips("您优秀的经营管理带来了客户一致的好评\n要再接再厉哟～")
                    .create());
            mAdapter.setNewData(resp.getList());
        }
        if (!CommonUtils.isEmpty(resp.getList())) {
            mRefreshLayout.setEnableLoadMore(resp.getList().size() == mPresent.getPageSize());
        }
    }

    @Override
    public String getComplaintStatus() {
        return mStatus;
    }

    @Override
    public void showCheckBox(boolean isCheck) {
        mAdapter.checkModal(isCheck);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEvent(ComplainManageEvent complainManageEvent) {
        if (complainManageEvent.getTarget() == ComplainManageEvent.TARGET.LIST) {
            if (complainManageEvent.getEvent() == ComplainManageEvent.EVENT.REFRESH) {
                mPresent.refresh();
            }
        }
    }
}
