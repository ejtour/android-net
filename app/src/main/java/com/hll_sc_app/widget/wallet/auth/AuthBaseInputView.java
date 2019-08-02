package com.hll_sc_app.widget.wallet.auth;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.account.IInfoInputView;
import com.hll_sc_app.app.wallet.common.WalletHelper;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.wallet.create.CreateInfoInputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class AuthBaseInputView extends CreateInfoInputView implements IInfoInputView {
    @BindView(R.id.abi_credit_code)
    EditText mCreditCode;
    @BindView(R.id.abi_start_date)
    TextView mStartDate;
    @BindView(R.id.abi_end_date)
    TextView mEndDate;
    @BindView(R.id.abi_business_scope)
    EditText mBusinessScope;
    @BindView(R.id.abi_register_address)
    EditText mRegisterAddress;
    @BindView(R.id.abi_business_license)
    ImgUploadBlock mBusinessLicense;
    @BindView(R.id.abi_create_account_license)
    ImgUploadBlock mCreateAccountLicense;
    @BindView(R.id.abi_next)
    TextView mNext;
    private ImgUploadBlock mCurUpload;
    private boolean mIsStartDate;
    private DateWindow mDateWindow;

    public AuthBaseInputView(Context context) {
        this(context, null);
    }

    public AuthBaseInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthBaseInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void initView() {
        super.initView();
        mBusinessLicense.setMaxSize(2097152);
        mCreateAccountLicense.setMaxSize(2097152);
        mBusinessLicense.setOnDeleteListener(this::deleteImage);
        mCreateAccountLicense.setOnDeleteListener(this::deleteImage);
    }

    @Override
    protected void bindView() {
        View view = View.inflate(getContext(), R.layout.view_wallet_auth_base_input, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData(AuthInfo info) {
        super.initData(info);
        mCreditCode.setText(info.getLicenseCode());
        mStartDate.setText(WalletHelper.transformDate(info.getLicenseBeginDate()));
        mEndDate.setText(WalletHelper.transformDate(info.getLicensePeriod()));
        mBusinessScope.setText(info.getBusiScope());
        mRegisterAddress.setText(info.getLicenseAddress());
        if (!TextUtils.isEmpty(info.getImgLicense()))
            mBusinessLicense.showImage(info.getImgLicense());
        if (!TextUtils.isEmpty(info.getImgBankLicense()))
            mCreateAccountLicense.showImage(info.getImgBankLicense());
    }

    private void deleteImage(View view) {
        view.setTag("");
        updateImage(view);
    }

    private void updateImage(View view) {
        if (view.getId() == R.id.abi_business_license)
            mAuthInfo.setImgLicense(view.getTag().toString());
        else mAuthInfo.setImgBankLicense(view.getTag().toString());
        updateConfirmStatus();
    }

    @OnTextChanged(value = {R.id.abi_credit_code, R.id.abi_start_date, R.id.abi_end_date,
            R.id.abi_business_scope, R.id.abi_register_address},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        updateConfirmStatus();
    }

    protected void updateConfirmStatus() {
        mNext.setEnabled(!TextUtils.isEmpty(mName.getText())
                && !TextUtils.isEmpty(mShortName.getText())
                && !TextUtils.isEmpty(mType.getText())
                && !TextUtils.isEmpty(mRegion.getText())
                && !TextUtils.isEmpty(mDetailAddress.getText())
                && !TextUtils.isEmpty(mContact.getText())
                && !TextUtils.isEmpty(mPhone.getText())
                && !TextUtils.isEmpty(mEmail.getText())
                && !TextUtils.isEmpty(mCreditCode.getText())
                && !TextUtils.isEmpty(mStartDate.getText())
                && !TextUtils.isEmpty(mEndDate.getText())
                && !TextUtils.isEmpty(mBusinessScope.getText())
                && !TextUtils.isEmpty(mRegisterAddress.getText())
                && !TextUtils.isEmpty(mBusinessLicense.getImgUrl())
                && !TextUtils.isEmpty(mCreateAccountLicense.getImgUrl()));
    }

    @OnTouch({R.id.abi_business_license, R.id.abi_create_account_license})
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mCurUpload = (ImgUploadBlock) view;
        }
        return false;
    }

    @Override
    public void setImageUrl(String url) {
        if (mCurUpload != null) {
            mCurUpload.showImage(url);
            mCurUpload.setTag(url);
            updateImage(mCurUpload);
        }
    }

    @Override
    public String getTitle() {
        return "基本信息(1/4)";
    }

    @OnClick(R.id.abi_start_date)
    public void selectStartDate() {
        WalletHelper.showLongValidDateDialog((Activity) getContext(), (dialog, item) -> {
            dialog.dismiss();
            mAuthInfo.setLicensePeriod("");
            mEndDate.setText("");
            if (item == 0) {
                mAuthInfo.setLicenseBeginDate(WalletHelper.PERMANENT_DATE);
                mAuthInfo.setLicensePeriod(WalletHelper.PERMANENT_DATE);
                mStartDate.setText("长期有效");
                mEndDate.setText("长期有效");
            } else showDateWindow(true);
        });
    }

    @OnClick(R.id.abi_end_date)
    public void selectEndDate() {
        WalletHelper.showLongValidDateDialog((Activity) getContext(), (dialog, item) -> {
            dialog.dismiss();
            if (item == 0) {
                mAuthInfo.setLicensePeriod(WalletHelper.PERMANENT_DATE);
                mEndDate.setText("长期有效");
            } else showDateWindow(false);
        });
    }

    @OnClick(R.id.abi_next)
    public void confirm(View view) {
        if (verifyValidity() && mCommitListener != null) {
            inflateInfo();
            mCommitListener.onClick(view);
        }
    }

    @Override
    public boolean verifyValidity() {
        return true;
    }

    @Override
    public void inflateInfo() {
        super.inflateInfo();
        mAuthInfo.setLicenseCode(mCreditCode.getText().toString());
        mAuthInfo.setBusiScope(mBusinessScope.getText().toString());
        mAuthInfo.setLicenseAddress(mRegisterAddress.getText().toString());
    }

    private void showDateWindow(boolean start) {
        mIsStartDate = start;
        if (mDateWindow == null) {
            mDateWindow = new DateWindow((Activity) getContext());
            mDateWindow.setSelectListener(date -> {
                String sDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
                String tDate = CalendarUtils.format(date, CalendarUtils.FORMAT_YYYY_MM_DD_CHN);
                if (mIsStartDate) {
                    mAuthInfo.setLicenseBeginDate(sDate);
                    mStartDate.setText(tDate);
                } else {
                    mAuthInfo.setLicensePeriod(sDate);
                    mEndDate.setText(tDate);
                }
            });
        }
        WalletHelper.showDate((Activity) getContext(), mDateWindow, start, mAuthInfo.getLicenseBeginDate(), mAuthInfo.getLicensePeriod());
    }
}
