package com.hll_sc_app.widget.invoice;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public class InvoiceHistoryWindow extends BasePopupWindow {
    @BindView(R.id.wih_list_view)
    RecyclerView mListView;
    private InvoiceHistoryAdapter mAdapter;

    public InvoiceHistoryWindow(Activity activity, BaseQuickAdapter.OnItemClickListener listener) {
        super(activity);
        View view = View.inflate(activity, R.layout.window_invoice_history, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setWidth(UIUtils.getScreenWidth(activity) - UIUtils.dip2px(40));
        setHeight(UIUtils.dip2px(170));
        setFocusable(true);
        setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_white_radius_3_solid));
        mAdapter = new InvoiceHistoryAdapter();
        mAdapter.setOnItemClickListener(listener);
        mListView.setAdapter(mAdapter);
    }

    public InvoiceHistoryWindow setList(List<InvoiceHistoryBean> list) {
        mAdapter.setNewData(list);
        return this;
    }

    class InvoiceHistoryAdapter extends BaseQuickAdapter<InvoiceHistoryBean, BaseViewHolder> {

        InvoiceHistoryAdapter() {
            super(R.layout.item_invoice_history);
        }

        @Override
        protected void convert(BaseViewHolder helper, InvoiceHistoryBean item) {
            ((TextView) helper.itemView).setText(item.getInvoiceTitle());
        }
    }
}
