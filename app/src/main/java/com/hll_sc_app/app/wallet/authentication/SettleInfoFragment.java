package com.hll_sc_app.app.wallet.authentication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
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
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.SingleSelectionWindow;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 结算信息
 *
 * @author zc
 */
public class SettleInfoFragment extends BaseLazyFragment implements IAuthenticationContract.ISettleInfoFragment, IAuthenticationContract.IUploadFragment {

    @BindView(R.id.txt_alter_2)
    TextView mTxtAlter;
    @BindView(R.id.txt_account_name)
    TextView mTxtAccountName;
    @BindView(R.id.txt_select_name)
    TextView mTxtSelectName;
    @BindView(R.id.edt_public_account)
    EditText mEdtPublickAccount;
    @BindView(R.id.edt_bank_account)
    EditText mEdtBankAccount;
    @BindView(R.id.txt_select_bank)
    TextView mTxtSelectBank;
    @BindView(R.id.upload_img)
    ImgUploadBlock mUploadProof;
    @BindView(R.id.group_select_name)
    Group mGroupSelectName;
    @BindView(R.id.group_account_name)
    Group mGroupAccountName;
    @BindView(R.id.group_public_account)
    Group mGroupPublicAccount;
    @BindView(R.id.group_bank_account)
    Group mGroupBankAccount;
    @BindView(R.id.group_upload_proof)
    Group mGroupUploadProof;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private SingleSelectionDialog.Builder<NameValue> mSelectDialogBuilder;

    @Override
    public void setUploadUrl(String url) {
        WalletInfo walletInfo = mView.getWalletInfo();
        mUploadProof.showImage(url);
        walletInfo.setImgBankLicense(url);
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
        if (walletInfo.getUnitType() == 1) {
            return !TextUtils.isEmpty(walletInfo.getBankAccount())
                    && !TextUtils.isEmpty(walletInfo.getBankName())
                    && !TextUtils.isEmpty(walletInfo.getImgBankLicense());
        } else {
            boolean isComplete = !TextUtils.isEmpty(walletInfo.getBankAccount())
                    && !TextUtils.isEmpty(walletInfo.getBankName())
                    && !TextUtils.isEmpty(walletInfo.getReceiverName());
            if (walletInfo.getReceiverType() == 1) {//个体
                return isComplete;
            } else {
                return isComplete && !TextUtils.isEmpty(walletInfo.getImgBankLicense());
            }
        }
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
        String sAlerter = "";
        int alerterIndex;
        if (walletInfo.getUnitType() == 1) {//集团
            mGroupAccountName.setVisibility(View.VISIBLE);
            mGroupPublicAccount.setVisibility(View.VISIBLE);
            mGroupUploadProof.setVisibility(View.VISIBLE);
            mGroupBankAccount.setVisibility(View.GONE);
            mGroupSelectName.setVisibility(View.GONE);
            mTxtAccountName.setText(walletInfo.getCompanyName());
            walletInfo.setReceiverName(walletInfo.getCompanyName());
            walletInfo.setBankPersonType(1);
            walletInfo.setReceiverType(2);
            sAlerter = "企业商户只能绑定与营业执照名称一致的对公账户";
            alerterIndex = 8;

        } else {//个人
            sAlerter = "个体工商户只能绑定法人本人的银行卡或营业执照名称一致的对公账户!";
            alerterIndex = 9;
            mGroupSelectName.setVisibility(View.VISIBLE);
            mGroupBankAccount.setVisibility(View.VISIBLE);
            mGroupAccountName.setVisibility(View.GONE);
            mGroupPublicAccount.setVisibility(View.GONE);
            //初始化开户名选择
            if (mSelectDialogBuilder == null) {
                mSelectDialogBuilder =  SingleSelectionDialog.newBuilder(getActivity(),NameValue::getName)
                        .setTitleText("选择开户名")
                        .setOnSelectListener(itemSelectBean -> {
                            walletInfo.setReceiverName(itemSelectBean.getName());
                            mTxtSelectName.setText(itemSelectBean.getName());
                            if (TextUtils.equals(itemSelectBean.getValue(), "0")) {//法人
                                mGroupUploadProof.setVisibility(View.GONE);
                                walletInfo.setBankPersonType(2);
                                walletInfo.setReceiverType(1);
                            } else {
                                mGroupUploadProof.setVisibility(View.VISIBLE);
                                walletInfo.setBankPersonType(1);
                                walletInfo.setReceiverType(2);
                            }
                        });
            }
            NameValue bean1 = new NameValue(walletInfo.getLpName(), "0");
            NameValue bean2 = new NameValue(walletInfo.getCompanyName(), "1");
            mSelectDialogBuilder.refreshList(Arrays.asList(bean1, bean2));
            mTxtSelectName.setText(walletInfo.getReceiverName());
            if (walletInfo.getBankPersonType() == 1) {//企业
                mSelectDialogBuilder.select(bean2);
                mGroupUploadProof.setVisibility(View.VISIBLE);
            } else if (walletInfo.getBankPersonType() == 99) {//私人
                mSelectDialogBuilder.select(bean1);
                mGroupUploadProof.setVisibility(View.GONE);
            } else {
                mSelectDialogBuilder.select(null);
            }
        }
        SpannableString alter = new SpannableString(sAlerter);
        alter.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, alerterIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtAlter.setText(alter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_settle_info, null);
    }

    @Override
    protected void initData() {
        //初始化显示页面
        WalletInfo walletInfo = mView.getWalletInfo();
        //显示数据
        if (walletInfo.getUnitType() == 1) {//企业
            mEdtPublickAccount.setText(walletInfo.getBankAccount());
        } else {//个体
            mEdtBankAccount.setText(walletInfo.getBankAccount());
        }

        initUpload(mUploadProof, "点击上传开户凭证");

        mTxtSelectBank.setText(walletInfo.getBankName());
        mUploadProof.showImage(walletInfo.getImgBankLicense());
        //绑定文本输入框的监听
        initTextViewWatch();
        //绑定开户行点击事件
        mTxtSelectBank.setOnClickListener(v -> {
            BankListActivity.start(getActivity(), walletInfo.getBankNO());
        });
        mTxtSelectName.setOnClickListener(v -> {
            mSelectDialogBuilder.create().show();
        });
    }

    private void initTextViewWatch() {
        mEdtBankAccount.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setBankAccount(value);
        }));
        mEdtPublickAccount.addTextChangedListener(generateWatcher(value -> {
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
