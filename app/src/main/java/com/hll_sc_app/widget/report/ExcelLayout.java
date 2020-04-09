package com.hll_sc_app.widget.report;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class ExcelLayout extends RelativeLayout {
    @BindView(R.id.vel_scroll_header)
    SyncHorizontalScrollView mScrollHeader;
    @BindView(R.id.vel_list_view)
    RecyclerView mListView;
    @BindView(R.id.vel_scroll_body)
    SyncHorizontalScrollView mScrollBody;
    @BindView(R.id.vel_refresh_view)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.vel_scroll_footer)
    SyncHorizontalScrollView mScrollFooter;
    private ExcelRow.ColumnData[] mColumnDataArray;
    private StringArrayAdapter mAdapter;
    private EmptyView mEmptyView;

    public ExcelLayout(Context context) {
        this(context, null);
    }

    public ExcelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExcelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_excel_layout, this);
        ButterKnife.bind(this, view);
        mScrollHeader.setLinkageViews(mScrollBody, mScrollFooter);
        mScrollBody.setLinkageViews(mScrollHeader, mScrollFooter);
        mScrollFooter.setLinkageViews(mScrollHeader, mScrollBody);
        mAdapter = new StringArrayAdapter();
        mListView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(((Activity) getContext())).setImage(R.drawable.ic_char_empty).setTips("当前日期下没有统计数据噢").create();
        mEmptyView.setBackgroundColor(Color.WHITE);
        mEmptyView.setOnTouchListener((v, event) -> true);
        addView(mEmptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void setHeaderView(View headerView) {
        mScrollHeader.removeAllViews();
        mScrollHeader.addView(headerView);
    }

    public void setFooterView(View footerView) {
        mScrollFooter.removeAllViews();
        mScrollFooter.addView(footerView);
    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        mRefreshView.setOnRefreshLoadMoreListener(listener);
    }

    public void setColumnDataList(ExcelRow.ColumnData... columnDataArray) {
        mColumnDataArray = columnDataArray;
    }

    public void closeHeaderOrFooter() {
        mRefreshView.closeHeaderOrFooter();
    }

    public void setData(List<? extends IStringArrayGenerator> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                mAdapter.setNewData(new ArrayList<>());
                mEmptyView.setVisibility(VISIBLE);
            } else {
                mAdapter.setNewData(new ArrayList<>(list));
                mEmptyView.setVisibility(GONE);
            }
        }
    }

    public void setEmptyView(String tips){
        mEmptyView.setTips(tips);
        addView(mEmptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void setEnableLoadMore(boolean loadMore) {
        mRefreshView.setEnableLoadMore(loadMore);
    }

    public class StringArrayAdapter extends BaseQuickAdapter<IStringArrayGenerator, BaseViewHolder> {

        StringArrayAdapter() {
            super(null);
        }

        @Override
        public void setNewData(@Nullable List<IStringArrayGenerator> data) {
            if (mColumnDataArray == null) return;
            super.setNewData(data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            ExcelRow row = new ExcelRow(parent.getContext());
            row.updateChildView(mColumnDataArray.length);
            row.updateItemData(mColumnDataArray);
            return new BaseViewHolder(row);
        }

        @Override
        protected void convert(BaseViewHolder helper, IStringArrayGenerator item) {
            ExcelRow itemView = (ExcelRow) helper.itemView;
            itemView.setTag(item);
            itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? android.R.color.white : R.color.color_fafafa);
            CharSequence[] array = {};
            itemView.updateRowDate(item.convertToRowData().toArray(array));
        }
    }
}
