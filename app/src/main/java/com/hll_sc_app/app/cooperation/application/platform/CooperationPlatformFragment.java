package com.hll_sc_app.app.cooperation.application.platform;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.application.BaseCooperationApplicationFragment;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.CooperationShopSettlementActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.event.CooperationEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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

    @Subscribe
    public void handleEvent(CooperationEvent event) {
        if (event.getMessage().equals(CooperationEvent.SHOP_NUM_CHANGED)) {
            mPresenter.queryCooperationPlatformList(false);
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cooperation_application_platform, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
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
                if (view.getId() == R.id.txt_status) {
                    ShopSettlementReq req = new ShopSettlementReq();
                    req.setFrom(BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_AGREE);
                    req.setPurchaserID(bean.getPurchaserID());
                    // 同意之前先选择结算方式
                    CooperationPurchaserDetail purchaserDetail = new CooperationPurchaserDetail();
                    purchaserDetail.setStatus(bean.getStatus());
                    purchaserDetail.setCooperationActive(bean.getCooperationActive());
                    CooperationShopSettlementActivity.start(req, purchaserDetail);
                } else if (view.getId() == R.id.content) {
                    RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, bean.getPurchaserID());
                } else if (view.getId() == R.id.txt_newShopNum) {
                    mPresenter.queryPurchaserDetail(bean.getPurchaserID());
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
    public void toNeedReviewShopList(ArrayList<PurchaserShopBean> list) {
        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOPS, list);
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
            viewHolder.addOnClickListener(R.id.txt_status)
                    .addOnClickListener(R.id.txt_newShopNum)
                    .addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                    .setText(R.id.txt_linkMan,
                            getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
                    .setText(R.id.txt_shopCount, getShopCountString(item))
                    .setText(R.id.txt_newShopNum, String.format("有%s个新门店申请", item.getApplyShopNum()))
                    .setGone(R.id.txt_newShopNum, item.getApplyShopNum() > 0 && !TextUtils.equals("0", item.getStatus()));
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
            }
            return content;
        }

        private void setStatus(BaseViewHolder helper, PurchaserBean item) {
            TextView txtStatus = helper.getView(R.id.txt_status);
            txtStatus.setClickable(false);
            switch (item.getStatus()) {
                case "0":
                    txtStatus.setClickable(true);
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
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setText("");
                    txtStatus.setTextColor(0xFFAEAEAE);
                    txtStatus.setBackground(null);
                    break;
            }
            if (item.getCooperationActive() == 1) {
                txtStatus.setBackground(new ColorDrawable());
                txtStatus.setTextColor(0xFFAEAEAE);
                txtStatus.setText("已停止");
            }
        }
    }
}