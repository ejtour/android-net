package com.hll_sc_app.app.aptitude.goods;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

class AptitudeGoodsAdapter extends BaseQuickAdapter<AptitudeBean, BaseViewHolder> {
    public AptitudeGoodsAdapter() {
        super(R.layout.item_aptitude_goods);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.iag_root)
                .addOnClickListener(R.id.iag_del);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AptitudeBean item) {
        String endTime = DateUtil.getReadableTime(item.getEndTime(), Constants.SLASH_YYYY_MM_DD);
        helper.setText(R.id.iag_name, item.getAptitudeName())
                .setText(R.id.iag_time, "检测时间：" +
                        (TextUtils.isEmpty(item.getCheckTime()) ? "— —" :
                                DateUtil.getReadableTime(item.getCheckTime(), CalendarUtils.FORMAT_YYYY_MM_DD_HH_MM)))
                .setText(R.id.iag_create, "创建人：" + item.getCreateBy() + "/" +
                        DateUtil.getReadableTime(item.getCreateTime(), Constants.SIGNED_YYYY_MM_DD_HH_MM))
                .setText(R.id.iag_goods_num, String.valueOf(item.getProductNum()))
                .setGone(R.id.iag_remain_day, item.getExpirationDay() <= 30 && !TextUtils.isEmpty(endTime))
                .setText(R.id.iag_remain_day, item.getExpirationDay() <= 0 ? "已到期" : ("剩余" + item.getExpirationDay() + "天"))
                .setTextColor(R.id.iag_remain_day, ContextCompat.getColor(helper.itemView.getContext(),
                        item.getExpirationDay() <= 0 ? R.color.color_f56564 : R.color.color_f5a623));
    }
}
