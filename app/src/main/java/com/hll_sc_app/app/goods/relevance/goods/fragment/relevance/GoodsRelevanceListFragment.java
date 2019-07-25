package com.hll_sc_app.app.goods.relevance.goods.fragment.relevance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.relevance.goods.GoodsRelevanceListActivity;
import com.hll_sc_app.app.goods.relevance.goods.fragment.BaseGoodsRelevanceFragment;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
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
 * 第三方商品关联-采购商列表-关联商品列表-已关联
 *
 * @author zhuyingsong
 * @date 2019/7/5
 */
public class GoodsRelevanceListFragment extends BaseGoodsRelevanceFragment implements GoodsRelevanceListFragmentContract.IGoodsRelevanceListView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    PurchaserBean mBean;
    private GoodsRelevanceListFragmentPresenter mPresenter;
    private GoodsRelevanceListAdapter mAdapter;
    private EmptyView mEmptyView;
    private String mGoodsName;

    public static GoodsRelevanceListFragment newInstance(PurchaserBean bean) {
        Bundle args = new Bundle();
        args.putParcelable("parcelable", bean);
        GoodsRelevanceListFragment fragment = new GoodsRelevanceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = GoodsRelevanceListFragmentPresenter.newInstance();
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
        rootView = inflater.inflate(R.layout.fragment_goods_un_relevance_list, container, false);
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
        mEmptyView = EmptyView.newBuilder(requireActivity()).setTips("还没有已关联的商品数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireActivity(),
            R.color.base_color_divider)
            , UIUtils.dip2px(5)));
        mAdapter = new GoodsRelevanceListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TransferDetailBean bean = (TransferDetailBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.txt_relevance_remove) {
                showTipsDialog(bean);
            } else if (id == R.id.txt_relevance_again) {
                RouterUtil.goToActivity(RouterConfig.GOODS_RELEVANCE_LIST_SELECT, bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void load(boolean refresh) {
        if (mBean == null) {
            if (getActivity() instanceof GoodsRelevanceListActivity)
                ((GoodsRelevanceListActivity) getActivity()).reqTransferDetail();
            return;
        }
        if (refresh) mPresenter.queryGoodsRelevanceList(false);
        else mPresenter.queryMoreGoodsRelevanceList();
    }

    private void showTipsDialog(TransferDetailBean bean) {
        SuccessDialog.newBuilder(requireActivity())
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("确认解除商品关联么")
            .setMessage("解除后未关联的商品将无法下单\n请谨慎操作")
            .setCancelable(false)
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.removeGoodsRelevance(bean);
                }
                dialog.dismiss();
            }, "我再看看", "确认解除").create().show();
    }

    @Override
    protected void initData() {
        if (mBean != null) mPresenter.start();
        else removeRelevanceSuccess();
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
        mLlTitle.setVisibility(total == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void removeRelevanceSuccess() {
        if (getActivity() instanceof GoodsRelevanceListActivity) {
            ((GoodsRelevanceListActivity) getActivity()).refreshFragment();
        }
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
        lazyLoad();
    }

    @Override
    public void refreshList(List<TransferDetailBean> beans) {
        if (!isPrepared()) return;
        hideLoading();
        showGoodsList(beans, false, beans.size());
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class GoodsRelevanceListAdapter extends BaseQuickAdapter<TransferDetailBean, BaseViewHolder> {
        GoodsRelevanceListAdapter() {
            super(R.layout.item_goods_relevance_list);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.addOnClickListener(R.id.txt_relevance_again)
                .addOnClickListener(R.id.txt_relevance_remove);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, TransferDetailBean item) {
            helper.setText(R.id.txt_goodsName, item.getGoodsName())
                .setText(R.id.txt_productName, item.getProductName())
                .setText(R.id.txt_productSpec, item.getProductSpec())
                .setText(R.id.txt_actionTime, CalendarUtils.format(CalendarUtils.parse(item.getActionTime(),
                    "yyyyMMddHHmmss"), "yyyy/MM/dd"))
                .setGone(R.id.txt_relevance_remove, item.getIsRelated() == null);
        }
    }
}
