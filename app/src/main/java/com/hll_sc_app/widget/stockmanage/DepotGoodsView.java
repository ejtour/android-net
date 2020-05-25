package com.hll_sc_app.widget.stockmanage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

public class DepotGoodsView extends ConstraintLayout {
    @BindView(R.id.vdg_search_view)
    SearchView mSearchView;
    @BindView(R.id.vdg_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vdg_list_view)
    RecyclerView mListView;
    private GoodsAdapter mAdapter;
    private OnClickListener mListener;
    private int mCurPos;
    private OnClickListener mOnClickListener;

    public DepotGoodsView(Context context) {
        this(context, null);
    }

    public DepotGoodsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepotGoodsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_depot_goods, this);
        ButterKnife.bind(this, view);
        mAdapter = new GoodsAdapter();
        mAdapter.bindToRecyclerView(mListView);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            mCurPos = position;
            if (mListener != null) {
                mListener.onClick(view1);
            }
        });
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(context));
    }

    public void setOnDelClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
    }

    @OnClick(R.id.vdg_add)
    public void onViewClicked(View view) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);
        }
    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        mRefreshLayout.setOnRefreshLoadMoreListener(listener);
    }

    public void setContentClickListener(SearchView.ContentClickListener listener) {
        mSearchView.setContentClickListener(listener);
    }

    public void showSearchContent(boolean show, String content) {
        mSearchView.showSearchContent(show, content);
    }

    public void setData(List<GoodsBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                mRefreshLayout.setVisibility(GONE);
            } else {
                mRefreshLayout.setVisibility(VISIBLE);
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 10);
    }

    public void closeHeaderOrFooter() {
        mRefreshLayout.closeHeaderOrFooter();
    }

    public void removeSuccess() {
        if (mAdapter.getData().size() == 1) {
            setData(null, false);
        } else {
            mAdapter.getData().remove(mCurPos);
            mAdapter.notifyDataSetChanged();
        }
    }

    private static class GoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        public GoodsAdapter() {
            super(R.layout.item_depot_detail_goods);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.addOnClickListener(R.id.ddg_del);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            ((GlideImageView) helper.setText(R.id.ddg_name, item.getProductName())
                    .setTag(R.id.ddg_del, item.getProductID())
                    .setText(R.id.ddg_code, "编码：" + item.getProductCode())
                    .setText(R.id.ddg_category, String.format("分类：%s > %s", item.getShopProductCategorySubName(), item.getShopProductCategoryThreeName()))
                    .setBackgroundRes(R.id.ddg_root, mData.indexOf(item) % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent)
                    .getView(R.id.ddg_image)).setImageURL(item.getImgUrl());
        }
    }
}
