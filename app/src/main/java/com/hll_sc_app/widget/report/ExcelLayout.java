package com.hll_sc_app.widget.report;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
    @BindView(R.id.vel_tips)
    ReportTipsView mTipsView;
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
    private int mRawPaddingTop;

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
        OnTouchListener touchListener = (v, event) -> {
            mListView.onTouchEvent(event);
            return false;
        };
        mScrollBody.setOnTouchListener(touchListener);
        mScrollHeader.setOnTouchListener(touchListener);
        mScrollFooter.setOnTouchListener(touchListener);
        mAdapter = new StringArrayAdapter();
        mListView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(((Activity) getContext())).setImage(R.drawable.ic_char_empty).setTips("当前日期下没有统计数据噢").create();
        mEmptyView.setBackgroundColor(Color.WHITE);
        mEmptyView.setOnTouchListener((v, event) -> true);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(BELOW, R.id.vel_tips);
        addView(mEmptyView, params);
    }

    public void setTips(String tips) {
        if (!TextUtils.isEmpty(tips)) {
            mRawPaddingTop = getPaddingTop();
            setPadding(getPaddingLeft(), 0, getPaddingRight(), getPaddingBottom());
            mTipsView.setTips(tips);
            mTipsView.setVisibility(VISIBLE);
        } else if (mTipsView.getVisibility() == VISIBLE) {
            setPadding(getPaddingLeft(), mRawPaddingTop, getPaddingRight(), getPaddingBottom());
            mTipsView.setVisibility(GONE);
        }
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

    public void setEmptyTip(CharSequence s) {
        mEmptyView.setTips(s);
    }

    public void setEnableRefresh(boolean refresh) {
        mRefreshView.setEnableRefresh(refresh);
    }

    public List<? extends IStringArrayGenerator> getData(){
        return mAdapter.getData();
    }

    /**
     * 配置高度自动改变
     *
     * @param emptyHeight  MATCH_PARENT代表固定高度，特定大小代表自动改变
     * @param enableNested 是否启用嵌套滚动
     */
    public void setAutoHeight(int emptyHeight, boolean enableNested) {
        LayoutParams refreshParam = (LayoutParams) mRefreshView.getLayoutParams();
        LayoutParams footerParam = (LayoutParams) mScrollFooter.getLayoutParams();
        LayoutParams emptyParam = (LayoutParams) mEmptyView.getLayoutParams();
        if (emptyHeight != LayoutParams.MATCH_PARENT) {
            refreshParam.addRule(ABOVE, 0);
            footerParam.addRule(ALIGN_PARENT_BOTTOM, 0);
            footerParam.addRule(BELOW, mRefreshView.getId());
            emptyParam.height = emptyHeight;
        } else {
            footerParam.addRule(ALIGN_PARENT_BOTTOM);
            footerParam.addRule(BELOW, 0);
            refreshParam.addRule(ABOVE, mScrollFooter.getId());
            emptyParam.height = LayoutParams.MATCH_PARENT;
        }
        mListView.setNestedScrollingEnabled(enableNested);
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
