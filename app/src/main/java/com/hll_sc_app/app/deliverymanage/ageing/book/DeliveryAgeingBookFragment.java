package com.hll_sc_app.app.deliverymanage.ageing.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 配送时效管理-预定设置
 *
 * @author 朱英松
 * @date 2019/7/29
 */
public class DeliveryAgeingBookFragment extends BaseLazyFragment implements DeliveryAgeingBookContract.IDeliveryAgeingBookView {
    @BindView(R.id.txt_days)
    TextView mTxtDays;
    Unbinder unbinder;
    private SingleSelectionDialog mDialog;
    private DeliveryAgeingBookContract.IDeliveryAgeingBookPresenter mPresenter;

    public static DeliveryAgeingBookFragment newInstance() {
        return new DeliveryAgeingBookFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = DeliveryAgeingBookPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delivery_ageing_book, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @OnClick({R.id.rl_days})
    public void onViewClicked() {
        showDaysSelectWindow();
    }

    @Override
    public void showBookingDate(String s) {
        mTxtDays.setText(String.format("%s天", s));
    }

    @Override
    public void showDaysSelectWindow() {
        if (mDialog == null) {
            List<NameValue> list = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                list.add(new NameValue(i + "天", String.valueOf(i)));
            }
            mDialog = SingleSelectionDialog.newBuilder(requireActivity(),
                NameValue::getName)
                .refreshList(list)
                .setTitleText("选择可预订天数")
                .setOnSelectListener(nameValue -> {
                    mTxtDays.setText(nameValue.getName());
                    mPresenter.editGroupParam(nameValue.getValue());
                }).create();
        }
        mDialog.show();
    }
}