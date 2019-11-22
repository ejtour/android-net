package com.hll_sc_app.app.crm.customer.record.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/22
 */

@Route(path = RouterConfig.CRM_CUSTOMER_RECORD_ADD)
public class AddVisitRecordActivity extends BaseLoadActivity implements IAddVisitRecordContract.IAddVisitRecordView {
    private static final int REQ_CODE = 0x398;
    @BindView(R.id.vra_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.vra_save)
    TextView mSave;
    @BindView(R.id.vra_type)
    TextView mType;
    @BindView(R.id.vra_is_plan)
    Switch mIsPlan;
    @BindView(R.id.vra_plan)
    TextView mPlan;
    @BindViews({R.id.vra_plan_label, R.id.vra_plan, R.id.vra_plan_div})
    List<View> mPlanViews;
    @BindView(R.id.vra_level)
    TextView mLevel;
    @BindView(R.id.vra_customer)
    TextView mCustomer;
    @BindView(R.id.vra_time)
    TextView mTime;
    @BindView(R.id.vra_way)
    TextView mWay;
    @BindView(R.id.vra_goal)
    TextView mGoal;
    @BindView(R.id.vra_reach)
    Switch mReach;
    @BindView(R.id.vra_result)
    EditText mResult;
    @BindView(R.id.vra_num)
    TextView mNum;
    @BindView(R.id.vra_next_time)
    TextView mNextTime;
    @BindView(R.id.vra_person)
    TextView mPerson;
    @Autowired(name = "parcelable")
    VisitRecordBean mBean;
    private SingleSelectionDialog mTypeDialog;
    private SingleSelectionDialog mLevelDialog;
    private DateWindow mTimeWindow;
    private DateWindow mNextTimeWindow;
    private SingleSelectionDialog mWayDialog;
    private SingleSelectionDialog mGoalDialog;
    private IAddVisitRecordContract.IAddVisitRecordPresenter mPresenter;

    public static void start(Activity context, VisitRecordBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_RECORD_ADD, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_visit_record_add);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initData();
    }

    private void initData() {
        if (mBean == null) {
            UserBean user = GreenDaoUtils.getUser();
            mBean = new VisitRecordBean();
            mBean.setActionType(1);
            mBean.setIsOnSchedule(2);
            mBean.setIsActive(2);
            mBean.setGroupID(user.getGroupID());
            mBean.setEmployeeID(user.getEmployeeID());
            mPerson.setText(user.getEmployeeName());
        } else {
            mTitleBar.setHeaderTitle("修改拜访记录");
            mBean.setActionType(2);
            mType.setText(CustomerHelper.getVisitCustomerType(mBean.getCustomerType()));
            mIsPlan.setChecked(mBean.getIsOnSchedule() == 1);
            mPlan.setText(mBean.getCustomerName());
            mCustomer.setText(mBean.getCustomerName());
            disable(mType);
            mIsPlan.setEnabled(false);
            disable(mPlan);
            disable(mLevel);
            disable(mCustomer);
            mTime.setText(DateUtil.getReadableTime(mBean.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
            mWay.setText(CustomerHelper.getVisitWay(mBean.getVisitWay()));
            mGoal.setText(CustomerHelper.getVisitGoal(mBean.getVisitGoal()));
            mReach.setChecked(mBean.getIsActive() == 1);
            mResult.setText(mBean.getVisitResult());
            mNextTime.setText(DateUtil.getReadableTime(mBean.getNextVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
            mPerson.setText(mBean.getVisitPersonnel());
        }
        mLevel.setText(CustomerHelper.getCustomerMaintainLevel(mBean.getMaintainLevel()));
        mPresenter = AddVisitRecordPresenter.newInstance();
        mPresenter.register(this);
    }

    private void disable(TextView textView) {
        textView.setEnabled(false);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @OnCheckedChanged({R.id.vra_is_plan, R.id.vra_reach})
    public void onPlanChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.vra_is_plan) {
            mBean.setIsOnSchedule(isChecked ? 1 : 2);
            if (isChecked && mBean.getActionType() != 2) {
                clearCustomerName();
            }
            ButterKnife.apply(mPlanViews, (view, index) -> view.setVisibility(mIsPlan.isChecked() ? View.VISIBLE : View.GONE));
        } else if (buttonView.getId() == R.id.vra_reach) {
            mBean.setIsActive(isChecked ? 1 : 2);
        }
    }

    private void clearCustomerName() {
        mPlan.setText("");
        mCustomer.setText("");
        mBean.setCustomerName("");
    }

    @OnClick(R.id.vra_save)
    public void save() {
        if (!mIsPlan.isChecked()) mBean.setPlanID("");
        mBean.setVisitResult(mResult.getText().toString().trim());
        mPresenter.save(mBean);
    }

    @OnTextChanged({R.id.vra_type, R.id.vra_plan, R.id.vra_level, R.id.vra_customer, R.id.vra_time, R.id.vra_way, R.id.vra_goal})
    public void updateEnable() {
        mSave.setEnabled(!TextUtils.isEmpty(mType.getText().toString())
                && !TextUtils.isEmpty(mLevel.getText().toString())
                && !TextUtils.isEmpty(mCustomer.getText().toString())
                && !TextUtils.isEmpty(mTime.getText().toString())
                && !TextUtils.isEmpty(mWay.getText().toString())
                && !TextUtils.isEmpty(mGoal.getText().toString())
                && (!mIsPlan.isChecked() || !TextUtils.isEmpty(mPlan.getText().toString()))
        );
    }

    @OnClick(R.id.vra_plan)
    public void selectPlan() {
        if (mBean.getCustomerType() == 0) {
            showToast("请选择客户类型");
            return;
        }
        showToast("选择拜访计划待添加");
    }

    @OnClick(R.id.vra_type)
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

    @OnClick(R.id.vra_level)
    public void selectLevelOrPlan() {
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

    @OnClick(R.id.vra_customer)
    public void selectCustomer() {
        if (mBean.getCustomerType() == 0) {
            showToast("请选择客户类型");
            return;
        }
        if (mBean.getIsOnSchedule() == 1) {
            showToast("请选择拜访计划");
            return;
        }
        showToast("选择客户待添加");
    }

    @OnClick(R.id.vra_time)
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

    @OnClick(R.id.vra_way)
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

    @OnClick(R.id.vra_goal)
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

    @OnClick(R.id.vra_next_time)
    public void selectNextTime() {
        if (mNextTimeWindow == null) {
            mNextTimeWindow = new DateWindow(this);
            mNextTimeWindow.setSelectListener(date -> {
                mBean.setNextVisitTime(CalendarUtils.toLocalDate(date));
                mNextTime.setText(CalendarUtils.format(date, CalendarUtils.FORMAT_DATE_TIME));
            });
        }
        mNextTimeWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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
