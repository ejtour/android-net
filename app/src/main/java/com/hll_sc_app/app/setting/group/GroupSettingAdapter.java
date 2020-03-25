package com.hll_sc_app.app.setting.group;

import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/12
 */

public class GroupSettingAdapter extends BaseQuickAdapter<GroupParam, BaseViewHolder> {
    private final CompoundButton.OnCheckedChangeListener mListener;

    GroupSettingAdapter(List<GroupParam> data, CompoundButton.OnCheckedChangeListener listener) {
        super(R.layout.item_group_setting, data);
        mListener = listener;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((SwitchButton) helper.getView(R.id.igs_switch)).setOnCheckedChangeListener(mListener);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupParam item) {
        helper.setText(R.id.igs_name, item.getName())
                .setText(R.id.igs_desc, item.getDesc())
                .setTag(R.id.igs_switch, mData.indexOf(item));
        ((SwitchButton) helper.getView(R.id.igs_switch)).setCheckedImmediatelyNoEvent(item.getValue() == 2);
    }
}
