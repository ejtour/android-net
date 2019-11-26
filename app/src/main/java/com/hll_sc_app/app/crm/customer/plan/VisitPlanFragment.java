package com.hll_sc_app.app.crm.customer.plan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.plan.detail.VisitPlanDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/23
 */

public class VisitPlanFragment extends BaseLazyFragment implements IVisitPlanContract.IVisitPlanView {

    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "object")
    boolean mIsAll;
    Unbinder unbinder;
    private IVisitPlanContract.IVisitPlanPresenter mPresenter;
    private VisitPlanAdapter mAdapter;
    private EmptyView mEmptyView;
    private int mCurPos;

    public static VisitPlanFragment newInstance(boolean isAll) {
        Bundle args = new Bundle();
        args.putBoolean("object", isAll);
        VisitPlanFragment fragment = new VisitPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = VisitPlanPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_simple_refresh_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new VisitPlanAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurPos = position;
            if (view.getId() == R.id.cvp_del)
                showTipDialog();
            else if (view.getId() == R.id.cvp_root) {
                VisitPlanDetailActivity.start(requireActivity(), mAdapter.getItem(position));
            }
        });
        if (!mIsAll)
            mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(requireContext()));
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), UIUtils.dip2px(1)));
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

    private void showTipDialog() {
        TipsDialog.newBuilder(requireActivity())
                .setTitle("删除拜访计划")
                .setMessage("您确定要删除该条拜访计划吗？")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        VisitPlanBean bean = mAdapter.getItem(mCurPos);
                        if (bean == null) return;
                        mPresenter.delPlan(bean.getId());
                    }
                }, "取消", "确定")
                .create().show();
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    void reload(boolean includeCurrent) {
        if (!includeCurrent && isFragmentVisible()) return;
        setForceLoad(true);
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<VisitPlanBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("您还没有拜访计划哦~");
            }
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void delSuccess() {
        showToast("删除计划成功");
        if (mAdapter.getData().size() > 1) {
            mAdapter.remove(mCurPos);
        } else {
            setData(null, false);
        }
        ((VisitPlanActivity) requireActivity()).reload(false);
    }

    @Override
    public String getSearchWords() {
        return ((VisitPlanActivity) requireActivity()).getSearchWords();
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public boolean isAll() {
        return mIsAll;
    }
}
