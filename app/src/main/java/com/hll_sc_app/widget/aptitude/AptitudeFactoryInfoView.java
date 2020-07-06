package com.hll_sc_app.widget.aptitude;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudeHelper;
import com.hll_sc_app.bean.aptitude.AptitudeInfoKV;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.MultiSelectionDialog;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/24
 */

public class AptitudeFactoryInfoView extends ConstraintLayout implements IAptitudeInfoCallback {
    @BindView(R.id.afi_floor_area)
    EditText mFloorArea;
    @BindView(R.id.afi_property)
    EditText mProperty;
    @BindView(R.id.afi_process_num)
    EditText mProcessNum;
    @BindView(R.id.afi_qa_num)
    EditText mQaNum;
    @BindView(R.id.afi_total_num)
    EditText mTotalNum;
    @BindView(R.id.afi_bank_name)
    EditText mBankName;
    @BindView(R.id.afi_bank_account)
    EditText mBankAccount;
    @BindView(R.id.afi_pay_method)
    TextView mPayMethod;
    @BindView(R.id.afi_pay_cycle)
    EditText mPayCycle;
    @BindView(R.id.afi_invoice_type)
    EditText mInvoiceType;
    @BindView(R.id.afi_product)
    EditText mProduct;
    @BindView(R.id.afi_ability)
    EditText mAbility;
    @BindView(R.id.afi_cycle)
    EditText mCycle;
    @BindView(R.id.afi_standard)
    TextView mStandard;
    @BindView(R.id.afi_certification)
    TextView mCertification;
    @BindView(R.id.afi_other_certification)
    EditText mOtherCertification;
    @BindViews({R.id.afi_floor_area, R.id.afi_property, R.id.afi_process_num, R.id.afi_qa_num, R.id.afi_pay_cycle,
            R.id.afi_total_num, R.id.afi_bank_name, R.id.afi_bank_account, R.id.afi_invoice_type,
            R.id.afi_product, R.id.afi_ability, R.id.afi_cycle, R.id.afi_standard, R.id.afi_pay_method})
    List<TextView> mTextViews;
    @BindViews({R.id.afi_pay_cycle_label, R.id.afi_pay_cycle, R.id.afi_pay_cycle_div})
    List<View> mPayCycles;
    @BindViews({R.id.afi_floor_area, R.id.afi_property, R.id.afi_process_num, R.id.afi_qa_num,
            R.id.afi_total_num, R.id.afi_bank_name, R.id.afi_bank_account, R.id.afi_pay_method,
            R.id.afi_pay_cycle, R.id.afi_invoice_type, R.id.afi_product, R.id.afi_ability,
            R.id.afi_cycle, R.id.afi_standard, R.id.afi_certification, R.id.afi_other_certification})
    List<View> mInputViews;
    private MultiSelectionDialog mPayMethodDialog;
    private MultiSelectionDialog mCertificationDialog;
    private SingleSelectionDialog mStandardDialog;

    public AptitudeFactoryInfoView(Context context) {
        this(context, null);
    }

