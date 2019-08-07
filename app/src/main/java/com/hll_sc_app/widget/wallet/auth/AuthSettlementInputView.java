package com.hll_sc_app.widget.wallet.auth;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.account.IInfoInputView;
import com.hll_sc_app.app.wallet.bank.BankListActivity;
import com.hll_sc_app.app.wallet.common.WalletHelper;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class AuthSettlementInputView extends ConstraintLayout implements IInfoInputView {
    @BindView(R.id.asi_next)
    TextView mNext;
    @BindView(R.id.asi_person_name)
    EditText mPersonName;
    @BindView(R.id.asi_bank_name)
    TextView mBankName;
    @BindView(R.id.asi_account_type)
    TextView mAccountType;
    @BindView(R.id.asi_card_no)
    EditText mCardNo;
    @BindView(R.id.asi_attachment)
    ImgUploadBlock mAttachment;
    @BindView(R.id.asi_attachment_label)
    TextView mAttachmentLabel;
    @BindView(R.id.asi_attachment_group)
    Group mAttachmentGroup;
    private OnClickListener mCommitListener;
    private AuthInfo mAuthInfo;
    private SingleSelectionDialog mAccountTypeDialog;

    public AuthSettlementInputView(Context context) {
        this(context, null);
    }

    public AuthSettlementInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthSettlementInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_wallet_auth_settlement_input, this);
        ButterKnife.bind(this, view);
        mAttachment.setOnDeleteListener(this::deleteImage);
        mAttachment.setMaxSize(2097152);
    }

    @Override
    public void initData(AuthInfo info) {
        mAuthInfo = info;
        mPersonName.setText(info.getReceiverName());
        mBankName.setText(info.getBankName());
        mAccountType.setText(WalletHelper.getBankType(info.getBankPersonType()));
        mCardNo.setText(info.getBankAccount());
        if (mAuthInfo.getBankPersonType() == 2) {
            mAttachment.showImage(info.getProxyProtocol());
        } else if (mAuthInfo.getBankPersonType() == 3) {
            mAttachment.showImage(info.getImgProxyContract());
        }
        updateAttachmentVisibility();
    }

    private void updateAttachmentVisibility() {
        if (mAuthInfo.getBankPersonType() == 2) {
            mAttachmentGroup.setVisibility(VISIBLE);
            mAttachmentLabel.setText("委托收款说明");
            mAttachment.setTitle("点击上传委托收款说明");
        } else if (mAuthInfo.getBankPersonType() == 3) {
            mAttachmentGroup.setVisibility(VISIBLE);
            mAttachmentLabel.setText("业务说明");
            mAttachment.setTitle("点击上传业务说明");
        } else {
            clearOptionalData();
            mAttachmentGroup.setVisibility(GONE);
        }
    }

    private void clearOptionalData() {
        if (TextUtils.isEmpty(mAttachment.getImgUrl())) return;
        mAttachment.deleteImage();
        deleteImage(mAttachment);
    }

    @Override
    public void setImageUrl(String url) {
        mAttachment.setTag(url);
        mAttachment.showImage(url);
        updateImage(mAttachment);
    }

    @Override
    public String getTitle() {
        return "结算信息(3/4)";
    }

    @Override
    public void setCommitListener(OnClickListener listener) {
        mCommitListener = listener;
    }

    @Override
    public boolean verifyValidity() {
        if (!mPersonName.getText().toString().matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$")) {
            ToastUtils.showShort(getContext(), "开户名中请勿包含特殊字符");
            return false;
        }
        if (!mCardNo.getText().toString().matches("^([1-9])(\\d{15}|\\d{17,18})$")) {
            ToastUtils.showShort(getContext(), "请输入正确的银行账户号");
            return false;
        }
        return true;
    }

    @Override
    public void inflateInfo() {
        mAuthInfo.setReceiverName(mPersonName.getText().toString());
        mAuthInfo.setBankAccount(mCardNo.getText().toString());
    }

    @OnTextChanged(value = {R.id.asi_person_name, R.id.asi_bank_name,
            R.id.asi_account_type, R.id.asi_card_no})
    public void onTextChanged() {
        updateConfirmStatus();
    }

    private void deleteImage(View view) {
        view.setTag("");
        updateImage(view);
    }

    private void updateImage(View view) {
        if (mAuthInfo.getBankPersonType() == 2) {
            mAuthInfo.setProxyProtocol(view.getTag().toString());
        } else mAuthInfo.setImgProxyContract(view.getTag().toString());
        updateConfirmStatus();
    }

    protected void updateConfirmStatus() {
        mNext.setEnabled(!TextUtils.isEmpty(mPersonName.getText())
                && !TextUtils.isEmpty(mBankName.getText())
                && !TextUtils.isEmpty(mAccountType.getText())
                && !TextUtils.isEmpty(mCardNo.getText())
                && (mAttachmentGroup.getVisibility() == GONE
                || !TextUtils.isEmpty(mAttachment.getImgUrl())));
    }

    @OnClick(R.id.asi_next)
    public void next(View view) {
        if (verifyValidity() && mCommitListener != null) {
            inflateInfo();
            mCommitListener.onClick(view);
        }
    }

    @OnClick(R.id.asi_bank_name)
    public void selectBank(View view) {
        BankListActivity.start((Activity) getContext(), mAuthInfo.getBankNo());
    }

    @OnClick(R.id.asi_account_type)
    public void selectAccountType() {
        if (mAccountTypeDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("对公账户", String.valueOf(1)));
            list.add(new NameValue("法人账户", String.valueOf(2)));
            int selectIndex = getSelectIndex(mAuthInfo.getBankPersonType());
            NameValue cur = null;
            if (selectIndex != -1) cur = list.get(selectIndex);
            mAccountTypeDialog = SingleSelectionDialog
                    .newBuilder((Activity) getContext(), NameValue::getName)
                    .setTitleText("选择账户类型")
                    .refreshList(list)
                    .setOnSelectListener(nameValue -> {
                        mAuthInfo.setBankPersonType(Integer.valueOf(nameValue.getValue()));
                        updateAttachmentVisibility();
                        mAccountType.setText(nameValue.getName());
                    })
                    .select(cur)
                    .create();
        }
        mAccountTypeDialog.show();
    }

    /**
     * 根据账户类型 返回单选窗口里所在的index
     */
    private int getSelectIndex(int bankType) {
        if (bankType == 1) {
            return 0;
        } else if (bankType == 2) {
            return 1;
        }
        return -1;
    }

    @Override
    public void setBankData(BankBean bean) {
        mAuthInfo.setBankName(bean.getBankName());
        mAuthInfo.setBankNo(bean.getBankNo());
        mAuthInfo.setBankCode(bean.getBankCode());
        mBankName.setText(bean.getBankName());
    }
}
