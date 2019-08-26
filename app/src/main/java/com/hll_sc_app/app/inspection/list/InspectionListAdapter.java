package com.hll_sc_app.app.inspection.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ThumbnailView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public class InspectionListAdapter extends BaseQuickAdapter<InspectionBean, BaseViewHolder> {
    InspectionListAdapter() {
        super(R.layout.item_inspection_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionBean item) {
        ((ThumbnailView) helper.setText(R.id.iil_shop_name, item.getShopName())
                .setText(R.id.iil_group_name, item.getPurchaserName())
                .setText(R.id.iil_date, DateUtil.getReadableTime(item.getCreateTime(), Constants.SLASH_YYYY_MM_DD))
                .getView(R.id.iil_thumbnail_view)).setData(item.getInspectionImgUrl().split(","));
    }
}
