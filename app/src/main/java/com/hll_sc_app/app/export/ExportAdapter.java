package com.hll_sc_app.app.export;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.export.ExportType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

class ExportAdapter extends BaseQuickAdapter<ExportType, BaseViewHolder> {
    public ExportAdapter() {
        super(R.layout.item_export);
        List<ExportType> list = new ArrayList<>();
        Collections.addAll(list, ExportType.values());
        setNewData(list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExportType item) {
        helper.setText(R.id.ie_label, item.getLabel())
                .setText(R.id.ie_desc, item.getDesc())
                .setImageResource(R.id.ie_image, item.getImage());
    }
}
