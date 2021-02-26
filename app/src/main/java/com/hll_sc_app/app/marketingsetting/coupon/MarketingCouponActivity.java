package com.hll_sc_app.app.marketingsetting.coupon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.CouponListAdapter;
import com.hll_sc_app.app.marketingsetting.coupon.check.MarketingCouponCheckActivity;
import com.hll_sc_app.app.marketingsetting.coupon.send.SendCouponActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.MarketingEvent;
import com.hll_sc_app.bean.marketingsetting.CouponListResp;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.hll_sc_app.bean.marketingsetting.CouponListResp.CouponBean.STATUS_INVALID;
import static com.hll_sc_app.bean.marketingsetting.CouponListResp.CouponBean.STATUS_PAUSE;
import static com.hll_sc_app.bean.marketingsetting.CouponListResp.CouponBean.STATUS_PROMOTION;
import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 优惠券
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_LIST)
public class MarketingCouponActivity extends BaseLoadActivity implements IMarketingCouponContract.IView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.title)
    TitleBar mTitle;
    private Unbinder unbinder;
    private CouponListAdapter mAdapter;
    private IMarketingCouponContract.IPresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_coupon_list);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        mPresent = MarketingCouponPresent.newInstance();
        mPresent.register(this);
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTitle.setRightBtnClick(v -> {
            RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_COUPON_ADD);
        });
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
            MarketingCouponCheckActivity.start(mAdapter.getItem(position).getId());
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
                    break;
                case R.id.txt_send:
                    SendCouponActivity.start(currentItem);
                    break;
                default:
                    break;
            }
        });
    }

    @Subscribe
    public void onEvent(MarketingEvent event) {
        if (event.getTarget() == MarketingEvent.Target.MARKETING_COUPON_LIST) {
            if (event.isRefresh()) {
                Observable.timer(900, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                        .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
                        .subscribe(aLong -> mPresent.freshList());
            }
        }
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
