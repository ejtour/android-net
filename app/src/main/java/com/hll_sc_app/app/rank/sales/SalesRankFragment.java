package com.hll_sc_app.app.rank.sales;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.event.RankEvent;
import com.hll_sc_app.bean.rank.RankParam;
import com.hll_sc_app.bean.rank.SalesRankBean;
import com.hll_sc_app.bean.rank.SalesRankResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class SalesRankFragment extends BaseLazyFragment implements ISalesRankContract.ISalesRankView {
    @BindView(R.id.fsr_date_label)
    TextView mDateLabel;
    @BindView(R.id.fsr_amount_my)
    TextView mAmountMy;
    @BindView(R.id.fsr_rank_my)
    TextView mRankMy;
    @BindView(R.id.fsr_list_view)
    RecyclerView mListView;
    @BindView(R.id.fsr_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private SalesRankAdapter mAdapter;
    private ISalesRankContract.ISalesRankPresenter mPresenter;
    private RankParam mRankParam;
    private EmptyView mEmptyView;

    public static SalesRankFragment newInstance(RankParam param) {
        SalesRankFragment fragment = new SalesRankFragment();
        fragment.mRankParam = param;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = SalesRankPresenter.newInstance(mRankParam);
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_sales_rank, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new SalesRankAdapter();
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
        switch (mRankParam.getDateType()) {
            case 1: // 日
                mDateLabel.setText(mRankParam.getFormatDate("yyyy年MM月dd日"));
                mRankMy.setText("我的日排名：0");
                break;
            case 2: // 周
                mDateLabel.setText(mRankParam.getFormatDate("yyyy年第w周"));
                if (mDateLabel.getText().toString().endsWith("第1周")) {
                    Date date = CalendarUtils.getDateAfter(mRankParam.getStartDate(), 5);
                    mDateLabel.setText(CalendarUtils.format(date, "yyyy年第w周"));
                }
                mRankMy.setText("我的周排名：0");
                break;
            case 3: // 月
                mDateLabel.setText(mRankParam.getFormatDate("yyyy年MM月"));
                mRankMy.setText("我的月排名：0");
                break;
        }
        mAmountMy.setText(String.format("¥%s", CommonUtils.formatMoney(0)));
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
    public void setData(SalesRankResp resp, boolean append) {
        mRankMy.setText(String.format("%s%s", mRankMy.getText().toString().substring(0, 6), resp.getGroupAmountRank()));
        mAmountMy.setText(String.format("¥%s", CommonUtils.formatMoney(resp.getValidTradeAmount())));
        List<SalesRankBean> list = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
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
