package com.hll_sc_app.app.warehouse.recommend;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.PurchaserBean;

/**
 * 代仓管理-我是货主默认介绍页面-查看推荐
 *
 * @author zhuyingsong
 * @date 2019/8/2
 */
public class WarehouseGroupListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
    private boolean recommend;

    public WarehouseGroupListAdapter() {
        super(R.layout.list_item_recommend_warehouse);
    }

    WarehouseGroupListAdapter(boolean recommend) {
        super(R.layout.list_item_recommend_warehouse);
        this.recommend = recommend;
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaserBean item) {
        ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getLogoUrl());
        helper.setText(R.id.txt_groupName, item.getGroupName())
            .setText(R.id.txt_linkman,
                "联系人：" + getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
            .setText(R.id.txt_groupArea, "所在地区：" + getString(item.getGroupArea()))
            .setGone(R.id.txt_recommend, recommend);
    }

    private String getString(String string) {
        if (TextUtils.isEmpty(string)) {
            return "无";
        } else {
            return string;
        }
    }
}
