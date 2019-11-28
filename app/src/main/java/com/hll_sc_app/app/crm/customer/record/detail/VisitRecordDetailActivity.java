package com.hll_sc_app.app.crm.customer.record.detail;

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
import com.hll_sc_app.app.crm.customer.record.add.AddVisitRecordActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/22
 */

@Route(path = RouterConfig.CRM_CUSTOMER_RECORD_DETAIL)
public class VisitRecordDetailActivity extends BaseLoadActivity {
    public static final int REQ_CODE = 0x436;

    @Autowired(name = "parcelable")
    VisitRecordBean mBean;
    @BindView(R.id.vrd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.vrd_status)
    TextView mStatus;
    @BindView(R.id.vrd_name)
    TextView mName;
    @BindView(R.id.vrd_address)
    TextView mAddress;
    @BindView(R.id.vrd_time)
    TextView mTime;
    @BindView(R.id.vrd_way)
    TextView mWay;
    @BindView(R.id.vrd_goal)
    TextView mGoal;
    @BindView(R.id.vrd_result)
    TextView mResult;
    @BindView(R.id.vrd_next_time)
    TextView mNextTime;
    @BindView(R.id.vrd_person)
    TextView mPerson;
    private boolean mHasChanged;

    public static void start(Activity context, VisitRecordBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_RECORD_DETAIL, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_visit_record_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        updateData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mHasChanged = true;
            VisitRecordBean record = data.getParcelableExtra(CustomerHelper.VISIT_KEY);
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
            mTitleBar.setRightBtnClick(v -> AddVisitRecordActivity.start(this, mBean));
        }
        mStatus.setText(mBean.getIsActive() == 1 ? "有效" : "无效");
        mStatus.setCompoundDrawablesWithIntrinsicBounds(
                mBean.getIsActive() == 1 ? R.drawable.ic_valid : R.drawable.ic_invalid, 0, 0, 0);
        mName.setText(mBean.getCustomerName());
        mAddress.setText(String.format("%s-%s-%s %s", mBean.getCustomerProvince(), mBean.getCustomerCity(), mBean.getCustomerDistrict(), mBean.getCustomerAddress()));
        mTime.setText(DateUtil.getReadableTime(mBean.getVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
        mWay.setText(CustomerHelper.getVisitWay(mBean.getVisitWay()));
        mGoal.setText(CustomerHelper.getVisitGoal(mBean.getVisitGoal()));
        mResult.setText(mBean.getVisitResult());
        mNextTime.setText(DateUtil.getReadableTime(mBean.getNextVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
        mPerson.setText(mBean.getVisitPersonnel());
    }
}
