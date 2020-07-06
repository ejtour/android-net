package com.hll_sc_app.app.order;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderManageAdapter extends BaseQuickAdapter<OrderResp, BaseViewHolder> {
    private int mSelectableNum;
    private String mGroupID;
    private boolean mCanCheck;

    OrderManageAdapter() {
        super(R.layout.item_order_manage);
        UserBean user = GreenDaoUtils.getUser();
        if (user != null) {
            mGroupID = user.getGroupID();
        }
    }

    void setCanCheck() {
        mCanCheck = true;
        notifyDataSetChanged();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.iom_check_box)
                .setGone(R.id.iom_check_box, mCanCheck)
                .setVisible(R.id.iom_image, !mCanCheck)
                .setGone(R.id.iom_warn, false);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp item) {
        ((GlideImageView) helper.getView(R.id.iom_image)).setImageURL(item.getImgUrl());
        View view = helper.getView(R.id.iom_check_box);
        view.setEnabled(item.isCanSelect(mGroupID));
        view.setSelected(item.isSelected());
        String name = (TextUtils.isEmpty(item.getStallName()) ? "" : (item.getStallName() + " - ")) + item.getShopName();
        SpannableString money = new SpannableString("¥ " + CommonUtils.formatMoney(item.getTotalAmount()));
        money.setSpan(new RelativeSizeSpan(0.75f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.iom_name, name)
                .setText(R.id.iom_money, money)
                .setText(R.id.iom_purchaser_name, "集团：" + item.getPurchaserName())
                .setText(R.id.iom_order_no, "订单号：" + item.getSubBillNo())
                .setText(R.id.iom_extra_info, OrderHelper.handleExtraInfo(item))
                .setGone(R.id.iom_divider, helper.getAdapterPosition() != getItemCount() - 1);
        LinearLayout tagGroup = (LinearLayout) helper.getView(R.id.iom_tag_group);
        tagGroup.removeAllViews();
        if (item.getDeliverType() == 2) {
            createTagText(tagGroup, false).setText("自提");
        }
        if (item.getIsSupplement() == 1) {
            createTagText(tagGroup, true).setText("补单");
        }
        if (item.getNextDayDelivery() == 1) {
            createTagText(tagGroup, false).setText("隔日送");
        }
        if (item.getShipperType() > 0) {
            createTagText(tagGroup, true).setText(item.getShipperType() == 3 ? "代配" : "代仓");
        }
    }

    private TextView createTagText(LinearLayout group, boolean solid) {
        TextView tag = new TextView(group.getContext());
        int space = UIUtils.dip2px(4);
        tag.setPadding(space, space / 2, space, space / 2);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.leftMargin = space;
        tag.setLayoutParams(ll);
        tag.setTextSize(10);
        if (solid) {
            tag.setBackgroundResource(R.drawable.bg_tag_primary_solid);
            tag.setTextColor(Color.WHITE);
        } else {
            tag.setBackgroundResource(R.drawable.bg_tag_primary_stroke);
            tag.setTextColor(ContextCompat.getColor(group.getContext(), R.color.colorPrimary));
        }
        group.addView(tag);
        return tag;
    }

    private int getItemPosition(OrderResp item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(OrderResp oldData, OrderResp newData) {
        if (oldData == null || newData == null) {
            return;
        }
        newData.setSelected(oldData.isSelected());
        setData(getItemPosition(oldData), newData);
    }

    void removeData(OrderResp data) {
        if (data == null) {
            return;
        }
        remove(getItemPosition(data));
    }

    @Override
    public void setNewData(@Nullable List<OrderResp> data) {
        preProcess(data);
        super.setNewData(data);
        mSelectableNum = 0;
        updateNum(data);
    }

    private void preProcess(@Nullable List<OrderResp> data) {
        if (!CommonUtils.isEmpty(mData) && !CommonUtils.isEmpty(data)) {
            for (OrderResp resp : data) {
                for (OrderResp orderResp : mData) {
                    if (resp.getSubBillNo().equals(orderResp.getSubBillNo())) {
                        resp.setSelected(orderResp.isSelected() && resp.isCanSelect(mGroupID));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void addData(@NonNull Collection<? extends OrderResp> newData) {
        super.addData(newData);
        updateNum(newData);
    }

    private void updateNum(Collection<? extends OrderResp> newData) {
        if (!CommonUtils.isEmpty(newData)) {
            for (OrderResp resp : newData) {
                if (resp.isCanSelect(mGroupID))
                    mSelectableNum++;
            }
        }
    }

    int getSelectableNum() {
        return mSelectableNum;
    }
}
