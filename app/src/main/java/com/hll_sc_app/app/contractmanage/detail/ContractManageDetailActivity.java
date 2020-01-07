package com.hll_sc_app.app.contractmanage.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.ContractManageActivity;
import com.hll_sc_app.app.contractmanage.add.ContractManageAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.event.ContractManageEvent;
import com.hll_sc_app.citymall.util.CalendarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 合同管理-详情
 */
@Route(path = RouterConfig.ACTIVITY_CONTRACT_MANAGE_DETAIL)
public class ContractManageDetailActivity extends BaseLoadActivity implements IContractManageDetailContract.IView {

    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_no)
    TextView mTxtNo;
    @BindView(R.id.txt_group_name)
    TextView mTxtGroupName;
    @BindView(R.id.txt_time_span)
    TextView mTxtTimeSpan;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.txt_time)
    TextView mTxtTime;
    @BindView(R.id.txt_left_days)
    TextView mTxtLeftDays;
    @BindView(R.id.txt_btn_del)
    TextView mTxtBtnDel;
    @BindView(R.id.txt_btn_mdf)
    TextView mTxtBtnMdf;
    @BindView(R.id.txt_btn_check)
    TextView mTxtBtnCheck;
    @BindView(R.id.view_bottom)
    View mViewBottom;
    @Autowired(name = "parcelable")
    ContractListResp.ContractBean mBean;

    private Unbinder unbinder;
    private IContractManageDetailContract.IPresent mPresent;

    public static void start(ContractListResp.ContractBean bean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        mPresent = ContractManageDetailPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    private void initView() {
        mTxtName.setText(mBean.getContractName());
        mTxtNo.setText(mBean.getContractCode());
        mTxtStatus.setText(mBean.getTransformStatus());
        mTxtStatus.setTextColor(ContractManageActivity.ContractListAdapter.getStatusColor(mBean.getStatus()));
        mTxtGroupName.setText(mBean.getGroupName());
        mTxtTimeSpan.setText(CalendarUtils.getDateFormatString(mBean.getStartDate(), "yyyyMMdd", "yyyy/MM/dd") + " - " +
                CalendarUtils.getDateFormatString(mBean.getEndDate(), "yyyyMMdd", "yyyy/MM/dd"));
        mTxtPerson.setText(mBean.getSignEmployeeName());
        mTxtTime.setText(CalendarUtils.getDateFormatString(mBean.getSignDate(), "yyyyMMdd", "yyyy/MM/dd"));
        mTxtLeftDays.setText(String.valueOf(mBean.getDistanceExpirationDate()));

        if (mBean.getStatus() == 0) {//待审核
            mViewBottom.setVisibility(View.VISIBLE);
            mTxtBtnDel.setVisibility(View.VISIBLE);
            mTxtBtnMdf.setVisibility(View.VISIBLE);
            if (GreenDaoUtils.containsAuth("")) {
                mTxtBtnCheck.setVisibility(View.VISIBLE);
            }
        } else {
            mViewBottom.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.txt_btn_del, R.id.txt_btn_mdf, R.id.txt_btn_check})
    public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_btn_del:
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("是否删除该合同")
                        .setMessage("xxxxxxxxxxxx")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.delete(mBean.getId());
                            }
                        }, "我再看看", "确认删除").create().show();
                break;
            case R.id.txt_btn_mdf:
                ContractManageAddActivity.start(mBean);
                finish();
                break;
            case R.id.txt_btn_check:
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("是否审核通过该合同")
                        .setMessage("xxxxxxxxxxxx")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.check(mBean.getId());
                            }
                        }, "我再看看", "确认审核通过").create().show();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void deleteSuccess() {
        showToast("删除成功");
        EventBus.getDefault().post(new ContractManageEvent(true));
        finish();
    }

    @Override
    public void checkSuccess() {
        showToast("审核通过");
        EventBus.getDefault().post(new ContractManageEvent(true));
        finish();
    }


}
