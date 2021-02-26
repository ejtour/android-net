package com.hll_sc_app.app.feedbackcomplain.platformcomplain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ComplainMangeAddActivity;
import com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.bean.event.ComplainManageEvent;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
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
 * 向平台投诉
 */
@Route(path = RouterConfig.ACTIVITY_PLATFORM_COMPLAIN_LIST)
public class PlatformComplainActivity extends BaseLoadActivity implements IPlatformComplainContract.IView {
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private Unbinder unbinder;
    private PlatformComplainAdapter mAdapter;
    private IPlatformComplainContract.IPresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_to_platform);
        unbinder = ButterKnife.bind(this);
        mPresent = PlatformComplainPresent.newInstance();
        mPresent.register(this);
        initView();
        mPresent.queryList(true);
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mTitle.setRightBtnClick(v -> {
            ComplainMangeAddActivity.start(null, ComplainMangeDetailActivity.SOURCE.PLATFORM);
        });
        mAdapter = new PlatformComplainAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ComplainMangeDetailActivity.start(mAdapter.getItem(position).getId(), ComplainMangeDetailActivity.SOURCE.PLATFORM);
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void querySuccess(List<ComplainListResp.ComplainListBean> complainListBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(complainListBeans);
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("暂时还没有接到平台投诉噢~").create());
            mAdapter.setNewData(complainListBeans);
        }
        mRefreshLayout.setEnableLoadMore(complainListBeans != null && complainListBeans.size() == mPresent.getPageSize());
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private class PlatformComplainAdapter extends BaseQuickAdapter<ComplainListResp.ComplainListBean, BaseViewHolder> {
        public PlatformComplainAdapter(@Nullable List<ComplainListResp.ComplainListBean> data) {
            super(R.layout.list_item_platform_complain, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ComplainListResp.ComplainListBean item) {
            helper.setText(R.id.txt_type, item.getTypeName())
                    .setText(R.id.txt_reason, item.getReasonName())
                    .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getCreateTime(), "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm"))
                    .setGone(R.id.txt_status, item.getOperationIntervention() == 2);
        }
    }

    @Subscribe
    public void onEvent(ComplainManageEvent event) {
        if (event.getTarget() == ComplainManageEvent.TARGET.LIST) {
            mPresent.refresh();
        }
    }

}
