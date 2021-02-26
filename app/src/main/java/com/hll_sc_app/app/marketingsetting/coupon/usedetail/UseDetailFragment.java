package com.hll_sc_app.app.marketingsetting.coupon.usedetail;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.marketingsetting.CouponUseDetailListResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_HH_MM_SS;

public class UseDetailFragment extends BaseLazyFragment implements IUseDetailContract.IView {
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private CouponUseInfoAdpater mAdapter;
    private IUseDetailContract.IPresent mPresent;

    private int discountStatus = -1;
    private String id = "";

    public static UseDetailFragment newInstance(int status, String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("status", status);
        UseDetailFragment fragment = new UseDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            discountStatus = bundle.getInt("status");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
        mRefresh = null;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_use_detail_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRefresh = view.findViewById(R.id.refreshLayout);
        mPresent = UseDetailPresent.newInstance();
        mPresent.register(this);
        initView();
        return view;
    }

    @Override
    protected void initData() {
        mPresent.getMarketingDetail(true);
    }

    private void initView() {
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new CouponUseInfoAdpater(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showDetail(CouponUseDetailListResp resp, boolean isMore) {
        if (!isMore && resp.getList().size() == 0) {
            mAdapter.setEmptyView(EmptyView.newBuilder((Activity) getContext()).setTips("优惠券在当前状态下没有使用记录噢~").create());
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
    public String getDiscountID() {
        return id;
    }

    @Override
    public int getCouponStatus() {
        return discountStatus;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefresh.closeHeaderOrFooter();
    }

    private class CouponUseInfoAdpater extends BaseQuickAdapter<CouponUseDetailListResp.CouponUseBean, BaseViewHolder> {
        public CouponUseInfoAdpater(@Nullable List<CouponUseDetailListResp.CouponUseBean> data) {
            super(R.layout.list_item_coupon_use_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CouponUseDetailListResp.CouponUseBean item) {
            helper.setText(R.id.txt_shop_name, item.getShopName())
                    .setText(R.id.txt_company_name, item.getPurchaserName())
                    .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getSendTime(), FORMAT_HH_MM_SS, "yyyy/MM/dd HH:mm"))
                    .setText(R.id.txt_condition, transformSendType(item.getSendType()));

        }

        private String transformSendType(int type) {
            switch (type) {
                case 1:
                    return "活动发放";
                case 2:
                    return "直接发放";
                case 3:
                    return "客户领取";
                default:
                    return "";
            }
        }
    }
}
