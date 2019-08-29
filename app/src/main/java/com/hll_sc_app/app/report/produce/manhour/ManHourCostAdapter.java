package com.hll_sc_app.app.report.produce.manhour;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.bean.report.purchase.ManHourReq;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public class ManHourCostAdapter extends BaseQuickAdapter<ManHourBean, BaseViewHolder> {
    ManHourCostAdapter() {
        super(R.layout.item_report_man_hour_cost, Collections.singletonList(new ManHourBean()));
    }

    List<ManHourReq.ManHourReqBean> getReqParams() {
        String groupID = UserConfig.getGroupID();
        List<ManHourReq.ManHourReqBean> list = new ArrayList<>();
        for (ManHourBean bean : getData()) {
            ManHourReq.ManHourReqBean reqBean = new ManHourReq.ManHourReqBean();
            reqBean.setCoopGroupName(bean.getCoopGroupName());
            reqBean.setGroupID(groupID);
            reqBean.setParams(Collections.singletonList(new ManHourReq.ManHourReqParam("1", bean.getValue())));
        }
        return list;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) helper.getView(R.id.mhc_man_hour_cost)).addTextChangedListener(new TextWatcher() {
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
                if (s.toString().startsWith("."))
                    s.insert(0, "0");
                if (!CommonUtils.checkMoneyNum(s.toString()) && s.length() > 1) {
                    s.delete(s.length() - 1, s.length());
                }
                item.setValue(TextUtils.isEmpty(s.toString()) ? "0" : s.toString());
            }
        });

        ((EditText) helper.getView(R.id.mhc_co_company)).addTextChangedListener(new TextWatcher() {
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
                item.setCoopGroupName(s.toString());
            }
        });

        helper.addOnClickListener(R.id.mhc_del);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ManHourBean item) {
        helper.setText(R.id.mhc_co_company, item.getCoopGroupName())
                .setText(R.id.mhc_man_hour_cost, CommonUtils.formatNumber(item.getValue()));
    }
}
