package com.hll_sc_app.app.rank.org;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.event.RankEvent;
import com.hll_sc_app.bean.rank.OrgRankBean;
import com.hll_sc_app.bean.rank.RankParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class OrgRankFragment extends BaseLazyFragment implements IOrgRankContract.IOrgRankView {

    @Autowired(name = "object")
    int mType;
    @BindView(R.id.for_name)
    TextView mName;
    @BindView(R.id.for_list_view)
    RecyclerView mListView;
    @BindView(R.id.for_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private RankParam mRankParam;
    private IOrgRankContract.IOrgRankPresenter mPresenter;
    private EmptyView mEmptyView;
    private OrgRankAdapter mAdapter;

    /**
     * @param type 1 集团，2门店
     */
    public static OrgRankFragment newInstance(int type, RankParam param) {
        Bundle args = new Bundle();
        args.putInt("object", type);
        OrgRankFragment fragment = new OrgRankFragment();
        fragment.setArguments(args);
        fragment.mRankParam = param;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = OrgRankPresenter.newInstance(mType == 2, mRankParam);
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_org_rank, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mName.setText(mType == 1 ? "集团名称" : "门店名称");
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new OrgRankAdapter();
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
        mAdapter = null;
        mEmptyView = null;
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<OrgRankBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有排名数据");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Subscribe
    public void handleRankEvent(RankEvent event) {
        setForceLoad(true);
        lazyLoad();
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
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
