package com.hll_sc_app.widget.wallet.auth;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.account.IInfoInputView;
import com.hll_sc_app.app.wallet.common.WalletHelper;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Utils;
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

public class AuthPersonInputView extends ConstraintLayout implements IInfoInputView {
    @BindView(R.id.api_next)
    TextView mNext;
    @BindView(R.id.api_person_name)
    EditText mPersonName;
    @BindView(R.id.api_card_type)
    TextView mCardType;
    @BindView(R.id.api_card_no)
    EditText mCardNo;
    @BindView(R.id.api_start_date)
    TextView mStartDate;
    @BindView(R.id.api_end_date)
    TextView mEndDate;
    @BindView(R.id.api_contact)
    EditText mContact;
    @BindView(R.id.api_card_photo_front)
    ImgUploadBlock mCardPhotoFront;
    @BindView(R.id.api_card_photo_behind)
    ImgUploadBlock mCardPhotoBehind;
    @BindView(R.id.api_consignor_name)
    EditText mConsignorName;
    @BindView(R.id.api_consignor_card_no)
    EditText mConsignorCardNo;
    @BindView(R.id.api_consignor_card_front)
    ImgUploadBlock mConsignorCardFront;
    @BindView(R.id.api_consignor_card_behind)
    ImgUploadBlock mConsignorCardBehind;
    @BindView(R.id.api_consignor_photo)
    ImgUploadBlock mConsignorPhoto;
    @BindView(R.id.api_consignor_group)
    Group mConsignorGroup;
    @BindViews({R.id.api_card_photo_front, R.id.api_card_photo_behind,
            R.id.api_consignor_card_front, R.id.api_consignor_card_behind, R.id.api_consignor_photo})
    List<ImgUploadBlock> mUploadBlockList;
    private ImgUploadBlock mCurUpload;
    private AuthInfo mAuthInfo;
    private OnClickListener mCommitListener;
    private boolean mIsStartDate;
    private DateWindow mDateWindow;
    private SingleSelectionDialog mCardTypeDialog;

    public AuthPersonInputView(Context context) {
        this(context, null);
    }

