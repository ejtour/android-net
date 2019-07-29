package com.hll_sc_app.app.staffmanage.detail.permission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.staff.RolePermissionResp;
import com.hll_sc_app.bean.staff.WrapperAuthInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 岗位权限简介
 *
 * @author zhuyingsong
 * @date 2018/7/26
 */
public class RolePermissionRightListAdapter extends BaseSectionQuickAdapter<WrapperAuthInfo, BaseViewHolder> {

    RolePermissionRightListAdapter(List<WrapperAuthInfo> data) {
        super(R.layout.item_staff_manage_role_pemission_content, R.layout.item_staff_manage_role_permission_title,
            data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, WrapperAuthInfo item) {
        helper.setText(R.id.txt_purchaserName, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, WrapperAuthInfo item) {
        TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);
        FlowAdapter adapter = new FlowAdapter(mContext, item.t.getSubAuth());
        flowLayout.setAdapter(adapter);
    }

    /**
     * 适配器
     */
    private class FlowAdapter extends TagAdapter<RolePermissionResp.AuthInfo> {
        private LayoutInflater mInflater;

        FlowAdapter(Context context, List<RolePermissionResp.AuthInfo> list) {
            super(list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, RolePermissionResp.AuthInfo s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.view_item_staff_manage_permission_filter, flowLayout,
                false);
            tv.setText(s.getAuthName());
            return tv;
        }
    }
}
