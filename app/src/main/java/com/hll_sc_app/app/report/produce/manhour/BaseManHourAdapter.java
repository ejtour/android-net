package com.hll_sc_app.app.report.produce.manhour;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.base.utils.UserConfig;
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

    List<ManHourReq.ManHourReqBean> getReqParams() {
        String groupID = UserConfig.getGroupID();
        List<ManHourReq.ManHourReqBean> list = new ArrayList<>();
        for (ManHourBean bean : getData()) {
            if (TextUtils.isEmpty(bean.getValue())) continue;
            ManHourReq.ManHourReqBean reqBean = new ManHourReq.ManHourReqBean();
            reqBean.setCoopGroupName(setGroupName() ? bean.getCoopGroupName() : null);
            reqBean.setGroupID(groupID);
            reqBean.setParams(Collections.singletonList(new ManHourReq.ManHourReqParam(getParamKey(), bean.getValue())));
            list.add(reqBean);
        }
        return list;
    }

    boolean setGroupName() {
        return false;
    }

    abstract String getParamKey();

    @Override
    protected void convert(BaseViewHolder helper, ManHourBean item) {

    }
}
