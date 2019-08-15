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
    private InvoiceHistoryAdapter mAdapter;

    public InvoiceHistoryWindow(Activity activity, BaseQuickAdapter.OnItemClickListener listener) {
        super(activity);
        RecyclerView listView = (RecyclerView) View.inflate(activity, R.layout.window_invoice_history, null);
        setContentView(listView);
        setWidth(UIUtils.getScreenWidth(activity) - UIUtils.dip2px(36));
        setHeight(UIUtils.dip2px(170));
        setFocusable(true);
        setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_invoice_history_window));
        mAdapter = new InvoiceHistoryAdapter();
        mAdapter.setOnItemClickListener(listener);
        listView.setAdapter(mAdapter);
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
