package com.hll_sc_app.app.staffmanage.detail.permission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.staff.RolePermissionResp;
import com.hll_sc_app.bean.staff.WrapperAuthInfo;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 岗位权限简介
 *
 * @author zhuyingsong
 * @date 2018/7/26
 */
@Route(path = RouterConfig.STAFF_PERMISSION)
public class RolePermissionActivity extends BaseLoadActivity implements RolePermissionContract.IRolePermissionView {
    @BindView(R.id.recyclerView_left)
    RecyclerView mRecyclerViewLeft;
    @BindView(R.id.recyclerView_right)
    RecyclerView mRecyclerViewRight;
    private RoleListAdapter mLeftAdapter;
    private RolePermissionRightListAdapter mRightAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager_role_permission);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        RolePermissionPresenter presenter = RolePermissionPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mLeftAdapter = new RoleListAdapter();
        mLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            mLeftAdapter.setSelect(position);
            if (mRightAdapter != null) {
                RolePermissionResp resp = (RolePermissionResp) adapter.getItem(position);
                if (resp != null) {
                    mRightAdapter.setNewData(getAuthInfo(resp.getAuths()));
                }
            }
        });
        mRightAdapter = new RolePermissionRightListAdapter(null);
        mRightAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前角色下没有权限").create());
        mRecyclerViewLeft.setAdapter(mLeftAdapter);
        mRecyclerViewRight.setAdapter(mRightAdapter);
    }

    private List<WrapperAuthInfo> getAuthInfo(List<RolePermissionResp.AuthInfo> authInfoList) {
        List<WrapperAuthInfo> list = new ArrayList<>();
        if (CommonUtils.isEmpty(authInfoList)) {
            return list;
        }
        for (RolePermissionResp.AuthInfo info : authInfoList) {
            list.add(new WrapperAuthInfo(true, info.getAuthName()));
            if (CommonUtils.isEmpty(info.getSubAuth())) {
                RolePermissionResp.AuthInfo bean = new RolePermissionResp.AuthInfo();
                bean.setAuthName("无");
                info.setSubAuth(Collections.singletonList(bean));
            }
            list.add(new WrapperAuthInfo(info));
        }
        return list;
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showList(List<RolePermissionResp> list) {
        if (!CommonUtils.isEmpty(list)) {
            list.get(0).setSelect(true);
            mLeftAdapter.setNewData(list);
            mRightAdapter.setNewData(getAuthInfo(list.get(0).getAuths()));
        } else {
            mLeftAdapter.setNewData(null);
            mRightAdapter.setNewData(null);
        }
    }

    private class RoleListAdapter extends BaseQuickAdapter<RolePermissionResp, BaseViewHolder> {

        RoleListAdapter() {
            super(R.layout.item_staff_manage_role);
        }

        @Override
        protected void convert(BaseViewHolder helper, RolePermissionResp item) {
            TextView txtRoleName = helper.getView(R.id.txt_purchaserName);
            txtRoleName.setText(item.getRole() != null ? item.getRole().getRoleName() : null);
            txtRoleName.setSelected(item.isSelect());
            txtRoleName.setBackgroundColor(item.isSelect() ? 0xFFFFFFFF : 0xFFF3F3F3);
        }

        public void setSelect(int position) {
            if (!CommonUtils.isEmpty(getData())) {
                for (RolePermissionResp resp : getData()) {
                    resp.setSelect(false);
                }
                RolePermissionResp resp = getItem(position);
                if (resp != null) {
                    resp.setSelect(true);
                }
                notifyDataSetChanged();
            }
        }
    }
}
