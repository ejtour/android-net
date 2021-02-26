package com.hll_sc_app.app.crm.customer.plan.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.AddVisitHelper;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.TitleBar;

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
    AddVisitHelper mVisitHelper;
    private IAddVisitPlanContract.IAddVisitPlanPresenter mPresenter;

    public static void start(Activity context, VisitPlanBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_PLAN_ADD, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            mBean.setVisitPersonnel(user.getEmployeeName());
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
        mVisitHelper = new AddVisitHelper(mBean, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mVisitHelper.onActivityResult(resultCode, data, mCustomer::setText);
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
        mVisitHelper.selectType(result -> {
            clearCustomerName();
            mType.setText(result);
        });
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
        mVisitHelper.selectLevel(result -> {
            clearCustomerName();
            mLevel.setText(result);
        });
    }

    @OnClick(R.id.vpa_customer)
    public void selectCustomer() {
        if (mBean.getCustomerType() == 0) {
            showToast("请选择客户类型");
            return;
        }
        mVisitHelper.selectCustomer();
    }

    @OnClick(R.id.vpa_time)
    public void selectTime() {
        mVisitHelper.selectTime(mTime::setText);
    }

    @OnClick(R.id.vpa_way)
    public void selectWay() {
        mVisitHelper.selectWay(mWay::setText);
    }

    @OnClick(R.id.vpa_goal)
    public void selectGoal() {
        mVisitHelper.selectGoal(mGoal::setText);
    }

    @Override
    public void saveSuccess() {
        mVisitHelper.saveSuccess();
    }
}
