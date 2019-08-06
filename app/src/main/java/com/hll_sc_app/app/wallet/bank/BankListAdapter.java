package com.hll_sc_app.app.wallet.bank;

import android.graphics.Color;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.wallet.BankBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/2
 */

public class BankListAdapter extends BaseQuickAdapter<BankBean, BaseViewHolder> {
    private String mCurBankNo;
    private int mHorPadding;

    BankListAdapter(String bankNo) {
        super(R.layout.item_single_selection);
        mCurBankNo = bankNo;
        mHorPadding = UIUtils.dip2px(10);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.itemView.setPadding(mHorPadding, 0, mHorPadding, 0);
        holder.itemView.setBackgroundColor(Color.WHITE);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean item) {
        boolean selected = item.getBankNo().equals(mCurBankNo);
        helper.setText(R.id.iss_label, item.getBankName())
                .setGone(R.id.iss_check, selected)
                .getView(R.id.iss_label).setSelected(selected);
    }
}
