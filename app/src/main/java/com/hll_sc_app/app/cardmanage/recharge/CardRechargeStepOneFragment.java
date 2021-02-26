package com.hll_sc_app.app.cardmanage.recharge;

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
import androidx.fragment.app.Fragment;

import com.hll_sc_app.R;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CardRechargeStepOneFragment extends Fragment {

    @BindView(R.id.edt_recharge)
    EditText mEdtRecharge;
    @BindView(R.id.edt_gift)
    EditText mEdtGift;
    @BindView(R.id.edt_mark)
    EditText mEdtMark;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNum;
    @BindView(R.id.txt_recharge)
    TextView mTxtRecharge;
    @BindView(R.id.txt_pay_type)
    TextView mTxtPayType;
    private CardManageBean mCardManageBean;//目前没用到
    private Unbinder unbinder;

    private SingleSelectionDialog mSelectPayTypeDialog;

    public static CardRechargeStepOneFragment newInstance(CardManageBean cardManageBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", cardManageBean);
        CardRechargeStepOneFragment fragment = new CardRechargeStepOneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCardManageBean = bundle.getParcelable("bean");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_manage_recharge_one, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        mEdtRecharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && (
                        !CommonUtils.isMoney(s.toString()) ||
                                Double.parseDouble(s.toString()) > 99999999.99
                )) {
                    String content = s.toString();
                    content = content.substring(0, content.length() - 1);
                    mEdtRecharge.setText(content);
                    mEdtRecharge.setSelection(content.length());
                    return;
                }
                mTxtRecharge.setEnabled(s.toString().length() > 0 && mTxtPayType.getText().toString().length() > 0);
            }
        });

        mEdtGift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && (
                        !CommonUtils.isMoney(s.toString()) ||
                                Double.parseDouble(s.toString()) > 99999999.99
                )) {
                    String content = s.toString();
                    content = content.substring(0, content.length() - 1);
                    mEdtGift.setText(content);
                    mEdtGift.setSelection(content.length());
                    return;
                }
            }
        });

        mEdtMark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTxtLeftNum.setText(String.valueOf(50 - s.toString().length()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_recharge, R.id.txt_pay_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_recharge:
                if (getActivity() instanceof ICardManageRechargeContract.IView) {
                    mCardManageBean.setCashBalanceText(mEdtRecharge.getText().toString());
                    if (TextUtils.isEmpty(mEdtGift.getText().toString())) {
                        mCardManageBean.setGiftBalanceText("0");
                    } else {
                        mCardManageBean.setGiftBalanceText(mEdtGift.getText().toString());
                    }
                    mCardManageBean.setRemark(mEdtMark.getText().toString());
                    ((ICardManageRechargeContract.IView) getActivity()).toNextStep(mCardManageBean);
                }
                break;
            case R.id.txt_pay_type:
                if (getActivity() == null) {
                    return;
                }
                if (mSelectPayTypeDialog == null) {
                    List<DropMenuBean> dropMenuBeans = new ArrayList<>();
                    dropMenuBeans.add(new DropMenuBean("微信支付", "0"));
                    dropMenuBeans.add(new DropMenuBean("支付宝支付", "1"));
                    dropMenuBeans.add(new DropMenuBean("银联", "2"));
                    dropMenuBeans.add(new DropMenuBean("现金", "3"));
                    dropMenuBeans.add(new DropMenuBean("银行卡", "4"));
                    dropMenuBeans.add(new DropMenuBean("支票", "5"));
                    dropMenuBeans.add(new DropMenuBean("其他", "6"));
                    mSelectPayTypeDialog = SingleSelectionDialog.newBuilder(getActivity(), DropMenuBean::getValue)
                            .refreshList(dropMenuBeans)
                            .setTitleText("支付方式")
                            .setOnSelectListener(dropMenuBean -> {
                                mSelectPayTypeDialog.dismiss();
                                mTxtPayType.setText(dropMenuBean.getValue());
                                mCardManageBean.setPayType(dropMenuBean.getKey());
                                mTxtRecharge.setEnabled(mEdtRecharge.getText().toString().length() > 0 && dropMenuBean.getKey().length() > 0);
                            })
                            .create();
                }
                mSelectPayTypeDialog.show();
                break;
            default:
                break;
        }
    }
}
