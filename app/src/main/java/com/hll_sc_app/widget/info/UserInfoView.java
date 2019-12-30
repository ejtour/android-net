package com.hll_sc_app.widget.info;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.app.info.modify.InfoModifyActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

public class UserInfoView extends RelativeLayout {
    @BindView(R.id.vui_company_name)
    TextView mCompanyName;
    @BindView(R.id.vui_name)
    TextView mName;
    @BindView(R.id.vui_work_num)
    TextView mWorkNum;
    @BindView(R.id.vui_account)
    TextView mAccount;
    @BindView(R.id.vui_email)
    TextView mEmail;
    @BindView(R.id.vui_job_container)
    LinearLayout mJobContainer;

    public UserInfoView(Context context) {
        this(context, null);
    }

    public UserInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_user_info, this);
        ButterKnife.bind(this, view);
    }

    public void setData() {
        UserBean user = GreenDaoUtils.getUser();
        mCompanyName.setText(user.getGroupName());
        mName.setText(user.getEmployeeName());
        mWorkNum.setText(user.getEmployeeCode());
        mAccount.setText(PhoneUtil.formatPhoneNum(user.getLoginPhone()));
        mEmail.setText(user.getEmail());
        String roleNames = user.getRoleNames();
        if (TextUtils.isEmpty(roleNames)){
            return;
        }
        String[] array = roleNames.split(",");
        mJobContainer.removeAllViews();
        for (String s : array) {
            mJobContainer.addView(createRoleView(s));
        }
    }

    public TextView createRoleView(String label) {
        TextView textView = new TextView(getContext());
        textView.setPadding(UIUtils.dip2px(5), 0, UIUtils.dip2px(5), 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UIUtils.dip2px(25));
        layoutParams.leftMargin = UIUtils.dip2px(5);
        textView.setBackgroundResource(R.drawable.bg_f5f5f5_radius_3_solid);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
        textView.setTextSize(12);
        textView.setLayoutParams(layoutParams);
        textView.setText(label);
        return textView;
    }

    @OnClick(R.id.vui_email)
    public void modifyEmail() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.EMAIL, mEmail.getText().toString());
    }
}
