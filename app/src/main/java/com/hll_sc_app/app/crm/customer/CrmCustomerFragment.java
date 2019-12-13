package com.hll_sc_app.app.crm.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.add.CustomerAddActivity;
import com.hll_sc_app.app.crm.customer.intent.add.AddCustomerActivity;
import com.hll_sc_app.app.crm.customer.plan.add.AddVisitPlanActivity;
import com.hll_sc_app.app.crm.customer.record.add.AddVisitRecordActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.customer.CrmCustomerResp;
import com.hll_sc_app.bean.customer.CrmShopResp;
import com.hll_sc_app.bean.home.VisitResp;
import com.hll_sc_app.citymall.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

@Route(path = RouterConfig.CRM_CUSTOMER)
public class CrmCustomerFragment extends BaseLoadFragment implements ICrmCustomerContract.ICrmCustomerView {
    @BindView(R.id.fcc_title_bar)
    TextView mTitleBar;
    @BindView(R.id.fcc_intent_left)
    TextView mIntentLeft;
    @BindView(R.id.fcc_intent_right)
    TextView mIntentRight;
    @BindView(R.id.fcc_partner_left)
    TextView mPartnerLeft;
    @BindView(R.id.fcc_partner_right)
    TextView mPartnerRight;
    @BindView(R.id.fcc_seas_left)
    TextView mSeasLeft;
    @BindView(R.id.fcc_seas_right)
    TextView mSeasRight;
    @BindView(R.id.fcc_plan_left)
    TextView mPlanLeft;
    @BindView(R.id.fcc_plan_right)
    TextView mPlanRight;
    @BindView(R.id.fcc_record_left)
    TextView mRecordLeft;
    @BindView(R.id.fcc_record_right)
    TextView mRecordRight;
    Unbinder unbinder;
    private CrmCustomerPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_customer, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                switch (data.getIntExtra(CustomerHelper.GOTO_KEY, 0)) {
                    case CustomerHelper.GOTO_INTENT:
                        AddCustomerActivity.start(requireActivity(), null);
                        break;
                    case CustomerHelper.GOTO_RECORD:
                        AddVisitRecordActivity.start(requireActivity(), null);
                        break;
                    case CustomerHelper.GOTO_PLAN:
                        AddVisitPlanActivity.start(requireActivity(), null);
                        break;
                    case CustomerHelper.GOTO_PARTNER_REGISTERED:
                        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                        break;
                    case CustomerHelper.GOTO_PARTNER_UNREGISTERED:
                        RouterUtil.goToActivity(RouterConfig.USER_REGISTER);
                        break;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    private void initData() {
        mPresenter = new CrmCustomerPresenter();
        mPresenter.register(this);
    }

    private void initView() {
        showStatusBar();
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            int height = ViewUtils.getStatusBarHeight(requireContext());
            mTitleBar.setPadding(0, height, 0, 0);
            mTitleBar.getLayoutParams().height += height;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fcc_add)
    public void add() {
        CustomerAddActivity.start(requireActivity());
    }

    @OnClick(R.id.fcc_intent_btn)
    public void intent() {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_INTENT);
    }

    @OnClick(R.id.fcc_partner_btn)
    public void partner() {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_PARTNER);
    }

    @OnClick(R.id.fcc_seas_btn)
    public void seas() {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEAS);
    }

    @OnClick(R.id.fcc_plan_btn)
    public void plan() {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_PLAN);
    }

    @OnClick(R.id.fcc_record_btn)
    public void record() {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_RECORD);
    }

    @Override
    public void setShopInfo(CrmShopResp resp) {
        mPartnerLeft.setText(String.format("我的合作门店%s个", resp.getMyShopNum()));
        mPartnerRight.setText(String.format("全部合作门店%s个", resp.getAllShopNum()));
        mSeasRight.setText(String.format("合作客户%s个", resp.getHighSeasCooperationShopNum()));
    }

    @Override
    public void setCustomerInfo(CrmCustomerResp resp) {
        mIntentLeft.setText(String.format("我的意向客户%s个", resp.getMyCustomerNum()));
        mIntentRight.setText(String.format("全部意向客户%s个", resp.getAllCustomerNum()));
        mSeasLeft.setText(String.format("意向客户%s个", resp.getHighSeaCustomerNum()));
    }

    @Override
    public void setVisitInfo(VisitResp resp) {
        mRecordLeft.setText(String.format("今日拜访%s个", resp.getVisitRecordCount()));
        mRecordRight.setText(String.format("有效拜访%s个", resp.getActiveVisitRecordCount()));
        mPlanLeft.setText(String.format("今日拜访计划%s个", resp.getVisitPlanCount()));
        mPlanRight.setText(String.format("已完成%s个", resp.getActiveVisitPlanCount()));
    }
}
