package com.hll_sc_app.app.goods.relevance.goods.fragment.unrelevance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.relevance.goods.GoodsRelevanceListActivity;
import com.hll_sc_app.app.goods.relevance.goods.fragment.BaseGoodsRelevanceFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
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
 * 第三方商品关联-采购商列表-关联商品列表-未关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public class GoodsUnRelevanceListFragment extends BaseGoodsRelevanceFragment implements GoodsUnRelevanceListFragmentContract.IGoodsRelevanceListView {
    @BindView(R.id.txt_tips)
    TextView mTxtTips;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private GoodsUnRelevanceListFragmentPresenter mPresenter;
    private GoodsRelevanceListAdapter mAdapter;
    private EmptyView mEmptyView;
    private PurchaserBean mBean;
    private String mGoodsName;

    public static GoodsUnRelevanceListFragment newInstance(PurchaserBean bean) {
        Bundle args = new Bundle();
        args.putParcelable("parcelable", bean);
        GoodsUnRelevanceListFragment fragment = new GoodsUnRelevanceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = GoodsUnRelevanceListFragmentPresenter.newInstance();
        mPresenter.register(this);
        Bundle args = getArguments();
        if (args != null) mBean = args.getParcelable("parcelable");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_goods_relevance_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                load(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                load(true);
            }
        });
        mEmptyView = EmptyView.newBuilder(requireActivity()).setTips("还没有未关联的商品数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireActivity(),
                R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new GoodsRelevanceListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TransferDetailBean bean = (TransferDetailBean) adapter.getItem(position);
            if (bean == null) return;
            switch (view.getId()) {
                case R.id.txt_relevance:
                    RouterUtil.goToActivity(RouterConfig.GOODS_RELEVANCE_LIST_SELECT, bean);
                    break;
                case R.id.txt_not_relevance:
                    mPresenter.reqDoNotRelevance(bean.getId());
                    break;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void load(boolean refresh) {
        if (mBean == null) {
            reloadTransferDetail();
            return;
        }
        if (refresh) mPresenter.queryGoodsUnRelevanceList(false);
        else mPresenter.queryMoreGoodsUnRelevanceList();
    }

    @Override
    protected void initData() {
        if (mBean != null) mPresenter.start();
        else if (getActivity() instanceof GoodsRelevanceListActivity)
            ((GoodsRelevanceListActivity) getActivity()).refreshFragment();
    }

    @Override
    public String getGroupId() {
        return mBean.getGroupID();
    }

    @Override
    public String getResourceType() {
        return mBean.getResourceType();
    }

    @Override
    public String getOperateModel() {
        return mBean.getOperateModel();
    }

    @Override
    public String getGoodsName() {
        return mGoodsName;
    }

    @Override
    public void showGoodsList(List<TransferDetailBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
        if (total == 0) {
            mTxtTips.setVisibility(View.GONE);
        } else {
            mTxtTips.setVisibility(View.VISIBLE);
            mTxtTips.setText(getTipString(String.valueOf(total)));
        }
    }

    @Override
    public void reloadTransferDetail() {
        if (getActivity() instanceof GoodsRelevanceListActivity)
            ((GoodsRelevanceListActivity) getActivity()).reqTransferDetail();
    }

    public SpannableString getTipString(String total) {
        SpannableString spannableString = new SpannableString("有" + total + "个第三方品项未关联商城商品，请及时关联，否则未关联的品项将无法下单。");
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireActivity(), R.color.base_red)),
                1, total.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    /**
     * 刷新当前 Fragment
     *
     * @param name 搜索词
     */
    @Override
    public void refreshFragment(String name) {
        this.mGoodsName = name;
        setForceLoad(true);
        if (isFragmentVisible()) {
            lazyLoad();
        }
    }

    @Override
    public void refreshList(List<TransferDetailBean> beans) {
        if (!isPrepared()) return;
        hideLoading();
        showGoodsList(beans, false, beans.size());
    }

    class GoodsRelevanceListAdapter extends BaseQuickAdapter<TransferDetailBean, BaseViewHolder> {
        GoodsRelevanceListAdapter() {
            super(R.layout.item_goods_un_relevance_list);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.addOnClickListener(R.id.txt_relevance).addOnClickListener(R.id.txt_not_relevance);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, TransferDetailBean item) {
            helper.setText(R.id.txt_goodsName, item.getGoodsName())
                    .setText(R.id.txt_goodsCode, "商品编码：" + getString(item.getGoodsCode()))
                    .setText(R.id.txt_saleUnitName, "单位：" + getString(item.getSaleUnitName()))
                    .setGone(R.id.txt_not_relevance, item.getIsRelated() != null && item.getIsRelated() == 1)
                    .setGone(R.id.txt_relevance, item.getIsRelated() == null || item.getIsRelated() == 1)
                    .setGone(R.id.txt_no_relevance, item.getIsRelated() != null && item.getIsRelated() == 0);
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
