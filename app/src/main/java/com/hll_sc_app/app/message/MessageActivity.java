package com.hll_sc_app.app.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.message.detail.MessageDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */
@Route(path = RouterConfig.MESSAGE)
public class MessageActivity extends BaseLoadActivity implements IMessageContract.IMessageView {

    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    private MessageAdapter mAdapter;
    private IMessageContract.IMessagePresenter mPresenter;
    private List<MessageBean> mSummaryList;
    private List<MessageBean> mMessageList;
    private EmptyView mEmptyView;

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.MESSAGE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_refresh_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("消息中心");
        mAdapter = new MessageAdapter();
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(70), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.setAdapter(mAdapter);
        if (UserConfig.crm()) mRefreshView.setEnableLoadMore(false);
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mMessageList = null;
                if (mSummaryList == null) mPresenter.loadSummary(false);
                mPresenter.refresh();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MessageBean item = mAdapter.getItem(position);
            if (item == null) return;
            if (item.getMessageTypeCode() != 0) {
                MessageDetailActivity.start(this, item.getMessageTypeCode());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MessageDetailActivity.REQ_CODE && resultCode == RESULT_OK) {
            startLoad();
        }
    }

    private void initData() {
        mPresenter = MessagePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void setData(List<MessageBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            mMessageList = list;
            refreshList();
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<MessageBean> list) {
        if (UserConfig.crm()) {
            List<MessageBean> newList = new ArrayList<>();
            for (MessageBean bean : list) {
                if (bean.getMessageTypeCode() == 1002){
                    newList.add(bean);
                    break;
                }
            }
            mAdapter.setNewData(newList);
            return;
        }
        mSummaryList = list;
        refreshList();
    }

    private void refreshList() {
        if (mSummaryList != null && mMessageList != null) {
            mMessageList.addAll(0, mSummaryList);
            setNewList(mMessageList);
        }
    }

    private void setNewList(List<MessageBean> list){
        if (CommonUtils.isEmpty(list)) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTipsTitle("暂时还没有收到任何消息噢~");
            mEmptyView.setTips("一有消息我会立马通知您");
        }
        mAdapter.setNewData(list);
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
                    .setOnClickListener(this::startLoad)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    private void startLoad() {
        mSummaryList = null;
        mMessageList = null;
        mPresenter.start();
    }
}
