package com.hll_sc_app.app.crm.customer.plan.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

@Route(path = RouterConfig.CRM_CUSTOMER_PLAN_ADD)
public class AddVisitPlanActivity extends BaseLoadActivity implements IAddVisitPlanContract.IAddVisitPlanView {
    private static final int REQ_CODE = 0x471;
    @BindView(R.id.vpa_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.vpa_save)
    TextView mSave;
    @BindView(R.id.vpa_type)
    TextView mType;
    @BindView(R.id.vpa_level)
    TextView mLevel;
    @BindView(R.id.vpa_customer)
    TextView mCustomer;
    @BindView(R.id.vpa_time)
    TextView mTime;
    @BindView(R.id.vpa_way)
    TextView mWay;
    @BindView(R.id.vpa_goal)
    TextView mGoal;
    @BindView(R.id.vpa_attention)
    EditText mAttention;
    @BindView(R.id.vpa_num)
    TextView mNum;
    @BindView(R.id.vpa_person)
    TextView mPerson;
    @Autowired(name = "parcelable")
    VisitPlanBean mBean;
    private SingleSelectionDialog mTypeDialog;
    private SingleSelectionDialog mLevelDialog;
    private DateWindow mTimeWindow;
    private SingleSelectionDialog mWayDialog;
    private SingleSelectionDialog mGoalDialog;
    private IAddVisitPlanContract.IAddVisitPlanPresenter mPresenter;

    public static void start(Activity context, VisitPlanBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_PLAN_ADD, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_visit_plan_add);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initData();
    }

    private void initData() {
        if (mBean == null) {
            UserBean user = GreenDaoUtils.getUser();
            mBean = new VisitPlanBean();
            mBean.setActionType(1);
            mBean.setGroupID(user.getGroupID());
            mBean.setEmployeeID(user.getEmployeeID());
            mPerson.setText(user.getEmployeeName());
        } else {
            mTitleBar.setHeaderTitle("修改拜访计划");
            mBean.setActionType(2);
            mType.setText(CustomerHelper.getVisitCustomerType(mBean.getCustomerType()));
            mCustomer.setText(mBean.getCustomerName());
            disable(mType);
            disable(mLevel);
            disable(mCustomer);
            disable(mTime);
            mTime.setText(DateUtil.getReadableTime(mBean.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
            mWay.setText(CustomerHelper.getVisitWay(mBean.getVisitWay()));
            mGoal.setText(CustomerHelper.getVisitGoal(mBean.getVisitGoal()));
            mAttention.setText(mBean.getAttentions());
            mPerson.setText(mBean.getVisitPersonnel());
        }
        mLevel.setText(CustomerHelper.getCustomerMaintainLevel(mBean.getMaintainLevel()));
        mPresenter = AddVisitPlanPresenter.newInstance();
        mPresenter.register(this);
    }

    private void disable(TextView textView) {
        textView.setEnabled(false);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @OnClick(R.id.vpa_save)
    public void save() {
        mBean.setAttentions(mAttention.getText().toString().trim());
        mPresenter.save(mBean);
    }

    @OnClick(R.id.vpa_type)
    public void selectType() {
        if (mTypeDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getVisitCustomerType(i), String.valueOf(i)));
            }
            mTypeDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择客户类型")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        int type = Integer.parseInt(value.getValue());
                        if (mBean.getCustomerType() != type) {
                            clearCustomerName();
                            mBean.setCustomerType(type);
                            mType.setText(value.getName());
                        }
                    })
                    .create();
        }
        mTypeDialog.show();
    }

    @OnTextChanged({R.id.vpa_type, R.id.vpa_level, R.id.vpa_customer, R.id.vpa_time, R.id.vpa_way, R.id.vpa_goal})
    public void updateEnable() {
        mSave.setEnabled(!TextUtils.isEmpty(mType.getText().toString())
                && !TextUtils.isEmpty(mLevel.getText().toString())
                && !TextUtils.isEmpty(mCustomer.getText().toString())
                && !TextUtils.isEmpty(mTime.getText().toString())
                && !TextUtils.isEmpty(mWay.getText().toString())
                && !TextUtils.isEmpty(mGoal.getText().toString()));
    }

    @OnTextChanged({R.id.vpa_attention})
    public void onTextChanged(CharSequence s) {
        mNum.setText(String.valueOf(200 - s.length()));
    }

    private void clearCustomerName() {
        mCustomer.setText("");
        mBean.setCustomerName("");
    }

    @OnClick(R.id.vpa_level)
    public void selectLevel() {
        if (mLevelDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getCustomerMaintainLevel(i), String.valueOf(i)));
            }
            mLevelDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择客户级别")
                    .select(nameValues.get(0))
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setMaintainLevel(Integer.parseInt(value.getValue()));
                        mLevel.setText(value.getName());
                    })
                    .create();
        }
        mLevelDialog.show();
    }

    @OnClick(R.id.vpa_customer)
    public void selectCustomer() {
        if (mBean.getCustomerType() == 0) {
            showToast("请选择客户类型");
            return;
        }
        showToast("选择客户待添加");
    }

    @OnClick(R.id.vpa_time)
    public void selectTime() {
        if (mTimeWindow == null) {
            mTimeWindow = new DateWindow(this);
            mTimeWindow.setSelectListener(date -> {
                mBean.setVisitTime(CalendarUtils.toLocalDate(date));
                mTime.setText(CalendarUtils.format(date, CalendarUtils.FORMAT_DATE_TIME));
            });
        }
        mTimeWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.vpa_way)
    public void selectWay() {
        if (mWayDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getVisitWay(i), String.valueOf(i)));
            }
            mWayDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择拜访方式")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setVisitWay(Integer.parseInt(value.getValue()));
                        mWay.setText(value.getName());
                    })
                    .create();
        }
        mWayDialog.show();
    }

    @OnClick(R.id.vpa_goal)
    public void selectGoal() {
        if (mGoalDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                nameValues.add(new NameValue(CustomerHelper.getVisitGoal(i), String.valueOf(i)));
            }
            mGoalDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择拜访目的")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setVisitGoal(Integer.parseInt(value.getValue()));
                        mGoal.setText(value.getName());
                    })
                    .create();
        }
        mGoalDialog.show();
    }

    @Override
    public void saveSuccess() {
        showToast("保存成功");
        Intent intent = new Intent();
        intent.putExtra(CustomerHelper.VISIT_KEY, mBean);
        setResult(RESULT_OK, intent);
        finish();
    }
}
