package com.hll_sc_app.app.report.produce.input;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputAdapter extends BaseQuickAdapter<ProduceDetailBean, BaseViewHolder> {
    private int mOldWidth;

    ProduceInputAdapter() {
        super(R.layout.item_report_produce_input);
        addData(new ProduceDetailBean());
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.rpi_del)
                .addOnClickListener(R.id.rpi_btn);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProduceDetailBean item) {
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
        helper.setText(R.id.rpi_company_name, item.getCoopGroupName())
                .setText(R.id.rpi_money, item.getTotalCost() == null ? "" :
                        "¥" + CommonUtils.formatMoney(CommonUtils.getDouble(item.getTotalCost())));
    }

    @Override
    public void addData(@NonNull ProduceDetailBean data) {
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

    private int getItemPosition(ProduceDetailBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    public void replace(ProduceDetailBean oldData, ProduceDetailBean newData) {
        int position = getItemPosition(oldData);
        if (position == -1 || newData == null) {
            return;
        }
        setData(position, newData);
    }
}
