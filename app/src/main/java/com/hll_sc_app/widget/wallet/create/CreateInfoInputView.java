package com.hll_sc_app.widget.wallet.create;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class CreateInfoInputView extends ConstraintLayout {
    @BindView(R.id.wci_name)
    TextView mName;
    @BindView(R.id.wci_short_name)
    EditText mShortName;
    @BindView(R.id.wci_type)
    TextView mType;
    @BindView(R.id.wci_region)
    TextView mRegion;
    @BindView(R.id.wci_detail_address)
    EditText mDetailAddress;
    @BindView(R.id.wci_contact)
    EditText mContact;
    @BindView(R.id.wci_phone)
    EditText mPhone;
    @BindView(R.id.wci_email)
    EditText mEmail;
    @BindView(R.id.cii_confirm)
    TextView mConfirm;
    @BindView(R.id.cii_check_box)
    CheckBox mCheckBox;
    private SingleSelectionDialog mTypeDialog;
    private OnClickListener mConfirmListener;

    public CreateInfoInputView(Context context) {
        this(context, null);
    }

    public CreateInfoInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateInfoInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_wallet_create_info_input, this);
        ButterKnife.bind(this, view);
        initView();
    }

    public void initData(AuthInfo info) {
        mName.setText(info.getCompanyName());
        mShortName.setText(info.getCompanyShortName());
        mType.setText(info.getUnit());
        mType.setTag(info.getUnitType());
        mDetailAddress.setText(info.getBusinessAddress());
        mContact.setText(info.getOperatorName());
        mPhone.setText(info.getOperatorMobile());
        mEmail.setText(info.getOperatorEmail());
    }

    private void initView() {
        mPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    public void setCompanyName(CharSequence text) {
        mName.setText(text);
    }

    public void setRegion(CharSequence text) {
        mRegion.setText(text);
    }

    public void setRegionClickListener(OnClickListener listener) {
        mRegion.setOnClickListener(listener);
    }

    public void setConfirmClickListener(OnClickListener listener) {
        mConfirmListener = listener;
    }

    @OnClick(R.id.wci_type)
    public void selectType() {
        if (mTypeDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("企业", "1"));
            list.add(new NameValue("个体工商户", "2"));
            mTypeDialog = SingleSelectionDialog.newBuilder((Activity) getContext(), NameValue::getName)
                    .refreshList(list)
                    .setTitleText("公司类型")
                    .setOnSelectListener(nameValue -> {
                        mType.setText(nameValue.getName());
                        mType.setTag(nameValue.getValue());
                    })
                    .create();
        }
        mTypeDialog.show();
    }

    public void updateAuthInfo(AuthInfo info) {
        info.setUnit(mType.getText().toString());
        info.setUnitType(Integer.parseInt(mType.getTag().toString()));
        info.setBusinessAddress(mDetailAddress.getText().toString());
        info.setCompanyShortName(mShortName.getText().toString());
        info.setOperatorName(mContact.getText().toString());
        info.setOperatorMobile(mPhone.getText().toString());
        info.setOperatorMobile(mEmail.getText().toString());
    }

    @OnClick(R.id.cii_confirm)
    public void confirm(View view) {
        if (verifyValidity() && mConfirmListener != null) {
            mConfirmListener.onClick(view);
        }
    }

    private boolean verifyValidity() {
        return true;
    }

    @OnClick({R.id.cii_wallet_protocol, R.id.cii_platform_protocol})
    public void onViewClicked(View view) {
        String url;
        String title;
        switch (view.getId()) {
            case R.id.cii_wallet_protocol:
                url = "http://res.hualala.com/group3/M03/C8/F8/wKgVbVy5nQfeHoVhAASAnoY2yxM407.pdf";
                title = "\"智慧支付钱包\"授权服务协议";
                break;
            case R.id.cii_platform_protocol:
                url = "http://res.hualala.com/group3/M03/C8/BD/wKgVbVy5nNmrbBOnAAK-Z3znjNU181.pdf";
                title = "开发平台授权协议";
                break;
            default:
                return;
        }
        WebActivity.start(title, "file:///android_asset/pdf/pdfView.html?" + url);
    }

    @OnTextChanged(value = {R.id.wci_name, R.id.wci_short_name, R.id.wci_type,
            R.id.wci_region, R.id.wci_detail_address,
            R.id.wci_contact, R.id.wci_phone, R.id.wci_email},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable s) {
        updateConfirmStatus();
    }

    @OnCheckedChanged(R.id.cii_check_box)
    public void onCheckedChanged(boolean checked) {
        updateConfirmStatus();
    }

    private void updateConfirmStatus() {
        mConfirm.setEnabled(!TextUtils.isEmpty(mName.getText())
                && !TextUtils.isEmpty(mShortName.getText())
                && !TextUtils.isEmpty(mType.getText())
                && !TextUtils.isEmpty(mRegion.getText())
                && !TextUtils.isEmpty(mDetailAddress.getText())
                && !TextUtils.isEmpty(mContact.getText())
                && !TextUtils.isEmpty(mPhone.getText())
                && !TextUtils.isEmpty(mEmail.getText())
                && mCheckBox.isChecked());
    }
}
