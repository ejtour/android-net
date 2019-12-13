package com.hll_sc_app.app.crm.customer.seas;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.seas.detail.CustomerSeasDetailActivity;
import com.hll_sc_app.app.order.place.select.SelectGoodsActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

public class CustomerSeasAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
    private boolean mFromPartner;
    private String mEmployeeID;
    private int mType;

    public CustomerSeasAdapter() {
        super(R.layout.item_crm_customer_seas);
        setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ccs_order) {
                PurchaserShopBean item = getItem(position);
                if (item != null)
                    SelectGoodsActivity.start(item.getPurchaserID(), item.getShopID(), item.getShopName());
                return;
            }
            if (view.getTag() != null && !TextUtils.isEmpty(view.getTag().toString()))
                UIUtils.callPhone(view.getContext(), view.getTag().toString());
        });
        setOnItemClickListener(
                (adapter, view, position) ->
                        CustomerSeasDetailActivity.start(((Activity) view.getContext()), getItem(position), mType));
        mEmployeeID = GreenDaoUtils.getUser().getEmployeeID();
    }

    public void setNewData(@Nullable List<PurchaserShopBean> data, boolean fromPartner) {
        super.setNewData(data);
        mFromPartner = fromPartner;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.ccs_phone)
                .addOnClickListener(R.id.ccs_order);
        if (mFromPartner) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) helper.getView(R.id.ccs_tag).getLayoutParams();
            params.endToEnd = -1;
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = ((ConstraintLayout.LayoutParams) helper.getView(R.id.ccs_name).getLayoutParams());
            params.leftMargin = UIUtils.dip2px(5);
            params.startToStart = -1;
            params.startToEnd = R.id.ccs_tag;
            params.endToStart = R.id.ccs_order;
            parent.requestLayout();
        }
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
        helper.setText(R.id.ccs_name, item.getShopName())
                .setText(R.id.ccs_address, item.getShopAddress())
                .setText(R.id.ccs_contact, item.getShopAdmin())
                .setTag(R.id.ccs_phone, item.getShopPhone())
                .setText(R.id.ccs_phone, item.getShopPhone())
                .setGone(R.id.ccs_order, mFromPartner && mEmployeeID.equals(item.getSalesmanID()))
                .setText(R.id.ccs_tag, item.getFallFlag() == 1 ? "掉" : item.getNewFlag() == 1 ? "新" : "")
                .setText(R.id.ccs_today, item.getTodayBillNum() + "单")
                .setText(R.id.ccs_week, item.getCurrentWeekBillNum() + "单")
                .setText(R.id.ccs_month, item.getCurrentMonthBillNum() + "单")
                .setText(R.id.ccs_return, item.getReturnBillNum() + "单")
                .setText(R.id.ccs_seven, String.format("%s/%s单",
                        CommonUtils.formatNumber(item.getSevenAvgBillNum()), item.getSevenBillNum()));
        TextView tag = helper.getView(R.id.ccs_tag);
        if (TextUtils.isEmpty(tag.getText())) {
            tag.setVisibility(View.GONE);
        } else {
            tag.setVisibility(View.VISIBLE);
            GradientDrawable background = (GradientDrawable) tag.getBackground();
            if (item.getFallFlag() == 1) {
                background.setColor(0xfffff6dd);
                tag.setTextColor(0xfff2b400);
            } else {
                tag.setTextColor(0xffaad35f);
                background.setColor(0xffecf5db);
            }
        }
    }
}
