package com.hll_sc_app.app.order.inspection;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.order.OrderInspectionDepositList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class OrderInspectionAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {

    OrderInspectionAdapter(@Nullable List<OrderDetailBean> data) {
        super(R.layout.item_order_inspection, data);
    }

    List<OrderInspectionReq.OrderInspectionItem> getReqList() {
        // 构造请求列表
        List<OrderInspectionReq.OrderInspectionItem> reqList = new ArrayList<>();
        for (OrderDetailBean bean : getData()) {
            reqList.add(OrderInspectionReq.OrderInspectionItem.copyFromDetailList(bean));
        }
        return reqList;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) holder.getView(R.id.ioi_edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OrderDetailBean bean = getItem(holder.getAdapterPosition());
                if (bean == null) return;
                Utils.processMoney(s, false);
                bean.setInspectionNum(Double.parseDouble(TextUtils.isEmpty(s.toString()) ? "0" : s.toString()));
            }
        });
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        helper.setText(R.id.ioi_name, item.getProductName()) // 商品名
                .setText(R.id.ioi_spec, item.getProductSpec()) // 商品规格
                .setText(R.id.ioi_order, "订：" + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName()) // 订货数
                .setText(R.id.ioi_unit, item.getInspectionUnit()) //签收单位
                .setText(R.id.ioi_edit, CommonUtils.formatNumber(item.getInspectionNum())); // 签收数量

        SpannableString num = new SpannableString(CommonUtils.formatNum(item.getAdjustmentNum()));
        num.setSpan(new StyleSpan(Typeface.BOLD), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        num.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_222222)),
                0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView deliver = helper.getView(R.id.ioi_deliver);
        deliver.setText("发：");
        deliver.append(num);
        deliver.append(item.getAdjustmentUnit());

        GlideImageView imageView = helper.getView(R.id.ioi_image);
        imageView.setImageURL(item.getImgUrl()); // 商品图
        // 押金商品
        List<OrderDepositBean> depositList = item.getDepositList();
        helper.setGone(R.id.ioi_deposit_group, depositList != null && depositList.size() > 0);
        ((OrderInspectionDepositList) helper.getView(R.id.ioi_deposit_list)).setData(depositList);
    }
}
