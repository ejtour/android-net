package com.hll_sc_app.app.wallet.authentication;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.PERMANENT_DATE;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformDate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.bean.wallet.WalletInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 联系人
 *
 * @author zc
 */
public class LinkInfoFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment {

    @BindView(R.id.edt_link_man)
    EditText mEdtLinkman;
    @BindView(R.id.edt_identity)
    EditText mEdtIdentity;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_mail)
    EditText mEdtMail;
    @BindView(R.id.edt_identity_begin)
    TextView mEdtIdentityBegin;
    @BindView(R.id.edt_identity_end)
    TextView mEdtIdentityEnd;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private boolean isStartDate;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }

    private void isSubmit() {
        mView.setSubmitButtonEnable(isInputComplete());
    }

    /**
     * 判断是否填充完整
     *
     * @return
     */
    private boolean isInputComplete() {
        WalletInfo walletInfo = mView.getWalletInfo();
        return !TextUtils.isEmpty(walletInfo.getOperatorName())
                && !TextUtils.isEmpty(walletInfo.getContactIDCardNo())
                && !TextUtils.isEmpty(walletInfo.getOperatorMobile())
                && !TextUtils.isEmpty(walletInfo.getOperatorEmail())
                && !TextUtils.isEmpty(walletInfo.getOperatorPeriodBeginDate())
                && !TextUtils.equals("0", walletInfo.getOperatorPeriodBeginDate())
                && !TextUtils.isEmpty(walletInfo.getOperatorPeriod())
                && !TextUtils.equals("0", walletInfo.getOperatorPeriod());
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        isSubmit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_link_info, null);
    }

    @Override
    protected void initData() {
        //初始化显示页面
        WalletInfo walletInfo = mView.getWalletInfo();
        mEdtLinkman.setText(walletInfo.getOperatorName());
        mEdtIdentity.setText(walletInfo.getContactIDCardNo());
        mEdtPhone.setText(walletInfo.getOperatorMobile());
        mEdtMail.setText(walletInfo.getOperatorEmail());
        mEdtIdentityBegin.setText(transformDate(walletInfo.getOperatorPeriodBeginDate()));
        mEdtIdentityEnd.setText(transformDate(walletInfo.getOperatorPeriod()));
        //绑定文本输入框的监听
        initTextViewWatch();
    }


    @OnClick({R.id.edt_identity_begin, R.id.edt_identity_end})
    public void onClick(View view) {
        WalletInfo walletInfo = mView.getWalletInfo();
        switch (view.getId()) {
            case R.id.edt_identity_begin:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    walletInfo.setOperatorPeriod("");
                    mEdtIdentityEnd.setText("");
                    if (index == 0) {
                        walletInfo.setOperatorPeriodBeginDate(PERMANENT_DATE);
                        walletInfo.setOperatorPeriod(PERMANENT_DATE);
                        mEdtIdentityBegin.setText("长期有效");
                        mEdtIdentityEnd.setText("长期有效");
                        isSubmit();
                    } else {
                        isStartDate = true;
                        showDateWindow(true);
                    }
                });
                break;
            case R.id.edt_identity_end:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    if (index == 0) {
                        walletInfo.setOperatorPeriod(PERMANENT_DATE);
                        mEdtIdentityEnd.setText("长期有效");
                        isSubmit();
                    } else {
                        isStartDate = false;
                        showDateWindow(false);
                    }
                });
                break;
            default:
                break;
        }
    }


    private void showDateWindow(boolean isStart) {
        WalletInfo walletInfo = mView.getWalletInfo();
        CommonMethod.showDate((BaseLoadActivity) mView, isStart, walletInfo.getLpIDCardPeriodBeginDate(), walletInfo.getLpIDCardPeriod(), (sDate, oDate) -> {
            if (isStartDate) {
                mEdtIdentityBegin.setText(oDate);
                walletInfo.setOperatorPeriodBeginDate(sDate);
            } else {
                mEdtIdentityEnd.setText(oDate);
                walletInfo.setOperatorPeriod(sDate);
            }
            isSubmit();
        });
    }

    private void initTextViewWatch() {
        mEdtLinkman.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setOperatorName(value);
        }));
        mEdtIdentity.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setContactIDCardNo(value);
        }));
        mEdtPhone.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setOperatorMobile(value);
        }));
        mEdtMail.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setOperatorEmail(value);
        }));
    }

    private TextWatcher generateWatcher(CommonMethod.AddInputChangeWalletInfo addInputChangeWalletInfo) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addInputChangeWalletInfo.onChanged(s.toString());
                isSubmit();
            }
        };
    }
}