    public AuthPersonInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthPersonInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_wallet_auth_person_input, this);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        ButterKnife.apply(mUploadBlockList, (view, index) -> {
            view.setOnDeleteListener(this::deleteImage);
            view.setMaxSize(2097152);
        });
    }

    @Override
    public void initData(AuthInfo info) {
        mAuthInfo = info;
        mPersonName.setText(info.getLpName());
        mCardType.setText(WalletHelper.transformCardType(info.getLpCardType()));
        mCardNo.setText(info.getLpIDCardNo());
        mStartDate.setText(WalletHelper.transformDate(info.getLpIDCardPeriodBeginDate()));
        mEndDate.setText(WalletHelper.transformDate(info.getLpIDCardPeriod()));
        mContact.setText(info.getLpPhone());
        mCardPhotoFront.showImage(info.getImgLPIDCardFront());
        mCardPhotoBehind.showImage(info.getImgLPIDCardBack());
        mConsignorName.setText(info.getProxyName());
        mConsignorCardNo.setText(info.getProxyIDCardNo());
        mConsignorCardFront.showImage(info.getImgProxyIDCardFront());
        mConsignorCardBehind.showImage(info.getImgProxyIDCardBack());
        mConsignorPhoto.showImage(info.getImgContactProxyContract());
        updateConsignorVisibility();
    }

    private void updateConsignorVisibility() {
        if (mAuthInfo.getLpCardType() == 0) {
            mCardNo.setKeyListener(DigitsKeyListener.getInstance("1234567890xX"));
            mCardNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
            clearOptionalData();
        } else {
            mCardNo.setKeyListener(DigitsKeyListener.getInstance("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));
            mCardNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(80)});
        }
        mConsignorGroup.setVisibility(mAuthInfo.getLpCardType() == 0 ? GONE : VISIBLE);
    }

    private void clearOptionalData() {
        String cardNo = mCardNo.getText().toString();
        if (!TextUtils.isEmpty(cardNo)) {
            String result = cardNo.replaceAll("[^0-9xX]", "");
            mCardNo.setText(result);
            mCardNo.setSelection(result.length());
        }
        mConsignorName.setText("");
        mAuthInfo.setProxyName("");
        mConsignorCardNo.setText("");
        mAuthInfo.setProxyIDCardNo("");
        if (!TextUtils.isEmpty(mConsignorCardFront.getImgUrl())) {
            mConsignorCardFront.deleteImage();
            deleteImage(mConsignorCardFront);
        }
        if (!TextUtils.isEmpty(mConsignorCardBehind.getImgUrl())) {
            mConsignorCardBehind.deleteImage();
            deleteImage(mConsignorCardBehind);
        }
        if (!TextUtils.isEmpty(mConsignorPhoto.getImgUrl())) {
            mConsignorPhoto.deleteImage();
            deleteImage(mConsignorPhoto);
        }
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
        return "法人信息(2/4)";
    }

    @Override
    public void setCommitListener(OnClickListener listener) {
        mCommitListener = listener;
    }

    @Override
    public boolean verifyValidity() {
        if (!mPersonName.getText().toString().matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$")) {
            ToastUtils.showShort(getContext(), "法人姓名中请勿包含特殊字符");
            return false;
        }
        if (!Utils.checkPhone(mContact.getText().toString())) {
            ToastUtils.showShort(getContext(), "请输入正确的法人联系电话");
            return false;
        }
        if (mAuthInfo.getLpCardType() != 0 && !mConsignorName.getText().toString().matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$")) {
            ToastUtils.showShort(getContext(), "授权联系人姓名中请勿包含特殊字符");
            return false;
        }
        if (mAuthInfo.getLpCardType() != 0 && !mConsignorCardNo.getText().toString().toUpperCase().matches("^[1-9](\\d{14}|(\\d{16}X)|\\d{17})$")) {
            ToastUtils.showShort(getContext(), "请输入正确的授权人身份证号");
            return false;
        }
        if (mAuthInfo.getLpCardType() == 0 && !mCardNo.getText().toString().toUpperCase().matches("^[1-9](\\d{14}|(\\d{16}X)|\\d{17})$")) {
            ToastUtils.showShort(getContext(), "请输入正确的法人身份证号");
            return false;
        }
        if (Integer.parseInt(mAuthInfo.getLpIDCardPeriodBeginDate()) > Integer.parseInt(mAuthInfo.getLpIDCardPeriod())) {
            ToastUtils.showShort(getContext(), "起始日期不能大于结束日期");
            return false;
        }
        return true;
    }

    @Override
    public void inflateInfo() {
        mAuthInfo.setLpName(mPersonName.getText().toString());
        mAuthInfo.setLpIDCardNo(mCardNo.getText().toString().toUpperCase());
        mAuthInfo.setLpPhone(mContact.getText().toString().replaceAll("\\s+", ""));
        mAuthInfo.setProxyName(mConsignorName.getText().toString());
        mAuthInfo.setProxyIDCardNo(mConsignorCardNo.getText().toString());
    }

    @OnClick(R.id.api_next)
    public void next(View view) {
        if (verifyValidity() && mCommitListener != null) {
            inflateInfo();
            mCommitListener.onClick(view);
        }
    }

    @OnClick(R.id.api_card_type)
    public void selectCardType() {
        if (mCardTypeDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("身份证", String.valueOf(0)));
            list.add(new NameValue("来往内地通行证", String.valueOf(2)));
            list.add(new NameValue("台胞证", String.valueOf(4)));
            list.add(new NameValue("护照", String.valueOf(9)));
            int selectIndex = getSelectIndex(mAuthInfo.getLpCardType());
            NameValue cur = null;
            if (selectIndex != -1) cur = list.get(selectIndex);
            mCardTypeDialog = SingleSelectionDialog
                    .newBuilder((Activity) getContext(), NameValue::getName)
                    .setTitleText("请选择法人证件类型")
                    .refreshList(list)
                    .setOnSelectListener(nameValue -> {
                        mAuthInfo.setLpCardType(Integer.valueOf(nameValue.getValue()));
                        updateConsignorVisibility();
                        mCardType.setText(nameValue.getName());
                    })
                    .select(cur)
                    .create();
        }
        mCardTypeDialog.show();
    }

    /**
     * 根据证件类型 返回单选窗口里所在的index
     */
    private int getSelectIndex(int cardType) {
        if (cardType == 0) {
            return 0;
        } else if (cardType == 2) {
            return 1;
        } else if (cardType == 4) {
            return 2;
        } else if (cardType == 9) {
            return 3;
        }
        return -1;
    }

    @OnClick(R.id.api_start_date)
    public void selectStartDate() {
        WalletHelper.showLongValidDateDialog((Activity) getContext(), (dialog, item) -> {
            dialog.dismiss();
            if (item == 0) {
                mAuthInfo.setLpIDCardPeriodBeginDate(WalletHelper.PERMANENT_DATE);
                mAuthInfo.setLpIDCardPeriod(WalletHelper.PERMANENT_DATE);
                mStartDate.setText("长期有效");
                mEndDate.setText("长期有效");
            } else showDateWindow(true);
        });
    }

    @OnClick(R.id.api_end_date)
    public void selectEndDate() {
        WalletHelper.showLongValidDateDialog((Activity) getContext(), (dialog, item) -> {
            dialog.dismiss();
            if (item == 0) {
                mAuthInfo.setLpIDCardPeriod(WalletHelper.PERMANENT_DATE);
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
                    mAuthInfo.setLpIDCardPeriodBeginDate(sDate);
                    mStartDate.setText(tDate);
                } else {
                    mAuthInfo.setLpIDCardPeriod(sDate);
                    mEndDate.setText(tDate);
                }
            });
        }
        WalletHelper.showDate((Activity) getContext(), mDateWindow, start, mAuthInfo.getLicenseBeginDate(), mAuthInfo.getLicensePeriod());
    }

    @OnTextChanged(value = {R.id.api_person_name, R.id.api_card_type, R.id.api_card_no,
            R.id.api_start_date, R.id.api_end_date, R.id.api_contact, R.id.api_consignor_name,
            R.id.api_consignor_card_no})
    public void onTextChanged() {
        updateConfirmStatus();
    }

    @OnTouch({R.id.api_card_photo_front, R.id.api_card_photo_behind,
            R.id.api_consignor_card_front, R.id.api_consignor_card_behind,
            R.id.api_consignor_photo})
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
            case R.id.api_card_photo_front:
                mAuthInfo.setImgLPIDCardFront(view.getTag().toString());
                break;
            case R.id.api_card_photo_behind:
                mAuthInfo.setImgLPIDCardBack(view.getTag().toString());
                break;
            case R.id.api_consignor_card_front:
                mAuthInfo.setImgProxyIDCardFront(view.getTag().toString());
                break;
            case R.id.api_consignor_card_behind:
                mAuthInfo.setImgProxyIDCardBack(view.getTag().toString());
                break;
            case R.id.api_consignor_photo:
                mAuthInfo.setImgContactProxyContract(view.getTag().toString());
                break;
        }
        updateConfirmStatus();
    }

    private void updateConfirmStatus() {
        mNext.setEnabled(!TextUtils.isEmpty(mPersonName.getText().toString())
                && !TextUtils.isEmpty(mCardType.getText().toString())
                && !TextUtils.isEmpty(mCardNo.getText().toString())
                && !TextUtils.isEmpty(mStartDate.getText().toString())
                && !TextUtils.isEmpty(mEndDate.getText().toString())
                && !TextUtils.isEmpty(mContact.getText().toString())
                && !TextUtils.isEmpty(mCardPhotoFront.getImgUrl())
                && !TextUtils.isEmpty(mCardPhotoBehind.getImgUrl())
                && (mConsignorGroup.getVisibility() == GONE || (!TextUtils.isEmpty(mConsignorName.getText().toString())
                && !TextUtils.isEmpty(mConsignorCardNo.getText().toString())
                && !TextUtils.isEmpty(mConsignorCardFront.getImgUrl())
                && !TextUtils.isEmpty(mConsignorCardBehind.getImgUrl())
                && !TextUtils.isEmpty(mConsignorPhoto.getImgUrl())))
        );
    }
}
