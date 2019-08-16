package com.hll_sc_app.app.marketingsetting.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.utils.ColorStr;

/**
 * 选择赠送的优惠券-赠券模式下
 */
public class CouponRuleSelectView extends LinearLayout {
    public static final int EDIT = 0;
    public static final int CHECK = 1;
    private TextView mTxtMinNum;
    private TextView mTxtGive;
    private TextView mTextCouponSelect;
    private EditText mEdtMinNum;
    private EditText mEdtCouponNum;
    private ImageView mArrow;
    private int mModal;

    private SelectListener mListener;

    public CouponRuleSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CouponRuleSelectView);
        mModal = array.getInt(R.styleable.CouponRuleSelectView_modal, mModal);
        initView(context);

    }

    private void initView(Context context) {
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = View.inflate(context, R.layout.view_coupon_rule_select, constraintLayout);
        bindView(view);
        initModal();
        mTextCouponSelect.setOnClickListener(v -> {
            if (this.mListener != null) {
                this.mListener.select();
            }
        });
        addView(view);

    }

    private void bindView(View root) {
        mTxtMinNum = root.findViewById(R.id.txt_min_num);
        mTxtGive = root.findViewById(R.id.txt_give);
        mTextCouponSelect = root.findViewById(R.id.txt_coupon_select);
        mEdtMinNum = root.findViewById(R.id.edt_min_num);
        mEdtCouponNum = root.findViewById(R.id.edt_coupon_num);
        mArrow = root.findViewById(R.id.img_arrow);
    }

    private void initModal() {
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
                mArrow.setVisibility(GONE);
                mTextCouponSelect.setTextColor(Color.parseColor("#5695D2"));
                break;
            default:
                break;
        }
    }

    public void initValue(String minNum, String couponName, String couponNum) {
        mEdtCouponNum.setText(couponNum);
        mEdtMinNum.setText(minNum);
        if (mModal == CHECK) {
            SpannableString ss = new SpannableString(couponName);
            ss.setSpan(new UnderlineSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2)); // 下划线
                }
            }, 0, couponName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            mTextCouponSelect.setText(couponName);
        }
    }

    /**
     * String[订单数满值，赠券个数]
     *
     * @return
     */
    public String[] getValue() {
        return new String[]{mEdtMinNum.getText().toString(), mEdtCouponNum.getText().toString()};
    }

    /**
     * 是否三项都填写了
     *
     * @return
     */
    public boolean isInputComplete() {
        return mEdtMinNum.getText().toString().trim().length() != 0 &&
                mEdtCouponNum.getText().toString().trim().length() != 0 &&
                mTextCouponSelect.getText().toString().trim().length() != 0;
    }

    public void setSelectListener(SelectListener selectListener) {
        this.mListener = selectListener;
    }

    public void setCouponName(String name) {
        mTextCouponSelect.setText(name);
    }

    /**
     * 点击赠券的事件
     */
    public interface SelectListener {
        void select();
    }
}
