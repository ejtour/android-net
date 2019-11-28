package com.hll_sc_app.app.crm.customer.plan.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.app.crm.customer.plan.add.AddVisitPlanActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/22
 */

@Route(path = RouterConfig.CRM_CUSTOMER_PLAN_DETAIL)
public class VisitPlanDetailActivity extends BaseLoadActivity {
    public static final int REQ_CODE = 0x764;

    @Autowired(name = "parcelable")
    VisitPlanBean mBean;
    @BindView(R.id.vpd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.vpd_type)
    TextView mType;
    @BindView(R.id.vpd_name)
    TextView mName;
    @BindView(R.id.vpd_address)
    TextView mAddress;
    @BindView(R.id.vpd_time)
    TextView mTime;
    @BindView(R.id.vpd_way)
    TextView mWay;
    @BindView(R.id.vpd_goal)
    TextView mGoal;
    @BindView(R.id.vpd_attention)
    TextView mAttention;
    @BindView(R.id.vpd_person)
    TextView mPerson;
    private boolean mHasChanged;

    public static void start(Activity context, VisitPlanBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_PLAN_DETAIL, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_visit_plan_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        updateData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mHasChanged = true;
            VisitPlanBean record = data.getParcelableExtra(CustomerHelper.VISIT_KEY);
            if (record != null) {
                mBean = record;
                updateData();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            Intent intent = new Intent();
            intent.putExtra(CustomerHelper.VISIT_KEY, mBean);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    private void updateData() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        if (mBean.getEmployeeID().equals(GreenDaoUtils.getUser().getEmployeeID())) {
            mTitleBar.setRightBtnVisible(true);
            mTitleBar.setRightBtnClick(v -> AddVisitPlanActivity.start(this, mBean));
        }
        mType.setText(CustomerHelper.getVisitCustomerType(mBean.getCustomerType()));
        mName.setText(mBean.getCustomerName());
        mAddress.setText(String.format("%s-%s-%s %s", mBean.getCustomerProvince(), mBean.getCustomerCity(), mBean.getCustomerDistrict(), mBean.getCustomerAddress()));
        mTime.setText(DateUtil.getReadableTime(mBean.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
        mWay.setText(CustomerHelper.getVisitWay(mBean.getVisitWay()));
        mGoal.setText(CustomerHelper.getVisitGoal(mBean.getVisitGoal()));
        mAttention.setText(mBean.getAttentions());
        mPerson.setText(mBean.getVisitPersonnel());
    }
}
