package com.hll_sc_app.app.marketingsetting.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;

import java.util.List;

import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_MJ;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_MZ;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_ZJ;


public class MarketingRuleAdapter extends BaseQuickAdapter<RuleListBean, BaseViewHolder> {

    private int mRuleType;

    public MarketingRuleAdapter(@Nullable List<RuleListBean> data) {
        super(R.layout.list_item_marketing_rule, data);
    }

    /**
     * 设置活动类型
     */
    public void setRultType(int type) {
        mRuleType = type;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        EditText mEdtCondition = viewHolder.getView(R.id.edt_condition);
        EditText mEdtValue = viewHolder.getView(R.id.edt_value);
        mEdtCondition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int index = viewHolder.getAdapterPosition();
                RuleListBean ruleListBean = getItem(index);
                if (ruleListBean != null) {
                    ruleListBean.setRuleCondition(s.toString());
                }
            }
        });
        mEdtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int index = viewHolder.getAdapterPosition();
                RuleListBean ruleListBean = getItem(index);
                if (ruleListBean != null) {
                    ruleListBean.setRuleDiscountValue(s.toString());
                }
            }
        });
        viewHolder.addOnClickListener(R.id.img_delete);
        return viewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, RuleListBean item) {
        helper.setText(R.id.txt_value, getValueText(mRuleType))
                .setText(R.id.txt_unit, getUnit(mRuleType))
                .setText(R.id.edt_condition, item.getRuleCondition())
                .setText(R.id.edt_value, item.getRuleDiscountValue());

        EditText mEdtValue = helper.getView(R.id.edt_value);
        mEdtValue.setHint(getHint(mRuleType));
    }

    /**
     * 买赠：送
     * 直降：统一降至
     * 满减：减免
     *
     * @param type
     * @return
     */
    private String getValueText(int type) {
        String sType = String.valueOf(type);
        if (TextUtils.equals(sType, RULE_MZ.getKey())) {
            return "送";
        } else if (TextUtils.equals(sType, RULE_ZJ.getKey())) {
            return "统一降至";
        } else if (TextUtils.equals(sType, RULE_MJ.getKey())) {
            return "减免";
        } else {
            return "";
        }
    }

    /**
     * 买赠：个
     * 直降：元
     * 满减：个
     *
     * @param type
     * @return
     */
    private String getUnit(int type) {
        String sType = String.valueOf(type);
        if (TextUtils.equals(sType, RULE_MZ.getKey())) {
            return "个";
        } else if (TextUtils.equals(sType, RULE_ZJ.getKey())) {
            return "元";
        } else if (TextUtils.equals(sType, RULE_MJ.getKey())) {
            return "个";
        } else {
            return "";
        }
    }

    /**
     * 买赠：填写数量
     * 直降：填写金额
     * 满减：填写数量
     *
     * @param type
     * @return
     */
    private String getHint(int type) {
        String sType = String.valueOf(type);
        if (TextUtils.equals(sType, RULE_MZ.getKey())) {
            return "填写数量";
        } else if (TextUtils.equals(sType, RULE_ZJ.getKey())) {
            return "填写金额";
        } else if (TextUtils.equals(sType, RULE_MJ.getKey())) {
            return "填写数量";
        } else {
            return "";
        }
    }
}
