package com.hll_sc_app.widget.stockmanage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.stockmanage.CategorySubBean;
import com.hll_sc_app.bean.stockmanage.CategoryThreeBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

public class DepotCategoryView extends ConstraintLayout {
    @BindView(R.id.vdc_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.vdc_right_list)
    RecyclerView mRightList;
    @BindView(R.id.vdc_empty_tip)
    TextView mEmptyTip;
    private SubAdapter mSubAdapter;
    private ThreeAdapter mThreeAdapter;
    private OnClickListener mListener;

    public DepotCategoryView(Context context) {
        this(context, null);
    }

    public DepotCategoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepotCategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_depot_category, this);
        ButterKnife.bind(this, view);
        mSubAdapter = new SubAdapter();
        mSubAdapter.bindToRecyclerView(mLeftList);
        mSubAdapter.setOnItemClickListener((adapter, view1, position) -> selectSub(position));
        mThreeAdapter = new ThreeAdapter();
        mThreeAdapter.bindToRecyclerView(mRightList);
    }

    private void selectSub(int position) {
        mThreeAdapter.setNewData(mSubAdapter.select(position).getThreeList());
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    @OnClick(R.id.vdc_setup)
    public void onViewClicked(View view) {
        if (mListener != null)
            mListener.onClick(view);
    }

    public void setData(List<CategorySubBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            mLeftList.setVisibility(VISIBLE);
            mRightList.setVisibility(VISIBLE);
            mEmptyTip.setVisibility(GONE);
            mSubAdapter.setNewData(list);
            selectSub(0);
        } else {
            mLeftList.setVisibility(GONE);
            mRightList.setVisibility(GONE);
            mEmptyTip.setVisibility(VISIBLE);
        }
    }

    private static class SubAdapter extends BaseQuickAdapter<CategorySubBean, BaseViewHolder> {
        private CategorySubBean mSelectBean;

        public SubAdapter() {
            super(null);
        }

        public CategorySubBean select(int position) {
            mSelectBean = getItem(position);
            if (mSelectBean != null) {
                for (CategorySubBean bean : getData()) {
                    bean.setSelect(bean.getShopProductCategorySubID().equals(mSelectBean.getShopProductCategorySubID()));
                }
            }
            notifyDataSetChanged();
            return mSelectBean;
        }

        @SuppressLint("ResourceType")
        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView itemView = new TextView(parent.getContext());
            itemView.setTextSize(12);
            itemView.setTextColor(ContextCompat.getColorStateList(parent.getContext(), R.drawable.color_state_on_pri_off_222));
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(30)));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setBackgroundResource(R.drawable.bg_depot_city_item);
            itemView.setSingleLine(true);
            itemView.setEllipsize(TextUtils.TruncateAt.END);
            return new BaseViewHolder(itemView);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategorySubBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setSelected(item.isSelect());
            itemView.setText(item.getShopProductCategorySubName());
        }

        @Override
        public void setNewData(@Nullable List<CategorySubBean> data) {
            if (!CommonUtils.isEmpty(data)) {
                if (mSelectBean != null) {
                    CategorySubBean select = null;
                    for (CategorySubBean bean : data) {
                        bean.setSelect(mSelectBean.getShopProductCategorySubID().equals(bean.getShopProductCategorySubID()));
                        if (mSelectBean.getShopProductCategorySubID().equals(bean.getShopProductCategorySubID())) {
                            select = bean;
                            bean.setSelect(true);
                        } else {
                            bean.setSelect(false);
                        }
                    }
                    if (select == null) {
                        select = data.get(0);
                        select.setSelect(true);
                    }
                    mSelectBean = select;
                } else {
                    mSelectBean = data.get(0);
                    mSelectBean.setSelect(true);
                }
            } else {
                mSelectBean = null;
            }
            super.setNewData(data);
            if (!CommonUtils.isEmpty(mData) && mSelectBean != null) {
                getRecyclerView().scrollToPosition(mData.indexOf(mSelectBean));
            }
        }
    }

    private static class ThreeAdapter extends BaseQuickAdapter<CategoryThreeBean, BaseViewHolder> {
        private int space;

        public ThreeAdapter() {
            super(null);
            space = UIUtils.dip2px(20);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView itemView = new TextView(parent.getContext());
            itemView.setTextSize(12);
            itemView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.color_222222));
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(30)));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setPadding(space, 0, 0, 0);
            itemView.setSingleLine(true);
            itemView.setEllipsize(TextUtils.TruncateAt.END);
            return new BaseViewHolder(itemView);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryThreeBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getShopProductCategoryThreeName());
        }
    }
}
