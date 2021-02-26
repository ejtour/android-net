package com.hll_sc_app.widget.info;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.app.info.doorway.InfoDoorwayActivity;
import com.hll_sc_app.app.info.license.InfoLicenseActivity;
import com.hll_sc_app.app.info.modify.InfoModifyActivity;
import com.hll_sc_app.app.info.other.InfoOtherActivity;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

public class GroupInfoCertifyView extends FrameLayout {
    @BindView(R.id.gic_status)
    ImageView mStatus;
    @BindView(R.id.gic_status_desc)
    TextView mStatusDesc;
    @BindView(R.id.gic_name)
    TextView mName;
    @BindView(R.id.gic_id_num)
    TextView mIdNum;
    @BindView(R.id.gic_doorway)
    TextView mDoorway;
    @BindView(R.id.gic_license)
    TextView mLicense;
    @BindView(R.id.gic_other)
    TextView mOther;
    @BindView(R.id.gic_not_ing)
    ConstraintLayout mNotIng;
    @BindView(R.id.gic_ing)
    LinearLayout mIng;
    @BindView(R.id.gic_submit)
    TextView mSubmit;
    private CertifyReq mData;
    private OnClickListener mListener;

    public GroupInfoCertifyView(Context context) {
        this(context, null);
    }

    public GroupInfoCertifyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupInfoCertifyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_group_info_certify, this);
        ButterKnife.bind(this, view);
    }

    public void withReq(CertifyReq req) {
        mData = req;
    }

    public void refreshData() {
        if (mData.isCertified() == GroupInfoResp.CERTIFYING) {
            mIng.setVisibility(VISIBLE);
            mNotIng.setVisibility(GONE);
            return;
        }
        mIng.setVisibility(GONE);
        mNotIng.setVisibility(VISIBLE);
        mName.setText(mData.getBusinessEntity());
        mIdNum.setText(mData.getEntityIDNo());
        boolean notPass = isEditable();
        mName.setClickable(notPass);
        mIdNum.setClickable(notPass);

        switch (mData.isCertified()) {
            case GroupInfoResp.NOTCERTIFY:
                String source = "完 成 认 证 信 息\n享 受 更 多 服 务";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#1AB394")),
                        source.indexOf("认"), source.indexOf("证") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F5A623")),
                        source.indexOf("服"), source.indexOf("务") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mStatusDesc.setText(spannableString);
                mSubmit.setText("提交审核");
                mStatus.setImageResource(R.drawable.ic_certify_no);
                break;
            case GroupInfoResp.PASS:
                source = "您已完成 企 业 身 份 认 证\n官方认证，享受更优质的服务";
                spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#1AB394")),
                        source.indexOf("企"), source.indexOf("证") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, source.indexOf("企"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(0.8f), source.indexOf("官"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_999999)),
                        source.indexOf("官"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mStatusDesc.setText(spannableString);
                mStatus.setImageResource(R.drawable.ic_certify_pass);

                mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mIdNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mDoorway.setHint("点击查看");
                mLicense.setHint("点击查看");
                mOther.setHint("点击查看");
                mSubmit.setVisibility(GONE);
                break;
            case GroupInfoResp.REJECT:
                source = "您 的 认 证 审 核 未 通 过 ！\n请修改资料信息重新提交审核";
                spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_ed5655)),
                        0, source.indexOf("！") + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(0.8f), source.indexOf("请"), source.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_999999)),
                        source.indexOf("请"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mStatusDesc.setText(spannableString);
                mStatus.setImageResource(R.drawable.ic_certify_not_pass);
                mSubmit.setText("重新审核");
                break;
        }

        if (notPass) {
            mDoorway.setText(TextUtils.isEmpty(mData.getFrontImg()) ? "" : "已上传");
            mLicense.setText(mData.licenseEnable() ? "已上传" : "");
            mOther.setText(CommonUtils.isEmpty(mData.getOtherLicenses()) ? "" : "已上传");
            mSubmit.setEnabled(mData.enable());
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    @OnClick(R.id.gic_doorway)
    public void uploadDoorWay() {
        InfoDoorwayActivity.start((Activity) getContext(),
                isEditable(),
                mData.getFrontImg());
    }

    private boolean isEditable() {
        return mData.isCertified() != GroupInfoResp.PASS;
    }

    @OnClick(R.id.gic_license)
    public void uploadLicense() {
        InfoLicenseActivity.start((Activity) getContext(), mData);
    }

    @OnClick(R.id.gic_other)
    public void uploadOther() {
        InfoOtherActivity.start((Activity) getContext(), mData.getOtherLicenses(), isEditable());
    }

    @OnClick(R.id.gic_name)
    public void modifyName() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.NAME, mName.getText().toString());
    }

    @OnClick(R.id.gic_id_num)
    public void modifyIdNum() {
        InfoModifyActivity.start((Activity) getContext(), ModifyType.ID_CARD, mIdNum.getText().toString());
    }

    @OnClick(R.id.gic_submit)
    public void submit(View view) {
        if (mListener != null) mListener.onClick(view);
    }
}
