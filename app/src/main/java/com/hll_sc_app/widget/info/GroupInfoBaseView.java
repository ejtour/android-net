package com.hll_sc_app.widget.info;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.app.info.modify.InfoModifyActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

public class GroupInfoBaseView extends RelativeLayout {
    public static final int REQUEST_CODE_CHOOSE = 104;
    @BindView(R.id.gib_avatar)
    GlideImageView mAvatar;
    @BindView(R.id.gib_code)
    TextView mCode;
    @BindView(R.id.gib_name)
    TextView mName;
    @BindView(R.id.gib_address)
    TextView mAddress;
    @BindView(R.id.gib_contact)
    TextView mContact;
    @BindView(R.id.gib_phone)
    TextView mPhone;
    @BindView(R.id.gib_fax)
    TextView mFax;
    @BindView(R.id.gib_email)
    TextView mEmail;

    public GroupInfoBaseView(Context context) {
        this(context, null);
    }

    public GroupInfoBaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupInfoBaseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_group_info_base, this);
        ButterKnife.bind(this, view);
    }

    public void setData(GroupInfoResp resp) {
        mAvatar.setImageURL(resp.getGroupLogoUrl());
        mCode.setText(resp.getGroupID());
        mName.setText(resp.getGroupName());
        mAddress.setText(resp.getGroupAddress());
        mContact.setText(resp.getLinkman());
        mPhone.setTag(resp.getGroupPhone());
        mPhone.setText(PhoneUtil.formatPhoneNum(resp.getGroupPhone()));
        mFax.setText(resp.getFax());
        String groupMail = resp.getGroupMail();
        mEmail.setTag(groupMail);
        if (!TextUtils.isEmpty(groupMail))
            groupMail = groupMail.split(";")[0];
        mEmail.setText(groupMail);
    }

    @OnClick(R.id.gib_avatar)
    public void changeAvatar() {
        UIUtils.selectPhoto((Activity) getContext(), REQUEST_CODE_CHOOSE, null);
    }

    @OnClick(R.id.gib_contact)
    public void modifyContact() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.CONTACT, mContact.getText().toString());
    }

    @OnClick(R.id.gib_phone)
    public void modifyPhone() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.PHONE, mPhone.getTag().toString());
    }

    @OnClick(R.id.gib_fax)
    public void modifyFax() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.FAX, mFax.getText().toString());
    }

    @OnClick(R.id.gib_email)
    public void modifyEmail() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.GROUP_EMAIL, mEmail.getTag().toString());
    }

    @OnClick(R.id.gib_invite_code_label)
    public void showInviteCode() {
        RouterUtil.goToActivity(RouterConfig.INFO_INVITE_CODE);
    }
}
