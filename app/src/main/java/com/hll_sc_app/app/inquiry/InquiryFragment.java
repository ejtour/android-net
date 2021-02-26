package com.hll_sc_app.app.inquiry;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.event.InquiryRefreshEvent;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */

public class InquiryFragment extends BaseLazyFragment implements IInquiryContract.IInquiryView {
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "status")
    int mStatus;
    private InquiryAdapter mAdapter;
    private InquiryPresenter mPresenter;
    private Unbinder unbinder;
    private EmptyView mEmptyView;

    public static InquiryFragment newInstance(int status) {
        Bundle args = new Bundle();
        args.putInt("status", status);
        InquiryFragment fragment = new InquiryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = InquiryPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_simple_refresh_list, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        int space = UIUtils.dip2px(10);
        mListView.setPadding(space, space, space, space);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mAdapter = new InquiryAdapter();
        mListView.setAdapter(mAdapter);
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
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        mEmptyView = null;
        mAdapter = null;
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<InquiryBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("暂无询价单");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
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

    @Override
    public int getStatus() {
        return mStatus;
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleRefreshEvent(InquiryRefreshEvent event) {
        setForceLoad(true);
        lazyLoad();
    }
}
