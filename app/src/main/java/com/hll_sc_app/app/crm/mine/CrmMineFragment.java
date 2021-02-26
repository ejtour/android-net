package com.hll_sc_app.app.crm.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.hll_sc_app.bean.mine.MenuItem;
import com.hll_sc_app.widget.mine.MenuGridLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

@Route(path = RouterConfig.CRM_MINE)
public class CrmMineFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.fcm_avatar)
    GlideImageView mAvatar;
    @BindView(R.id.fcm_group_name)
    TextView mGroupName;
    @BindView(R.id.fcm_salesman_info)
    TextView mSalesmanInfo;
    @BindView(R.id.fcm_grid_layout)
    MenuGridLayout mGridLayout;
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
        mGridLayout.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fcm_salesman_code, R.id.fcm_settings, R.id.fcm_info_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fcm_salesman_code:
                RouterUtil.goToActivity(RouterConfig.INFO_INVITE_CODE);
                break;
            case R.id.fcm_settings:
                SettingActivity.start();
                break;
            case R.id.fcm_info_btn:
                InfoActivity.start(requireActivity());
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MenuItem item = (MenuItem) adapter.getItem(position);
        if (item == null) return;
        switch (item) {
            case GOODS_REPO:
                GoodsTemplateListActivity.start(true);
                break;
            case STAFF:
                RouterUtil.goToActivity(RouterConfig.STAFF_LIST);
                break;
            case GOODS_DEMAND:
                GoodsDemandEntryActivity.start();
                break;
            case COMPLIANT:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST);
                break;
            case BILL_LIST:
                RouterUtil.goToActivity(RouterConfig.BILL_LIST);
                break;
            case RETURN_AUDIT:
                AuditActivity.start(0);
                break;
            case INVOICE:
                RouterUtil.goToActivity(RouterConfig.INVOICE_ENTRY);
                break;
            case DELIVERY_ROUTE:
                RouterUtil.goToActivity(RouterConfig.DELIVERY_ROUTE);
                break;
            case REPORT_CENTER:
                MenuActivity.start(ReportMenu.class.getSimpleName());
                break;
            case SALESMAN_RANK:
                RouterUtil.goToActivity(RouterConfig.CRM_RANK);
                break;
            case GOODS_SPECIAL_DEMAND:
                RouterUtil.goToActivity(RouterConfig.GOODS_SPECIAL_DEMAND_ENTRY);
                break;
        }
    }
}
