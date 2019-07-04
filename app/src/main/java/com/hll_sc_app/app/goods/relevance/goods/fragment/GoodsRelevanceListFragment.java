package com.hll_sc_app.app.goods.relevance.goods.fragment;

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
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.GoodsRelevanceBean;
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
 * 第三方商品关联-采购商列表-关联商品列表-未关联、已关联
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
public class GoodsRelevanceListFragment extends BaseLazyFragment implements GoodsRelevanceListFragmentContract.IGoodsRelevanceListView {
    @BindView(R.id.txt_tips)
    TextView mTxtTips;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    String mGroupId;
    String mResourceType;
    String mOperateModel;
    private GoodsRelevanceListFragmentPresenter mPresenter;
    private GoodsRelevanceListAdapter mAdapter;
    private EmptyView mEmptyView;

    public static GoodsRelevanceListFragment newInstance(String groupId, String resourceType, String operateModel) {
        Bundle args = new Bundle();
        args.putString("object0", groupId);
        args.putString("object1", resourceType);
        args.putString("object2", operateModel);
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
        if (args != null) {
            mGroupId = args.getString("object0");
            mResourceType = args.getString("object1");
            mOperateModel = args.getString("object2");
        }
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
        intView();
        return rootView;
    }

    private void intView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsUnRelevanceList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsUnRelevanceList(false);
            }
        });
        mEmptyView = EmptyView.newBuilder(requireActivity()).setTips("还没有未关联的商品数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireActivity(),
            R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new GoodsRelevanceListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            // TODO:未关联商品列表
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public String getGroupId() {
        return mGroupId;
    }

    @Override
    public String getResourceType() {
        return mResourceType;
    }

    @Override
    public String getOperateModel() {
        return mOperateModel;
    }

    @Override
    public void showGoodsList(List<GoodsRelevanceBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        if (total != 0) {
            mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
        } else {
            mRefreshLayout.setEnableLoadMore(list.size() == GoodsListReq.PAGE_SIZE);
        }
        if (total == 0) {
            mTxtTips.setVisibility(View.GONE);
        } else {
            mTxtTips.setVisibility(View.VISIBLE);
            mTxtTips.setText(getTipString(String.valueOf(total)));
        }
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

    class GoodsRelevanceListAdapter extends BaseQuickAdapter<GoodsRelevanceBean, BaseViewHolder> {
        GoodsRelevanceListAdapter() {
            super(R.layout.item_goods_relevance_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsRelevanceBean item) {
            helper.setText(R.id.txt_goodsName, item.getGoodsName())
                .setText(R.id.txt_goodsCode, "商品编码：" + getString(item.getGoodsCode()))
                .setText(R.id.txt_saleUnitName, "单位：" + getString(item.getSaleUnitName()))
                .addOnClickListener(R.id.txt_relevance);
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
