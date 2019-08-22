package com.hll_sc_app.widget.report;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class ExcelLayout extends LinearLayout {
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
        setOrientation(VERTICAL);
        mScrollHeader.setLinkageViews(mScrollBody, mScrollFooter);
        mScrollBody.setLinkageViews(mScrollHeader, mScrollFooter);
        mScrollFooter.setLinkageViews(mScrollHeader, mScrollBody);
        mAdapter = new StringArrayAdapter();
        mListView.setAdapter(mAdapter);
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

    public void closeHeaderOrFooter(){
        mRefreshView.closeHeaderOrFooter();
    }

    public void setData(List<List<CharSequence>> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
    }

    public void setEnableLoadMore(boolean loadMore){
        mRefreshView.setEnableLoadMore(loadMore);
    }

    public class StringArrayAdapter extends BaseQuickAdapter<List<CharSequence>, BaseViewHolder> {

        StringArrayAdapter() {
            super(null);
        }

        @Override
        public void setNewData(@Nullable List<List<CharSequence>> data) {
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
        protected void convert(BaseViewHolder helper, List<CharSequence> item) {
            ExcelRow itemView = (ExcelRow) helper.itemView;
            itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? android.R.color.white : R.color.color_fafafa);
            itemView.updateRowDate(item.toArray(new CharSequence[]{}));
        }
    }
}
