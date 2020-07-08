package com.hll_sc_app.app.menu;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.menu.MenuBean;
import com.hll_sc_app.citymall.util.ViewUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class MenuAdapter extends BaseQuickAdapter<MenuBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private OnItemClickListener mListener;

    public MenuAdapter(@Nullable List<MenuBean> data, OnItemClickListener listener) {
        super(R.layout.item_menu, data);
        mListener = listener;
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        TextView label = helper.setText(R.id.im_extra, item.getExtra())
                .setText(R.id.im_name, item.getLabel())
                .getView(R.id.im_name);
        label.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
        View divider = helper.getView(R.id.im_divider);
        label.getLayoutParams().height = UIUtils.dip2px(item.getIcon() == 0 ? 45 : 50);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) divider.getLayoutParams();
        if (item.isSection() || helper.getAdapterPosition() == getItemCount() - 1) {
            params.leftMargin = 0;
            params.height = UIUtils.dip2px(item.isSection() ? 10 : 0);
            divider.setBackground(ContextCompat.getDrawable(divider.getContext(), R.color.base_activity_bg));
        } else {
            params.leftMargin = UIUtils.dip2px(item.getIcon() == 0 ? 10 : 36);
            params.height = ViewUtils.dip2px(divider.getContext(), 0.5f);
            divider.setBackground(ContextCompat.getDrawable(divider.getContext(), R.color.color_eeeeee));
        }
        divider.setLayoutParams(params);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MenuBean item = getItem(position);
        if (item == null) return;
        if (!item.doFinally() && mListener != null) {
            mListener.onItemClick(adapter, view, position);
        }
    }
}
