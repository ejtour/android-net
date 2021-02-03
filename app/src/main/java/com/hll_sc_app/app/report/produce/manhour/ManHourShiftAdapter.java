package com.hll_sc_app.app.report.produce.manhour;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
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

public class ManHourShiftAdapter extends BaseManHourAdapter {
    ManHourShiftAdapter() {
        super(R.layout.item_report_man_hour_shift);
    }

    @Override
    List<ManHourReq.ManHourReqBean> getReqParams() {
        List<ManHourReq.ManHourReqParam> list = new ArrayList<>();
        ManHourReq.ManHourReqBean reqBean = null;
        for (ManHourBean bean : getData()) {
            if (emptyValue(bean)) continue;
            if (reqBean == null) {
                reqBean = new ManHourReq.ManHourReqBean();
                reqBean.setCoopGroupName(setGroupName() ? bean.getCoopGroupName() : null);
                reqBean.setGroupID(UserConfig.getGroupID());
                reqBean.setParams(list);
            }
            list.add(new ManHourReq.ManHourReqParam(getParamKey(), bean.getValue()));
        }
        return reqBean == null ? new ArrayList<>() : Collections.singletonList(reqBean);
    }

    @Override
    String getParamKey() {
        return "2";
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) helper.getView(R.id.mhs_shift)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ManHourBean item = getItem(helper.getAdapterPosition());
                if (item == null) return;
                item.setValue(s.toString());
            }
        });
        helper.addOnClickListener(R.id.mhs_del);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ManHourBean item) {
        super.convert(helper, item);
        helper.setText(R.id.mhs_shift, item.getValue());
    }
}
