package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.AccountPeriodSelectWindow;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/4
 */
public class AccountPayView extends ConstraintLayout {
    public static final String TERM_WEEK = "1";
    public static final String TERM_MONTH = "2";
    public static final String[] WEEK_ARRAY = {"日", "一", "二", "三", "四", "五", "六"};
    @BindView(R.id.vap_create_date)
    TextView mCreateDate;
    @BindView(R.id.vap_create_tip)
    TextView mCreateTip;
    @BindView(R.id.vap_settle_date_edit)
    EditText mSettleDate;
    @BindView(R.id.vap_settle_tip)
    TextView mSettleTip;
    private AccountPeriodSelectWindow mPeriodSelectWindow;

    public AccountPayView(Context context) {
        this(context, null);
    }

    public AccountPayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccountPayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_account_pay, this);
        ButterKnife.bind(this, view);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @OnClick(R.id.vap_create_date)
    void showAccountPeriodWindow() {
        UIUtils.hideActivitySoftKeyboard((Activity) getContext());
        if (mPeriodSelectWindow == null) {
            mPeriodSelectWindow = new AccountPeriodSelectWindow((Activity) getContext());
            mPeriodSelectWindow.setSelectListener((payTermType, payTerm) -> {
                setCreateDate(TextUtils.equals(payTermType, "周结") ? "1" : "2", payTerm);
            });
        }
        mPeriodSelectWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
    }

    public void setData(String type, String createDate, String settleDate) {
        setCreateDate(type, CommonUtils.getInt(createDate));
        mSettleDate.setText(TextUtils.isEmpty(settleDate) ? "0" : settleDate);
    }

    public String getType() {
        return getTag(R.id.base_tag_1) == null ? "0" : String.valueOf(getTag(R.id.base_tag_1));
    }

    public String getPeriod() {
        return getTag(R.id.base_tag_2) == null ? null : String.valueOf(getTag(R.id.base_tag_2));
    }

    public String getSettleDate() {
        return mSettleDate.getText().toString();
    }

    private void setCreateDate(String flag, int createDate) {
        boolean isWeek = TextUtils.equals(flag, TERM_WEEK);
        if (isWeek) {
            setTag(R.id.base_tag_1, flag);
            setTag(R.id.base_tag_2, createDate);
            mCreateDate.setText(String.format("周结，每周%s", getWeekLabel(createDate)));
            mCreateTip.setVisibility(VISIBLE);
        } else if (TextUtils.equals(flag, TERM_MONTH)) {
            setTag(R.id.base_tag_1, flag);
            setTag(R.id.base_tag_2, createDate);
            mCreateDate.setText(String.format("月结，每月%s号", createDate));
            mCreateTip.setVisibility(VISIBLE);
        } else {
            setTag(R.id.base_tag_1, null);
            setTag(R.id.base_tag_2, null);
            mCreateDate.setText(null);
            mCreateTip.setVisibility(GONE);
        }
        if (mCreateTip.getVisibility() == VISIBLE) {
            String flagLabel = isWeek ? "周" : "月";
            String date = isWeek ? (getWeekLabel(createDate)) : (createDate + "号");
            String end = createDate == 1 ? (flagLabel + "末") : ("本" + flagLabel +
                    (isWeek ? (getWeekLabel(createDate == 0 ? 6 : (createDate - 1)))
                            : ((createDate - 1) + "号")));
            String source = String.format("设置每%s%s，生成对账周期为上%s%s至%s",
                    flagLabel, date, flagLabel, date, end);
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666)),
                    source.indexOf("每") + (isWeek ? 1 : 2), source.indexOf("，"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666)),
                    source.indexOf("为") + 1, source.indexOf("至"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666)),
                    source.indexOf("至") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mCreateTip.setText(ss);
        }
    }

    private String getWeekLabel(int week) {
        if (week < 0 || week >= WEEK_ARRAY.length) return "";
        return WEEK_ARRAY[week];
    }

    @OnTextChanged(R.id.vap_settle_date_edit)
    void onTextChanged(CharSequence s) {
        String source = String.format("在生成账单后%s日内进行结算，涉及报表统计金额数据", CommonUtils.getInt(s.toString()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666)), 6, source.indexOf("日") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSettleTip.setText(ss);
    }
}