    public AptitudeFactoryInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AptitudeFactoryInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_aptitude_factory_info, this);
        ButterKnife.bind(this, view);
        setEditable(false);
    }

    @Override
    public void withData(AptitudeInfoResp resp) {
        if (resp == null) return;
        mFloorArea.setText(resp.getFloorSpace());
        mProperty.setText(resp.getLandStatus());
        mProcessNum.setText(resp.getProcessManCount());
        mQaNum.setText(resp.getQualityManCount());
        mTotalNum.setText(resp.getCompanyManCount());
        mBankName.setText(resp.getBankName());
        mBankAccount.setText(resp.getBankAccount());
        mProduct.setText(resp.getMajorProduct());
        mInvoiceType.setText(resp.getInvoiceType());
        mAbility.setText(resp.getProductCapacity());
        mCycle.setText(resp.getDeliveryCycle());
        handlePayMethod(resp.getPaymentWay());
        mPayCycle.setText(resp.getPaymentCycle());
        handleStandard(resp.getProductStandard());
        handleCertification(resp.getProductCertification(), true);
    }

    @Override
    public void setEditable(boolean editable) {
        for (View view : mInputViews) {
            if (view instanceof EditText) {
                view.setFocusable(editable);
                view.setFocusableInTouchMode(editable);
            } else if (view instanceof TextView) {
                view.setClickable(editable);
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, editable ? R.drawable.ic_arrow_gray : 0, 0);
            }
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public AptitudeInfoReq getReq() {
        AptitudeInfoReq req = new AptitudeInfoReq();
        List<AptitudeInfoKV> list = new ArrayList<>();
        req.setAptitudeList(list);
        for (TextView textView : mTextViews) {
            String value = textView.getText().toString().trim();
            if (!TextUtils.isEmpty(value)) {
                Object tag = textView.getTag(R.id.base_tag_1);
                if (tag == null) {
                    list.add(new AptitudeInfoKV(textView.getTag().toString(), value));
                } else if (tag instanceof String) {
                    list.add(new AptitudeInfoKV(textView.getTag().toString(), tag.toString()));
                } else if (tag instanceof List) {
                    for (String s : (List<String>) tag) {
                        list.add(new AptitudeInfoKV(textView.getTag().toString(), s));
                    }
                }
            } else {
                list.add(new AptitudeInfoKV(textView.getTag().toString(), ""));
            }
        }

        String certification = mCertification.getText().toString().trim();
        if (TextUtils.isEmpty(certification)) {
            list.add(new AptitudeInfoKV(mCertification.getTag().toString(), ""));
        } else {
            List<String> strings = (List<String>) mCertification.getTag(R.id.base_tag_1);
            for (String string : strings) {
                if (string.equals("其他")) {
                    String other = mOtherCertification.getText().toString().trim();
                    if (!TextUtils.isEmpty(other)) {
                        list.add(new AptitudeInfoKV(mCertification.getTag().toString(), other));
                    }
                } else {
                    list.add(new AptitudeInfoKV(mCertification.getTag().toString(), string));
                }
            }
        }
        return req;
    }


    private void handlePayMethod(List<String> ways) {
        mPayMethod.setTag(R.id.base_tag_1, ways);
        if (!CommonUtils.isEmpty(ways)) {
            List<String> names = new ArrayList<>();
            for (String way : ways) {
                names.add(AptitudeHelper.getPayMethod(CommonUtils.getInt(way)));
            }
            if (ways.contains("6")) {
                ButterKnife.apply(mPayCycles, (view, index) -> view.setVisibility(VISIBLE));
            } else {
                ButterKnife.apply(mPayCycles, (view, index) -> view.setVisibility(GONE));
                mPayCycle.setText("");
            }
            mPayMethod.setText(TextUtils.join(",", names));
        } else {
            mPayMethod.setText("");
            ButterKnife.apply(mPayCycles, (view, index) -> view.setVisibility(GONE));
            mPayCycle.setText("");
        }
    }

    @OnClick(R.id.afi_pay_method)
    public void selectPayMethod() {
        if (mPayMethodDialog == null) {
            MultiSelectionDialog.WrapperName<NameValue> wrapperName = new MultiSelectionDialog.WrapperName<NameValue>() {
                @Override
                public String getName(NameValue nameValue) {
                    return nameValue.getName();
                }

                @Override
                public String getKey(NameValue nameValue) {
                    return nameValue.getValue();
                }
            };
            mPayMethodDialog = MultiSelectionDialog.newBuilder((Activity) getContext(), wrapperName)
                    .setTitleText("请选择")
                    .refreshList(AptitudeHelper.getPayMethodList())
                    .selectByKey(mPayMethod.getTag(R.id.base_tag_1) instanceof List ? (List) mPayMethod.getTag(R.id.base_tag_1) : new ArrayList<>())
                    .setOnSelectListener(nameValues -> {
                        List<String> list = new ArrayList<>();
                        for (NameValue nameValue : nameValues) {
                            list.add(nameValue.getValue());
                        }
                        handlePayMethod(list);
                    })
                    .create();
        }
        mPayMethodDialog.show();
    }

    @OnClick(R.id.afi_standard)
    public void selectStandard() {
        if (mStandardDialog == null) {
            List<NameValue> standardList = AptitudeHelper.getStandardList();
            Object tag = mStandard.getTag(R.id.base_tag_1);
            NameValue cur = null;
            if (tag != null) {
                int value = CommonUtils.getInt(tag.toString());
                if (value > 0) {
                    cur = standardList.get(value - 1);
                }
            }
            mStandardDialog = SingleSelectionDialog.newBuilder((Activity) getContext(), NameValue::getName)
                    .refreshList(standardList)
                    .setTitleText("请选择遵守标准")
                    .select(cur)
                    .setOnSelectListener(nameValue -> {
                        handleStandard(nameValue.getValue());
                    })
                    .create();
        }
        mStandardDialog.show();
    }

    private void handleStandard(String value) {
        if (value == null) return;
        mStandard.setText(AptitudeHelper.getStandard(CommonUtils.getInt(value)));
        mStandard.setTag(R.id.base_tag_1, value);
    }

    @OnClick(R.id.afi_certification)
    public void selectCertification() {
        if (mCertificationDialog == null) {
            MultiSelectionDialog.WrapperName<String> wrapperName = new MultiSelectionDialog.WrapperName<String>() {
                @Override
                public String getName(String s) {
                    return s;
                }

                @Override
                public String getKey(String s) {
                    return s;
                }
            };
            mCertificationDialog = MultiSelectionDialog.newBuilder((Activity) getContext(), wrapperName)
                    .setTitleText("请选择")
                    .refreshList(AptitudeHelper.getCertificationList())
                    .selectByKey(mCertification.getTag(R.id.base_tag_1) instanceof List ? (List) mCertification.getTag(R.id.base_tag_1) : new ArrayList<>())
                    .setOnSelectListener(strings -> {
                        handleCertification(strings, false);
                    })
                    .create();
        }
        mCertificationDialog.show();
    }

    private void handleCertification(List<String> certs, boolean init) {
        if (!CommonUtils.isEmpty(certs) && init) {
            certs = new ArrayList<>(new LinkedHashSet<>(certs));
            List<String> list = AptitudeHelper.getCertificationList();
            if (certs.contains("其他")) {
                mOtherCertification.setText("其他");
            } else {
                for (String cert : certs) {
                    if (!list.contains(cert)) {
                        mOtherCertification.setText(cert);
                        certs.set(certs.indexOf(cert), "其他");
                        break;
                    }
                }
            }
        }
        mCertification.setTag(R.id.base_tag_1, certs);
        if (!CommonUtils.isEmpty(certs)) {
            if (certs.contains("其他")) {
                mOtherCertification.setVisibility(VISIBLE);
            } else {
                mOtherCertification.setVisibility(GONE);
                mOtherCertification.setText("");
            }
            mCertification.setText(TextUtils.join(",", certs));
        } else {
            mOtherCertification.setVisibility(GONE);
            mOtherCertification.setText("");
            mCertification.setText("");
        }
    }
}
