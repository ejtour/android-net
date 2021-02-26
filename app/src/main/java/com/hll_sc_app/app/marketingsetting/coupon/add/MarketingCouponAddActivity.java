package com.hll_sc_app.app.marketingsetting.coupon.add;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.bean.event.MarketingEvent;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_LOCAL_DATE;
import static com.hll_sc_app.citymall.util.CommonUtils.isMoney;

/**
 * 新增优惠券
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_ADD)
public class MarketingCouponAddActivity extends BaseLoadActivity implements IMarketingCouponAddContract.IView {
    private static final String FORMAT_TIME_OUT = "yyyy-MM-dd";
    @BindView(R.id.title)
    TitleBar mTitile;
    @BindView(R.id.edt_content_name)
    EditText mEdtName;
    @BindView(R.id.edt_content_value)
    EditText mEdtValue;
    @BindView(R.id.txt_content_time_type)
    TextView mTxtTimeType;
    @BindView(R.id.txt_content_time_span)
    TextView mTxtTimeSpan;
    @BindView(R.id.edt_content_time_from)
    EditText mEdtTimeFrom;
    @BindView(R.id.group_time_from)
    Group mGroupTimeFrom;
    @BindView(R.id.txt_content_condition)
    TextView mTxtCondition;
    @BindView(R.id.edt_content_full)
    EditText mEdtFull;
    @BindView(R.id.group_full_condition)
    Group mGroupFull;
    private Unbinder unbinder;

    private SingleSelectionDialog mSelectTimeTypeDialog;
    private SingleSelectionDialog mSelectConditionDialog;
    private DateSelectWindow mDateSelectWindow;

    private IMarketingCouponAddContract.IPresent mpresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_coupon_add);
        unbinder = ButterKnife.bind(this);
        mpresent = MarketingCouponAddPresent.newInstance();
        mpresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTitile.setRightBtnClick(v -> {
            if (isComplete()) {
                onSave();
            }
        });

        mEdtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !isMoney(s.toString())) {
                    mEdtValue.setText(s.subSequence(0, s.length() - 1));
                    mEdtValue.setSelection(s.length() - 1);
                }
            }
        });

        mEdtFull.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !isMoney(s.toString())) {
                    mEdtFull.setText(s.subSequence(0, s.length() - 1));
                    mEdtFull.setSelection(s.length() - 1);
                }
            }
        });
    }

    private boolean isComplete() {
        if (TextUtils.isEmpty(getCouponName())) {
            showToast("请输入优惠券名称，最多10位");
            return false;
        } else if (TextUtils.isEmpty(mEdtValue.getText().toString().trim())) {
            showToast("请输入优惠券单张面额");
            return false;
        } else if (mTxtTimeType.getTag() == null) {
            showToast("请选择有效期限定方式");
            return false;
        } else if (mTxtTimeSpan.getTag(R.id.base_tag_1) == null && mEdtTimeFrom.getText().toString().trim().length() == 0) {
            showToast("请指定有效期限");
            return false;
        } else if (mTxtCondition.getTag() == null) {
            showToast("选择使用条件");
            return false;
        } else if (getCouponCondition() == 1 && mEdtFull.getText().toString().trim().length() == 0) {
            showToast("请正确填写满减金额");
            return false;
        }

        return true;
    }

    private void onSave() {
        mpresent.save();
    }

    @OnClick({R.id.txt_content_time_type, R.id.txt_content_time_span, R.id.txt_content_condition})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_content_time_type:
                if (mSelectTimeTypeDialog == null) {
                    NameValue nameValue1 = new NameValue("指定有效期", "0");
                    NameValue nameValue2 = new NameValue("按领用日限定", "1");
                    ArrayList<NameValue> nameValues = new ArrayList<>();
                    nameValues.add(nameValue1);
                    nameValues.add(nameValue2);
                    mSelectTimeTypeDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .setTitleText("有效期限定方式")
                            .refreshList(nameValues)
                            .setOnSelectListener(nameValue -> {
                                if (TextUtils.equals(nameValue.getValue(), "0")) {
                                    mGroupTimeFrom.setVisibility(View.GONE);
                                    mTxtTimeSpan.setVisibility(View.VISIBLE);
                                    mTxtTimeSpan.setHint("请指定有效期限");
                                } else {
                                    mGroupTimeFrom.setVisibility(View.VISIBLE);
                                    mTxtTimeSpan.setVisibility(View.GONE);
                                    mTxtTimeSpan.setHint("请先选择有效期限定方式");
                                }
                                mTxtTimeType.setTag(nameValue);
                                mTxtTimeType.setText(nameValue.getName());
                            }).create();

                }
                mSelectTimeTypeDialog.show();
                break;
            case R.id.txt_content_time_span:
                if (mTxtTimeType.getTag() == null) {
                    showToast("请先选择有效期限定方式");
                } else {
                    if (mDateSelectWindow == null) {
                        mDateSelectWindow = new DateSelectWindow(this);
                        mDateSelectWindow.setForbiddenStartBeforeCurrent(false);
                        mDateSelectWindow.setSelectListener((startDate, endDate) -> {
                            mTxtTimeSpan.setText(CalendarUtils.getDateFormatString(startDate, FORMAT_LOCAL_DATE, FORMAT_TIME_OUT) + "~" + CalendarUtils.getDateFormatString(endDate, FORMAT_LOCAL_DATE, FORMAT_TIME_OUT));
                            mTxtTimeSpan.setTag(R.id.base_tag_1, startDate);
                            mTxtTimeSpan.setTag(R.id.base_tag_2, endDate);
                        });
                    }
                    mDateSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.txt_content_condition:
                if (mSelectConditionDialog == null) {
                    NameValue nameValue1 = new NameValue("无使用限制", "0");
                    NameValue nameValue2 = new NameValue("满额使用", "1");
                    ArrayList<NameValue> nameValues = new ArrayList<>();
                    nameValues.add(nameValue1);
                    nameValues.add(nameValue2);
                    mSelectConditionDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .setTitleText("使用条件")
                            .refreshList(nameValues)
                            .setOnSelectListener(nameValue -> {
                                if (TextUtils.equals(nameValue.getValue(), "0")) {
                                    mGroupFull.setVisibility(View.GONE);
                                } else {
                                    mGroupFull.setVisibility(View.VISIBLE);

                                }
                                mTxtCondition.setText(nameValue.getName());
                                mTxtCondition.setTag(nameValue);
                            }).create();
                }
                mSelectConditionDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void addSuccess() {
        MarketingEvent event = new MarketingEvent();
        event.setTarget(MarketingEvent.Target.MARKETING_COUPON_LIST);
        event.setRefresh(true);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public String getCouponName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public List<RuleListBean> getRuleList() {
        List<RuleListBean> listBeans = new ArrayList<>();
        RuleListBean bean = new RuleListBean();
        bean.setRuleDiscountValue(mEdtValue.getText().toString());
        if (getCouponCondition() == 1) {
            bean.setRuleCondition(mEdtFull.getText().toString());
        } else {
            bean.setRuleCondition("0");
        }
        listBeans.add(bean);
        return listBeans;
    }

    @Override
    public int getValidityType() {
        if (mTxtTimeType.getTag() != null) {
            String type = ((NameValue) mTxtTimeType.getTag()).getValue();
            return Integer.parseInt(type);
        }
        return -1;

    }

    @Override
    public int getCouponCondition() {
        if (mTxtCondition.getTag() != null) {
            String type = ((NameValue) mTxtCondition.getTag()).getValue();
            return Integer.parseInt(type);
        }
        return -1;
    }

    @Override
    public String getValidityDays() {
        return mEdtTimeFrom.getText().toString();
    }

    @Override
    public String getDiscountStartTime() {
        if (mTxtTimeSpan.getTag(R.id.base_tag_1) != null) {
            return mTxtTimeSpan.getTag(R.id.base_tag_1).toString() + "0000";
        }
        return null;
    }

    @Override
    public String getDiscountEndTime() {
        if (mTxtTimeSpan.getTag(R.id.base_tag_2) != null) {
            return mTxtTimeSpan.getTag(R.id.base_tag_2).toString() + "2359";
        }
        return null;
    }
}
