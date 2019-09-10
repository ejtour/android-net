package com.hll_sc_app.app.complainmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.adapter.ComplainListApdater;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

public class ComplainListFragment extends BaseLazyFragment implements IComplainManageContract.IView {
    private final static String BUNDLE_STATUS_NAME = "status";

    private int mStatus = -1;

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private ComplainListApdater mAdapter;
    private IComplainManageContract.IPresent mPresent;

    public static ComplainListFragment newInstance(int status) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_STATUS_NAME, status);
        ComplainListFragment fragment = new ComplainListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mStatus = args.getInt(BUNDLE_STATUS_NAME);
        }
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        mAdapter = new ComplainListApdater(null);
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
    public int getComplaintStatus() {
        return mStatus;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }
}
