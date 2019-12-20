package com.hll_sc_app.app.feedbackcomplain.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.feedbackcomplain.feedback.detail.FeedbackDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
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

    private final int GOTO_ADD_FROM_LIST = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresent.queryList(true);
    }

    private void initView() {
        mTitle.setRightBtnClick(v -> {
            RouterUtil.goToActivity(RouterConfig.ACTIVITY_FEED_BACK_ADD, this, GOTO_ADD_FROM_LIST);
        });
        mAdapter = new FeedbackAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            FeedbackDetailActivity.start(mAdapter.getItem(position).getFeedbackID());
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
        mPresent.queryList(true);
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

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GOTO_ADD_FROM_LIST:
                if (resultCode == RESULT_OK) {
                    mPresent.queryList(true);
                }
                break;
            default:
                break;
        }
    }

    private class FeedbackAdapter extends BaseQuickAdapter<FeedbackListResp.FeedbackBean, BaseViewHolder> {
        public FeedbackAdapter(@Nullable List<FeedbackListResp.FeedbackBean> data) {
            super(R.layout.list_item_feedback, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FeedbackListResp.FeedbackBean item) {
            helper.setText(R.id.txt_title, item.getContent())
                    .setText(R.id.txt_status, item.getIsAnswer() == 0 ? "待回复" : "已回复")
                    .setBackgroundRes(R.id.view_status, item.getIsAnswer() == 0 ? R.drawable.bg_radius_orange_fff5a623 : R.drawable.bg_radius_green_7ed321);
        }
    }
}
