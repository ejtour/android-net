package com.hll_sc_app.app.marketingsetting.coupon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.CouponListAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.marketingsetting.CouponListResp;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hll_sc_app.bean.marketingsetting.CouponListResp.CouponBean.STATUS_INVALID;
import static com.hll_sc_app.bean.marketingsetting.CouponListResp.CouponBean.STATUS_PAUSE;
import static com.hll_sc_app.bean.marketingsetting.CouponListResp.CouponBean.STATUS_PROMOTION;

/**
 * 优惠券
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_LIST)
public class MarketingCouponActivity extends BaseLoadActivity implements IMarketingCouponContract.IView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Unbinder unbinder;
    private CouponListAdapter mAdapter;
    private IMarketingCouponContract.IPresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_coupon_list);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = MarketingCouponPresent.newInstance();
        mPresent.register(this);
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.freshList();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CouponListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            //todo 优惠券详情 -zc

        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CouponListResp.CouponBean currentItem = mAdapter.getItem(position);

            switch (view.getId()) {
                case R.id.txt_invalid:
                    mPresent.changeCouponStatus(currentItem.getId(), STATUS_INVALID);
                    break;
                case R.id.txt_pause:
                    mPresent.changeCouponStatus(currentItem.getId(), STATUS_PAUSE);
                    break;
                case R.id.txt_start:
                    mPresent.changeCouponStatus(currentItem.getId(), STATUS_PROMOTION);
                case R.id.txt_send:
                    //todo 发放 -zc
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void getCouponListSuccess(CouponListResp resp, boolean isMore) {
        if (resp.getTotalSize() == 0) {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有优惠券哦~").create());
            mAdapter.setNewData(null);
        } else {
            if (isMore) {
                mAdapter.addData(resp.getList());
            } else {
                mAdapter.setNewData(resp.getList());
            }
        }
    }

    @Override
    public void changeStatusSuccess(int status) {
        showToast(String.format("%s优惠券成功", status == STATUS_PROMOTION ? "启用" : status == STATUS_PAUSE ? "暂停" : "作废"));
        mPresent.freshList();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }


}
