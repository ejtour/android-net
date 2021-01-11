package com.hll_sc_app.app.crm.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.audit.AuditActivity;
import com.hll_sc_app.app.goods.template.GoodsTemplateListActivity;
import com.hll_sc_app.app.goodsdemand.entry.GoodsDemandEntryActivity;
import com.hll_sc_app.app.info.InfoActivity;
import com.hll_sc_app.app.menu.MenuActivity;
import com.hll_sc_app.app.menu.stratery.ReportMenu;
import com.hll_sc_app.app.setting.SettingActivity;
import com.hll_sc_app.base.BaseFragment;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

@Route(path = RouterConfig.CRM_MINE)
public class CrmMineFragment extends BaseFragment {
    @BindView(R.id.fcm_avatar)
    GlideImageView mAvatar;
    @BindView(R.id.fcm_group_name)
    TextView mGroupName;
    @BindView(R.id.fcm_salesman_info)
    TextView mSalesmanInfo;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        UserBean user = GreenDaoUtils.getUser();
        mAvatar.setImageURL(user.getGroupLogoUrl());
        mGroupName.setText(user.getGroupName());
        mSalesmanInfo.setText(String.format("%s  |  %s", user.getEmployeeName(), user.getLoginPhone()));
        StatusBarUtil.fitSystemWindowsWithMarginTop(mAvatar);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fcm_salesman_code, R.id.fcm_product_lib, R.id.fcm_staff_manage,
            R.id.fcm_new_product_feedback, R.id.fcm_complaint_manage, R.id.fcm_bill_list,
            R.id.fcm_refund, R.id.fcm_invoice_center, R.id.fcm_delivery_route, R.id.fcm_report_center,
            R.id.fcm_salesman_rank, R.id.fcm_product_special_demand, R.id.fcm_settings, R.id.fcm_info_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fcm_salesman_code:
                RouterUtil.goToActivity(RouterConfig.INFO_INVITE_CODE);
                break;
            case R.id.fcm_product_lib:
                GoodsTemplateListActivity.start(true);
                break;
            case R.id.fcm_staff_manage:
                RouterUtil.goToActivity(RouterConfig.STAFF_LIST);
                break;
            case R.id.fcm_new_product_feedback:
                GoodsDemandEntryActivity.start();
                break;
            case R.id.fcm_complaint_manage:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST);
                break;
            case R.id.fcm_bill_list:
                RouterUtil.goToActivity(RouterConfig.BILL_LIST);
                break;
            case R.id.fcm_refund:
                AuditActivity.start(0);
                break;
            case R.id.fcm_invoice_center:
                RouterUtil.goToActivity(RouterConfig.INVOICE_ENTRY);
                break;
            case R.id.fcm_delivery_route:
                RouterUtil.goToActivity(RouterConfig.DELIVERY_ROUTE);
                break;
            case R.id.fcm_report_center:
                MenuActivity.start(ReportMenu.class.getSimpleName());
                break;
            case R.id.fcm_salesman_rank:
                RouterUtil.goToActivity(RouterConfig.CRM_RANK);
                break;
            case R.id.fcm_product_special_demand:
                RouterUtil.goToActivity(RouterConfig.GOODS_SPECIAL_DEMAND_ENTRY);
                break;
            case R.id.fcm_settings:
                SettingActivity.start();
                break;
            case R.id.fcm_info_btn:
                InfoActivity.start(requireActivity());
                break;
        }
    }
}
