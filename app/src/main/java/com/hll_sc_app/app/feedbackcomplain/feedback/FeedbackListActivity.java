package com.hll_sc_app.app.feedbackcomplain.feedback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.complain.FeedbackListResp;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 意见反馈列表
 */
@Route(path = RouterConfig.ACTIVITY_FEED_BACK_LIST)
public class FeedbackListActivity extends BaseLoadActivity implements IFeedbackListContract.IView {
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private Unbinder unbinder;
    private IFeedbackListContract.IPresent mPresent;
    private FeedbackAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        unbinder = ButterKnife.bind(this);
        mPresent = FeedbackListPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTitle.setRightBtnClick(v -> {
            //todo:新建

        });
        mAdapter = new FeedbackAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        mListView.setAdapter(mAdapter);
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
    }

    @Override
    public void querySuccess(List<FeedbackListResp.FeedbackBean> feedbackBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(feedbackBeans);
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("暂时还没有意见反馈噢~").create());
            mAdapter.setNewData(feedbackBeans);
        }
        mRefreshLayout.setEnableLoadMore(feedbackBeans != null && feedbackBeans.size() == mPresent.getPageSize());

    }

    private class FeedbackAdapter extends BaseQuickAdapter<FeedbackListResp.FeedbackBean, BaseViewHolder> {
        public FeedbackAdapter(@Nullable List<FeedbackListResp.FeedbackBean> data) {
            super(R.layout.list_item_feedback, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FeedbackListResp.FeedbackBean item) {

        }
    }
}
