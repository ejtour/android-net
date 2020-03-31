package com.hll_sc_app.app.wallet.authentication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.bank.BankListActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.SingleSelectionDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 小微商户
 * 结算信息
 *
 * @author zc
 */
public class SettleInfoSmallFragment extends BaseLazyFragment implements IAuthenticationContract.ISettleInfoFragment, IAuthenticationContract.IUploadFragment {

    @BindView(R.id.txt_alter_2)
    TextView mTxtAlter;
    @BindView(R.id.edt_bank_account)
    EditText mEdtBankAccount;
    @BindView(R.id.txt_select_bank)
    TextView mTxtSelectBank;
    @BindView(R.id.img_card_front)
    ImgUploadBlock mImgCardFront;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private SingleSelectionDialog.Builder<NameValue> mSelectDialogBuilder;

    @Override
    public void setUploadUrl(String url) {
        WalletInfo walletInfo = mView.getWalletInfo();
        mImgCardFront.showImage(url);
        walletInfo.setBackCardPic(url);
        isSubmit();
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
        return !TextUtils.isEmpty(walletInfo.getBackCardPic()) &&
                !TextUtils.isEmpty(walletInfo.getBankAccount()) &&
                !TextUtils.isEmpty(walletInfo.getBankName())
                ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }

    @Override
    public void updateBank(BankBean bankListBean) {
        WalletInfo walletInfo = mView.getWalletInfo();
        walletInfo.setBankName(bankListBean.getBankName());
        walletInfo.setBankNO(bankListBean.getBankNo());
        walletInfo.setBankCode(bankListBean.getBankCode());
        mTxtSelectBank.setText(bankListBean.getBankName());
        isSubmit();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        initView();
        isSubmit();
    }

    private void initView() {
        WalletInfo walletInfo = mView.getWalletInfo();
        mImgCardFront.showImage(walletInfo.getBackCardPic());
        mEdtBankAccount.setText(walletInfo.getBankAccount());
        mTxtSelectBank.setText(walletInfo.getBankName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_small_step_3, null);
    }

    @Override
    protected void initData() {
        SpannableString alter = new SpannableString("只能绑定法人本人的银行卡");
        alter.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtAlter.setText(alter);
        //显示数据
        initUpload(mImgCardFront, "点击上传银行卡");
        //绑定文本输入框的监听
        initTextViewWatch();
        //绑定开户行点击事件
        WalletInfo walletInfo = mView.getWalletInfo();
        mTxtSelectBank.setOnClickListener(v -> {
            BankListActivity.start(getActivity(), walletInfo.getBankNO());
        });
    }

    private void initTextViewWatch() {
        mEdtBankAccount.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setBankAccount(value);
        }));
    }

    /**
     * 设置上传组件的公共配置
     *
     * @param title
     */
    private void initUpload(ImgUploadBlock imgUploadBlock, String title) {
        CommonMethod.setUploadImg(imgUploadBlock, title, v -> {
            imgUploadBlock.showImage("");
            WalletInfo walletInfo = mView.getWalletInfo();
            walletInfo.setImgBankLicense("");
            isSubmit();
        }, uploadImgBlock -> {
            return true;
        });
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
