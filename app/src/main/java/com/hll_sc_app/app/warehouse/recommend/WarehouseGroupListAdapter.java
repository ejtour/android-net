package com.hll_sc_app.app.warehouse.recommend;

import android.text.TextUtils;
import android.view.ViewGroup;

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
    public static final String TYPE_ADD = "add";
    private String type;
    private boolean recommend;

    public WarehouseGroupListAdapter() {
        super(R.layout.list_item_recommend_warehouse);
    }

    public WarehouseGroupListAdapter(String type) {
        super(R.layout.list_item_recommend_warehouse);
        this.type = type;
    }

    WarehouseGroupListAdapter(boolean recommend) {
        super(R.layout.list_item_recommend_warehouse);
        this.recommend = recommend;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        viewHolder.addOnClickListener(R.id.content).addOnClickListener(R.id.txt_del);
        return viewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaserBean item) {
        ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getLogoUrl());
        helper.setText(R.id.txt_groupName, TextUtils.equals(type, TYPE_ADD) ? item.getPurchaserName() :
            item.getGroupName())
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
