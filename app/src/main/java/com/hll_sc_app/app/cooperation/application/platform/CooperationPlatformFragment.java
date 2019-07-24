package com.hll_sc_app.app.cooperation.application.platform;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.application.BaseCooperationApplicationFragment;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 合作采购商-我收到的申请-平台申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION)
public class CooperationPlatformFragment extends BaseCooperationApplicationFragment implements CooperationPlatformContract.ICooperationPlatformView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    Unbinder unbinder;
    private String mSearchParam;
    private EmptyView mEmptyView;
    private EmptyView mNetEmptyView;
    private PurchaserListAdapter mAdapter;
    private CooperationPlatformContract.ICooperationPlatformPresenter mPresenter;

    public static CooperationPlatformFragment newInstance() {
        return new CooperationPlatformFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CooperationPlatformPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cooperation_application_platform, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreCooperationPlatformList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryCooperationPlatformList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(),
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mEmptyView = EmptyView.newBuilder(getActivity()).setTipsTitle("喔唷，居然是「 空 」的").create();
        mNetEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
            setForceLoad(true);
            lazyLoad();
        }).create();
        mAdapter = new PurchaserListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if (view.getId() == R.id.txt_status && TextUtils.equals(bean.getStatus(), "0")) {
                    ShopSettlementReq req = new ShopSettlementReq();
                    req.setFrom(BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_AGREE);
                    req.setPurchaserID(bean.getPurchaserID());
                    // 同意之前先选择结算方式
                    RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, req);
                } else {
                    RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, bean.getPurchaserID());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException exception) {
        super.showError(exception);
        if (exception.getLevel() == UseCaseException.Level.NET) {
            mAdapter.setEmptyView(mNetEmptyView);
        }
    }

    @Override
    public void showCooperationPlatformList(List<PurchaserBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == CooperationPlatformPresenter.PAGE_SIZE);
    }

    @Override
    public String getSearchParam() {
        return mSearchParam;
    }

    @Override
    public void toSearch(String searchParam) {
        mSearchParam = searchParam;
        setForceLoad(true);
        lazyLoad();
    }

    @Override
    public void refresh() {
        setForceLoad(true);
        lazyLoad();
    }

    private static class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter() {
            super(R.layout.item_cooperation_purchaser_application);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_status).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                .setText(R.id.txt_linkMan,
                    getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
                .setText(R.id.txt_shopCount, getShopCountString(item))
                .setGone(R.id.txt_newShopNum, CommonUtils.getDouble(item.getNewShopNum()) != 0);
            ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getLogoUrl());
            setStatus(helper, item);
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private String getShopCountString(PurchaserBean item) {
            String content = null;
            if (CommonUtils.getDouble(item.getShopCount()) != 0) {
                content = "合作" + CommonUtils.formatNumber(item.getShopCount()) + "个门店";
            } else {
                if (TextUtils.equals(item.getResourceType(), "1")) {
                    // 哗啦啦供应链
                    content = "哗啦啦供应链";
                } else if (TextUtils.equals(item.getResourceType(), "2")) {
                    // 天财供应链
                    content = "天财供应链";
                } else if (TextUtils.equals(item.getResourceType(), "0")) {
                    // 二十二城
                    content = "二十二城";
                }
            }
            return content;
        }

        private void setStatus(BaseViewHolder helper, PurchaserBean item) {
            TextView txtStatus = helper.getView(R.id.txt_status);
            switch (item.getStatus()) {
                case "0":
                    // 待同意
                    txtStatus.setBackgroundResource(R.drawable.bg_button_mid_solid_primary);
                    txtStatus.setTextColor(0xFFFFFFFF);
                    txtStatus.setText(R.string.agree);
                    break;
                case "1":
                    // 未同意
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setTextColor(0xFF999999);
                    txtStatus.setText("已拒绝");
                    break;
                case "2":
                    // 已同意
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setTextColor(0xFF999999);
                    txtStatus.setText("已同意");
                    break;
                default:
                    break;
            }
        }
    }
}