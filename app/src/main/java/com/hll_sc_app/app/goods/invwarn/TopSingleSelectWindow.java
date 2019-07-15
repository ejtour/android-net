package com.hll_sc_app.app.goods.invwarn;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 代仓商品库存预警-仓库选择
 * 第三方商品关联-来源选择
 *
 * @author 朱英松
 * @date 2019/7/2
 */
public class TopSingleSelectWindow<T> extends BasePopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private WrapperName<T> mWrapperName;
    private SelectConfirmListener<T> mListener;

    public TopSingleSelectWindow(Activity context, WrapperName<T> wrapperName) {
        super(context);
        this.mWrapperName = wrapperName;
        View view = View.inflate(context, R.layout.window_house_select, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        initView();
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(mActivity,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mAdapter = new ListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            T t = mAdapter.getItem(position);
            mAdapter.select(t);
            if (mListener != null) {
                mListener.confirm(t);
            }
            mAdapter.notifyDataSetChanged();
            dismiss();
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public void refreshList(List<T> list) {
        mAdapter.setNewData(list);
    }

    public void setListener(SelectConfirmListener<T> listener) {
        this.mListener = listener;
    }

    public interface WrapperName<T> {
        /**
         * 获取要显示的名称
         *
         * @param t bean
         * @return 显示的名称
         */
        String getName(T t);
    }

    public interface SelectConfirmListener<T> {
        /**
         * 确定
         *
         * @param t bean
         */
        void confirm(T t);
    }

    private class ListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        private T mSelect;

        ListAdapter() {
            super(R.layout.item_window_house);
        }

        public void select(T t) {
            this.mSelect = t;
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView textView = helper.getView(R.id.txt_houseName);
            textView.setText(mWrapperName.getName(item));
            helper.setVisible(R.id.img_select, mSelect == item);
            textView.setSelected(mSelect == item);
        }
    }
}
