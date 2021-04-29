package com.hll_sc_app.app.print.template;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.print.PrintTemplateBean;
import com.hll_sc_app.widget.print.PrintTemplateActionBar;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
class PrintTemplateAdapter extends BaseQuickAdapter<PrintTemplateBean, BaseViewHolder> {
    PrintTemplateAdapter() {
        super(R.layout.item_print_template);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.ipt_actions);
        return helper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PrintTemplateBean bean) {
        helper.setText(R.id.ipt_name, bean.getTemplateName())
                .setText(R.id.ipt_size, "适用纸张：" + bean.getPaperSize())
                .setText(R.id.ipt_desc, "模板说明：" + bean.getTemplateRemark())
                .setGone(R.id.ipt_status, bean.isHasActive() != null && bean.isHasActive());
        ((PrintTemplateActionBar) helper.getView(R.id.ipt_actions)).setData(bean);
    }
}
