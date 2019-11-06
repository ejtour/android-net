package com.hll_sc_app.app.cardmanage.recharge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CardRechargeStepTwoFragment extends Fragment {

    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_card_no)
    TextView mTxtCardNo;
    @BindView(R.id.txt_recharge)
    TextView mTxtRecharge;
    @BindView(R.id.txt_gift)
    TextView mTxtGift;
    @BindView(R.id.txt_total)
    TextView mTxtTotal;

    private Unbinder unbinder;

    public static CardRechargeStepTwoFragment newInstance() {
        CardRechargeStepTwoFragment fragment = new CardRechargeStepTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_manage_recharge_two, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_btn_recharge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_btn_recharge:
                if (getActivity() instanceof ICardManageRechargeContract.IView) {
                    ((ICardManageRechargeContract.IView) getActivity()).recharge();
                }
                break;
            default:
                break;
        }
    }

    public void updateValue(CardManageBean bean) {
        mTxtName.setText(bean.getPurchaserName());
        mTxtCardNo.setText(bean.getCardNo());
        mTxtRecharge.setText("¥" + CommonUtils.formatMoney(Double.parseDouble(bean.getCashBalanceText())));
        mTxtGift.setText("¥" + CommonUtils.formatMoney(Double.parseDouble(bean.getGiftBalanceText())));
        BigDecimal total = CommonUtils.addDouble(Double.parseDouble(bean.getCashBalanceText()), Double.parseDouble(bean.getGiftBalanceText()));
        mTxtTotal.setText("¥" + total.toString());
    }
}
