package com.hll_sc_app.app.marketingsetting.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.marketingsetting.GiveBean;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * 赠券
 */
public class CouponRuleAdapter extends BaseQuickAdapter<RuleListBean, BaseViewHolder> {
    public static final int EDIT = 0;
    public static final int CHECK = 1;

    private int discountType;
    private int mModal;

    public CouponRuleAdapter(@Nullable List<RuleListBean> data, int modal, int discountType) {
        super(R.layout.list_item_marketing_coupon_rule, data);
        this.discountType = discountType;
        mModal = modal;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.setText(R.id.txt_min_num, discountType == 1 ? "订单满" : "订购数满");
        ((EditText) holder.getView(R.id.edt_min_num)).setHint(discountType == 1 ? "填写金额" : "填写数量");
        holder.setText(R.id.txt_min_unit, discountType == 1 ? "元" : "个");
        holder.addOnClickListener(R.id.txt_coupon_select);
        holder.getView(R.id.img_delete).setVisibility(mModal == EDIT ? View.VISIBLE : View.GONE);
        if (mModal == EDIT) {
            holder.addOnClickListener(R.id.img_delete);
        }
        initModal(holder);

        ((EditText) holder.getView(R.id.edt_min_num)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(holder.getLayoutPosition()).setRuleCondition(s.toString());
            }
        });
        ((EditText) holder.getView(R.id.edt_coupon_num)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(holder.getLayoutPosition()).setRuleDiscountValue(s.toString());
                GiveBean giveBean = getItem(holder.getLayoutPosition()).getGiveList().get(0);
                giveBean.setGiveCount(s.toString());
                giveBean.setGiveType(2);
            }
        });

        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, RuleListBean item) {
        GiveBean giveBean = item.getGiveList().get(0);
        helper.setText(R.id.edt_min_num, item.getRuleCondition())
                .setText(R.id.txt_coupon_select, setCouponName(giveBean.getGiveTargetName()))
                .setText(R.id.edt_coupon_num, giveBean.getGiveCount());
    }

    public SpannableString setCouponName(String couponName) {
        SpannableString ss = new SpannableString(TextUtils.isEmpty(couponName) ? "" : couponName);
        if (mModal == CHECK) {
            ss.setSpan(new UnderlineSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2)); // 下划线
                }
            }, 0, couponName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void initModal(BaseViewHolder holder) {
        TextView mTxtMinNum = holder.getView(R.id.txt_min_num);
        TextView mTxtGive = holder.getView(R.id.txt_give);
        TextView mTextCouponSelect = holder.getView(R.id.txt_coupon_select);
        EditText mEdtMinNum = holder.getView(R.id.edt_min_num);
        EditText mEdtCouponNum = holder.getView(R.id.edt_coupon_num);
        ImageView mArrow = holder.getView(R.id.img_arrow);
        switch (mModal) {
            case EDIT:
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mTxtMinNum.getLayoutParams();
                params.setMargins(UIUtils.dip2px(45), 0, 0, 0);
                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) mTxtGive.getLayoutParams();
                params2.endToEnd = R.id.txt_min_num;
                break;
            case CHECK:
                params = (ConstraintLayout.LayoutParams) mTxtMinNum.getLayoutParams();
                params.setMargins(UIUtils.dip2px(0), 0, 0, 0);
                params2 = (ConstraintLayout.LayoutParams) mTxtGive.getLayoutParams();
                params2.startToStart = R.id.txt_min_num;
                mEdtCouponNum.setEnabled(false);
                mEdtMinNum.setEnabled(false);
                mArrow.setVisibility(View.GONE);
                mTextCouponSelect.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                mTextCouponSelect.setTextColor(Color.parseColor("#5695D2"));
                break;
            default:
                break;
        }
    }
}
