package com.hll_sc_app.app.info.other;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

public class InfoOtherAdapter extends BaseQuickAdapter<GroupInfoResp.OtherLicensesBean, BaseViewHolder> {

    private final boolean mEditable;
    private final View.OnTouchListener mListener;

    InfoOtherAdapter(boolean editable, View.OnTouchListener listener, List<GroupInfoResp.OtherLicensesBean> data) {
        super(R.layout.item_info_other);
        mEditable = editable;
        mListener = listener;
        if (data == null)
            data = new ArrayList<>();
        if (mEditable && data.size() == 0)
            data.add(new GroupInfoResp.OtherLicensesBean());
        setNewData(data);
    }

    ArrayList<GroupInfoResp.OtherLicensesBean> getUsableData() {
        ArrayList<GroupInfoResp.OtherLicensesBean> list = new ArrayList<>();
        for (GroupInfoResp.OtherLicensesBean bean : mData) {
            if (!TextUtils.isEmpty(bean.getOtherLicenseName()) && bean.getOtherLicenseType() > 0) {
                list.add(bean);
            }
        }
        return list;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        if (mEditable) {
            helper.addOnClickListener(R.id.iio_type)
                    .addOnClickListener(R.id.iio_del);
        }
        helper.setGone(R.id.iio_del, mEditable)
                .setTextColor(R.id.iio_type_label, ContextCompat.getColor(parent.getContext(), mEditable ? R.color.color_666666 : R.color.color_222222))
                .setTextColor(R.id.iio_type, ContextCompat.getColor(parent.getContext(), mEditable ? R.color.color_222222 : R.color.color_666666));
        TextView type = helper.getView(R.id.iio_type);
        type.setCompoundDrawablesWithIntrinsicBounds(0, 0, mEditable ? R.drawable.ic_arrow_gray : 0, 0);
        type.setGravity(mEditable ? Gravity.RIGHT | Gravity.CENTER_VERTICAL : Gravity.LEFT | Gravity.CENTER_VERTICAL);
        ImgUploadBlock block = helper.getView(R.id.iio_pic);
        block.setEditable(mEditable);
        block.setOnDeleteListener(v -> {
            int position = helper.getAdapterPosition();
            GroupInfoResp.OtherLicensesBean bean = mData.get(position);
            bean.setOtherLicenseName(null);
            notifyItemChanged(position);
        });
        block.setOnTouchListener(mListener);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupInfoResp.OtherLicensesBean item) {
        int position = mData.indexOf(item);
        ImgUploadBlock block = helper.setText(R.id.iio_type, getLicenseName(item.getOtherLicenseType()))
                .setText(R.id.iio_no, String.format("其他证照%s", position + 1))
                .getView(R.id.iio_pic);
        block.showImage(item.getOtherLicenseName());
        block.setTag(position);
    }

    private String getLicenseName(int type) {
        switch (type) {
            case 1:
                return "税务登记证";
            case 2:
                return "餐饮服务许可证";
            case 3:
                return "食品经营许可证";
            case 4:
                return "组织机构代码证";
            case 5:
                return "民办非企业单位证件照";
            case 6:
                return "其他证件照";
            default:
                return "";
        }
    }
}
