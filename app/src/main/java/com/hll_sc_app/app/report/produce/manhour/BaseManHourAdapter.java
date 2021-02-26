package com.hll_sc_app.app.report.produce.manhour;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.bean.report.purchase.ManHourReq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public abstract class BaseManHourAdapter extends BaseQuickAdapter<ManHourBean, BaseViewHolder> {
    BaseManHourAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
        addData(new ManHourBean());
    }

    private int mOldWidth;

    List<ManHourReq.ManHourReqBean> getReqParams() {
        String groupID = UserConfig.getGroupID();
        List<ManHourReq.ManHourReqBean> list = new ArrayList<>();
        for (ManHourBean bean : getData()) {
            if (emptyValue(bean)) continue;
            ManHourReq.ManHourReqBean reqBean = new ManHourReq.ManHourReqBean();
            reqBean.setCoopGroupName(setGroupName() ? bean.getCoopGroupName() : null);
            reqBean.setGroupID(groupID);
            reqBean.setParams(Collections.singletonList(new ManHourReq.ManHourReqParam(getParamKey(), bean.getValue())));
            list.add(reqBean);
        }
        return list;
    }

    boolean emptyValue(ManHourBean bean) {
        return TextUtils.isEmpty(bean.getValue());
    }

    boolean setGroupName() {
        return false;
    }

    abstract String getParamKey();

    @Override
    protected void convert(BaseViewHolder helper, ManHourBean item) {
        SwipeItemLayout itemView = (SwipeItemLayout) helper.itemView;
        View del = ((ViewGroup) itemView.getChildAt(1)).getChildAt(0);
        ViewGroup.LayoutParams params = del.getLayoutParams();
        if (mOldWidth == 0) mOldWidth = params.width;
        if (mData.size() == 1 && params.width != 0) {
            params.width = 0;
            del.setLayoutParams(params);
        } else if (mData.size() != 1 && params.width != mOldWidth) {
            params.width = mOldWidth;
            del.setLayoutParams(params);
        }
    }

    @Override
    public void addData(@NonNull ManHourBean data) {
        if (mData.size() == 1) {
            mData.add(data);
            notifyDataSetChanged();
        } else super.addData(data);
    }

    @Override
    public void remove(int position) {
        if (mData.size() == 2) {
            mData.remove(position);
            notifyDataSetChanged();
        } else super.remove(position);
    }
}
