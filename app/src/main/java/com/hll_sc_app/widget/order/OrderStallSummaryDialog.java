package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.bean.order.summary.SummaryShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/27
 */

public class OrderStallSummaryDialog extends BaseDialog {
    @BindView(R.id.oss_title)
    TextView mTitle;
    @BindView(R.id.oss_list_view)
    RecyclerView mListView;
    private OrderStallSummaryAdapter mAdapter;

    public OrderStallSummaryDialog(@NonNull Activity context) {
        super(context);
    }

    private void initView() {
        mAdapter = new OrderStallSummaryAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(R.drawable.bg_white_radius_top_half_8_solid);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_order_stall_summary, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public OrderStallSummaryDialog setData(SummaryShopBean bean) {
        mTitle.setText(bean.getShopName());
        mAdapter.setNewData(bean.getStallList());
        return this;
    }

    @OnClick(R.id.oss_close)
    @Override
    public void dismiss() {
        super.dismiss();
    }

    private static class OrderStallSummaryAdapter extends BaseQuickAdapter<SummaryShopBean, BaseViewHolder> {

        public OrderStallSummaryAdapter() {
            super(R.layout.item_order_stall_summary);
        }

        @Override
        protected void convert(BaseViewHolder helper, SummaryShopBean item) {
            helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent);
            String info = String.format("%s种商品共%s件  |  ¥%s", CommonUtils.formatNum(item.getProductCount()),
                    CommonUtils.formatNum(item.getProductNum()),
                    CommonUtils.formatMoney(item.getProductAmount()));
            helper.setText(R.id.oss_stall_name, item.getShopName())
                    .setText(R.id.oss_stall_info, info);
        }
    }
}
