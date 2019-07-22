package com.hll_sc_app.app.report;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.ReportItem;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class ReportEntryAdapter extends BaseQuickAdapter<ReportItem, BaseViewHolder> {
    ReportEntryAdapter(@Nullable List<ReportItem> data) {
        super(R.layout.item_report_entry, data);
        setOnItemClickListener((adapter, view, position) -> {
            ReportItem item = getItem(position);
            if (item == null) return;
            RouterUtil.goToActivity(item.getPath());
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportItem item) {
        View divider = helper.setText(R.id.ire_name, item.getLabel())
                .setImageResource(R.id.ire_icon, item.getIcon())
                .getView(R.id.ire_divider);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) divider.getLayoutParams();
        if (item.isBottomDivider() || helper.getAdapterPosition() == getItemCount() - 1) {
            params.leftMargin = 0;
            params.height = UIUtils.dip2px(10);
            divider.setBackground(ContextCompat.getDrawable(divider.getContext(), R.color.base_activity_bg));
        } else {
            params.leftMargin = UIUtils.dip2px(40);
            params.height = UIUtils.dip2px(1);
            divider.setBackground(ContextCompat.getDrawable(divider.getContext(), R.color.color_eeeeee));
        }
    }
}
