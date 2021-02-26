package com.hll_sc_app.app.crm.customer.partner;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.crm.customer.partner.detail.CustomerPartnerDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
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
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerPartnerFragment extends BaseLazyFragment implements ICustomerPartnerContract.ICustomerPartnerView {
    @BindView(R.id.ccp_title)
    TextView mTitle;
    @BindView(R.id.ccp_list_view)
    RecyclerView mListView;
    @BindView(R.id.ccp_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object")
    boolean mIsAll;
    Unbinder unbinder;
    private ICustomerPartnerContract.ICustomerPartnerPresenter mPresenter;
    private CustomerPartnerAdapter mAdapter;
    private EmptyView mEmptyView;

    public static CustomerPartnerFragment newInstance(boolean isAll) {
        Bundle args = new Bundle();
        args.putBoolean("object", isAll);
        CustomerPartnerFragment fragment = new CustomerPartnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = CustomerPartnerPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_crm_customer_partner, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new CustomerPartnerAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean item = mAdapter.getItem(position);
            if (item != null)
                CustomerPartnerDetailActivity.start(item.getPurchaserID(), mIsAll);
        });
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(70), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
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

    private void updateNum(int groupNum, int shopNum, int newNum) {
        String source = String.format("管理集团 %s 家，共 %s 个门店，其中 %s 个新店", groupNum, shopNum, newNum);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
                source.indexOf("团") + 1, source.indexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
                source.indexOf("共") + 1, source.indexOf("个"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_ed5655)),
                source.indexOf("中") + 1, source.lastIndexOf("个"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTitle.setText(ss);
        mTitle.setVisibility(View.VISIBLE);
    }

    void reload() {
        setForceLoad(true);
        lazyLoad();
    }

    @Override
    protected void initData() {
        if (isAll() && !RightConfig.checkRight(getString(R.string.right_partner_list_all))) {
            showToast(getString(R.string.right_tips));
            mRefreshLayout.setEnablePureScrollMode(true);
            mTitle.setVisibility(View.GONE);
            return;
        }
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
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

    @Override
    public void setData(CooperationPurchaserResp resp, boolean append) {
        updateNum(resp.getGroupTotal(), resp.getShopTotal(), resp.getNewShopNum());
        List<PurchaserBean> list = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("还没有数据哦~");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public boolean isAll() {
        return mIsAll;
    }

    @Override
    public String getSearchWords() {
        return ((BaseCustomerActivity) requireActivity()).getSearchWords();
    }
}
