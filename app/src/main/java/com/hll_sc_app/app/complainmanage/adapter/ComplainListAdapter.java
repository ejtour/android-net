package com.hll_sc_app.app.complainmanage.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplainListAdapter extends BaseQuickAdapter<ComplainListResp.ComplainListBean, BaseViewHolder> {
    private boolean isCheck = false;
    private Map<String, ComplainListResp.ComplainListBean> selectedBeanMap;

    public ComplainListAdapter(@Nullable List<ComplainListResp.ComplainListBean> data) {
        super(R.layout.list_item_comlain_manage_list, data);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComplainListResp.ComplainListBean item) {
        helper.setText(R.id.txt_shop_name, item.getPurchaserShopName())
                .setText(R.id.txt_group_name, item.getPurchaserName())
                .setText(R.id.txt_status, getStatus(item.getStatus()))
                .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"))
                .setText(R.id.txt_complain_type, "投诉类型:" + item.getTypeName())
                .setText(R.id.txt_complain_reason, "投诉原因:" + item.getReasonName());

        GlideImageView glideImageView = helper.getView(R.id.glide_img);
        glideImageView.setImageURL(item.getGroupLogoUrl());
        /*退款tip*/
        TextView mTxtTip = helper.getView(R.id.txt_tip);
        mTxtTip.setVisibility(item.getSourceBusiness() == 2 ? View.VISIBLE : View.GONE);
        /*平台介入tip*/
        TextView mTxtInject = helper.getView(R.id.txt_platform_inject);
        mTxtInject.setVisibility(item.getOperationIntervention() > 0 ? View.VISIBLE : View.GONE);

        /*编辑模式 checkbox*/
        CheckBox checkBox = helper.getView(R.id.checkbox);
        checkBox.setVisibility(isCheck ? View.VISIBLE : View.GONE);
        if (selectedBeanMap != null) {
            checkBox.setChecked(selectedBeanMap.containsKey(item.getId()));
        }

    }

    private String getStatus(int status) {
        switch (status) {
            case 1:
                return "未处理";
            case 2:
                return "已回复";
            case 3:
                return "已结束";
            case 4:
                return "已撤销";
            default:
                return "";
        }
    }

    public void updateSelect(ComplainListResp.ComplainListBean bean) {
        if (selectedBeanMap == null) {
            selectedBeanMap = new HashMap<>();
        }
        if (selectedBeanMap.containsKey(bean.getId())) {
            selectedBeanMap.remove(bean.getId());
        } else {
            selectedBeanMap.put(bean.getId(), bean);
        }
        notifyDataSetChanged();
    }

    public Map<String, ComplainListResp.ComplainListBean> getSelectedBeanMap() {
        return selectedBeanMap;
    }

    public void setSelectedBeanMap(Map<String, ComplainListResp.ComplainListBean> selectedBeanMap) {
        this.selectedBeanMap = selectedBeanMap;
    }

    /**
     * 是否是编辑模式
     * @return
     */
    public boolean isCheckModel() {
        return isCheck;
    }

    public void checkModal(boolean isCheck) {
        this.isCheck = isCheck;
        notifyDataSetChanged();
    }
}
