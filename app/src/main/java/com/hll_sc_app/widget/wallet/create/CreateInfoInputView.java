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
import com.hll_sc_app.app.wallet.account.create.ICreateAccountContract;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.wallet.AreaSelectDialog;

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
    protected TextView mName;
    @BindView(R.id.wci_short_name)
    protected EditText mShortName;
    @BindView(R.id.wci_type)
    protected TextView mType;
    @BindView(R.id.wci_region)
    protected TextView mRegion;
    @BindView(R.id.wci_detail_address)
    protected EditText mDetailAddress;
    @BindView(R.id.wci_contact)
    protected EditText mContact;
    @BindView(R.id.wci_phone)
    protected EditText mPhone;
    @BindView(R.id.wci_email)
    protected EditText mEmail;
    @BindView(R.id.cii_confirm)
    TextView mConfirm;
    @BindView(R.id.cii_check_box)
    CheckBox mCheckBox;
    protected AuthInfo mAuthInfo;
    private SingleSelectionDialog mTypeDialog;
    private AreaSelectDialog mAreaSelectDialog;
    private OnClickListener mConfirmListener;

    public CreateInfoInputView(Context context) {
        this(context, null);
    }

    public CreateInfoInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateInfoInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void initData(AuthInfo info) {
        mAuthInfo = info;
        mName.setText(info.getCompanyName());
        mShortName.setText(info.getCompanyShortName());
        mType.setText(info.getUnit());
        mDetailAddress.setText(info.getBusinessAddress());
        mContact.setText(info.getOperatorName());
        mPhone.setText(info.getOperatorMobile());
        mEmail.setText(info.getOperatorEmail());
        if (!TextUtils.isEmpty(info.getLicenseProvinceName())) {
            String licenseAddress =
                    info.getLicenseProvinceName() + "-" + info.getLicenseCityName() + "-" + info.getLicenseDistrictName();
            mRegion.setText(licenseAddress);
            AreaInfo provinceArea = new AreaInfo(info.getLicenseProvinceCode(), info.getLicenseProvinceName());
            mAreaSelectDialog.setProvice(provinceArea);
            AreaInfo cityArea = new AreaInfo(info.getLicenseCityCode(), info.getLicenseCityName());
            mAreaSelectDialog.setCity(cityArea);
            AreaInfo distributeArea = new AreaInfo(info.getLicenseDistrictCode(), info.getLicenseDistrictName());
            mAreaSelectDialog.setDistribute(distributeArea);
        }
    }

    protected void initView() {
        bindView();
        mPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    protected void bindView() {
        View view = View.inflate(getContext(), R.layout.view_wallet_create_info_input, this);
        ButterKnife.bind(this, view);
    }

    public void setRegion(CharSequence text) {
        mRegion.setText(text);
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
                        mAuthInfo.setUnit(nameValue.getName());
                        mAuthInfo.setUnitType(Integer.parseInt(nameValue.getValue()));
                    })
                    .create();
        }
        mTypeDialog.show();
    }

    @OnClick(R.id.wci_region)
    public void showAreaDialog(View view) {
        if (mAreaSelectDialog == null) return;
        mAreaSelectDialog.show();
    }

    public void setAreaSelectListener(AreaSelectDialog.NetAreaWindowEvent event) {
        mAreaSelectDialog = new AreaSelectDialog((Activity) getContext());
        mAreaSelectDialog.addNetAreaWindowEvent(event);
    }

    public void setAreaList(List<AreaInfo> areaInfoList) {
        if (!CommonUtils.isEmpty(areaInfoList)) {
            switch (areaInfoList.get(0).getAreaType()) {
                case ICreateAccountContract.AreaType.PROVINCE:
                    mAreaSelectDialog.setProvinces(areaInfoList);
                    break;
                case ICreateAccountContract.AreaType.CITY:
                    mAreaSelectDialog.setCitys(areaInfoList);
                    break;
                case ICreateAccountContract.AreaType.DISTRIBUTE:
                    mAreaSelectDialog.setDistributes(areaInfoList);
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick(R.id.cii_confirm)
    void confirm(View view) {
        if (verifyValidity() && mConfirmListener != null) {
            mAuthInfo.setBusinessAddress(mDetailAddress.getText().toString());
            mAuthInfo.setCompanyShortName(mShortName.getText().toString());
            mAuthInfo.setOperatorName(mContact.getText().toString());
            mAuthInfo.setOperatorMobile(mPhone.getText().toString());
            mAuthInfo.setOperatorEmail(mEmail.getText().toString());
            mConfirmListener.onClick(view);
        }
    }

    protected boolean verifyValidity() {
        return true;
    }

    @OnClick({R.id.cii_wallet_protocol, R.id.cii_platform_protocol})
    void onViewClicked(View view) {
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
    void onCheckedChanged(boolean checked) {
        updateConfirmStatus();
    }

    protected void updateConfirmStatus() {
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
