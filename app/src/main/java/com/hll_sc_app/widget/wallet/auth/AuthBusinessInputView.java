package com.hll_sc_app.widget.wallet.auth;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.account.IInfoInputView;
import com.hll_sc_app.app.wallet.common.WalletHelper;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class AuthBusinessInputView extends ConstraintLayout implements IInfoInputView {

    @BindView(R.id.abi_confirm)
    TextView mConfirm;
    @BindView(R.id.abi_license_judgment)
    TextView mLicenseJudgment;
    @BindView(R.id.abi_start_date)
    TextView mStartDate;
    @BindView(R.id.abi_end_date)
    TextView mEndDate;
    @BindView(R.id.abi_license_photo)
    ImgUploadBlock mLicensePhoto;
    @BindView(R.id.abi_license_info_group)
    Group mLicenseInfoGroup;
    @BindView(R.id.abi_shop_doorway)
    ImgUploadBlock mShopDoorway;
    @BindView(R.id.abi_shop_indoor)
    ImgUploadBlock mShopIndoor;
    @BindViews({R.id.abi_license_photo, R.id.abi_shop_doorway, R.id.abi_shop_indoor})
    List<ImgUploadBlock> mUploadBlocks;
    private OnClickListener mCommitListener;
    private AuthInfo mAuthInfo;
    private SingleSelectionDialog mJudgementDialog;
    private ImgUploadBlock mCurUpload;
    private boolean mIsStartDate;
    private DateWindow mDateWindow;

    public AuthBusinessInputView(Context context) {
        this(context, null);
    }

    public AuthBusinessInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthBusinessInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_wallet_auth_business_input, this);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        ButterKnife.apply(mUploadBlocks, (view, index) -> {
            view.setOnDeleteListener(this::deleteImage);
            view.setMaxSize(2097152);
        });
    }

    @Override
    public void initData(AuthInfo info) {
        mAuthInfo = info;
        mLicenseJudgment.setText(info.getIsImgBusiPermission() == 0 ? "有" : "无");
        mStartDate.setText(WalletHelper.transformDate(info.getBusiPermissionBeginDate()));
        mEndDate.setText(WalletHelper.transformDate(info.getBusiPermissionEndDate()));
        mLicensePhoto.showImage(info.getImgBusiPermission());
        mShopDoorway.showImage(info.getImgBusiDoor());
        mShopIndoor.showImage(info.getImgBusiEnv());
        updateLicenseVisibility();
    }

    @Override
    public void setImageUrl(String url) {
        if (mCurUpload != null) {
            mCurUpload.setTag(url);
            mCurUpload.showImage(url);
            updateImage(mCurUpload);
        }
    }

    @Override
    public String getTitle() {
        return "经营信息(4/4)";
    }

    @Override
    public void setCommitListener(OnClickListener listener) {
        mCommitListener = listener;
    }

    @Override
    public boolean verifyValidity() {
        if (mAuthInfo.getIsImgBusiPermission() == 0 &&
                Integer.parseInt(mAuthInfo.getBusiPermissionBeginDate()) > Integer.parseInt(mAuthInfo.getBusiPermissionEndDate())) {
            ToastUtils.showShort(getContext(), "起始日期不能大于结束日期");
            return false;
        }
        return true;
    }

    @Override
    public void inflateInfo() {
        // no-op
    }

    @OnClick(R.id.abi_confirm)
    public void confirm(View view) {
        if (verifyValidity() && mCommitListener != null) {
            mCommitListener.onClick(view);
        }
    }

    @OnClick(R.id.abi_license_judgment)
    public void judgment() {
        if (mJudgementDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("有", String.valueOf(0)));
            list.add(new NameValue("无", String.valueOf(1)));
            int selectIndex = mAuthInfo.getIsImgBusiPermission();
            NameValue cur = null;
            if (selectIndex != -1) cur = list.get(selectIndex);
            mJudgementDialog = SingleSelectionDialog
                    .newBuilder((Activity) getContext(), NameValue::getName)
                    .setTitleText("是否有相关许可证")
                    .refreshList(list)
                    .setOnSelectListener(nameValue -> {
                        mAuthInfo.setIsImgBusiPermission(Integer.valueOf(nameValue.getValue()));
                        updateLicenseVisibility();
                        mLicenseJudgment.setText(nameValue.getName());
                    })
                    .select(cur)
                    .create();
        }
        mJudgementDialog.show();
    }

    @OnTextChanged(value = {R.id.abi_license_judgment, R.id.abi_start_date, R.id.abi_end_date})
    public void onTextChanged() {
        updateConfirmStatus();
    }

    @OnTouch({R.id.abi_license_photo, R.id.abi_shop_doorway, R.id.abi_shop_indoor})
    public boolean onTouch(View view) {
        mCurUpload = (ImgUploadBlock) view;
        return false;
    }

    private void deleteImage(View view) {
        view.setTag("");
        updateImage(view);
    }

    private void updateImage(View view) {
        switch (view.getId()) {
            case R.id.abi_license_photo:
                mAuthInfo.setImgBusiPermission(view.getTag().toString());
                break;
            case R.id.abi_shop_doorway:
                mAuthInfo.setImgBusiDoor(view.getTag().toString());
                break;
            case R.id.abi_shop_indoor:
                mAuthInfo.setImgBusiEnv(view.getTag().toString());
                break;
        }
        updateConfirmStatus();
    }

    private void updateConfirmStatus() {
        mConfirm.setEnabled(!TextUtils.isEmpty(mLicenseJudgment.getText().toString())
                        && !TextUtils.isEmpty(mShopDoorway.getImgUrl())
                        && !TextUtils.isEmpty(mShopIndoor.getImgUrl())
                        && (mLicenseInfoGroup.getVisibility() == GONE || (!TextUtils.isEmpty(mStartDate.getText().toString())
                        && !TextUtils.isEmpty(mEndDate.getText().toString())
                        && !TextUtils.isEmpty(mLicensePhoto.getImgUrl())
                ))
        );
    }

    private void updateLicenseVisibility() {
        mLicenseInfoGroup.setVisibility(mAuthInfo.getIsImgBusiPermission() == 0 ? VISIBLE : GONE);
    }

    @OnClick(R.id.abi_start_date)
    public void selectStartDate() {
        WalletHelper.showLongValidDateDialog((Activity) getContext(), (dialog, item) -> {
            dialog.dismiss();
            mAuthInfo.setBusiPermissionEndDate("");
            mEndDate.setText("");
            if (item == 0) {
                mAuthInfo.setBusiPermissionBeginDate(WalletHelper.PERMANENT_DATE);
                mAuthInfo.setBusiPermissionEndDate(WalletHelper.PERMANENT_DATE);
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
                mAuthInfo.setBusiPermissionEndDate(WalletHelper.PERMANENT_DATE);
                mEndDate.setText("长期有效");
            } else showDateWindow(false);
        });
    }

    private void showDateWindow(boolean start) {
        mIsStartDate = start;
        if (mDateWindow == null) {
            mDateWindow = new DateWindow((Activity) getContext());
            mDateWindow.setSelectListener(date -> {
                String sDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
                String tDate = CalendarUtils.format(date, CalendarUtils.FORMAT_YYYY_MM_DD_CHN);
                if (mIsStartDate) {
                    mAuthInfo.setBusiPermissionBeginDate(sDate);
                    mStartDate.setText(tDate);
                } else {
                    mAuthInfo.setBusiPermissionEndDate(sDate);
                    mEndDate.setText(tDate);
                }
            });
        }
        WalletHelper.showDate((Activity) getContext(), mDateWindow, start, mAuthInfo.getLicenseBeginDate(), mAuthInfo.getLicensePeriod());
    }
}
