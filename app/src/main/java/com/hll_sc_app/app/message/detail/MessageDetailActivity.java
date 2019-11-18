package com.hll_sc_app.app.message.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.message.MessageHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.message.MessageDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

@Route(path = RouterConfig.MESSAGE_DETAIL)
public class MessageDetailActivity extends BaseLoadActivity implements IMessageDetailContract.IMessageDetailView {
    public static final int REQ_CODE = 453;
    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "object0")
    int mCode;
    private IMessageDetailContract.IMessageDetailPresenter mPresenter;
    private MessageDetailAdapter mAdapter;
    private boolean mHasChanged;
    private int mCurPos;

    /**
     * @param code 消息代码
     */
    public static void start(Activity context, int code) {
        Object[] args = {code};
        RouterUtil.goToActivity(RouterConfig.MESSAGE_DETAIL, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_refresh_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) setResult(RESULT_OK);
        super.onBackPressed();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(MessageHelper.getMessageType(mCode));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.setPadding(0, 0, 0, 0);
        mAdapter = new MessageDetailAdapter();
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurPos = position;
            MessageDetailBean item = mAdapter.getItem(mCurPos);
            if (item != null && item.getReadStatus() == 1) {
                mPresenter.markAsRead(item.getId());
            }
        });
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

    private void initData() {
        mPresenter = MessageDetailPresenter.newInstance(mCode);
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void setData(List<MessageDetailBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
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
    public void success() {
        mHasChanged = true;
        MessageDetailBean item = mAdapter.getItem(mCurPos);
        if (item != null) {
            item.setReadStatus(2);
            mAdapter.notifyItemChanged(mCurPos);
        }
    }
}
