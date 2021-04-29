package com.hll_sc_app.app.print.list;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
class PrinterListAdapter extends BaseQuickAdapter<PrinterBean, BaseViewHolder> {

    private final boolean mIsSelect;

    PrinterListAdapter(boolean isSelect) {
        super(R.layout.item_printer_list);
        mIsSelect = isSelect;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.ipl_item, R.id.ipl_remove);
        return helper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PrinterBean bean) {
        boolean enable = !mIsSelect || CommonUtils.getInt(bean.getResultCode()) == 1;
        helper.setText(R.id.ipl_name, bean.getDeviceName())
                .setText(R.id.ipl_status, bean.getReturnMsg())
                .setEnabled(R.id.ipl_item, enable);
        helper.getView(R.id.ipl_name).setEnabled(enable);
        ((TextView) helper.getView(R.id.ipl_status)).setCompoundDrawablesWithIntrinsicBounds(0, 0,
                mIsSelect ? 0 : R.drawable.ic_arrow_gray, 0);
    }
}
